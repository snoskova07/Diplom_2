package org.example.api;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.example.model.LogoutUserRequest;

import static org.example.config.ConfigUrl.LOGOUT_USER_URL;

public class LogoutApi extends BaseApi  {
    @Step("Отправка запроса на logout")
    public Response logoutUser(LogoutUserRequest logoutUserRequest) {
        return postSpecification()
                .body(logoutUserRequest)
                .when()
                .post(LOGOUT_USER_URL);
    }
}
