package org.example.config;

public class ConfigUrl {
    public static final String BASE_URL = "http://stellarburgers.nomoreparties.site/";

    //RegisterApi
    public static final String REGISTER_USER_URL = BASE_URL + "api/auth/register/";

    //LoginApi
    public static final String LOGIN_USER_URL = BASE_URL + "api/auth/login/";

    //LogoutApi
    public static final String LOGOUT_USER_URL = BASE_URL + "api/auth/logout/";

    //UserApi (редактирование и удаление)
    public static final String USER_URL = BASE_URL + "api/auth/user/";

    //OrderApi
    public static final String ORDER_URL = BASE_URL + "api/orders/";

    //IngredientApi
    public static final String INGREDIENTS_URL = BASE_URL + "api/ingredients/";
}
