package org.example.helper;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.example.api.IngredientApi;
import org.example.api.OrderApi;
import org.example.model.*;
import org.hamcrest.MatcherAssert;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.hamcrest.CoreMatchers.notNullValue;

public class OrderHelper {
    OrderApi orderApi = new OrderApi();
    IngredientApi ingredientApi = new IngredientApi();

    @Step("Получение списка ингредиентов")
    public GetIngredientsResponse getIngredients() {
        Response response = ingredientApi.getAllIngredients();
        GetIngredientsResponse getOrderResponse = response.as(GetIngredientsResponse.class);
        return getOrderResponse;
    }

    @Step("Создание заказа")
    public CreateOrderResponse createOrderResponse(CreateOrderRequest createOrderRequest, String accessToken, int status) {
        Response response = orderApi.createOrder(createOrderRequest, accessToken);
        response.then().assertThat().statusCode(status);
        CreateOrderResponse createOrderResponse = response.as(CreateOrderResponse.class);
        return createOrderResponse;
    }

    @Step("Получение списка заказов конкретного пользователя")
    public GetUserOrdersResponse getUserOrdersResponse(String accessToken, int status) {
        Response response = orderApi.getUserOrders(accessToken);
        response.then().assertThat().statusCode(status);
        GetUserOrdersResponse getUserOrdersResponse = response.as(GetUserOrdersResponse.class);
        return getUserOrdersResponse;
    }

    @Step("Создание списка ингредиентов для оформления заказа")
    public List<String> generateIngredientsList() {

        //Отдельные списки с id для каждого типа
        ArrayList<String> bunIdList = new ArrayList<>();
        ArrayList<String> mainIdList = new ArrayList<>();
        ArrayList<String> sauceIdList = new ArrayList<>();

        //Список того, что попадет в заказ
        ArrayList<String> orderList = new ArrayList<>();

        // Получение списка всех доступных ингредиентов
        GetIngredientsResponse getIngredientsResponse = this.getIngredients();
        MatcherAssert.assertThat(getIngredientsResponse, notNullValue());
        List<DataBurger> ingredients = getIngredientsResponse.getData();

        //Сортировка всех ингредиентов по спискам
        for (DataBurger ingredient : ingredients) {

            if ((ingredient.getType()).equals("bun")) {
                bunIdList.add(ingredient.get_id());
            }
            if ((ingredient.getType()).equals("main")) {
                mainIdList.add(ingredient.get_id());
            }
            if ((ingredient.getType()).equals("sauce")) {
                sauceIdList.add(ingredient.get_id());
            }
        }

        //Получение рандомных элементов из списков
        Random rand = new Random();
        String idBun = bunIdList.get(rand.nextInt(bunIdList.size()));
        String idMain = mainIdList.get(rand.nextInt(mainIdList.size()));
        String idSauce = sauceIdList.get(rand.nextInt(sauceIdList.size()));

        //Формирование списка для заказа
        orderList.add(idBun);
        orderList.add(idMain);
        orderList.add(idSauce);
        return orderList;
    }

}
