package org.example;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.example.api.UserApi;
import org.example.helper.UserGenerator;
import org.example.helper.UserHelper;
import org.example.model.*;
import org.hamcrest.MatcherAssert;
import org.junit.Assert;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.notNullValue;
public class EditUserTest {
    UserApi userApi;
    UserHelper userHelper;
    CreateUserRequest createUserRequest;
    CreateUserResponse createUserResponse;
    EditUserRequest editUserRequest;
    EditUserResponse editUserResponse;
    LoginUserRequest loginUserRequest;
    LoginUserResponse loginUserResponse;
    LogoutUserRequest logoutUserRequest;
    LogoutUserResponse logoutUserResponse;
    String email;
    String password;
    String name;
    String accessToken;
    String refreshToken;
    @Before
    public void setup() {
        userApi = new UserApi();
        userHelper = new UserHelper();
        //Создание пользователя
        createUserRequest = UserGenerator.getRandomUser();
        createUserResponse = userHelper.createUser(createUserRequest, 200);
        email = createUserRequest.getEmail();
        password = createUserRequest.getPassword();
        name = createUserRequest.getName();
        accessToken = createUserResponse.getAccessToken();
        refreshToken = createUserResponse.getRefreshToken();
        //Авторизация
        loginUserResponse = userHelper.loginUser(email, password, 200);
        accessToken = createUserResponse.getAccessToken();
    }

    @After
    public void deleteUser() {
        System.out.println("Удаление пользователя");
        userHelper.deleteUser(accessToken, 202);
    }

    @Test
    @DisplayName("Изменение email пользователя и авторизация с новым email")
    public void successChangeEmail() {
        System.out.println("Задание нового email");
        email = "sv@asd123.ru";
        editUserRequest = new EditUserRequest(email, password, name);

        System.out.println("Отправка запроса на изменение");
        editUserResponse = userHelper.editUserSuccess(accessToken, editUserRequest, 200);

        System.out.println("Проверка ответа");
        MatcherAssert.assertThat(editUserResponse, notNullValue());
        Assert.assertTrue(editUserResponse.getSuccess());
        Assert.assertEquals(editUserResponse.getUser().getEmail(), email);

        System.out.println("Logout");
        String refreshToken = loginUserResponse.getRefreshToken();
        userHelper.logoutUser(refreshToken, 200);

        System.out.println("Login с новым email");
        loginUserResponse = userHelper.loginUser(email, password, 200);
        MatcherAssert.assertThat(loginUserResponse, notNullValue());
        Assert.assertTrue(loginUserResponse.getSuccess());
        Assert.assertEquals(loginUserResponse.getUser().getEmail(), email);
    }

    @Test
    @DisplayName("Изменение password пользователя с авторизацией")
    public void successChangePassword() {
        System.out.println("Задание нового password");
        password = "password";
        editUserRequest = new EditUserRequest(email, password, name);

        System.out.println("Отправка запроса на изменение");
        editUserResponse = userHelper.editUserSuccess(accessToken, editUserRequest, 200);

        System.out.println("Проверка ответа");
        MatcherAssert.assertThat(editUserResponse, notNullValue());
        Assert.assertTrue(editUserResponse.getSuccess());

        System.out.println("Logout");
        String refreshToken = loginUserResponse.getRefreshToken();
        userHelper.logoutUser(refreshToken, 200);

        System.out.println("Login с новым password");
        loginUserResponse = userHelper.loginUser(email, password, 200);
        MatcherAssert.assertThat(loginUserResponse, notNullValue());
        Assert.assertTrue(loginUserResponse.getSuccess());
    }

    @Test
    @DisplayName("Изменение name пользователя с авторизацией")
    public void successChangeName() {
        System.out.println("Задание нового name");
        name = "name";
        editUserRequest = new EditUserRequest(email, password, name);

        System.out.println("Отправка запроса на изменение");
        editUserResponse = userHelper.editUserSuccess(accessToken, editUserRequest, 200);

        System.out.println("Проверка ответа");
        MatcherAssert.assertThat(editUserResponse, notNullValue());
        Assert.assertTrue(editUserResponse.getSuccess());

        System.out.println("Logout");
        String refreshToken = loginUserResponse.getRefreshToken();
        userHelper.logoutUser(refreshToken, 200);

        System.out.println("Login с новым name");
        loginUserResponse = userHelper.loginUser(email, password, 200);
        MatcherAssert.assertThat(loginUserResponse, notNullValue());
        Assert.assertTrue(loginUserResponse.getSuccess());
        Assert.assertEquals(loginUserResponse.getUser().getName(), name);

    }

    @Test
    @DisplayName("Изменение email пользователя без авторизации невозможно")
    public void failedChangeEmail() {
        System.out.println("Logout");
        String refreshToken = loginUserResponse.getRefreshToken();
        userHelper.logoutUser(refreshToken, 200);

        System.out.println("Задание нового email");
        email = "sv@qwe.ru";

        EditUserRequest editUserRequest = new EditUserRequest(email, password, name);
        System.out.println("Отправка запроса на изменение");
        editUserResponse = userHelper.editUserSuccess("", editUserRequest, 401);
        System.out.println("Проверка ответа");
        MatcherAssert.assertThat(editUserResponse, notNullValue());
        Assert.assertFalse(editUserResponse.getSuccess());
        Assert.assertEquals("You should be authorised", editUserResponse.getMessage());
    }

    @Test
    @DisplayName("Изменение password пользователя без авторизации невозможно")
    public void failedChangePassword() {
        System.out.println("Logout");
        String refreshToken = loginUserResponse.getRefreshToken();
        userHelper.logoutUser(refreshToken, 200);

        System.out.println("Задание нового email");
        password = "passwd";

        EditUserRequest editUserRequest = new EditUserRequest(email, password, name);
        System.out.println("Отправка запроса на изменение");
        editUserResponse = userHelper.editUserSuccess("", editUserRequest, 401);
        System.out.println("Проверка ответа");
        MatcherAssert.assertThat(editUserResponse, notNullValue());
        Assert.assertFalse(editUserResponse.getSuccess());
        Assert.assertEquals("You should be authorised", editUserResponse.getMessage());
    }

    @Test
    @DisplayName("Изменение name пользователя без авторизации невозможно")
    public void failedChangeName() {
        System.out.println("Logout");
        String refreshToken = loginUserResponse.getRefreshToken();
        userHelper.logoutUser(refreshToken, 200);

        System.out.println("Задание нового email");
        name = "name1";

        EditUserRequest editUserRequest = new EditUserRequest(email, password, name);
        System.out.println("Отправка запроса на изменение");
        editUserResponse = userHelper.editUserSuccess("", editUserRequest, 401);
        System.out.println("Проверка ответа");
        MatcherAssert.assertThat(editUserResponse, notNullValue());
        Assert.assertFalse(editUserResponse.getSuccess());
        Assert.assertEquals("You should be authorised", editUserResponse.getMessage());
    }

}
