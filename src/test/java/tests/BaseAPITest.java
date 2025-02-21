package tests;

import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeClass;
import utils.config.ConfigReader;
import utils.config.ProjectConfig;

/**
 * Базовый класс для API-тестов.
 * Наследует BaseTest, но без UI-инициализации.
 * Подключает RestAssured и задаёт общую логику для запросов (базовый URL, хедеры и т.д.).
 */
public abstract class BaseAPITest extends BaseTest {

//    private static final Logger logger = LoggerFactory.getLogger(BaseAPITest.class);
//    protected static RequestSpecification baseRequest;
//    private static final ProjectConfig CONFIG = ConfigReader.getConfig();
//
//    @BeforeClass
//    public void setupAPI() {
//        // Задаём базовую URI для RestAssured,
//        // например, baseUrl, если оно у нас в YAML
//        RestAssured.baseURI = CONFIG.getBaseUrl(); // или другой ключ, если у вас отдельный apiUrl
//
//        // Задаём базовую спецификацию, которую можно переиспользовать в тестах
//        baseRequest = RestAssured
//                .given()
//                .log().all()
//                .contentType("application/json"); // или любой другой формат
//
//        // Если нужно — авторизация, токены и т. д.
//        // Пример авторизации через базовый token (заголовки):
//        // baseRequest.header("Authorization", "Bearer " + getAuthToken());
//    }
//
//    /**
//     * Пример метода, который возвращает токен (если используется Auth через отдельный endpoint).
//     * Можно вызывать в конкретных тестах, где нужно.
//     */
//    protected String getAuthToken() {
//        // Допустим, в YAML или config есть логин/пароль
//        String user = CONFIG.getValidUsername();
//        String pass = CONFIG.getValidPassword();
//
//        // Пример вызова эндпоинта /auth:
//        String token = RestAssured
//                .given()
//                .log().all()
//                .contentType("application/json")
//                .body("{\"username\": \"" + user + "\", \"password\":\"" + pass + "\"}")
//                .when()
//                .post("/auth")
//                .then()
//                .statusCode(200)
//                .extract()
//                .path("token");
//
//        return token;
//    }
}

