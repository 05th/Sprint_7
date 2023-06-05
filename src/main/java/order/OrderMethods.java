package order;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;

import static constants.endPoints.*;
import static io.restassured.RestAssured.given;

public class OrderMethods {
    public static RequestSpecification requestSpec() {
        return given().log().all()
                .contentType(ContentType.JSON)
                .baseUri(URL);
    }

    @Step("Create new Order")
    public ValidatableResponse createNewOrder(OrderModel orderModel) {
        return requestSpec()
                .body(orderModel)
                .when()
                .post(ORDER_CREATE)
                .then();
    }

    @Step("Get Order List")
    public ValidatableResponse getOrderList() {
        return requestSpec()
                .when()
                .get(ORDER_LIST)
                .then();
    }

    @Step("Cancel Order")
    public ValidatableResponse cancelOrder(int track) {
        return requestSpec()
                .body(track)
                .when()
                .put(ORDER_CANCEL)
                .then();
    }
}