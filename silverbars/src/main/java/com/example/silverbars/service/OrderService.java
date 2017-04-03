package com.example.silverbars.service;

import com.example.silverbars.dao.OrderDao;
import com.example.silverbars.exception.OrderNotFoundException;
import com.example.silverbars.model.Order;
import com.example.silverbars.model.OrderSummary;
import com.example.silverbars.model.OrderSummaryItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.silverbars.model.OrderType.BUY;
import static com.example.silverbars.model.OrderType.SELL;

@Service
public class OrderService {

    private OrderDao orderDao;

    @Autowired
    public OrderService(OrderDao orderDao) {
        this.orderDao = orderDao;
    }

    public Order registerOrder(Order order){
        return orderDao.addOrder(order);
    }

    public void cancelOrder(String orderId) throws OrderNotFoundException{

        orderDao.deleteOrder(orderId);

    }

    public OrderSummary getOrderSummary(){

        OrderSummary orderSummary = new OrderSummary();
        List<OrderSummaryItem> buys = orderDao.getOrderSummaryByOrderType(BUY)
                .stream()
                .sorted((a,b) -> Double.compare(a.getUnitPrice() , b.getUnitPrice()))
                .collect(Collectors.toList());
        orderSummary.setBuys(buys);

        List<OrderSummaryItem> sells = orderDao.getOrderSummaryByOrderType(SELL)
                .stream()
                .sorted((a,b) -> Double.compare(b.getUnitPrice() , a.getUnitPrice()))
                .collect(Collectors.toList());
        orderSummary.setSells(sells);
        return orderSummary;
    }
}
