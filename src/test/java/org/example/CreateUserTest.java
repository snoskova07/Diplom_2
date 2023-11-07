package org.example;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.example.api.UserApi;
import org.example.helper.UserGenerator;
import org.example.helper.UserHelper;
import org.example.model.CreateUserRequest;
import org.example.model.CreateUserResponse;
import org.example.model.DeleteUserRequest;
import org.example.model.DeleteUserResponse;
import org.hamcrest.MatcherAssert;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.example.config.ConfigUrl.DELETE_USER_URL;
import static org.hamcrest.CoreMatchers.notNullValue;

public class CreateUserTest {
    UserApi userApi;
    CreateUserRequest createUserRequest;
    DeleteUserRequest deleteUserRequest;
    DeleteUserResponse deleteUserResponse;
    UserHelper userHelper;

    @Before
    public void setUp() {
        userApi = new UserApi();
    }

    @Test
    @DisplayName("Успешное создание пользователя ")
    public void createUser() {
        createUserRequest = UserGenerator.getRandomUser();
        Response createResponse = userApi.createUser(createUserRequest);
        CreateUserResponse createUserResponse = createResponse.as(CreateUserResponse.class);
        MatcherAssert.assertThat(createUserResponse, notNullValue());
        Assert.assertTrue(createUserResponse.success);

        String accessToken = createUserResponse.getAccessToken();
        deleteUserRequest = new DeleteUserRequest(accessToken);
     //   deleteUserResponse = userHelper.deleteUserSuccess(accessToken);
        deleteUserResponse =  given()
                .headers(
                        "Authorization",
                        accessToken)
                .contentType(ContentType.JSON)
                .when()
                .delete(DELETE_USER_URL)
                .then()
                .statusCode(202)
                .and()
                .extract()
                .as(DeleteUserResponse.class);
        Assert.assertTrue(deleteUserResponse.success);
        Assert.assertEquals(deleteUserResponse.getMessage(), "User successfully removed");
   //     System.out.println(deleteUserResponse.getMessage());
    }



}
