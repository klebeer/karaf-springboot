package ec.devnull.aether.test;

import ec.devnull.aether.KarafBootMaven;
import org.eclipse.aether.artifact.Artifact;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Kleber Ayala
 */
public class KarafBootMavenTest {

    @Before
    public void setUp() {
        String etcHome = this.getClass().getResource("/").getFile();
        System.setProperty("karaf.etc", etcHome);
    }

    @Test
    public void bundleResolveTest() {
        KarafBootMaven karafBootMaven = new KarafBootMaven();
        String bundle = "mvn:org.javassist/javassist/3.22.0-GA";
        try {
            Artifact artifact = karafBootMaven.bundleResolve(bundle);
            Assert.assertNotNull(artifact);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
