package org.example.helper;
import io.qameta.allure.Step;
import org.example.api.UserApi;
import org.example.model.DeleteUserResponse;

public class UserHelper {
    UserApi userApi = new UserApi();

    @Step("Удаление пользователя")
    public DeleteUserResponse deleteUserSuccess(String accessToken) {
        return userApi.deleteUser(accessToken)
                .then()
                .statusCode(200)
                .and()
                .extract()
                .as(DeleteUserResponse.class);
    }
}
