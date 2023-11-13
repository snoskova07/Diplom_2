package org.example.api;
import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.example.model.*;

import java.util.List;

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

    @Step("Получение спмска ингредиентов")
    public Response getIngredients() {
        return postSpecification()
                .when()
                .get(INGREDIENTS_URL);
    }

    @Step("Получение списка ингредиентов")
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
