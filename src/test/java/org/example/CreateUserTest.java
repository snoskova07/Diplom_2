package org.example;

import io.qameta.allure.junit4.DisplayName;
import org.example.api.UserApi;
import org.example.helper.UserGenerator;
import org.example.helper.UserHelper;
import org.example.model.CreateUserRequest;
import org.example.model.CreateUserResponse;
import org.hamcrest.MatcherAssert;
import org.junit.*;

import static org.hamcrest.CoreMatchers.notNullValue;

public class CreateUserTest {
    private UserApi userApi;
    private UserHelper userHelper;
    private CreateUserRequest createUserRequest;
    private CreateUserResponse createUserResponse;
    private CreateUserResponse createExistUserResponse;
    private String accessToken;

    @Before
    public void setup() {
        userApi = new UserApi();
        userHelper = new UserHelper();
        createUserRequest = UserGenerator.getRandomUser();
    }

    @After
    public void deleteUser() {
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
    }

    @Test
    @DisplayName("Создание пользователя, который уже зарегистрирован невозможно")
    public void createAlreadyRegisteredUserIsNotPossible() {
        createUserResponse = userHelper.createUser(createUserRequest, 200);
        createExistUserResponse = userHelper.createUser(createUserRequest, 403);
        Assert.assertFalse(createExistUserResponse.getSuccess());
        Assert.assertEquals("User already exists", createExistUserResponse.getMessage());
    }

    @Test()
    @DisplayName("Создание пользователя с пустым полем email невозможно")
    public void createUserWithoutEmailIsNotPossible() {
        createUserRequest.setEmail("");
        createUserResponse = userHelper.createUser(createUserRequest, 403);
        Assert.assertFalse(createUserResponse.getSuccess());
        Assert.assertEquals("Email, password and name are required fields", createUserResponse.getMessage());
    }

    @Test
    @DisplayName("Создание пользователя с пустым полем password невозможно")
    public void createUserWithoutPasswordIsNotPossible() {
        createUserRequest.setPassword("");
        createUserResponse = userHelper.createUser(createUserRequest, 403);
        Assert.assertFalse(createUserResponse.getSuccess());
        Assert.assertEquals("Email, password and name are required fields", createUserResponse.getMessage());
    }

    @Test
    @DisplayName("Создание пользователя с пустым полем name невозможно")
    public void createUserWithoutNameIsNotPossible() {
        createUserRequest.setName("");
        createUserResponse = userHelper.createUser(createUserRequest, 403);
        Assert.assertFalse(createUserResponse.getSuccess());
        Assert.assertEquals("Email, password and name are required fields", createUserResponse.getMessage());
    }
}
