package tests.ui;

import io.qameta.allure.Step;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pages.LoginPage;
import tests.BaseUITest;
import utils.TestUtils;
import utils.config.ConfigReader;
import utils.listeners.TestListener;
import utils.listeners.TestSuiteListener;

@Listeners({TestListener.class, TestSuiteListener.class})
public class LoginTest extends BaseUITest {

    private static final Logger logger = LoggerFactory.getLogger(LoginTest.class);

    @Test(description = "Успешный вход с валидными учетными данными")
    public void testValidLogin() {
        logger.info("Начало теста: testValidLogin");
        openLoginPage();

        // Получаем логин/пароль из YAML:
        String validUser = ConfigReader.getConfig().getValidUsername();
        String validPass = ConfigReader.getConfig().getValidPassword();
        logger.info("Получены валидные учетные данные из конфига");

        inputValidCredentials(validUser, validPass);
        verifyHomePageAccessible();
    }

    @Step("Открываем страницу логина")
    public void openLoginPage() {
        logger.info("Шаг: Открываем страницу логина");
        LoginPage loginPage = new LoginPage();
        loginPage.openLoginPage();
    }

    @Step("Вводим валидные учетные данные и кликаем на логин")
    public void inputValidCredentials(String username, String password) {
        logger.info("Шаг: Вводим валидные учетные данные для пользователя: {}", username);
        LoginPage loginPage = new LoginPage();
        loginPage.inputUsername(username)
                .inputPassword(password)
                .clickLogin();
    }

    @Step("Проверяем, что мы находимся на главной странице")
    public void verifyHomePageAccessible() {
        logger.info("Шаг: Проверяем, что главная страница доступна после логина");
        LoginPage loginPage = new LoginPage();
        Assert.assertTrue(loginPage.isHomeMenuItemVisible(),
                "Не удалось войти в систему. Главная страница недоступна.");
    }

    @Test(description = "Ошибка при входе с неверными учетными данными")
    public void testInvalidLogin() {
        logger.info("Начало теста: testInvalidLogin");
        LoginPage loginPage = new LoginPage();
        loginPage.openLoginPage();

        // Генерируем случайные некорректные учетные данные для избежания ошибки "to many attempts, please try again later"
        String invalidUser = TestUtils.getRandomName();
        String invalidPass = TestUtils.getRandomName();
        logger.info("Сгенерированы случайные некорректные учетные данные: {} / {}", invalidUser, invalidPass);

        inputInvalidCredentials(invalidUser, invalidPass);
        verifyErrorMessage();
    }

    @Step("Вводим неверные учетные данные и кликаем на логин")
    public void inputInvalidCredentials(String username, String password) {
        logger.info("Шаг: Вводим неверные учетные данные для пользователя: {}", username);
        LoginPage loginPage = new LoginPage();
        loginPage.inputUsername(username)
                .inputPassword(password)
                .clickLogin();
    }

    @Step("Проверяем, что сообщение об ошибке отображается")
    public void verifyErrorMessage() {
        logger.info("Шаг: Проверяем, что сообщение об ошибке отображается");
        LoginPage loginPage = new LoginPage();
        Assert.assertTrue(loginPage.isErrorMessageVisible("Login credentials incorrect, please try again."),
                "Ожидаемое сообщение об ошибке не отображается.");
    }

    @Test(description = "Ошибка при пустых полях логина и пароля")
    public void testEmptyFields() {
        logger.info("Начало теста: testEmptyFields");
        openLoginPage();

        leaveFieldsEmpty();
        verifyEmptyFieldMessages();
    }

    @Step("Оставляем поля логина и пароля пустыми и кликаем на логин")
    public void leaveFieldsEmpty() {
        logger.info("Шаг: Оставляем поля логина и пароля пустыми и кликаем на логин");
        LoginPage loginPage = new LoginPage();
        loginPage.leaveUsernameEmpty();
        loginPage.leavePasswordEmpty();
        loginPage.clickLogin();
    }

    @Step("Проверяем, что сообщение 'Missing required field' отображается для полей логина и пароля")
    public void verifyEmptyFieldMessages() {
        logger.info("Шаг: Проверяем, что сообщения 'Missing required field' для логина и пароля отображаются");
        LoginPage loginPage = new LoginPage();
        Assert.assertTrue(loginPage.isUsernameRequiredMessageVisible(),
                "Сообщение 'Missing required field' для поля логина не отображается.");
        Assert.assertTrue(loginPage.isPasswordRequiredMessageVisible(),
                "Сообщение 'Missing required field' для поля пароля не отображается.");
    }

    @Test(description = "Вход через iframe, если он существует")
    public void testLoginWithIframe() {
        logger.info("Начало теста: testLoginWithIframe");
        openLoginPage();

        if (checkAndSwitchToIframe()) {
            performLoginInIframe();
        }
    }

    @Step("Проверяем наличие iframe и переключаемся в него, если он существует")
    public boolean checkAndSwitchToIframe() {
        logger.info("Шаг: Проверяем наличие iframe");
        LoginPage loginPage = new LoginPage();
        if (loginPage.isIFrameExists()) {
            logger.info("iframe найден, переключаемся в него");
            loginPage.switchToIframeByWebElement("//iframe[@id='#_yuiResizeMonitor']");
            return true;
        }
        logger.info("iframe не найден");
        return false;
    }

    @Step("Вводим учетные данные и кликаем на логин в iframe")
    public void performLoginInIframe() {
        logger.info("Шаг: Вводим учетные данные и кликаем на логин в iframe");
        LoginPage loginPage = new LoginPage();

        // Берём валидные учетные данные из ConfigReader
        String validUser = ConfigReader.getConfig().getValidUsername();
        String validPass = ConfigReader.getConfig().getValidPassword();
        logger.info("Получены валидные учетные данные из конфига для iframe");

        loginPage.inputUsername(validUser)
                .inputPassword(validPass)
                .clickLogin();

        verifyHomePageAccessible();
    }
}
