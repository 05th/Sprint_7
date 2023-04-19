package courierTests;

import courier.*;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class LoginCourierTest {
    private final CourierDataGenerator courierDataGenerator = new CourierDataGenerator();
    private Courier courier;
    private Credentials credentials;
    private CourierMethods courierMethods;
    private CourierModel courierModel;
    private int courierId;

    @Before
    @Step("Create login test data for courier")
    public void setUp() {
        courierMethods = new CourierMethods();
        courierModel = courierDataGenerator.createCourierWithRandomData();
        courierMethods.createCourier(courierModel);
        credentials = Credentials.from(courierModel);
        courier = new Courier();
    }

    @Test
    @DisplayName("Successful login")
    @Description("Courier can login with valid data")
    public void courierLoginOkValidData() {
        ValidatableResponse responseLoginCourier = courierMethods.loginCourier(credentials);
        courier.loginCourierOk(responseLoginCourier);
        courierId = responseLoginCourier.extract().path("id");
    }

    @Test
    @DisplayName("Login field is empty")
    @Description("Courier can not login with empty login field")
    public void courierLoginErrorEmptyLogin() {
        Credentials courierCredsWithoutLogin = new Credentials("", courierModel.getPassword());
        ValidatableResponse responseLoginErrorMessage = courierMethods.loginCourier(courierCredsWithoutLogin);
        courier.loginCourierWithIncompleteData(responseLoginErrorMessage);
    }

    @Test
    @DisplayName("Login with empty password field")
    @Description("Courier can not login with empty password field")
    public void courierLoginErrorEmptyPassword() {
        Credentials courierCredsWithoutPassword = new Credentials(courierModel.getLogin(), "");
        ValidatableResponse responseLoginErrorMessage = courierMethods.loginCourier(courierCredsWithoutPassword);
        courier.loginCourierWithIncompleteData(responseLoginErrorMessage);
    }

    @Test
    @DisplayName("Login with empty password field & login field")
    @Description("Courier can not login with empty password field & login password field")
    public void courierLoginErrorEmptyLoginAndPassword() {
        Credentials courierCredsWithoutLoginAndPassword = new Credentials("", "");
        ValidatableResponse responseLoginErrorMessage = courierMethods.loginCourier(courierCredsWithoutLoginAndPassword);
        courier.loginCourierWithIncompleteData(responseLoginErrorMessage);
    }

    @Test
    @DisplayName("Login with invalid login")
    @Description("Courier can not be login with registered login before")
    public void courierLoginErrorAccountNotFound() {
        Credentials courierCredsErrorAccountNotFound = new Credentials(CourierDataGenerator.LOGIN_FAKER, courierModel.getPassword());
        ValidatableResponse responseLoginErrorMessage = courierMethods.loginCourier(courierCredsErrorAccountNotFound);
        courier.loginCourierWithInvalidDataAccountNotFound(responseLoginErrorMessage);
    }

    @After
    @Step("Delete courier")
    public void deleteCourier() {
        if (courierId != 0) {
            courierMethods.deleteCourier(courierId);
        }
    }
}