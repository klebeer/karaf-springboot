package ec.devnull.springboot.patch;

import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;

import java.util.HashMap;
import java.util.Map;

/**
 * Just to get a OSGI service from springboot app
 *
 * @author Kleber Ayala
 */
public class OSGIServiceProxy<T> {


    private final Class<T> typeParameterClass;
    private Map<String, ServiceTracker<?, ?>> trackers = new HashMap<>();

    public OSGIServiceProxy(Class<T> typeParameterClass) {
        this.typeParameterClass = typeParameterClass;
    }


    public T getService() {
        BundleContext ctx = FrameworkUtil.getBundle(typeParameterClass).getBundleContext();
        ServiceReference serviceReference = ctx.getServiceReference(typeParameterClass.getName());
        return (T) typeParameterClass.cast(ctx.getService(serviceReference));

    }


}
