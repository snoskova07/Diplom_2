package org.example.api;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.example.model.CreateUserRequest;
import org.example.model.DeleteUserRequest;

import static io.restassured.RestAssured.given;
import static org.example.config.ConfigUrl.*;
public class UserApi extends BaseApi {

    @Step("Отправка запроса на создание пользователя")
    public Response createUser(CreateUserRequest createUserRequest) {
        return postSpecification()
                .body(createUserRequest)
                .when()
                .post(REGISTER_USER_URL);
    }

    @Step("Отправка запроса на удаление пользователя")
    public Response deleteUser(String accessToken) {
        return given()
                .headers(
                        "Authorization",
                        accessToken,
                        ContentType.JSON,
                        "Accept",
                        ContentType.JSON)
                .when()
                .delete(DELETE_USER_URL);
    }
}
