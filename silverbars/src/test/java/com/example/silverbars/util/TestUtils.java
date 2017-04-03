package com.example.silverbars.util;

import com.example.silverbars.db.InMemoryOrderDB;
import com.example.silverbars.model.Order;

import static com.example.silverbars.model.OrderType.BUY;
import static com.example.silverbars.model.OrderType.SELL;

public class TestUtils {
    public static void populateDbwithSellAndBuyOrders(InMemoryOrderDB orderDB){
        insertBuyOrder(orderDB,"123",305.0,2.3);
        insertBuyOrder(orderDB,"174",350.0,5.0);
        insertBuyOrder(orderDB,"123",450.0,3.7);
        insertBuyOrder(orderDB,"165",305.0,5.8);

        insertSellOrder(orderDB,"123",425.0,3.3);
        insertSellOrder(orderDB,"174",365.0,6.2);
        insertSellOrder(orderDB,"123",210.0,4.0);
        insertSellOrder(orderDB,"165",365.0,5.6);
        insertSellOrder(orderDB,"174",425.0,6.1);
    }

    public static Order insertSellOrder(InMemoryOrderDB orderDB,String userId, double unitPrice, double quantity){
        Order order = new Order();
        order.setUserId(userId);
        order.setUnitPrice(unitPrice);
        order.setQuantity(quantity);
        order.setOrderType(SELL);
        return orderDB.addOrder(order);
    }

    public static Order insertBuyOrder(InMemoryOrderDB orderDB,String userId,double unitPrice,double quantity){
        Order order = new Order();
        order.setUserId(userId);
        order.setUnitPrice(unitPrice);
        order.setQuantity(quantity);
        order.setOrderType(BUY);
        return orderDB.addOrder(order);
    }

}
