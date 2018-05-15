package ec.devnull.springboot.config.test;

import ec.devnull.springboot.config.ConfigService;
import ec.devnull.springboot.test.AbstractKarafBootTest;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.ops4j.pax.exam.Configuration;
import org.ops4j.pax.exam.Option;
import org.ops4j.pax.exam.junit.PaxExam;
import org.ops4j.pax.exam.spi.reactors.ExamReactorStrategy;
import org.ops4j.pax.exam.spi.reactors.PerMethod;
import org.ops4j.pax.exam.util.Filter;

import javax.inject.Inject;
import java.util.Dictionary;

import static org.ops4j.pax.exam.CoreOptions.options;

/**
 * @author Kleber Ayala
 */
@RunWith(PaxExam.class)
@ExamReactorStrategy(PerMethod.class)
public class SrpingBootConfigManagerTest extends AbstractKarafBootTest {


    @Inject
    @Filter("(&(objectclass=ec.devnull.springboot.config.ConfigService)(name=test))")
    private ConfigService configService;

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
        Dictionary dictionary = configService.getProperties();
        Assert.assertNotNull(dictionary);
        Assert.assertEquals("test", dictionary.get("name"));
    }

}
