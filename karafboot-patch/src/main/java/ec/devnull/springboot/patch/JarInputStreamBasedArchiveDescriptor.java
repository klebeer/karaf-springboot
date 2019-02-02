package ec.devnull.springboot.patch;


import ec.devnull.aether.KarafBootMaven;
import org.eclipse.aether.artifact.Artifact;
import org.hibernate.boot.archive.internal.JarFileBasedArchiveDescriptor;
import org.hibernate.boot.archive.spi.*;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;
import java.util.zip.ZipEntry;

import static org.hibernate.internal.log.UrlMessageBundle.URL_LOGGER;

/**
 * An ArchiveDescriptor implementation that works on archives accessible through a {@link java.util.jar.JarInputStream}.
 * NOTE : This is less efficient implementation than {@link JarFileBasedArchiveDescriptor}
 *
 * @author Emmanuel Bernard
 * @author Steve Ebersole
 */
public class JarInputStreamBasedArchiveDescriptor extends AbstractArchiveDescriptor {

    /**
     * Constructs a JarInputStreamBasedArchiveDescriptor
     *
     * @param archiveDescriptorFactory The factory creating this
     * @param url                      The url to the JAR file
     * @param entry                    The prefix for entries within the JAR url
     */
    public JarInputStreamBasedArchiveDescriptor(
            ArchiveDescriptorFactory archiveDescriptorFactory,
            URL url,
            String entry) {
        super(archiveDescriptorFactory, url, entry);
    }

    @Override
    public void visitArchive(ArchiveContext context) {
        final JarInputStream jarInputStream;
        try {
            KarafBootMaven karafBootMaven = new KarafBootMaven();
            BundleContext bundleContext = FrameworkUtil.getBundle(SpringBootActivator.class).getBundleContext();
            String location = bundleContext.getBundle().getLocation();
            Artifact artifact = karafBootMaven.bundleResolve(location);
            jarInputStream = new JarInputStream(new FileInputStream(artifact.getFile()));

        } catch (Exception e) {
            //really should catch IOException but Eclipse is buggy and raise NPE...
            URL_LOGGER.logUnableToFindFileByUrl(getArchiveUrl(), e);
            return;
        }

        try {
            JarEntry jarEntry;
            while ((jarEntry = jarInputStream.getNextJarEntry()) != null) {
                final String jarEntryName = jarEntry.getName();
                if (getEntryBasePrefix() != null && !jarEntryName.startsWith(getEntryBasePrefix())) {
                    continue;
                }

                if (jarEntry.isDirectory()) {
                    continue;
                }

                if (jarEntryName.equals(getEntryBasePrefix())) {
                    // exact match, might be a nested jar entry (ie from jar:file:..../foo.ear!/bar.jar)
                    //
                    // This algorithm assumes that the zipped file is only the URL root (including entry), not
                    // just any random entry
                    try {
                        final JarInputStream subJarInputStream = new JarInputStream(jarInputStream);
                        try {
                            ZipEntry subZipEntry = jarInputStream.getNextEntry();
                            while (subZipEntry != null) {
                                if (!subZipEntry.isDirectory()) {
                                    final String subName = extractName(subZipEntry);
                                    final InputStreamAccess inputStreamAccess = buildByteBasedInputStreamAccess(subName, subJarInputStream);

                                    final ArchiveEntry entry = new ArchiveEntry() {
                                        @Override
                                        public String getName() {
                                            return subName;
                                        }

                                        @Override
                                        public String getNameWithinArchive() {
                                            return subName;
                                        }

                                        @Override
                                        public InputStreamAccess getStreamAccess() {
                                            return inputStreamAccess;
                                        }
                                    };

                                    context.obtainArchiveEntryHandler(entry).handleEntry(entry, context);
                                }
                                subZipEntry = jarInputStream.getNextJarEntry();
                            }
                        } finally {
                            subJarInputStream.close();
                        }
                    } catch (Exception e) {
                        throw new ArchiveException("Error accessing nested jar", e);
                    }
                } else {
                    final String entryName = extractName(jarEntry);
                    final InputStreamAccess inputStreamAccess
                            = buildByteBasedInputStreamAccess(entryName, jarInputStream);

                    final String relativeName = extractRelativeName(jarEntry);

                    final ArchiveEntry entry = new ArchiveEntry() {
                        @Override
                        public String getName() {
                            return entryName;
                        }

                        @Override
                        public String getNameWithinArchive() {
                            return relativeName;
                        }

                        @Override
                        public InputStreamAccess getStreamAccess() {
                            return inputStreamAccess;
                        }
                    };

                    context.obtainArchiveEntryHandler(entry).handleEntry(entry, context);
                }
            }

            jarInputStream.close();
        } catch (IOException ioe) {
            throw new ArchiveException(
                    String.format("Error accessing JarInputStream [%s]", getArchiveUrl()),
                    ioe
            );
        }
    }
}
