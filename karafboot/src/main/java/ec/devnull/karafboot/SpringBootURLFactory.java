package ec.devnull.karafboot;

import org.apache.catalina.webresources.ClasspathURLStreamHandler;
import org.apache.catalina.webresources.war.Handler;

import java.net.URLStreamHandler;
import java.net.URLStreamHandlerFactory;

/**
 * URL Factory para los protocolos war y classpath. que utiliza la instancia de tomcat-embebed dentro de spting boot,
 * el Factory que se inicia normalmente fue deshabilitado y remplazado por esta instancia
 *
 * @author Kleber Ayala
 */
public class SpringBootURLFactory implements URLStreamHandlerFactory {

    private static final String WAR_PROTOCOL = "war";
    private static final String CLASSPATH_PROTOCOL = "classpath";

    private static volatile SpringBootURLFactory instance = null;


    private SpringBootURLFactory() {
        //Nuestro Custom URL Factory obtiene el factory actual y agrega el de tomcat
        UrlStreamHandlerRegistry urlStreamHandlerRegistry = UrlStreamHandlerRegistry.register();
        urlStreamHandlerRegistry.factory(this);
    }


    public static SpringBootURLFactory getInstance() {
        // Double checked locking. OK because instance is volatile.
        if (instance == null) {
            synchronized (SpringBootURLFactory.class) {
                if (instance == null) {
                    instance = new SpringBootURLFactory();
                }
            }
        }
        return instance;
    }

    public static void register() {
        getInstance();
    }


    @Override
    public URLStreamHandler createURLStreamHandler(String protocol) {

        if (WAR_PROTOCOL.equals(protocol)) {
            return new Handler();
        } else if (CLASSPATH_PROTOCOL.equals(protocol)) {
            return new ClasspathURLStreamHandler();
        }

        return null;
    }
}
