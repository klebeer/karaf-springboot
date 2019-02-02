package ec.devnull.springboot.config;


import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.cm.ManagedServiceFactory;

import java.util.*;


public class SrpingBootConfigManager implements ManagedServiceFactory {

    private BundleContext context;
    private Map<String, ServiceRegistration> serviceRegistrationMap;

    public SrpingBootConfigManager(BundleContext context) {
        this.context = context;
        this.serviceRegistrationMap = new HashMap<>();
    }

    @Override
    public String getName() {
        return "springboot";
    }

    @Override
    public void updated(final String pid, final Dictionary config) {
        deleted(pid);
        if (config == null) {
            return;
        }
        if (config.get("name") == null) {
            config.put("name", UUID.randomUUID().toString());
        }


        Dictionary<String, Object> props = new Hashtable<>();
        props.put("name", config.get("name"));

        ConfigService configService = new KarafConfigService(config);

        ServiceRegistration service = this.context.registerService(ConfigService.class, configService, props);
        serviceRegistrationMap.put(pid, service);
    }

    @Override
    public void deleted(String pid) {
        ServiceRegistration services = serviceRegistrationMap.remove(pid);
        if (services != null) {
            services.unregister();
        }
    }

    synchronized void destroy() {
        for (String pid : serviceRegistrationMap.keySet()) {
            deleted(pid);
        }
    }


}
