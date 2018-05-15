package ec.devnull.springboot.dummy.service;

import ec.devnull.springboot.dummy.model.Order;
import ec.devnull.springboot.dummy.model.OrderResponse;

import java.util.Optional;

/**
 * @author Kleber Ayala
 */
public interface RestClientService {

    Optional<String> login(String apiKey);

    OrderResponse processOrder(Order order, Optional<String> jwtToken);


}
