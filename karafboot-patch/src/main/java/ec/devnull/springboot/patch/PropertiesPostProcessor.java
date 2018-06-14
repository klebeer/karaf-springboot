package ec.devnull.springboot.patch;

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

import java.util.Dictionary;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Kleber Ayala
 */
@SuppressWarnings("unchecked")
public abstract class PropertiesPostProcessor implements EnvironmentPostProcessor, Ordered {


    /**
     * Name of the custom property source added by this post processor class
     */
    private static final String PROPERTY_SOURCE_NAME = "karafProperties";
    private Map<String, ServiceTracker<?, ?>> trackers = new HashMap<>();

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {


        try {
            Bundle osgiBundle = FrameworkUtil.getBundle(ConfigService.class);
            if (osgiBundle == null) {
                return;
            } else {
                BundleContext bundleContext = osgiBundle.getBundleContext();
                String objectclass = "(objectclass=" + ConfigService.class.getName() + ")";
                String symbolicname = "(name=" + getSpringInstanceName() + ")";
                Filter filter = FrameworkUtil.createFilter("(&" + objectclass + symbolicname + ")");

                ServiceTracker<ConfigService, ConfigService> serviceTracker = new ServiceTracker<>(bundleContext, filter, null);

                serviceTracker.open();
                trackers.put(getSpringInstanceName(), serviceTracker);

                ConfigService configService = serviceTracker.getService();
                Dictionary<String, String> properties = configService.getProperties();

                Enumeration<String> e = properties.keys();
                Map<String, Object> propertySource = new HashMap<>();

                while (e.hasMoreElements()) {
                    String propName = e.nextElement();
                    propertySource.put(propName, properties.get(propName));
                }

                environment.getPropertySources().addFirst(new MapPropertySource(PROPERTY_SOURCE_NAME, propertySource));
            }

        } catch (Exception e) {
            throw new IllegalStateException("Error fetching properties from Karaf");
        }
    }

    public abstract String getSpringInstanceName();

    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE + 1000;
    }
}