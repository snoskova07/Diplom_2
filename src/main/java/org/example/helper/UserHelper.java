package org.example.helper;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.example.api.LoginApi;
import org.example.api.LogoutApi;
import org.example.api.RegisterApi;
import org.example.api.UserApi;
import org.example.model.*;

public class UserHelper {
    RegisterApi registerApi = new RegisterApi();
    UserApi userApi = new UserApi();
    LoginApi loginApi = new LoginApi();
    LogoutApi logoutApi = new LogoutApi();

    @Step("Создание пользователя")
    public CreateUserResponse createUser(CreateUserRequest createUserRequest, int status) {
        Response response = registerApi.createUser(createUserRequest);
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
        Response response = loginApi.loginUser(loginUserRequest);
        response.then().assertThat().statusCode(status);
        LoginUserResponse loginUserResponse = response.as(LoginUserResponse.class);
        return loginUserResponse;
    }

    @Step("Выход")
    public LogoutUserResponse logoutUser(String refreshToken, int status) {
        LogoutUserRequest logoutUserRequest = new LogoutUserRequest(refreshToken);
        Response response = logoutApi.logoutUser(logoutUserRequest);
        response.then().assertThat().statusCode(status);
        LogoutUserResponse logoutUserResponse = response.as(LogoutUserResponse.class);
        return logoutUserResponse;
    }
}
