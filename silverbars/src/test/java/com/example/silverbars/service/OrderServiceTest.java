package com.example.silverbars.service;

import com.example.silverbars.dao.OrderDao;
import com.example.silverbars.exception.OrderNotFoundException;
import com.example.silverbars.model.Order;
import com.example.silverbars.model.OrderSummary;
import com.example.silverbars.model.OrderSummaryItem;
import com.example.silverbars.model.OrderType;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.silverbars.model.OrderType.BUY;
import static com.example.silverbars.model.OrderType.SELL;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
public class OrderServiceTest {

    private OrderService orderService;

    @Mock
    private OrderDao orderDao;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        orderService = new OrderService(orderDao);
    }

    @Test
    public void registerOrder() throws Exception {
        Order order = new Order();
        order.setUserId("1234");
        order.setQuantity(3.5);
        order.setUnitPrice(306);
        order.setOrderType(BUY);

        orderService.registerOrder(order);

        Mockito.verify(orderDao).addOrder(order);
    }

    @Test
    public void cancelOrder() throws Exception {

        String orderId = "5678";
        orderService.cancelOrder(orderId);
        Mockito.verify(orderDao).deleteOrder(orderId);

    }
    @Test(expected = OrderNotFoundException.class)
    public void cancelOrder_throwsExceptionWhenOrderNotFound() throws Exception{
        Mockito.doThrow(OrderNotFoundException.class).when(orderDao).deleteOrder(Mockito.anyString());
        String orderId = "5678";
        orderService.cancelOrder(orderId);
    }

    @Test
    public void getOrderSummary_ReturnsData() throws Exception {

        Mockito.when(orderDao.getOrderSummaryByOrderType(BUY))
                .thenReturn(Arrays.asList(new OrderSummaryItem(BUY,305.0,4.5),
                                            new OrderSummaryItem(BUY,322.0,3.0),
                                            new OrderSummaryItem(BUY,315.0,2.5)
                            ));
        Mockito.when(orderDao.getOrderSummaryByOrderType(SELL))
                .thenReturn(Arrays.asList(new OrderSummaryItem(SELL,555.0,2.5),
                        new OrderSummaryItem(SELL,322.0,4.0),
                        new OrderSummaryItem(SELL,400.0,5.5),
                        new OrderSummaryItem(SELL,423.0,7.0)
                ));
        OrderSummary orderSummary = orderService.getOrderSummary();
        Mockito.verify(orderDao,Mockito.times(2)).getOrderSummaryByOrderType(Mockito.any(OrderType.class));

        assertThat(orderSummary.getBuys(), Matchers.hasSize(3));
        assertThat(orderSummary.getSells(), Matchers.hasSize(4));

        checkSummaryItemSortOrder(orderSummary.getBuys(),"ASC");
        checkSummaryItemSortOrder(orderSummary.getSells(),"DESC");

    }

    @Test
    public void getOrderSummary_ReturnsOnlyBuyData() throws Exception {

        Mockito.when(orderDao.getOrderSummaryByOrderType(BUY))
                .thenReturn(Arrays.asList(new OrderSummaryItem(BUY,305.0,4.5),
                        new OrderSummaryItem(BUY,315.0,2.5),
                        new OrderSummaryItem(BUY,322.0,3.0)
                ));
        Mockito.when(orderDao.getOrderSummaryByOrderType(SELL))
                .thenReturn(Collections.EMPTY_LIST);

        OrderSummary orderSummary = orderService.getOrderSummary();
        Mockito.verify(orderDao,Mockito.times(2)).getOrderSummaryByOrderType(Mockito.any(OrderType.class));

        assertThat(orderSummary.getBuys(), Matchers.hasSize(3));
        assertThat(orderSummary.getSells(), Matchers.hasSize(0));

    }

    private void checkSummaryItemSortOrder(List<OrderSummaryItem> orderSummaryItems,String sortOrder){
        Comparator<OrderSummaryItem> doubleComparator;
        if(sortOrder == "ASC")
            doubleComparator = (a,b) -> Double.compare(a.getUnitPrice(), b.getUnitPrice());
        else
            doubleComparator = (a,b) -> Double.compare(b.getUnitPrice(), a.getUnitPrice());

        List<OrderSummaryItem> sortedItems = orderSummaryItems.stream().sorted(doubleComparator).collect(Collectors.toList());
        for(int i=0; i< sortedItems.size();i++){
            assertThat(orderSummaryItems.get(i),Matchers.is(sortedItems.get(i)));
        }

    }

    @Configuration
    @ComponentScan("com.example.silverbars")
    public static class SpringConfig {

    }

}