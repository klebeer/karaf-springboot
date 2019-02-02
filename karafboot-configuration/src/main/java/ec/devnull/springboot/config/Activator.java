package ec.devnull.springboot.config;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.cm.ManagedServiceFactory;

import java.util.Dictionary;
import java.util.Hashtable;

/**
 * Dynamically registers services based on configuration files that starts with karaf.springboot-*
 *
 * @author Kleber Ayala
 */
public class Activator implements BundleActivator {

    private static final String FACTORY_PID = "karaf.springboot";

    private ServiceRegistration manServiceRegistration;

    @Override
    public void start(BundleContext context) {
        Dictionary<String, String> props = new Hashtable<>();
        props.put(Constants.SERVICE_PID, FACTORY_PID);
        SrpingBootConfigManager configManager = new SrpingBootConfigManager(context);
        manServiceRegistration = context.registerService(ManagedServiceFactory.class.getName(), configManager, props);
    }

    @Override
    public void stop(BundleContext context) {
        manServiceRegistration.unregister();
    }

}
