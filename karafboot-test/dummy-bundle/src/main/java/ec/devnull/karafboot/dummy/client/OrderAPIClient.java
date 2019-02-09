package ec.devnull.karafboot.dummy.client;

import ec.devnull.karafboot.dummy.model.Order;
import ec.devnull.karafboot.dummy.model.OrderResponse;
import feign.Headers;
import feign.RequestLine;


public interface OrderAPIClient {

    @RequestLine("POST /order/")
    @Headers("Content-Type: application/json")
    OrderResponse process(Order order);
}
