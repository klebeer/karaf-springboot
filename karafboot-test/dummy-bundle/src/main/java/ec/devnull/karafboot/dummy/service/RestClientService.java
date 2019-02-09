package ec.devnull.karafboot.dummy.service;

import ec.devnull.karafboot.dummy.model.Order;
import ec.devnull.karafboot.dummy.model.OrderResponse;

import java.util.Optional;

/**
 * @author Kleber Ayala
 */
public interface RestClientService {

    Optional<String> login(String apiKey);

    OrderResponse processOrder(Order order, Optional<String> jwtToken);


}
