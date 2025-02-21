package tests.api;

import io.restassured.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.config.ProjectConfig;
import utils.config.ConfigReader;

import static io.restassured.RestAssured.given;

public class CSRFHelper {
    private static final Logger logger = LoggerFactory.getLogger(CSRFHelper.class);

    // Получаем конфигурацию из файла
    private static final ProjectConfig CONFIG = ConfigReader.getConfig();

    public static String getCSRFToken() {
        logger.info("Пробуем получить CSRF-токен через POST: {}{}", CONFIG.getBaseUrl(), CONFIG.getLoginEndpoint());

        // Используем значения из конфигурации
        Response loginResponse = given()
                .baseUri(CONFIG.getBaseUrl()) // BASE_URL из конфигурации
                .contentType("application/x-www-form-urlencoded")
                .formParam("username", CONFIG.getValidUsername()) // VALID_USERNAME из конфигурации
                .formParam("password", CONFIG.getValidPassword()) // VALID_PASSWORD из конфигурации
                .post(CONFIG.getLoginEndpoint()) // LOGIN_ENDPOINT из конфигурации
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
