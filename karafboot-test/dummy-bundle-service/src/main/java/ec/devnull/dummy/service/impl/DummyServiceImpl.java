package ec.devnull.dummy.service.impl;

import ec.devnull.dummy.service.DummyService;
import org.osgi.service.component.annotations.Component;

/**
 * @author Kleber Ayala
 */
@Component(
        service = DummyService.class
)
public class DummyServiceImpl implements DummyService {

    @Override
    public String doStuff() {
        return "Soy una respuesta OSGI :)";
    }
}
