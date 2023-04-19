package courierTests;

import courier.*;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class CreateCourierTest {
    protected final CourierDataGenerator courierDataGenerator = new CourierDataGenerator();
    private CourierMethods courierMethods;
    private CourierModel courierModel;
    private Courier courier;
    int courierId;

    @Before
    @Step("Creating test courier data ")
    public void setUp() {
        courierMethods = new CourierMethods();
        courierModel = courierDataGenerator.createCourierWithRandomData();
        courier = new Courier();
    }


    @Test
    @DisplayName("Create new Courier")
    @Description("Courier can be created")
    public void courierCanBeCreated() {
        ValidatableResponse responseCreateCourier = courierMethods.createCourier(courierModel);
        Credentials credentials = Credentials.from(courierModel);
        courierId = courierMethods.loginCourier(credentials).extract().path("id");
        courier.createCourierWithValidDataOk(responseCreateCourier);
    }

    @Test
    @DisplayName("Create courier with empty login field")
    @Description("Courier can not be created without login")
    public void courierCanNotBeCreatedWithoutLogin() {
        courierModel.setLogin(null);
        ValidatableResponse responseNullLogin = courierMethods.createCourier(courierModel);
        courier.createCourierWithIncompleteData(responseNullLogin);
    }

    @Test
    @DisplayName("Create courier with empty password field")
    @Description("Courier can not be created without password")
    public void courierCanNotBeCreatedWithoutPassword() {
        courierModel.setPassword(null);
        ValidatableResponse responseNullPassword = courierMethods.createCourier(courierModel);
        courier.createCourierWithIncompleteData(responseNullPassword);
    }

    @Test
    @DisplayName("Create courier with empty password & login field")
    @Description("Courier can not be created without password & login")
    public void courierCanNotBeCreatedWithoutLoginAndPassword() {
        courierModel.setLogin(null);
        courierModel.setPassword(null);
        ValidatableResponse responseNullFields = courierMethods.createCourier(courierModel);
        courier.createCourierWithIncompleteData(responseNullFields);
    }

    @Test
    @DisplayName("Create courier with registered login before")
    @Description("Courier can not be created with registered login before")
    public void courierCanNotBeCreatedWithSameLogin() {
        courierMethods.createCourier(courierModel);
        ValidatableResponse responseCreateCourier = courierMethods.createCourier(courierModel);
        courier.createCourierWithRegisteredLoginBefore(responseCreateCourier);
    }

    @After
    @Step("Removing test courier data")
    public void deleteCourier() {
        if (courierId != 0) {
            courierMethods.deleteCourier(courierId);
        }
    }
}