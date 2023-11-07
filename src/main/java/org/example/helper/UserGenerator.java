package org.example.helper;

import io.qameta.allure.Step;
import org.apache.commons.lang3.RandomStringUtils;
import org.example.model.CreateUserRequest;

public class UserGenerator {

    @Step("Генерация данных пользователя")
    public static CreateUserRequest getRandomUser() {
        String email = RandomStringUtils.randomAlphabetic(5).toLowerCase() + '@' + RandomStringUtils.randomAlphabetic(5).toLowerCase() + ".qwe";
        String password = RandomStringUtils.randomAlphabetic(10);
        String name = RandomStringUtils.randomAlphabetic(10);
        return new CreateUserRequest(email, password, name);
    }

}
