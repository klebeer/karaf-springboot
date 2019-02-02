package ec.devnull.aether;

import lombok.extern.slf4j.Slf4j;
import org.apache.maven.artifact.repository.ArtifactRepository;
import org.apache.maven.repository.internal.MavenRepositorySystemUtils;
import org.eclipse.aether.DefaultRepositorySystemSession;
import org.eclipse.aether.RepositorySystem;
import org.eclipse.aether.RepositorySystemSession;
import org.eclipse.aether.artifact.Artifact;
import org.eclipse.aether.artifact.DefaultArtifact;
import org.eclipse.aether.connector.basic.BasicRepositoryConnectorFactory;
import org.eclipse.aether.impl.DefaultServiceLocator;
import org.eclipse.aether.repository.LocalRepository;
import org.eclipse.aether.repository.RemoteRepository;
import org.eclipse.aether.resolution.ArtifactRequest;
import org.eclipse.aether.resolution.ArtifactResolutionException;
import org.eclipse.aether.resolution.ArtifactResult;
import org.eclipse.aether.spi.connector.RepositoryConnectorFactory;
import org.eclipse.aether.spi.connector.transport.TransporterFactory;
import org.eclipse.aether.transport.file.FileTransporterFactory;
import org.eclipse.aether.transport.http.HttpTransporterFactory;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * @author Kleber Ayala
 */
@Slf4j
public class KarafBootMaven {

    private final static String USER_HOME = System.getProperty("user.home");
    private final static String REPO_LOCAL = USER_HOME + File.separator + ".m2" + File.separator + "repository";
    private static String KARAF_ETC = System.getProperty("karaf.etc");
    private static String REPO = "org.ops4j.pax.url.mvn.cfg";
    private static String REPO_PARAM = "org.ops4j.pax.url.mvn.repositories";

    private List<ArtifactRepository> artifactRepositoryList;


    public KarafBootMaven() {
        //x defecto
    }

    public KarafBootMaven(List<ArtifactRepository> artifactRepositoryList) {
        this.artifactRepositoryList = artifactRepositoryList;
    }


    public Artifact resolve(String groupId, String artifactId, String version) throws ArtifactResolutionException {
        RepositorySystem system = getRepositorySystem();
        RepositorySystemSession session = getRepositorySystemSession(system);
        ArtifactRequest artifactRequest = new ArtifactRequest();
        artifactRequest.setRepositories(getCurrentRepo());
        String artifactKey = groupId.concat(":").concat(artifactId).concat(":").concat(version);
        Artifact artifact = new DefaultArtifact(artifactKey);
        artifactRequest.setArtifact(artifact);
        ArtifactResult artifactResult = system.resolveArtifact(session, artifactRequest);
        artifact = artifactResult.getArtifact();
        return artifact;
    }


    private List<RemoteRepository> getCurrentRepo() {
        if (artifactRepositoryList == null) {
            return reposKaraf();
        } else {
            return repos();
        }
    }

    public Artifact bundleResolve(String bundleLocation) throws ArtifactResolutionException {
        bundleLocation = bundleLocation.substring(4, bundleLocation.length());
        String groupId = bundleLocation.substring(0, bundleLocation.indexOf("/"));
        bundleLocation = bundleLocation.substring(bundleLocation.indexOf("/") + 1, bundleLocation.length());
        String artifactId = bundleLocation.substring(0, bundleLocation.indexOf("/"));

        bundleLocation = bundleLocation.substring(bundleLocation.indexOf("/") + 1, bundleLocation.length());
        String version = bundleLocation.substring(0, bundleLocation.length());

        return resolve(groupId, artifactId, version);
    }

    private RepositorySystem getRepositorySystem() {
        DefaultServiceLocator serviceLocator = MavenRepositorySystemUtils.newServiceLocator();
        serviceLocator
                .addService(RepositoryConnectorFactory.class, BasicRepositoryConnectorFactory.class);
        serviceLocator.addService(TransporterFactory.class, FileTransporterFactory.class);
        serviceLocator.addService(TransporterFactory.class, HttpTransporterFactory.class);

        serviceLocator.setErrorHandler(new DefaultServiceLocator.ErrorHandler() {
            @Override
            public void serviceCreationFailed(Class<?> type, Class<?> impl, Throwable exception) {
                exception.printStackTrace();
            }
        });

        return serviceLocator.getService(RepositorySystem.class);
    }

    private DefaultRepositorySystemSession getRepositorySystemSession(RepositorySystem system) {
        DefaultRepositorySystemSession session = MavenRepositorySystemUtils.newSession();

        LocalRepository localRepo = new LocalRepository(REPO_LOCAL);
        session.setLocalRepositoryManager(system.newLocalRepositoryManager(session, localRepo));

        session.setTransferListener(new ConsoleTransferListener());

        return session;
    }


    private RemoteRepository getCentralMavenRepository() {
        return new RemoteRepository.Builder("central", "default", "http://central.maven.org/maven2/")
                .build();
    }

    private RemoteRepository newCentralRepository() {
        return new RemoteRepository.Builder("central", "default", "http://central.maven.org/maven2/").build();
    }

    private List<RemoteRepository> repos() {
        List<RemoteRepository> repositories = new ArrayList<>();
        for (ArtifactRepository artifactRepository : artifactRepositoryList) {
            repositories.add((new RemoteRepository.Builder(artifactRepository.getId(), "default", artifactRepository.getUrl()).build()));
        }
        return repositories;
    }

    private List<RemoteRepository> reposKaraf() {
        List<RemoteRepository> repositories = new ArrayList<>();
        try {
            File cfgRepo = new File(KARAF_ETC + File.separator + REPO);
            FileInputStream fis = null;

            fis = new FileInputStream(cfgRepo);

            Properties properties = new Properties();
            properties.load(fis);
            String[] repos = properties.getProperty(REPO_PARAM).split(",");
            for (int i = 0; i < repos.length; i++) {
                if (!repos[i].contains("snapshots")) {
                    String repo = getRepo(repos[i]);
                    repositories.add((new RemoteRepository.Builder("repo" + i, "default", repo).build()));
                }
            }
        } catch (Exception e) {
            log.error("Error al resolver los repos de karaf", e);
        }
        return repositories;
    }

    private String getRepo(String repoUri) {
        if (repoUri.contains("@id")) {
            repoUri = repoUri.substring(0, repoUri.indexOf("@id"));
        }
        return repoUri;
    }
}


