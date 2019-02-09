package ec.devnull.karafboot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.net.URL;
import java.net.URLStreamHandler;
import java.net.URLStreamHandlerFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;

/**
 * Este register de URLs permite registrar mas de un factory de URLS
 *
 * @author https://github.com/chonton/url-extension/blob/master/src/main/java/org/honton/chas/url/extension/urlhandler/UrlStreamHandlerRegistry.java
 */
public class UrlStreamHandlerRegistry implements URLStreamHandlerFactory {

    private static UrlStreamHandlerRegistry SINGLETON;
    private static Logger LOGGER = LoggerFactory.getLogger(UrlStreamHandlerRegistry.class);
    private final List<URLStreamHandlerFactory> factories = new ArrayList<>();

    /**
     * Get the singleton instance of the registry/factory.  This will also invoke
     * URL.setURLStreamHandlerFactory and {@link #providers()} on first call to this method.
     *
     * @return The register UrlStreamHandlerRegistry
     */
    public static UrlStreamHandlerRegistry register() {
        synchronized (UrlStreamHandlerRegistry.class) {
            if (SINGLETON == null) {
                try {
                    Field factoryField = URL.class.getDeclaredField("factory");
                    factoryField.setAccessible(true);
                    Object currentFactory = factoryField.get(null);
                    factoryField.set(null, null);

                    SINGLETON = new UrlStreamHandlerRegistry();
                    SINGLETON.factory((URLStreamHandlerFactory) currentFactory);
                    URL.setURLStreamHandlerFactory(SINGLETON);
                    SINGLETON.providers();

                } catch (Exception e) {
                    LOGGER.error("Error at register  URL Stream ", e);
                }
            }
            return SINGLETON;
        }
    }


    /**
     * Register all URLStreamHandlerFactory found in META-INF/services/java.net.URLStreamHandlerFactory files.
     */
    public void providers() {
        // register providers found in jars
        for (URLStreamHandlerFactory factory : ServiceLoader.load(URLStreamHandlerFactory.class)) {
            factory(factory);
        }
    }

    /**
     * Register a URLStreamHandlerFactory
     *
     * @param urlStreamHandlerFactory A factory instance to register.  The factory must produce thread-safe URLStreamHandlers.
     */
    public UrlStreamHandlerRegistry factory(URLStreamHandlerFactory urlStreamHandlerFactory) {
        factories.add(urlStreamHandlerFactory);
        return this;
    }

    /**
     * Get or Create a URLStreamHandler for a scheme.  First check for a  URLStreamHandler
     * registered with the scheme, then query each registered for the schema.
     *
     * @param scheme The scheme for the URLStreamHandler
     * @return A thread-safe URLStreamHandler for the scheme or null
     */
    @Override
    public URLStreamHandler createURLStreamHandler(String scheme) {
        for (URLStreamHandlerFactory factory : factories) {
            URLStreamHandler urlStreamHandler = factory.createURLStreamHandler(scheme);
            if (urlStreamHandler != null) {
                return urlStreamHandler;
            }
        }
        return null;
    }
}
