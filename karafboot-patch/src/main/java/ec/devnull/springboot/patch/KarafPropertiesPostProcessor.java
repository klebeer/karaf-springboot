package ec.devnull.springboot.patch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Kleber Ayala
 */
public class KarafPropertiesPostProcessor implements EnvironmentPostProcessor {


    private static final String PROPERTY_SOURCE_NAME = "karafProperties";

    private String[] KEYS = {
            "excel.threads",
            "cronDelay",
            "cronDelayEmail",
            "spring.mail.username",
            "spring.mail.password",
            "spring.mail.host",
            "spring.mail.port",
            "spring.mail.properties.mail.transport.protocol",
            "spring.mail.properties.mail.smtp.auth",
            "spring.mail.properties.mail.smtp.starttls.enabled",
            "spring.mail.properties.mail.debug",
            "spring.mail.properties.mail.smtp.starttls.required",
            "spring.mail.properties.mail.socketFactory.port",
            "spring.mail.properties.mail.socketFactory.class",
            "spring.mail.properties.mail.socketFactory.fallback",
            "white.executor.threads",
            "white.search.threads",
            "lot.sync.threads",
            "lot.async.threads",
            "lot.soap.threads",
            "excel.async.threads",
            "kpi.threads",
            "upload.threads"
    };

    /**
     * Adds Spring Environment custom logic. This custom logic fetch properties from database and setting highest precedence
     */
    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {

        Map<String, Object> propertySource = new HashMap<>();


//
//                    propertySource.put(key, rs.getString("value"));
//
//
//                rs.close();
//                preparedStatement.clearParameters();


        // Create a custom property source with the highest precedence and add it to Spring Environment
        environment.getPropertySources().addFirst(new MapPropertySource(PROPERTY_SOURCE_NAME, propertySource));

//        } catch (Throwable e) {
//            throw new RuntimeException(e);
//        }
    }
}