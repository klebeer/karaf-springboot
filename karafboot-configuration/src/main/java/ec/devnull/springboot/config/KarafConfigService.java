package ec.devnull.springboot.config;

import java.util.Dictionary;

/**
 * Simple Karaf Properties Service implementation

 * @author Kleber Ayala
 */
public class KarafConfigService implements ConfigService {


    private Dictionary properties;

    public KarafConfigService(Dictionary properties) {
        this.properties = properties;

    }

    @Override
    public Dictionary<String, String> getProperties() {
        return this.properties;
    }
}
