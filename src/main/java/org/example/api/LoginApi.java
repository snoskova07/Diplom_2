package org.example.api;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.example.model.LoginUserRequest;
import static org.example.config.ConfigUrl.*;
public class LoginApi extends BaseApi {

    @Step("Отправка запроса на login")
    public Response loginUser(LoginUserRequest loginUserRequest) {
        return postSpecification()
                .body(loginUserRequest)
                .when()
                .post(LOGIN_USER_URL);
    }
}
