package org.example.api;

import io.qameta.allure.Step;
import io.restassured.response.Response;

import static org.example.config.ConfigUrl.INGREDIENTS_URL;

public class IngredientApi extends BaseApi {
    @Step("Получение списка всех ингредиентов")
    public Response getAllIngredients() {
        return postSpecification()
                .when()
                .get(INGREDIENTS_URL);
    }
}
