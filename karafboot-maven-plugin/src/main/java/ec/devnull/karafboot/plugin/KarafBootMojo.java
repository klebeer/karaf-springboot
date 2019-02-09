package ec.devnull.karafboot.plugin;

import ec.devnull.aether.KarafBootMaven;
import org.apache.maven.artifact.Artifact;
import org.apache.maven.artifact.factory.DefaultArtifactFactory;
import org.apache.maven.artifact.handler.manager.ArtifactHandlerManager;
import org.apache.maven.artifact.repository.ArtifactRepository;
import org.apache.maven.execution.MavenSession;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Component;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.plugins.dependency.fromConfiguration.ArtifactItem;
import org.apache.maven.plugins.dependency.fromConfiguration.UnpackMojo;
import org.apache.maven.project.MavenProject;
import org.codehaus.mojo.exec.ExecJavaMojo;
import org.codehaus.plexus.archiver.manager.ArchiverManager;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Kleber Ayala
 */
@Mojo(name = "prepare", defaultPhase = LifecyclePhase.PROCESS_SOURCES, requiresProject = false, threadSafe = true)
public class KarafBootMojo extends AbstractMojo {


    @Parameter(defaultValue = "${session}", readonly = true, required = true)
    protected MavenSession session;
    @Parameter(defaultValue = "${project}", readonly = true, required = true)
    private MavenProject project;
    @Component
    private ArtifactHandlerManager artifactHandlerManager;

    @Component
    private org.apache.maven.shared.artifact.resolve.ArtifactResolver artifactResolver;

    @Parameter(property = "spring.version", defaultValue = "${spring.version}")
    private String springVersion;


    @Parameter(property = "tomcat.version", defaultValue = "${tomcat.version}")
    private String tomcatVersion;

    @Parameter(defaultValue = "${project.build.outputDirectory}")
    private File markersDirectory;

    @Component
    private ArchiverManager archiverManager;

    @Parameter(property = "outputDirectory", defaultValue = "${project.build.outputDirectory}")
    private File outputDirectory;


    @Parameter(defaultValue = "${plugin.artifacts}")
    private List<Artifact> pluginDependencies;

    @Parameter(property = "karafboot.version", defaultValue = "1.0.0")
    private String projectVersion;


    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {

        try {
            unpack();
            patch();

        } catch (Exception e) {
            throw new MojoExecutionException("Error creating Karaf Boot jar: " + e.getMessage(), e);
        }

    }

    private void unpack() throws
            MojoExecutionException, MojoFailureException, IllegalAccessException {


        UnpackMojo unpackMojo = new UnpackMojo();


        Field sessionFiled = findUnderlying(UnpackMojo.class, "session");
        sessionFiled.setAccessible(true);
        sessionFiled.set(unpackMojo, this.session);


        Field artifactHandlerManagerField = findUnderlying(UnpackMojo.class, "artifactHandlerManager");
        artifactHandlerManagerField.setAccessible(true);
        artifactHandlerManagerField.set(unpackMojo, this.artifactHandlerManager);

        Field artifactResolverrField = findUnderlying(UnpackMojo.class, "artifactResolver");
        artifactResolverrField.setAccessible(true);
        artifactResolverrField.set(unpackMojo, this.artifactResolver);


        Field projectField = findUnderlying(UnpackMojo.class, "project");
        projectField.setAccessible(true);
        projectField.set(unpackMojo, this.project);


        List<ArtifactItem> theArtifactItems = new ArrayList<>();

        ArtifactItem springCore = new ArtifactItem();
        springCore.setGroupId("org.springframework");
        springCore.setArtifactId("spring-core");
        springCore.setVersion(springVersion);

        ArtifactItem tomcatEmbed = new ArtifactItem();
        tomcatEmbed.setGroupId("org.apache.tomcat.embed");
        tomcatEmbed.setArtifactId("tomcat-embed-core");
        tomcatEmbed.setVersion(tomcatVersion);


        theArtifactItems.add(springCore);
        theArtifactItems.add(tomcatEmbed);

        unpackMojo.setArtifactItems(theArtifactItems);
        unpackMojo.setOverWriteReleases(true);

        unpackMojo.setMarkersDirectory(markersDirectory);

        unpackMojo.setArchiverManager(archiverManager);


        unpackMojo.setOutputDirectory(outputDirectory);
        unpackMojo.execute();
    }

    private void patch() throws Exception {

        ExecJavaMojo execJavaMojo = new ExecJavaMojo();
        List<ArtifactRepository> repositories = session.getCurrentProject().getRemoteArtifactRepositories();


        KarafBootMaven karafBootMaven = new KarafBootMaven(repositories);
        org.eclipse.aether.artifact.Artifact javassistAether = karafBootMaven.resolve("org.javassist", "javassist", "3.22.0-GA");
        org.eclipse.aether.artifact.Artifact karafbootPatchAether = karafBootMaven.resolve("ec.devnull", "karafboot", projectVersion);
        org.eclipse.aether.artifact.Artifact hibernateAether = karafBootMaven.resolve("org.hibernate", "hibernate-core", "5.0.12.Final");


        Field mainClass = ExecJavaMojo.class.getDeclaredField("mainClass");
        mainClass.setAccessible(true);
        mainClass.set(execJavaMojo, "ec.devnull.karafboot.JavassistTransformer");

        Field arguments = ExecJavaMojo.class.getDeclaredField("arguments");
        arguments.setAccessible(true);

        String[] args = {outputDirectory.getPath(), "-classpath"};
        arguments.set(execJavaMojo, args);


        Field includePluginDependenciesField = ExecJavaMojo.class.getDeclaredField("includePluginDependencies");
        includePluginDependenciesField.setAccessible(true);
        includePluginDependenciesField.set(execJavaMojo, Boolean.TRUE);


        Field pluginDependenciesField = ExecJavaMojo.class.getDeclaredField("pluginDependencies");
        pluginDependenciesField.setAccessible(true);

        DefaultArtifactFactory defaultArtifactFactory = new DefaultArtifactFactory();


        Field artifactHandlerManagerField = DefaultArtifactFactory.class.getDeclaredField("artifactHandlerManager");
        artifactHandlerManagerField.setAccessible(true);
        artifactHandlerManagerField.set(defaultArtifactFactory, artifactHandlerManager);


        Artifact javassist = defaultArtifactFactory.createArtifact("org.javassist", "javassist", "3.22.0-GA", "compile", "jar");
        Artifact karafbootPatch = defaultArtifactFactory.createArtifact("ec.devnull", "karafboot", projectVersion, "compile", "jar");

        javassist.setFile(javassistAether.getFile());
        karafbootPatch.setFile(karafbootPatchAether.getFile());


        Artifact hibernate = defaultArtifactFactory.createArtifact("org.hibernate", "hibernate-core", "5.0.12.Final", "compile", "jar");
        hibernate.setFile(hibernateAether.getFile());

        List<Artifact> allDependencies = new ArrayList();
        allDependencies.addAll(pluginDependencies);
        allDependencies.add(javassist);
        allDependencies.add(karafbootPatch);
        allDependencies.add(hibernate);

        pluginDependenciesField.set(execJavaMojo, allDependencies);
        execJavaMojo.execute();
    }

    private Field findUnderlying(Class<?> clazz, String fieldName) {
        Class<?> current = clazz;
        do {
            try {
                return current.getDeclaredField(fieldName);
            } catch (Exception e) {
            }
        } while ((current = current.getSuperclass()) != null);
        return null;
    }

}
