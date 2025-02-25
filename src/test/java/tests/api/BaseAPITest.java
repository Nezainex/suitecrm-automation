package tests.api;

import tests.BaseTest;

/**
 * Базовый класс для API-тестов.
 * Наследует BaseTest, но без UI-инициализации.
 * Подключает RestAssured и задаёт общую логику для запросов (базовый URL, хедеры и т.д.).
 */
public abstract class BaseAPITest extends BaseTest {

//    private static final Logger LOG = LoggerFactory.getLogger(BaseAPITest.class);
//    protected static RequestSpecification baseRequest;
//
//    @BeforeClass
//    public void setupAPI() {
//        // Задаём базовую URI для RestAssured,
//        // например, baseUrl, если оно у нас в YAML
//        RestAssured.baseURI = ConfigReader.getConfig().getBaseUrl(); // или другой ключ, если у вас отдельный apiUrl
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
//        String user = ConfigReader.getConfig().getValidUsername();
//        String pass = ConfigReader.getConfig().getValidPassword();
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

