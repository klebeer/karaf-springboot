package ec.devnull.karafboot.dummy;

import ec.devnull.karafboot.dummy.service.RestClientService;
import ec.devnull.karafboot.dummy.service.impl.RestClientServiceImpl;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

import java.util.Dictionary;
import java.util.Hashtable;

/**
 * @author Kleber Ayala
 */
public class Activator implements BundleActivator {

    private ServiceRegistration restServiceRegistration;

    @Override
    public void start(BundleContext context) {
        Dictionary<String, String> props = new Hashtable<>();
        RestClientService restClientService = new RestClientServiceImpl();
        restServiceRegistration = context.registerService(RestClientService.class.getName(), restClientService, props);
    }

    @Override
    public void stop(BundleContext context) {
        restServiceRegistration.unregister();
    }

}
