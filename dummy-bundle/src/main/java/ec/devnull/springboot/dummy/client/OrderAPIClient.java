/*
 * mcm-rest-client
 *
 * Copyright (c) 2018.  FISA Group.
 *
 * This software is the confidential and proprietary information FISA GROUP
 * ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with FISA Group.
 */

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
