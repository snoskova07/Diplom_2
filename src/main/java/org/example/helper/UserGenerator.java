package org.example.helper;

import io.qameta.allure.Step;
import org.apache.commons.lang3.RandomStringUtils;
import org.example.model.CreateUserRequest;

public class UserGenerator {

    @Step("Генерация данных пользователя")
    public static CreateUserRequest getRandomUser() {
        String email = "mail" + RandomStringUtils.randomAlphabetic(2).toLowerCase() + '@' + RandomStringUtils.randomAlphabetic(5).toLowerCase() + ".qwe";
        String password = "passwd" + RandomStringUtils.randomAlphabetic(5);
        String name = "name" + RandomStringUtils.randomAlphabetic(3);
        return new CreateUserRequest(email, password, name);
    }

}
