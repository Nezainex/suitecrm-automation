package tests.api;

import io.restassured.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.config.ConfigReader;

import static io.restassured.RestAssured.given;

public class CSRFHelper {
    private static final Logger LOG = LoggerFactory.getLogger(CSRFHelper.class);

    public static String getCSRFToken() {
        LOG.info("Пробуем получить CSRF-токен через POST: {}{}", ConfigReader.getConfig().getBaseUrl(), ConfigReader.getConfig().getLoginEndpoint());

        // Используем значения из конфигурации
        Response loginResponse = given()
                .baseUri(ConfigReader.getConfig().getBaseUrl()) // BASE_URL из конфигурации
                .contentType("application/x-www-form-urlencoded")
                .formParam("username", ConfigReader.getConfig().getValidUsername()) // VALID_USERNAME из конфигурации
                .formParam("password", ConfigReader.getConfig().getValidPassword()) // VALID_PASSWORD из конфигурации
                .post(ConfigReader.getConfig().getLoginEndpoint()) // LOGIN_ENDPOINT из конфигурации
                .then()
                .extract().response();

        int statusCode = loginResponse.statusCode();
        LOG.info("Получен статус-код: {}", statusCode);

        if (statusCode != 200) {
            LOG.error("Не удалось залогиниться! Код: {}", statusCode);
            throw new RuntimeException("Ошибка входа! Проверьте учетные данные или эндпоинт.");
        }

        // Достаём куку, где лежит XSRF-TOKEN
        String csrfToken = loginResponse.getCookie("XSRF-TOKEN");
        LOG.info("Из Set-Cookie извлечён XSRF-TOKEN: {}", csrfToken);

        if (csrfToken == null || csrfToken.isEmpty()) {
            LOG.error("CSRF-токен пуст или отсутствует!");
            throw new RuntimeException("CSRF-токен пуст или отсутствует!");
        }
        LOG.info("CSRF-токен успешно получен!");

        return csrfToken;
    }
}
