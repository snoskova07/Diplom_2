package org.example;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.example.api.UserApi;
import org.example.helper.UserGenerator;
import org.example.helper.UserHelper;
import org.example.model.CreateUserRequest;
import org.example.model.CreateUserResponse;
import org.example.model.LoginUserRequest;
import org.example.model.LoginUserResponse;
import org.hamcrest.MatcherAssert;
import org.junit.Assert;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.notNullValue;

public class LoginUserTest {
    private UserApi userApi;
    private  CreateUserRequest createUserRequest;
    private CreateUserResponse createUserResponse;
    private UserHelper userHelper;
    private String accessToken;
    private  String email;
    private String password;
    @Before
    public void setup() {
        userApi = new UserApi();
        userHelper = new UserHelper();

        //Создание пользователя
        createUserRequest = UserGenerator.getRandomUser();
        createUserResponse = userHelper.createUser(createUserRequest, 200);
        email = createUserRequest.getEmail();
        password = createUserRequest.getPassword();
    }

    @After
    public void deleteUser() {
        //Удаление пользователя
        accessToken = createUserResponse.getAccessToken();
        userHelper.deleteUser(accessToken, 202);
    }

    @Test
    @DisplayName("Логин под существующим пользователем")
    public void successLoginByUser() {
        //Авторизация
        LoginUserResponse loginResponse = userHelper.loginUser(email, password, 200);
        MatcherAssert.assertThat(loginResponse, notNullValue());
        Assert.assertTrue(loginResponse.getSuccess());
    }

    @Test
    @DisplayName("Логин с неверным email")
    public void failedLoginByUserWithWrongEmail() {
        //Авторизация
        email = "wrong";
        LoginUserResponse loginResponse = userHelper.loginUser(email, password, 401);
        Assert.assertFalse(loginResponse.getSuccess());
        Assert.assertEquals("email or password are incorrect", loginResponse.getMessage());
    }

    @Test
    @DisplayName("Логин с неверным паролем")
    public void failedLoginByUserWithWrongPassword() {
        //Авторизация
        password = "wrong";
        LoginUserResponse loginResponse = userHelper.loginUser(email, password, 401);
        Assert.assertFalse(loginResponse.getSuccess());
        Assert.assertEquals("email or password are incorrect", loginResponse.getMessage());
    }

}
