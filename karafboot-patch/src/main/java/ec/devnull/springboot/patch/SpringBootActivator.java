package ec.devnull.springboot.patch;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.lang.reflect.ParameterizedType;
import java.security.AccessController;
import java.security.PrivilegedAction;

/**
 * @author Kleber Ayala
 */
public abstract class SpringBootActivator<T> implements BundleActivator {

    private ConfigurableApplicationContext applicationContext = null;


    @Override
    public void start(BundleContext context) throws Exception {

        ClassLoader ccl = Thread.currentThread().getContextClassLoader();
        try {
            Class<T> springAppClass = karafBootClassloader(ccl);

            SpringBootURLFactory.register();
            String[] args = {};
            SpringApplication springApplication = new SpringApplication(springAppClass);
            springApplication.setBannerMode(Banner.Mode.OFF);
            applicationContext = springApplication.run(args);

        } finally {
            Thread.currentThread().setContextClassLoader(ccl);
        }
    }

    private Class<T> karafBootClassloader(ClassLoader ccl) {
        Class<T> springAppClass = getGenericTypeClass();
        AccessController.doPrivileged((PrivilegedAction) () -> {
            JoinClassLoader joinClassLoader = new JoinClassLoader(springAppClass.getClassLoader(), ccl);
            Thread.currentThread().setContextClassLoader(joinClassLoader);
            return null;
        });
        return springAppClass;
    }

    @Override
    public void stop(BundleContext context) throws Exception {
        ClassLoader ccl = Thread.currentThread().getContextClassLoader();
        try {
            karafBootClassloader(ccl);
            if (applicationContext != null) {
                applicationContext.close();
            }
        } finally {
            Thread.currentThread().setContextClassLoader(ccl);
        }
    }

    private Class<T> getGenericTypeClass() {
        try {
            String className = ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0].getTypeName();
            Class<?> clazz = Class.forName(className);
            return (Class<T>) clazz;
        } catch (Exception e) {
            throw new IllegalStateException("Class is not parametrized with generic type!!! Please use extends <> ", e);
        }
    }


}
