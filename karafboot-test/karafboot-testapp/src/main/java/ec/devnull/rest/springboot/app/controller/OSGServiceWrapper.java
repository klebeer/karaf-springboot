package ec.devnull.rest.springboot.app.controller;

import ec.devnull.dummy.service.DummyService;
import ec.devnull.karafboot.OSGIServiceProxy;
import org.springframework.stereotype.Component;

/**
 * @author Kleber Ayala
 */

@Component
public class OSGServiceWrapper {


    public String getStuff() {

        OSGIServiceProxy<DummyService> dummyServiceProxy = new OSGIServiceProxy(DummyService.class);

        DummyService dummyService = dummyServiceProxy.getService();
        return dummyService.doStuff();
    }
}