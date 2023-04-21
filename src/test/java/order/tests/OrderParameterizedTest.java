package orderTests;

import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import order.OrderMethods;
import order.OrderModel;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;

@RunWith(Parameterized.class)
public class OrderParameterizedTest {
    private final List<String> colour;

    private OrderMethods orderMethods;
    private int track;

    public OrderParameterizedTest(List<String> colour) {
        this.colour = colour;
    }

    @Parameterized.Parameters(name = "Scooter colour: {0}")
    public static Object[][] getScooterColour() {
        return new Object[][]{
                {List.of("BLACK")},
                {List.of("GRAY")},
                {List.of("BLACK, GRAY")},
                {List.of()}
        };
    }
    @Before
    public void setUp() {
        orderMethods = new OrderMethods();
    }


    @Test
    @DisplayName("Get Scooter order with different colour")
    @Description("Checking order for scooter with different colour")
    public void orderingWithScootersInDifferentColors() {
        OrderModel orderModel = new OrderModel(colour);
        ValidatableResponse responseCreateOrder = orderMethods.createNewOrder(orderModel);
        track = responseCreateOrder.extract().path("track");
        responseCreateOrder.assertThat()
                .statusCode(201)
                .body("track", is(notNullValue()));
    }

    @After
    @Step("Cancel test order")
    public void cancelTestOrder() {
        orderMethods.cancelOrder(track);
    }
}