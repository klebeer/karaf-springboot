package ec.devnull.karafboot.plugin;

import org.apache.maven.artifact.DependencyResolutionRequiredException;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.*;
import org.apache.maven.plugins.shade.Shader;
import org.apache.maven.plugins.shade.mojo.ArchiveFilter;
import org.apache.maven.plugins.shade.mojo.ArtifactSet;
import org.apache.maven.plugins.shade.mojo.ShadeMojo;
import org.apache.maven.plugins.shade.resource.AppendingTransformer;
import org.apache.maven.plugins.shade.resource.ManifestResourceTransformer;
import org.apache.maven.plugins.shade.resource.ResourceTransformer;
import org.apache.maven.plugins.shade.resource.ServicesResourceTransformer;
import org.apache.maven.project.MavenProject;

import java.io.File;
import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Kleber Ayala
 */
@Mojo(name = "patch", defaultPhase = LifecyclePhase.PACKAGE, threadSafe = true, requiresDependencyResolution = ResolutionScope.RUNTIME)
public class KarafBootPatchMojo extends AbstractMojo {


    @Parameter(property = "start-class", defaultValue = "${start-class}")
    private String startClass;

    @Component(hint = "default", role = Shader.class)
    private Shader shader;

    @Parameter(defaultValue = "${project}")
    private MavenProject project;

    @Parameter(defaultValue = "${project.artifactId}")
    private String shadedArtifactId;

    @Parameter(property = "outputDirectory", defaultValue = "${project.build.outputDirectory}")
    private File outputDirectory;


    @Parameter(defaultValue = "shaded")
    private String shadedClassifierName;


    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        try {
            shade();
        } catch (Exception e) {
            throw new MojoExecutionException("Error creating Karaf Boot jar: " + e.getMessage(), e);
        }
    }

    private void shade() throws IllegalAccessException, MojoExecutionException {


        ShadeMojo shadeMojo = new ShadeMojo();

        Field reducedPomFiled = findUnderlying(ShadeMojo.class, "createDependencyReducedPom");
        reducedPomFiled.setAccessible(true);
        reducedPomFiled.set(shadeMojo, Boolean.FALSE);

        ArtifactSet artifactSet = new ArtifactSet();

        Set<String> excludes = new HashSet();
        excludes.add("org.osgi:*");


        ArchiveFilter filter = new ArchiveFilter();

        Field artifactField = findUnderlying(ArchiveFilter.class, "artifact");
        artifactField.setAccessible(true);
        artifactField.set(filter, "*:*");


        Field artifactExcludesField = findUnderlying(ArchiveFilter.class, "excludes");
        artifactExcludesField.setAccessible(true);
        artifactExcludesField.set(filter, new HashSet<>());

        filter.getExcludes().add("META-INF/*.SF");
        filter.getExcludes().add("META-INF/*.DSA");
        filter.getExcludes().add("META-INF/*.RSA");


        Field filtersFiled = findUnderlying(ShadeMojo.class, "filters");
        filtersFiled.setAccessible(true);
        filtersFiled.set(shadeMojo, new ArchiveFilter[]{filter});


        Field excludesField = findUnderlying(ArtifactSet.class, "excludes");
        excludesField.setAccessible(true);
        excludesField.set(artifactSet, excludes);


        Field artifactSetField = findUnderlying(ShadeMojo.class, "artifactSet");
        artifactSetField.setAccessible(true);
        artifactSetField.set(shadeMojo, artifactSet);


        ResourceTransformer[] transformers = new ResourceTransformer[5];

        ResourceTransformer springHandlers = new AppendingTransformer();
        Field springHandlersFiled = findUnderlying(AppendingTransformer.class, "resource");
        springHandlersFiled.setAccessible(true);
        springHandlersFiled.set(springHandlers, "META-INF/spring.handlers");

        transformers[0] = springHandlers;


        ResourceTransformer springFactory = new PropertiesMergingResourceTransformer();
        Field springFactoryFiled = findUnderlying(PropertiesMergingResourceTransformer.class, "resource");
        springFactoryFiled.setAccessible(true);
        springFactoryFiled.set(springFactory, "META-INF/spring.factories");

        transformers[1] = springFactory;


        ResourceTransformer springSchemas = new AppendingTransformer();
        Field springSchemasFiled = findUnderlying(AppendingTransformer.class, "resource");
        springSchemasFiled.setAccessible(true);
        springSchemasFiled.set(springSchemas, "META-INF/spring.schemas");

        transformers[2] = springSchemas;


        ResourceTransformer springResource = new ServicesResourceTransformer();

        transformers[3] = springResource;


        ResourceTransformer springManifest = new ManifestResourceTransformer();
        Field springManifestFiled = findUnderlying(ManifestResourceTransformer.class, "mainClass");
        springManifestFiled.setAccessible(true);
        springManifestFiled.set(springManifest, startClass);

        transformers[4] = springManifest;


        Field transformersField = findUnderlying(ShadeMojo.class, "transformers");
        transformersField.setAccessible(true);
        transformersField.set(shadeMojo, transformers);


        Field projectField = findUnderlying(ShadeMojo.class, "project");
        projectField.setAccessible(true);
        projectField.set(shadeMojo, project);


        Field shaderField = findUnderlying(ShadeMojo.class, "shader");
        shaderField.setAccessible(true);
        shaderField.set(shadeMojo, shader);


        Field shadedArtifactIdField = findUnderlying(ShadeMojo.class, "shadedArtifactId");
        shadedArtifactIdField.setAccessible(true);
        shadedArtifactIdField.set(shadeMojo, shadedArtifactId);


        Field outputDirectoryField = findUnderlying(ShadeMojo.class, "outputDirectory");
        outputDirectoryField.setAccessible(true);
        outputDirectoryField.set(shadeMojo, outputDirectory);


        Field shadedClassifierNameField = findUnderlying(ShadeMojo.class, "shadedClassifierName");
        shadedClassifierNameField.setAccessible(true);
        shadedClassifierNameField.set(shadeMojo, shadedClassifierName);

        shadeMojo.execute();

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
