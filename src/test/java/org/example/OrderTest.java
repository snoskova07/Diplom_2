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

import static org.hamcrest.CoreMatchers.notNullValue;

public class OrderTest {
    Response loginResponse;
    LoginUserRequest loginUserRequest;
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
    @DisplayName("Создание заказа с авторизацией")
    public void successCreateOrder() {
        //Создание заказа
        createOrderRequest = new CreateOrderRequest(orderHelper.generateIngredientsList());
        System.out.println(createOrderRequest.getIngredients());
        createOrderResponse = orderHelper.createOrderResponse(createOrderRequest, accessToken, 200);
        Assert.assertTrue(createOrderResponse.getSuccess());
    }

    @Test
    @DisplayName("Создание заказа без авторизации невозможно")
    public void createOrderWithoutLoginIsNotPossible() {
        //Создание заказа
        createOrderRequest = new CreateOrderRequest(orderHelper.generateIngredientsList());
        createOrderResponse = orderHelper.createOrderResponse(createOrderRequest, "",401);
        Assert.assertFalse(createOrderResponse.getSuccess());
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
        //Авторизация
        LoginUserResponse loginResponse = userHelper.loginUser(email, password, 200);
        //Создание заказа
        getUserOrdersResponse = orderHelper.getUserOrdersResponse(accessToken, 200);
        Assert.assertTrue(getUserOrdersResponse.getSuccess());
        //Проверка кода ответа
    }

    @Test
    @DisplayName("Получение заказов пользователя с авторизацией")
    public void getUserOrdersWithoutAuthorizationFailed() {
        //Авторизация
        //Создание заказа
        getUserOrdersResponse = orderHelper.getUserOrdersResponse("wrong access token", 403);
        Assert.assertFalse(getUserOrdersResponse.getSuccess());
        //Проверка кода ответа
    }
}
