package com.example.silverbars.dao;

import com.example.silverbars.db.InMemoryOrderDB;
import com.example.silverbars.exception.OrderNotFoundException;
import com.example.silverbars.model.Order;
import com.example.silverbars.model.OrderSummaryItem;
import com.example.silverbars.model.OrderType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class OrderDaoImpl implements OrderDao {

    private static DecimalFormat decimalFormat = new DecimalFormat(".##");
    @Autowired
    private InMemoryOrderDB orderDB;

    public Order addOrder(Order order) {
        return orderDB.addOrder(order);
    }

    public void deleteOrder(String orderId) throws OrderNotFoundException {
        orderDB.deleteOrder(orderId);
    }

    public List<OrderSummaryItem> getOrderSummaryByOrderType(OrderType orderType) {
        Map<Double,OrderSummaryItem> orderSummaryItemMap = new HashMap<>();
        for(Order order:orderDB.getAllOrders()){
            if(order.getOrderType() == orderType){
                OrderSummaryItem summaryItem = orderSummaryItemMap.get(order.getUnitPrice());
                if(summaryItem == null){
                    summaryItem = new OrderSummaryItem(orderType,order.getUnitPrice(),order.getQuantity());
                    orderSummaryItemMap.put(order.getUnitPrice(),summaryItem);
                }else{
                    summaryItem.setQuantity(round(summaryItem.getQuantity()+order.getQuantity()));
                }
            }

        }

        return new ArrayList<>(orderSummaryItemMap.values());
    }

    private double round(double value){
        String stringValue = decimalFormat.format(value);
        return Double.parseDouble(stringValue);
    }

}
