package ec.devnull.springboot.dummy.service.impl;

import ec.devnull.springboot.dummy.auth.JWTAuth;
import ec.devnull.springboot.dummy.auth.JWTClient;
import ec.devnull.springboot.dummy.auth.RestClient;
import ec.devnull.springboot.dummy.client.OrderAPIClient;
import ec.devnull.springboot.dummy.model.Order;
import ec.devnull.springboot.dummy.model.OrderResponse;
import ec.devnull.springboot.dummy.service.RestClientService;

import java.util.Optional;

/**
 * @author Kleber Ayala
 */
public class RestClientServiceImpl implements RestClientService {

    @Override
    public Optional<String> login(String apiKey) {
        JWTAuth jwtAuth = JWTAuth.get().
                headerApiKey("api", apiKey);
        JWTClient jwtClient = RestClient.get().
                auth(jwtAuth).
                apiClass(JWTClient.class).
                url("http://localhost:".concat("8090")).
                build();

        Optional<String> jwtToken = jwtClient.getToken();
        return jwtToken;
    }

    @Override
    public OrderResponse processOrder(Order order, Optional<String> jwtToken) {

        OrderAPIClient orderAPIClient = RestClient.
                get().
                withJWTToken(jwtToken).
                apiClass(OrderAPIClient.class).
                url("http://localhost:".
                        concat("8090").
                        concat("/api/")).
                build();

        return orderAPIClient.process(new Order());
        
    }
}
