package org.example;

import io.qameta.allure.junit4.DisplayName;
import org.example.api.UserApi;
import org.example.helper.UserGenerator;
import org.example.helper.UserHelper;
import org.example.model.CreateUserRequest;
import org.example.model.CreateUserResponse;
import org.hamcrest.MatcherAssert;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.notNullValue;

public class CreateUserTest {
    UserApi userApi;
    UserHelper userHelper;
    CreateUserRequest createUserRequest;
    CreateUserResponse createUserResponse;
    CreateUserResponse createExistUserResponse;
    String accessToken;

    @Before
    public void setup() {
        userApi = new UserApi();
        userHelper = new UserHelper();
        createUserRequest = UserGenerator.getRandomUser();
    }

    @Test
    @DisplayName("Успешное создание уникального пользователя")
    public void createUserSuccess() {
        // Создание пользователя
        createUserResponse = userHelper.createUser(createUserRequest, 200);
        MatcherAssert.assertThat(createUserResponse, notNullValue());
        Assert.assertTrue(createUserResponse.getSuccess());
        //Удаление пользователя
        accessToken = createUserResponse.getAccessToken();
        userHelper.deleteUser(accessToken, 202);
    }

    @Test
    @DisplayName("Создание пользователя, который уже зарегистрирован невозможно")
    public void createAlreadyRegisteredUserIsNotPossible() {
        createUserResponse = userHelper.createUser(createUserRequest, 200);
        createExistUserResponse = userHelper.createUser(createUserRequest, 403);
        Assert.assertFalse(createExistUserResponse.getSuccess());
        Assert.assertEquals(createExistUserResponse.getMessage(), "User already exists");
        //Удаление пользователя
        accessToken = createUserResponse.getAccessToken();
        userHelper.deleteUser(accessToken, 202);
    }

    @Test
    @DisplayName("Создание пользователя с пустым полем email невозможно")
    public void createUserWithoutEmailIsNotPossible() {
        createUserRequest.setEmail("");
        createUserResponse = userHelper.createUser(createUserRequest, 403);
        Assert.assertFalse(createUserResponse.getSuccess());
        Assert.assertEquals(createUserResponse.getMessage(), "Email, password and name are required fields");
    }

    @Test
    @DisplayName("Создание пользователя с пустым полем password невозможно")
    public void createUserWithoutPasswordIsNotPossible() {
        createUserRequest.setPassword("");
        createUserResponse = userHelper.createUser(createUserRequest, 403);
        Assert.assertFalse(createUserResponse.getSuccess());
        Assert.assertEquals(createUserResponse.getMessage(), "Email, password and name are required fields");
    }

    @Test
    @DisplayName("Создание пользователя с пустым полем name невозможно")
    public void createUserWithoutNameIsNotPossible() {
        createUserRequest.setName("");
        createUserResponse = userHelper.createUser(createUserRequest, 403);
        Assert.assertFalse(createUserResponse.getSuccess());
        Assert.assertEquals(createUserResponse.getMessage(), "Email, password and name are required fields");
    }
}
