package ec.devnull.rest.springboot.app.controller;


import ec.devnull.rest.springboot.app.model.Order;
import ec.devnull.rest.springboot.app.model.OrderResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@Api(value = "order", description = "Rest API para Operaciones con las Ordenes", tags = {"Ordenes"})
public class OrderController {


    @Autowired
    private OSGServiceWrapper osgServiceWrapper;

    @RequestMapping(value = "/order/", method = RequestMethod.POST, produces = "application/json")
    @ApiOperation(value = "Procesa una Orden de una Empresa", response = OrderResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "The resource not found")
    }
    )
    public OrderResponse processOrder(Order order) {


        OrderResponse response = new OrderResponse().returnCode(1).returnCodeDesc("Todo Bien :)");

        if (osgServiceWrapper != null) {
            String stuff = osgServiceWrapper.getStuff();
            response.returnCodeDesc(stuff);
        }
        return response;
    }


}
