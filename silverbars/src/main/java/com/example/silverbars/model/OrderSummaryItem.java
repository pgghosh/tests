package com.example.silverbars.model;

public class OrderSummaryItem {

    private OrderType orderType;
    private double unitPrice;
    private double quantity;

    public OrderSummaryItem(OrderType orderType, double unitPrice, double quantity) {
        this.orderType = orderType;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
    }

    public OrderType getOrderType() {
        return orderType;
    }

    public void setOrderType(OrderType orderType) {
        this.orderType = orderType;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }
}
