package org.example.api;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.example.model.CreateUserRequest;
import static org.example.config.ConfigUrl.*;
public class RegisterApi extends BaseApi {

    @Step("Отправка запроса на создание пользователя")
    public Response createUser(CreateUserRequest createUserRequest) {
        return postSpecification()
                .body(createUserRequest)
                .when()
                .post(REGISTER_USER_URL);
    }
}
