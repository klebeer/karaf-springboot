package ec.devnull.karafboot.app.test;

import ec.devnull.dummy.service.DummyService;
import ec.devnull.karafboot.config.ConfigService;
import ec.devnull.karafboot.dummy.service.RestClientService;
import ec.devnull.karafboot.test.AbstractKarafBootTest;
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
    @Filter("(&(objectclass=ec.devnull.karafboot.config.ConfigService)(name=ec.devnull.rest.springboot.app.JWTRestApplicationDummy))")
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
                mvnBundle("javax.annotation", "javax.annotation-api"),
                mvnBundle("org.apache.servicemix.specs", "org.apache.servicemix.specs.saaj-api-1.3"),
                mvnBundle("org.apache.servicemix.specs", "org.apache.servicemix.specs.jaxws-api-2.2"),
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
        Assert.assertEquals("ec.devnull.rest.springboot.app.JWTRestApplicationDummy", dictionary.get("name"));
    }


    @Test
    public void springBootInstalledTest() {
        Assert.assertNotNull(restClientService);
        Assert.assertNotNull(dummyService);
        Optional<String> jwt = Optional.empty();
        try {
            jwt = restClientService.login("601f1889667efaebb33b8c12572835da3f027f78");
        } catch (Exception e) {
            //el bundle aun no esta activo
            try {
                Thread.sleep(20000);
            } catch (InterruptedException e1) {
            }
            jwt = restClientService.login("601f1889667efaebb33b8c12572835da3f027f78");
        }

        Assert.assertThat(jwt.get(), CoreMatchers.containsString("Bearer"));
        System.out.println("JSON WEB TOKEN-->".concat(jwt.get()));
    }

}

