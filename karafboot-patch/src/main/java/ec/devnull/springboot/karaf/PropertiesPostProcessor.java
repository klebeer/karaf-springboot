package ec.devnull.springboot.karaf;

import ec.devnull.springboot.config.ConfigService;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Filter;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.Ordered;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * @author Kleber Ayala
 */
@SuppressWarnings("unchecked")
public class PropertiesPostProcessor implements EnvironmentPostProcessor, Ordered {


    /**
     * Name of the custom property source added by this post processor class
     */
    private static final String PROPERTY_SOURCE_NAME = "karafProperties";

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {


        try {

            Object sourceApp = application.getAllSources().toArray()[0];
            String springClassName = ((Class) sourceApp).getCanonicalName();

            Bundle osgiBundle = FrameworkUtil.getBundle(Class.forName(springClassName));
            if (osgiBundle == null) {
                Map<String, Object> propertySource = loadDefaultProperties();
                environment.getPropertySources().addFirst(new MapPropertySource(PROPERTY_SOURCE_NAME, propertySource));
                return;
            } else {

                BundleContext bundleContext = osgiBundle.getBundleContext();
                String objectclass = "(objectclass=" + ConfigService.class.getName() + ")";
                String symbolicname = "(name=" + springClassName + ")";
                Filter filter = FrameworkUtil.createFilter("(&" + objectclass + symbolicname + ")");

                ServiceTracker<ConfigService, ConfigService> serviceTracker = new ServiceTracker<>(bundleContext, filter, null);
                serviceTracker.open();
                ConfigService configService = serviceTracker.getService();

                if (configService == null) {
                    return;
                }

                Dictionary<String, String> properties = configService.getProperties();

                Enumeration<String> e = properties.keys();
                Map<String, Object> propertySource = new HashMap<>();

                while (e.hasMoreElements()) {
                    String propName = e.nextElement();
                    propertySource.put(propName, properties.get(propName));
                }

                environment.getPropertySources().addFirst(new MapPropertySource(PROPERTY_SOURCE_NAME, propertySource));
                serviceTracker.close();
            }

        } catch (Exception e) {
            throw new IllegalStateException("Error fetching properties from Karaf", e);
        }
    }

    private Map<String, Object> loadDefaultProperties() throws IOException {
        Map<String, Object> propertySource = new HashMap<>();
        InputStream propStream = this.getClass().getClassLoader().getResourceAsStream("application.properties");
        Properties defaultProperties = new Properties();
        defaultProperties.load(propStream);

        Enumeration<String> enums = (Enumeration<String>) defaultProperties.propertyNames();
        while (enums.hasMoreElements()) {
            String key = enums.nextElement();
            String value = defaultProperties.getProperty(key);
            propertySource.put(key, value);
        }
        return propertySource;
    }


    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE + 1000;
    }
}