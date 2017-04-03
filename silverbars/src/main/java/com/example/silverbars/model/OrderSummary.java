package com.example.silverbars.model;

import java.util.List;

public class OrderSummary {

    private List<OrderSummaryItem> buys;
    private List<OrderSummaryItem> sells;

    public List<OrderSummaryItem> getBuys() {
        return buys;
    }

    public void setBuys(List<OrderSummaryItem> buys) {
        this.buys = buys;
    }

    public List<OrderSummaryItem> getSells() {
        return sells;
    }

    public void setSells(List<OrderSummaryItem> sells) {
        this.sells = sells;
    }
}
