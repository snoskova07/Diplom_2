package org.example.helper;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.example.api.UserApi;
import org.example.model.*;

import static org.hamcrest.Matchers.notNullValue;

public class UserHelper {
    UserApi userApi = new UserApi();

    @Step("Создание пользователя")
    public CreateUserResponse createUser(CreateUserRequest createUserRequest, int status) {
        Response response = userApi.createUser(createUserRequest);
        response.then().statusCode(status);
        CreateUserResponse createUserResponse = response.as(CreateUserResponse.class);
        return createUserResponse;
    }

    @Step("Редактироание пользователя")
    public EditUserResponse editUserSuccess(String accessToken, EditUserRequest editUserRequest, int status) {
        Response response = userApi.editUser(accessToken, editUserRequest);
        response.then().assertThat().statusCode(status);
        EditUserResponse editUserResponse = response.as(EditUserResponse.class);
        return editUserResponse;
    }

    @Step("Удаление пользователя")
    public DeleteUserResponse deleteUser(String accessToken, int status) {
           Response response = userApi.deleteUser(accessToken);
           response.then().assertThat().statusCode(status);
           DeleteUserResponse deleteUserResponse = response.as(DeleteUserResponse.class);
           return deleteUserResponse;
    }

    @Step("Авторизация")
    public LoginUserResponse loginUser(String email, String password, int status) {
        LoginUserRequest loginUserRequest = new LoginUserRequest(email, password);
        Response response = userApi.loginUser(loginUserRequest);
        response.then().assertThat().statusCode(status);
        LoginUserResponse loginUserResponse = response.as(LoginUserResponse.class);
        return loginUserResponse;
    }

    @Step("Выход")
    public LogoutUserResponse logoutUser(String refreshToken, int status) {
        LogoutUserRequest logoutUserRequest = new LogoutUserRequest(refreshToken);
        Response response = userApi.logoutUser(logoutUserRequest);
        response.then().assertThat().statusCode(status);
        LogoutUserResponse logoutUserResponse = response.as(LogoutUserResponse.class);
        return logoutUserResponse;
    }


}
