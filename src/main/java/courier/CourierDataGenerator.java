package courier;

import com.github.javafaker.Faker;
import io.qameta.allure.Step;

public class CourierDataGenerator {
    static Faker faker = new Faker();

    public static final String LOGIN_FAKER = faker.name().username();

    @Step("Create courier with random data")
    public CourierModel createCourierWithRandomData() {
        return new CourierModel(
                faker.name().username(),
                faker.internet().password(),
                faker.name().firstName());
    }
}