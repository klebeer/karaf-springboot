package ec.devnull.springboot.dummy.client;

import ec.devnull.springboot.dummy.model.Order;
import ec.devnull.springboot.dummy.model.OrderResponse;
import feign.Headers;
import feign.RequestLine;


public interface OrderAPIClient {

    @RequestLine("POST /order/")
    @Headers("Content-Type: application/json")
    OrderResponse process(Order order);
}
