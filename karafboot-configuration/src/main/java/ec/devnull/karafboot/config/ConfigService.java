package ec.devnull.karafboot.config;

import java.util.Dictionary;

/**
 * Simple Karaf Properties keeper
 *
 * @author Kleber Ayala
 */
public interface ConfigService {

    Dictionary<String, String> getProperties();

}
