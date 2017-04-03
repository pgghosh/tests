package com.example.silverbars.resource.provider;

import com.example.silverbars.exception.OrderNotFoundException;
import org.glassfish.jersey.server.ContainerException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public class ContainerExceptionMapper implements ExceptionMapper<ContainerException> {
    @Override
    public Response toResponse(ContainerException e) {
        if(e.getCause() instanceof OrderNotFoundException){
            return Response.status(Response.Status.NOT_FOUND).build();
        }else {
            return Response.serverError().build();
        }
    }
}
