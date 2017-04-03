package com.example.silverbars.resource;

import com.example.silverbars.exception.OrderNotFoundException;
import com.example.silverbars.model.Order;
import com.example.silverbars.model.OrderSummary;
import com.example.silverbars.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/orders")
@Component
public class OrderRestResource {

    @Autowired
    private OrderService orderService;

    @POST
    @Path("registerOrder")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Order registerOrder(@RequestBody final Order order) {

        return orderService.registerOrder(order);
    }

    @DELETE
    @Path("{orderId}")
    public void cancelOrder(@PathParam("orderId") final String orderId) throws OrderNotFoundException {
        orderService.cancelOrder(orderId);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("summary")
    public OrderSummary getOrderSummary() {

        return orderService.getOrderSummary();
    }



}
