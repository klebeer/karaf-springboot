package ec.devnull.springboot.config;

import java.util.Dictionary;

/**
 * Simple Karaf Properties keeper
 *
 * @author Kleber Ayala
 */
public interface ConfigService {

    Dictionary<String, String> getProperties();

}
