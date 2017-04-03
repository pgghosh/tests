package com.example.silverbars.dao;

import com.example.silverbars.db.InMemoryOrderDB;
import com.example.silverbars.exception.OrderNotFoundException;
import com.example.silverbars.model.Order;
import com.example.silverbars.model.OrderSummaryItem;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.example.silverbars.model.OrderType.BUY;
import static com.example.silverbars.model.OrderType.SELL;
import static com.example.silverbars.util.TestUtils.insertBuyOrder;
import static com.example.silverbars.util.TestUtils.populateDbwithSellAndBuyOrders;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
public class OrderDaoImplTest {

    @Autowired
    private InMemoryOrderDB orderDB;

    @Autowired
    private OrderDao orderDao;

    @Before
    public void setUp() throws Exception {
        orderDB.clearDB();
    }


    @Test
    public void addOrder() throws Exception {
        Order order = new Order();
        order.setUserId("1234");
        order.setQuantity(3.5);
        order.setUnitPrice(306);
        order.setOrderType(BUY);

        assertThat(orderDB.count(), Matchers.is(0));
        orderDao.addOrder(order);
        assertThat(orderDB.count(), Matchers.is(1));

    }

    @Test
    public void deleteOrder() throws Exception {
        Order addedOrder = insertBuyOrder(orderDB,"1234",306,3.5);

        assertThat(orderDB.count(), Matchers.is(1));
        orderDao.deleteOrder(addedOrder.getOrderId());
        assertThat(orderDB.count(), Matchers.is(0));

    }

    @Test(expected = OrderNotFoundException.class)
    public void deleteOrder_throwsExceptionWhenOrderIsNotFound() throws Exception {
        Order addedOrder = insertBuyOrder(orderDB,"1234",306,3.5);

        assertThat(orderDB.count(), Matchers.is(1));
        orderDao.deleteOrder("12345");
        assertThat(orderDB.count(), Matchers.is(1));

    }

    @Test
    public void getOrderSummaryByOrderType_ForBuys() throws Exception {
        populateDbwithSellAndBuyOrders(orderDB);

        List<OrderSummaryItem> orderSummaryItems = orderDao.getOrderSummaryByOrderType(BUY);
        assertThat(orderSummaryItems,Matchers.hasSize(3));
        Map<Double,OrderSummaryItem> summaryItemMap = orderSummaryItems.stream().collect(Collectors.toMap(OrderSummaryItem::getUnitPrice, Function.identity()));
        assertThat(summaryItemMap.get(305.0).getQuantity(),Matchers.is(8.1));
        assertThat(summaryItemMap.get(350.0).getQuantity(),Matchers.is(5.0));
        assertThat(summaryItemMap.get(450.0).getQuantity(),Matchers.is(3.7));

    }

    @Test
    public void getOrderSummaryByOrderType_ForSells() throws Exception {
        populateDbwithSellAndBuyOrders(orderDB);

        List<OrderSummaryItem> orderSummaryItems = orderDao.getOrderSummaryByOrderType(SELL);
        assertThat(orderSummaryItems,Matchers.hasSize(3));
        Map<Double,OrderSummaryItem> summaryItemMap = orderSummaryItems.stream().collect(Collectors.toMap(OrderSummaryItem::getUnitPrice, Function.identity()));
        assertThat(summaryItemMap.get(425.0).getQuantity(),Matchers.is(9.4));
        assertThat(summaryItemMap.get(365.0).getQuantity(),Matchers.is(11.8));
        assertThat(summaryItemMap.get(210.0).getQuantity(),Matchers.is(4.0));

    }


    @Configuration
    @ComponentScan("com.example.silverbars")
    public static class SpringConfig {

    }

}