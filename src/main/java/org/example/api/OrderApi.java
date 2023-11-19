package org.example.api;
import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.example.model.*;

import static io.restassured.RestAssured.given;
import static org.example.config.ConfigUrl.*;
public class OrderApi extends BaseApi {

    @Step("Создание заказа")
    public Response createOrder(CreateOrderRequest createOrderRequest, String accessToken) {
        return given()
                .headers(
                        "Authorization",
                        accessToken)
                .contentType(ContentType.JSON)
                .body(createOrderRequest)
                .when()
                .post(ORDER_URL);
    }

    @Step("Получение списка заказов пользователя")
    public Response getUserOrders(String accessToken) {
        return given()
                .headers(
                        "Authorization",
                        accessToken)
                .contentType(ContentType.JSON)
                .when()
                .get(ORDER_URL);
    }
}
