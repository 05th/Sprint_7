package courier;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;

import static constants.endPoints.*;
import static io.restassured.RestAssured.given;

public class CourierMethods {
    public static RequestSpecification requestSpec() {
        return given().log().all()
                .contentType(ContentType.JSON)
                .baseUri(URL);
    }

    @Step("Create courier")
    public ValidatableResponse createCourier(CourierModel courierModel) {
        return requestSpec()
                .body(courierModel)
                .when()
                .post(COURIER_CREATE)
                .then();
    }

    @Step("Login courier")
    public ValidatableResponse loginCourier(Credentials credentials) {
        return requestSpec()
                .body(credentials)
                .when()
                .post(COURIER_LOGIN)
                .then();
    }

    @Step("Delete Courier")
    public ValidatableResponse deleteCourier(int courierId) {
        return requestSpec()
                .when()
                .delete(COURIER_DELETE + courierId)
                .then();
    }
}