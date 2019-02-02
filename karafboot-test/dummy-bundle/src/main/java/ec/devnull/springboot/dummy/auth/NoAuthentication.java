package ec.devnull.springboot.dummy.auth;

import feign.RequestTemplate;

public class NoAuthentication implements Authentication {

    @Override
    public void apply(RequestTemplate template) {
        //dummy
    }
}
