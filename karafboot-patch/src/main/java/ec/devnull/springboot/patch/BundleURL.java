package ec.devnull.springboot.patch;

import ec.devnull.aether.KarafBootMaven;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import java.io.IOException;
import java.net.URL;
import java.util.Set;

/**
 * @author Kleber Ayala
 */
@Slf4j
public class BundleURL extends PathMatchingResourcePatternResolver {


    public Set<Resource> findResources(Resource rootDirResource, String subPattern) throws IOException {
        java.io.File rootDir;
        try {
            KarafBootMaven karafBootMaven = new KarafBootMaven();
            org.osgi.framework.BundleContext bundleContext = org.osgi.framework.FrameworkUtil.getBundle(this.getClass()).getBundleContext();
            String location = bundleContext.getBundle().getLocation();
            org.eclipse.aether.artifact.Artifact artifact = karafBootMaven.bundleResolve(location);
            rootDir = artifact.getFile();
            String fixedPath = "file:".concat(rootDir.getAbsolutePath()).concat("!").concat(rootDirResource.getURL().getFile());


            URL fixedUrl = new URL("jar", "", -1, fixedPath);
            Resource karafResource = new UrlResource(fixedUrl);

            return doFindPathMatchingJarResources(karafResource, karafResource.getURL(), subPattern);

        } catch (Exception ex) {
            if (log.isWarnEnabled()) {
                log.warn("Cannot search for matching files underneath " + rootDirResource +
                        " because it does not correspond to a directory in the file system", ex);
            }
            return java.util.Collections.emptySet();
        }

    }

}
