package orderTests;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import order.OrderMethods;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.notNullValue;

public class ListOrderTest {
    @Test
    @DisplayName("Get order List")
    @Description("Order List successful gotten")
    public void getOrderList() {
        OrderMethods orderMethods = new OrderMethods();
        ValidatableResponse responseOrderList = orderMethods.getOrderList();
        responseOrderList.assertThat()
                .statusCode(200)
                .body("orders", notNullValue());
    }
}