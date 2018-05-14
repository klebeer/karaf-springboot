package ec.devnull.springboot.config;

import ec.devnull.springboot.test.AbstractKarafBootTest;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.ops4j.pax.exam.Configuration;
import org.ops4j.pax.exam.ConfigurationManager;
import org.ops4j.pax.exam.Option;
import org.ops4j.pax.exam.junit.PaxExam;
import org.ops4j.pax.exam.options.MavenArtifactProvisionOption;
import org.ops4j.pax.exam.options.MavenUrlReference;
import org.ops4j.pax.exam.util.Filter;

import javax.inject.Inject;

import java.util.Dictionary;

import static org.ops4j.pax.exam.CoreOptions.*;

/**
 * @author Kleber Ayala
 */
@RunWith(PaxExam.class)
public class SrpingBootConfigManagerTest extends AbstractKarafBootTest {


    protected ConfigurationManager cm = new ConfigurationManager();
    @Inject
    @Filter("(&(objectclass=ec.devnull.springboot.config.ConfigService)(name=test))")
    private ConfigService configService;
    private String karafVersion = cm.getProperty("karaf.version");
    private MavenUrlReference karafUrl = maven().groupId("org.apache.karaf").artifactId("apache-karaf-minimal")
            .version(karafVersion).type("tar.gz");

    @Configuration
    public Option[] config() {

        return options(
                karafDefaults(),
                applyConfig("karaf.springboot-test.cfg"),
                mvnBundle("ec.devnull", "karafboot-configuration")
        );
    }

    @Test
    public void getPropertiesTest() {
        Dictionary dictionary=configService.getProperties();
        Assert.assertNotNull(dictionary);
    }

    protected MavenArtifactProvisionOption mvnBundle(String groupId, String artifactId) {
        return mavenBundle(groupId, artifactId).versionAsInProject();
    }
}
