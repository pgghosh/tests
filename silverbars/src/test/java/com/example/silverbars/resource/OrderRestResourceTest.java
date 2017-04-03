package com.example.silverbars.resource;

import com.example.silverbars.db.InMemoryOrderDB;
import com.example.silverbars.model.Order;
import com.example.silverbars.model.OrderType;
import com.jayway.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static com.example.silverbars.util.TestUtils.populateDbwithSellAndBuyOrders;
import static com.jayway.restassured.RestAssured.given;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
public class OrderRestResourceTest {

    @LocalServerPort
    int port;

    @Autowired
    private InMemoryOrderDB orderDB;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {}

    @AfterClass
    public static void tearDownAfterClass() throws Exception {}

    @Before
    public void setUp() throws Exception {
        orderDB.clearDB();
    }

    @After
    public void tearDown() throws Exception {}

    @Test
    public void testAddOrder() {
        Order order = new Order();
        order.setUserId("12345");
        order.setUnitPrice(310.0);
        order.setQuantity(4.7);
        order.setOrderType(OrderType.BUY);

        given()
                .port(port)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(order).
        when()
                .post("/orders/registerOrder").
        then()
                .statusCode(HttpStatus.SC_OK)
                .body("orderId", Matchers.notNullValue())
                .body("userId", Matchers.is(order.getUserId()));

        assertThat(orderDB.count(),Matchers.is(1));
    }

    @Test
    public void testCancelOrder() {

        Order order = new Order();
        order.setUserId("12345");
        order.setUnitPrice(310.0);
        order.setQuantity(4.7);
        order.setOrderType(OrderType.BUY);
        Order newOrder = orderDB.addOrder(order);

        assertThat(orderDB.count(),Matchers.is(1));
        given()
                .port(port).
        when()
                .delete("/orders/"+newOrder.getOrderId())
                .then()
                .statusCode(HttpStatus.SC_NO_CONTENT);
        assertThat(orderDB.count(),Matchers.is(0));

    }

    @Test
    public void testGetOrderSummary() {

        populateDbwithSellAndBuyOrders(orderDB);
        given()
                .port(port).
        when()
                .get("/orders/summary").
        then()
                .statusCode(HttpStatus.SC_OK)
                .body("buys",Matchers.hasSize(3))
                .body("buys[0].unitPrice",Matchers.is((float)305.0))
                .body("buys[0].quantity",Matchers.is((float)8.1))
                .body("buys[1].unitPrice",Matchers.is((float)350.0))
                .body("buys[1].quantity",Matchers.is((float)5.0))
                .body("buys[2].unitPrice",Matchers.is((float)450.0))
                .body("buys[2].quantity",Matchers.is((float)3.7))
                .body("sells",Matchers.hasSize(3))
                .body("sells[0].unitPrice",Matchers.is((float)425.0))
                .body("sells[0].quantity",Matchers.is((float)9.4))
                .body("sells[1].unitPrice",Matchers.is((float)365.0))
                .body("sells[1].quantity",Matchers.is((float)11.8))
                .body("sells[2].unitPrice",Matchers.is((float)210.0))
                .body("sells[2].quantity",Matchers.is((float)4.0));

    }

    @Test
    public void testCancelNonExistentOrder() {
        Order order = new Order();
        order.setUserId("12345");
        order.setUnitPrice(310.0);
        order.setQuantity(4.7);
        order.setOrderType(OrderType.BUY);
        Order newOrder = orderDB.addOrder(order);

        assertThat(orderDB.count(),Matchers.is(1));
        given()
                .port(port).
                when()
                .delete("/orders/"+newOrder.getOrderId()+1)
                .then()
                .statusCode(HttpStatus.SC_NOT_FOUND);
        assertThat(orderDB.count(),Matchers.is(1));
    }


}
