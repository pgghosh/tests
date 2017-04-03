package com.example.silverbars.resource.provider;

import com.example.silverbars.exception.OrderNotFoundException;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public class OrderNotFoundExceptionMapper implements ExceptionMapper<OrderNotFoundException> {
    @Override
    public Response toResponse(OrderNotFoundException e) {
        Response response = Response.status(Response.Status.NOT_FOUND).type(MediaType.APPLICATION_JSON).build();
        return response;
    }
}
