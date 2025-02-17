package tests.api;

import io.restassured.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static io.restassured.RestAssured.given;

public class CSRFHelper {
    private static final Logger logger = LoggerFactory.getLogger(CSRFHelper.class);

    // Правим на тот эндпоинт, который реально существует:
    private static final String BASE_URL = "http://localhost:8080";
    private static final String LOGIN_ENDPOINT = "/login ";

    // Логин/пароль меняйте при необходимости
    private static final String VALID_USERNAME = "user";
    private static final String VALID_PASSWORD = "bitnami";

    public static String getCSRFToken() {
        logger.info("Пробуем получить CSRF-токен через POST: {}{}", BASE_URL, LOGIN_ENDPOINT);

        Response loginResponse = given()
                .baseUri("http://localhost:8080")
                .contentType("application/x-www-form-urlencoded")
                .formParam("username", VALID_USERNAME)
                .formParam("password", VALID_PASSWORD)
                .post("/login")
                .then()
                .extract().response();

        int statusCode = loginResponse.statusCode();
        logger.info("Получен статус-код: {}", statusCode);

        if (statusCode != 200) {
            logger.error("Не удалось залогиниться! Код: {}", statusCode);
            throw new RuntimeException("Ошибка входа! Проверьте учетные данные или эндпоинт.");
        }

        // Достаём куку, где лежит XSRF-TOKEN
        String csrfToken = loginResponse.getCookie("XSRF-TOKEN");
        logger.info("Из Set-Cookie извлечён XSRF-TOKEN: {}", csrfToken);

        if (csrfToken == null || csrfToken.isEmpty()) {
            logger.error("CSRF-токен пуст или отсутствует!");
            throw new RuntimeException("CSRF-токен пуст или отсутствует!");
        }
        logger.info("CSRF-токен успешно получен!");

        return csrfToken;
    }
}
