package org.example;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.example.api.OrderApi;
import org.example.api.UserApi;
import org.example.helper.OrderHelper;
import org.example.helper.UserGenerator;
import org.example.helper.UserHelper;
import org.example.model.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class OrderTest {
    UserApi userApi;
    OrderApi orderApi;
    OrderHelper orderHelper;
    CreateUserRequest createUserRequest;
    CreateUserResponse createUserResponse;
    CreateOrderRequest createOrderRequest;
    CreateOrderResponse createOrderResponse;
    GetUserOrdersResponse getUserOrdersResponse;
    UserHelper userHelper;
    String accessToken;
    String email;
    String password;
    @Before
    public void setup() {
        userApi = new UserApi();
        orderApi = new OrderApi();
        userHelper = new UserHelper();
        orderHelper = new OrderHelper();
        //Создание пользователя
        createUserRequest = UserGenerator.getRandomUser();
        createUserResponse = userHelper.createUser(createUserRequest, 200);
        email = createUserRequest.getEmail();
        password = createUserRequest.getPassword();
        accessToken = createUserResponse.getAccessToken();
    }

    @After
    public void deleteUser() {
        //Удаление пользователя
        userHelper.deleteUser(accessToken, 202);
    }

    @Test
    @DisplayName("Успешное создание заказа с авторизацией")
    public void successCreateOrder() {
        //Создание списка из 3 ингредиентов для формирования заказа
        List<String> orderList = orderHelper.generateIngredientsList();
        //Создание запроса
        createOrderRequest = new CreateOrderRequest(orderList);
        //Отправка запроса
        createOrderResponse = orderHelper.createOrderResponse(createOrderRequest, accessToken, 200);
        Assert.assertTrue(createOrderResponse.getSuccess());
        Assert.assertEquals(orderList.get(0), createOrderResponse.getOrder().getIngredients().get(0).get_id());
        Assert.assertEquals(orderList.get(1), createOrderResponse.getOrder().getIngredients().get(1).get_id());
        Assert.assertEquals(orderList.get(2), createOrderResponse.getOrder().getIngredients().get(2).get_id());
    }

    @Test
    @DisplayName("Создание заказа без авторизации")
    public void successCreateOrderWithoutLogin() {
        //Создание списка из 3 ингредиентов для формирования заказа
        List<String> orderList = orderHelper.generateIngredientsList();
        //Создание заказа
        createOrderRequest = new CreateOrderRequest(orderList);
        createOrderResponse = orderHelper.createOrderResponse(createOrderRequest, "",200);
        Assert.assertTrue(createOrderResponse.getSuccess());
        Assert.assertNotNull(createOrderResponse.getOrder().getNumber());
    }

    @Test
    @DisplayName("Создание заказа без ингредиентов невозможно")
    public void createOrderWithoutIngredientsIsNotPossible() {
        //Создание заказа
        List<String> orderList = new ArrayList<>();
        createOrderRequest = new CreateOrderRequest(orderList);
        createOrderResponse = orderHelper.createOrderResponse(createOrderRequest, accessToken,  400);
        Assert.assertFalse(createOrderResponse.getSuccess());
        Assert.assertEquals(createOrderResponse.getMessage(), "Ingredient ids must be provided");
    }

    @Test
    @DisplayName("Создание заказа с неверным хешем ингредиента невозможно")
    public void createOrderWithWrongHashIsNotPossible() {
        //Создание заказа
        List<String> orderList = new ArrayList<>();
        orderList.add("wronghash");
        createOrderRequest = new CreateOrderRequest(orderList);
        Response response = orderApi.createOrder(createOrderRequest, accessToken);
        response.then().statusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR);
    }

    @Test
    @DisplayName("Получение заказов пользователя с авторизацией")
    public void getUserOrders() {
        //Создание заказа
        getUserOrdersResponse = orderHelper.getUserOrdersResponse(accessToken, 200);
        Assert.assertTrue(getUserOrdersResponse.getSuccess());
    }

    @Test
    @DisplayName("Получение заказов пользователя с авторизацией")
    public void getUserOrdersWithoutAuthorizationFailed() {
        //Создание заказа
        getUserOrdersResponse = orderHelper.getUserOrdersResponse("wrong access token", 403);
        Assert.assertFalse(getUserOrdersResponse.getSuccess());
    }
}
