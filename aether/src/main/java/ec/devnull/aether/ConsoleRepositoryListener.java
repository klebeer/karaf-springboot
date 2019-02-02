package ec.devnull.aether;

import lombok.extern.slf4j.Slf4j;
import org.eclipse.aether.AbstractRepositoryListener;
import org.eclipse.aether.RepositoryEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by mauchilan on 10/9/15.
 */
@Slf4j
public class ConsoleRepositoryListener extends AbstractRepositoryListener {



    public void artifactDeployed(RepositoryEvent event) {
        log.debug("Deployed " + event.getArtifact() + " to " + event.getRepository());
    }

    public void artifactDeploying(RepositoryEvent event) {
        log.debug("Deploying " + event.getArtifact() + " to " + event.getRepository());
    }

    public void artifactDescriptorInvalid(RepositoryEvent event) {
        log.debug("Invalid artifact descriptor for " + event.getArtifact() + ": "
                + event.getException().getMessage());
    }

    public void artifactDescriptorMissing(RepositoryEvent event) {
        log.debug("Missing artifact descriptor for " + event.getArtifact());
    }

    public void artifactInstalled(RepositoryEvent event) {
        log.debug("Installed " + event.getArtifact() + " to " + event.getFile());
    }

    public void artifactInstalling(RepositoryEvent event) {
        log.debug("Installing " + event.getArtifact() + " to " + event.getFile());
    }

    public void artifactResolved(RepositoryEvent event) {
        log.debug("Resolved artifact " + event.getArtifact() + " from " + event.getRepository());
    }

    public void artifactDownloading(RepositoryEvent event) {
        log.debug("Downloading artifact " + event.getArtifact() + " from " + event.getRepository());
    }

    public void artifactDownloaded(RepositoryEvent event) {
        log.debug("Downloaded artifact " + event.getArtifact() + " from " + event.getRepository());
    }

    public void artifactResolving(RepositoryEvent event) {
        log.info("Resolving artifact " + event.getArtifact());
    }

    public void metadataDeployed(RepositoryEvent event) {
        log.debug("Deployed " + event.getMetadata() + " to " + event.getRepository());
    }

    public void metadataDeploying(RepositoryEvent event) {
        log.debug("Deploying " + event.getMetadata() + " to " + event.getRepository());
    }

    public void metadataInstalled(RepositoryEvent event) {
        log.debug("Installed " + event.getMetadata() + " to " + event.getFile());
    }

    public void metadataInstalling(RepositoryEvent event) {
        log.debug("Installing " + event.getMetadata() + " to " + event.getFile());
    }

    public void metadataInvalid(RepositoryEvent event) {
        log.info("Invalid metadata " + event.getMetadata());
    }

    public void metadataResolved(RepositoryEvent event) {
        log.debug("Resolved metadata " + event.getMetadata() + " from " + event.getRepository());
    }

    public void metadataResolving(RepositoryEvent event) {
        log.debug("Resolving metadata " + event.getMetadata() + " from " + event.getRepository());
    }
}
