package ec.devnull.springboot.patch;

import ec.devnull.springboot.config.ConfigService;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Filter;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.InvalidSyntaxException;
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
public abstract class PropertiesPostProcessor implements EnvironmentPostProcessor, Ordered {

    public static final int DEFAULT_ORDER = Ordered.LOWEST_PRECEDENCE + 1000;
    /**
     * Name of the custom property source added by this post processor class
     */
    private static final String PROPERTY_SOURCE_NAME = "karafProperties";
    private Map<String, ServiceTracker<?, ?>> trackers = new HashMap<>();


    /**
     * Adds Spring Environment custom logic. This custom logic fetch properties from database and setting highest precedence
     */
    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {


        try {
            BundleContext bundleContext = FrameworkUtil.getBundle(ConfigService.class).getBundleContext();
            String objectclass = "(objectclass=" + ConfigService.class.getName() + ")";
            String symbolicname = "(name=" + getSpringInstanceName() + ")";
            Filter filter = FrameworkUtil.createFilter("(&" + objectclass + symbolicname + ")");

            ServiceTracker serviceTracker = new ServiceTracker(bundleContext, filter, null);
            serviceTracker.open();
            trackers.put(getSpringInstanceName(), serviceTracker);

        } catch (InvalidSyntaxException e) {
            e.printStackTrace();
        }

        Map<String, Object> propertySource = new HashMap<>();

        try {

            ServiceTracker serviceTracker = trackers.get(getSpringInstanceName());

            ConfigService configService = (ConfigService) serviceTracker.getService();
            Dictionary<String, String> properties = configService.getProperties();


            Enumeration<String> e = properties.keys();
            while (e.hasMoreElements()) {
                String propName = e.nextElement();
                propertySource.put(propName, properties.get(propName));
            }

            // Create a custom property source with the highest precedence and add it to Spring Environment
            environment.getPropertySources().addFirst(new MapPropertySource(PROPERTY_SOURCE_NAME, propertySource));

        } catch (Exception e) {
            throw new RuntimeException("Error fetching properties from Karaf");
        }
    }

    public abstract String getSpringInstanceName();

    @Override
    public int getOrder() {
        return DEFAULT_ORDER;
    }
}