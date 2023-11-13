package org.example.api;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.example.model.CreateUserRequest;
import org.example.model.EditUserRequest;
import org.example.model.LoginUserRequest;
import org.example.model.LogoutUserRequest;

import static io.restassured.RestAssured.given;
import static org.example.config.ConfigUrl.*;
import static org.hamcrest.Matchers.equalTo;

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
                        accessToken)
                .contentType(ContentType.JSON)
                .when()
                .delete(USER_URL);
    }

    @Step("Отправка запроса на логин")
    public Response loginUser(LoginUserRequest loginUserRequest) {
        return postSpecification()
                .body(loginUserRequest)
                .when()
                .post(LOGIN_USER_URL);
    }

    @Step("Отправка запроса на редактирование пользователя")
    public Response editUser(String accessToken, EditUserRequest editUserRequest) {
        return given()
                .headers(
                        "Authorization",
                        accessToken)
                .contentType(ContentType.JSON)
                .body(editUserRequest)
                .when()
                .patch(USER_URL);
    }

    @Step("Отправка запроса на редактирование пользователя")
    public Response logoutUser(LogoutUserRequest logoutUserRequest) {
        return postSpecification()
                .body(logoutUserRequest)
                .when()
                .post(LOGOUT_USER_URL);
    }
}
