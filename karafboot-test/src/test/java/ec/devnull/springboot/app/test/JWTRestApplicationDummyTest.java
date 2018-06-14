package ec.devnull.springboot.app.test;

import ec.devnull.springboot.config.ConfigService;
import ec.devnull.dummy.service.DummyService;

import ec.devnull.springboot.dummy.service.RestClientService;
import ec.devnull.springboot.test.AbstractKarafBootTest;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.ops4j.pax.exam.Configuration;
import org.ops4j.pax.exam.Option;
import org.ops4j.pax.exam.junit.PaxExam;
import org.ops4j.pax.exam.spi.reactors.ExamReactorStrategy;
import org.ops4j.pax.exam.spi.reactors.PerClass;
import org.ops4j.pax.exam.util.Filter;

import javax.inject.Inject;
import java.util.Dictionary;
import java.util.Optional;

import static org.ops4j.pax.exam.CoreOptions.options;

/**
 * @author Kleber Ayala
 */
@RunWith(PaxExam.class)
@ExamReactorStrategy(PerClass.class)
public class JWTRestApplicationDummyTest extends AbstractKarafBootTest {


    @Inject
    @Filter("(&(objectclass=ec.devnull.springboot.config.ConfigService)(name=test))")
    private ConfigService configService;

    @Inject
    private RestClientService restClientService;

    @Inject
    private DummyService dummyService;

    @Configuration
    public Option[] config() {
        return options(
                karafDefaults(),

                applyConfig("karaf.springboot-test.cfg"),

                mvnBundle("ec.devnull", "dummy-bundle-service"),
                mvnBundle("ec.devnull", "karafboot-configuration"),
                mvnBundle("ec.devnull", "karafboot-testapp"),
                mvnBundle("ec.devnull", "dummy-bundle")


        );
    }

    @Test
    public void getPropertiesTest() {
        Dictionary dictionary = configService.getProperties();
        Assert.assertNotNull(dictionary);
        Assert.assertEquals("test", dictionary.get("name"));
    }


    @Test
    public void springBootInstalledTest() {
        Assert.assertNotNull(restClientService);
        Assert.assertNotNull(dummyService);

        Optional<String> jwt = restClientService.login("601f1889667efaebb33b8c12572835da3f027f78");

        Assert.assertThat(jwt.get(), CoreMatchers.containsString("Bearer"));
        System.out.println("JSON WEB TOKEN-->".concat(jwt.get()));
    }

}

