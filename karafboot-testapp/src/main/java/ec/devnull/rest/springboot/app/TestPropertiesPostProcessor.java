package ec.devnull.rest.springboot.app;


import ec.devnull.springboot.patch.PropertiesPostProcessor;

/**
 * @author Kleber Ayala
 */
public class TestPropertiesPostProcessor extends PropertiesPostProcessor {

    @Override
    public String getSpringInstanceName() {
        return "test";
    }
}
