package com.example.silverbars.db;

import com.example.silverbars.exception.OrderNotFoundException;
import com.example.silverbars.model.Order;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class InMemoryOrderDB {

    private Map<String,Order> orderDb = new HashMap<String, Order>();
    private Integer lastGeneratedOrderId=0;

    public Order addOrder(Order order){
        lastGeneratedOrderId++;
        String orderId = lastGeneratedOrderId.toString();
        Order newOrder = cloneOrderWithId(order,orderId);
        orderDb.put(orderId,newOrder);
        return newOrder;
    }

    public void deleteOrder(String orderId) throws OrderNotFoundException{
        if(orderDb.remove(orderId) == null) {
            throw new OrderNotFoundException("Order with Id : " + orderId + " not found");
        }
    }

    public Collection<Order> getAllOrders(){
        return orderDb.values();
    }

    private Order cloneOrderWithId(Order order,String orderId){
        Order newOrder = new Order();
        BeanUtils.copyProperties(order,newOrder);
        newOrder.setOrderId(orderId);
        return newOrder;
    }

    public void clearDB(){
        orderDb.clear();
        lastGeneratedOrderId=0;
    }

    public int count(){
        return orderDb.size();
    }

    public List<Order> findOrdersByUserId(final String userId){
        return orderDb.entrySet()
                .stream()
                .filter(e -> e.getValue().getUserId() == userId)
                .map(e-> e.getValue())
                .collect(Collectors.toList());
    }

}
