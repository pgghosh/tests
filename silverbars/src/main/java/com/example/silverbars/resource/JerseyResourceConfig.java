package com.example.silverbars.resource;

import com.example.silverbars.resource.provider.ContainerExceptionMapper;
import com.example.silverbars.resource.provider.OrderNotFoundExceptionMapper;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

@Component
public class JerseyResourceConfig extends ResourceConfig{

    public JerseyResourceConfig() {
        register(OrderNotFoundExceptionMapper.class);
        register(ContainerExceptionMapper.class);
        register(OrderRestResource.class);
    }
}
