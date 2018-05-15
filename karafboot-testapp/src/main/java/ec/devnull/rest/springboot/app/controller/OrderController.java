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

package ec.devnull.rest.springboot.app.controller;


import ec.devnull.rest.springboot.app.model.Order;
import ec.devnull.rest.springboot.app.model.OrderResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@Api(value = "order", description = "Rest API para Operaciones con las Ordenes", tags = {"Ordenes"})
public class OrderController {


    @RequestMapping(value = "/order/", method = RequestMethod.POST, produces = "application/json")
    @ApiOperation(value = "Procesa una Orden de una Empresa", response = OrderResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "The resource not found")
    }
    )
    public OrderResponse processOrder(Order order) {
        OrderResponse response = new OrderResponse().returnCode(1).returnCodeDesc("Todo Bien :)");

        return response;
    }

}
