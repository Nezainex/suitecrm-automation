package tests.api;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import utils.listeners.TestListener;
import utils.listeners.TestSuiteListener;
import utils.config.ProjectConfig;
import utils.config.ConfigReader;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;

@Listeners({TestListener.class, TestSuiteListener.class})
public class AuthTest {
    private static final Logger logger = LoggerFactory.getLogger(AuthTest.class);

    // Получаем конфигурацию из файла
    private static final ProjectConfig CONFIG = ConfigReader.getConfig();

    @Test(description = "API: Логин через GraphQL")
    public void testLoginWithGraphQL() {
        getXsrfTokenAndPerformLogin();
    }

    @Step("Получаем XSRF-токен и выполняем запрос на логин")
    private void getXsrfTokenAndPerformLogin() {
        // 1. Делаем GET-запрос для получения XSRF-TOKEN
        Response getResponse = given()
                .baseUri(CONFIG.getBaseUrl()) // Используем baseUrl из конфигурации
                .header("User-Agent", "PostmanRuntime/7.43.0") // Устанавливаем User-Agent из Postman
                .get("/login")
                .then()
                .log().ifValidationFails()
                .extract().response();

        // Извлекаем куку XSRF-TOKEN
        String xsrfCookie = getResponse.getCookie("XSRF-TOKEN");
        logger.info("Получен XSRF-TOKEN: {}", xsrfCookie);

        // Проверяем, что токен получен
        if (xsrfCookie == null || xsrfCookie.isEmpty()) {
            throw new RuntimeException("XSRF-TOKEN не получен. Проверьте эндпоинт GET /login");
        }

        // 2. Формируем GraphQL-запрос для логина
        String graphqlPayload = """
        {
          "operationName": "login",
          "variables": {
            "username": "%s",
            "password": "%s"
          },
          "query": "mutation login($username: String!, $password: String!) {\\n  login(username: $username, password: $password) {\\n    token\\n    user {\\n      id\\n      username\\n    }\\n  }\\n}"
        }
        """.formatted(CONFIG.getValidUsername(), CONFIG.getValidPassword());  // Подставляем данные из конфигурации

        // 3. Выполняем POST-запрос
        Response loginResponse = given()
                .baseUri(CONFIG.getBaseUrl()) // Используем baseUrl из конфигурации
                .cookie("XSRF-TOKEN", xsrfCookie) // Передаем токен в куках
                .header("X-XSRF-TOKEN", xsrfCookie) // Передаем токен в заголовке
                .header("Referer", "http://localhost:8080/login") // Устанавливаем Referer
                .header("Origin", "http://localhost:8080") // Устанавливаем Origin
                .header("User-Agent", "PostmanRuntime/7.43.0") // Устанавливаем User-Agent
                .contentType("application/json") // Устанавливаем тип содержимого
                .body(graphqlPayload)
                .log().all() // Логируем весь запрос
                .post("/api/graphql") // Используем заранее определённый эндпоинт
                .then()
                .log().all() // Логируем весь ответ
                .extract().response();

        // Проверяем статус-код
        int statusCode = loginResponse.statusCode();
        logger.info("Статус-код запроса логина: {}", statusCode);
        assertEquals(statusCode, 200, "Ожидали 200 при логине");

        // Логируем тело ответа
        String responseBody = loginResponse.getBody().asString();
        logger.info("Ответ GraphQL: {}", responseBody);

        verifyResponseContainsUserData(responseBody);
    }

    @Step("Проверяем, что тело ответа содержит данные пользователя")
    private void verifyResponseContainsUserData(String responseBody) {
        assert responseBody.contains("token") : "Токен авторизации не найден в ответе";
        assert responseBody.contains("username") : "Имя пользователя не найдено в ответе";
    }
}
