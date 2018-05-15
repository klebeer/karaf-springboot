package ec.devnull.rest.springboot.app;

import ec.devnull.springboot.patch.JoinClassLoader;
import ec.devnull.springboot.patch.SpringBootURLFactory;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.springframework.boot.env.PropertiesPropertySourceLoader;


/**
 * @author kleber
 */
public class Activator implements BundleActivator {

    private JWTRestApplicationDummy jwtRestApplication;

    @Override
    public void start(BundleContext bundleContext) throws Exception {

        ClassLoader ccl = Thread.currentThread().getContextClassLoader();

        BundleContext context = FrameworkUtil.getBundle(Activator.class).getBundleContext();

        try {
            JoinClassLoader joinClassLoader = new JoinClassLoader(PropertiesPropertySourceLoader.class.getClassLoader(), ccl);
            Thread.currentThread().setContextClassLoader(joinClassLoader);
            SpringBootURLFactory.register();
            jwtRestApplication = new JWTRestApplicationDummy();
            jwtRestApplication.start();

        } finally {
            Thread.currentThread().setContextClassLoader(ccl);
        }

    }

    @Override
    public void stop(BundleContext bundleContext) throws Exception {
        if (jwtRestApplication != null) {
            jwtRestApplication.stop();
        }

    }
}
