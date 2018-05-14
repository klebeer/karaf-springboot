package ec.devnull.rest.client;


import ec.devnull.springboot.patch.PropertiesPostProcessor;

/**
 * @author Kleber Ayala
 */
public class TestPropertiesPostProcessor extends PropertiesPostProcessor {

    @Override
    public String getSpringInstanceName() {
        return "test1";
    }
}
