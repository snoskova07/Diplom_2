package org.example.api;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.example.model.EditUserRequest;
import static io.restassured.RestAssured.given;
import static org.example.config.ConfigUrl.*;

public class UserApi extends BaseApi {
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
}
