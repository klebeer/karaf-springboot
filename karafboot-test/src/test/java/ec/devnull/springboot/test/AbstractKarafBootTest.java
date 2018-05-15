package ec.devnull.springboot.test;


import org.apache.karaf.features.FeaturesService;
import org.ops4j.pax.exam.ConfigurationManager;
import org.ops4j.pax.exam.Option;
import org.ops4j.pax.exam.options.MavenArtifactProvisionOption;
import org.ops4j.pax.exam.options.MavenUrlReference;

import javax.inject.Inject;
import java.io.File;

import static org.junit.Assert.assertTrue;
import static org.ops4j.pax.exam.CoreOptions.*;
import static org.ops4j.pax.exam.karaf.options.KarafDistributionOption.*;

/**
 * @author Kleber Ayala
 */
public class AbstractKarafBootTest {

    protected ConfigurationManager cm = new ConfigurationManager();
    @Inject
    private FeaturesService featuresService;
    private String karafVersion = cm.getProperty("karaf.version");

    private MavenUrlReference karafUrl = maven().
            groupId("org.apache.karaf").
            artifactId("apache-karaf-minimal").
            version(karafVersion).type("tar.gz");

    protected void assertFeatureInstalled(String featureName) throws Exception {
        assertTrue("Required feature " + featureName + " not installed",
                featuresService.isInstalled(featuresService.getFeature(featureName)));
    }

    protected Option applyConfig(String configName) {
        return replaceConfigurationFile("etc/" + configName, new File("src/test/resources/" + configName));
    }

    protected MavenArtifactProvisionOption mvnBundle(String groupId, String artifactId) {
        return mavenBundle(groupId, artifactId).versionAsInProject();
    }

    protected Option karafDefaults() {
        return composite(
                karafDistributionConfiguration().frameworkUrl(karafUrl)
                        .unpackDirectory(new File("target/exam")).useDeployFolder(false), //
                configureConsole(),
                keepRuntimeFolder());
    }
}
