package com.example.silverbars.dao;

import com.example.silverbars.exception.OrderNotFoundException;
import com.example.silverbars.model.Order;
import com.example.silverbars.model.OrderSummaryItem;
import com.example.silverbars.model.OrderType;

import java.util.List;


public interface OrderDao {
    Order addOrder(Order order);
    void deleteOrder(String orderId) throws OrderNotFoundException;
    List<OrderSummaryItem> getOrderSummaryByOrderType(OrderType orderType);
}
