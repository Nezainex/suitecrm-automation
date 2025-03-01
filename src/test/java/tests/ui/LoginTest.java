package tests.ui;

import io.qameta.allure.Step;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.BasePage;
import pages.LoginPage;
import utils.config.ConfigReader;

public class LoginTest extends BaseUITest {

    private static final Logger LOG = LoggerFactory.getLogger(LoginTest.class);

    @Test(description = "Успешный вход с валидными учетными данными")
    public void testValidLogin() {
        LOG.info("Начало теста: testValidLogin");
        openLoginPage();

        // Получаем логин/пароль из YAML:
        String validUser = ConfigReader.getConfig().getValidUsername();
        String validPass = ConfigReader.getConfig().getValidPassword();
        LOG.info("Получены валидные учетные данные из конфига");

        inputValidCredentials(validUser, validPass);
        verifyHomePageAccessible();
    }

    @Step("Открываем страницу логина")
    public void openLoginPage() {
        LOG.info("Шаг: Открываем страницу логина");
        LoginPage loginPage = new LoginPage();
        loginPage.openLoginPage();
    }

    @Step("Вводим валидные учетные данные и кликаем на логин")
    public void inputValidCredentials(String username, String password) {
        LOG.info("Шаг: Вводим валидные учетные данные для пользователя: {}", username);
        LoginPage loginPage = new LoginPage();
        loginPage.inputUsername(username)
                .inputPassword(password)
                .clickLogin();
    }

    @Step("Проверяем, что мы находимся на главной странице")
    public void verifyHomePageAccessible() {
        LOG.info("Шаг: Проверяем, что главная страница доступна после логина");
        LoginPage loginPage = new LoginPage();
        Assert.assertTrue(loginPage.isHomeMenuItemVisible(),
                "Не удалось войти в систему. Главная страница недоступна.");
    }

    @Test(description = "Ошибка при входе с неверными учетными данными")
    public void testInvalidLogin() {
        LOG.info("Начало теста: testInvalidLogin");
        LoginPage loginPage = new LoginPage();
        loginPage.openLoginPage();

        // Генерируем случайные некорректные учетные данные для избежания ошибки "to many attempts, please try again later"
        String invalidUser = BasePage.getRandomName();
        String invalidPass = BasePage.getRandomName();
        LOG.info("Сгенерированы случайные некорректные учетные данные: {} / {}", invalidUser, invalidPass);

        inputInvalidCredentials(invalidUser, invalidPass);
        verifyErrorMessage();
    }

    @Step("Вводим неверные учетные данные и кликаем на логин")
    public void inputInvalidCredentials(String username, String password) {
        LOG.info("Шаг: Вводим неверные учетные данные для пользователя: {}", username);
        LoginPage loginPage = new LoginPage();
        loginPage.inputUsername(username)
                .inputPassword(password)
                .clickLogin();
    }

    @Step("Проверяем, что сообщение об ошибке отображается")
    public void verifyErrorMessage() {
        LOG.info("Шаг: Проверяем, что сообщение об ошибке отображается");
        LoginPage loginPage = new LoginPage();
        Assert.assertTrue(loginPage.isErrorMessageVisible("Login credentials incorrect, please try again."),
                "Ожидаемое сообщение об ошибке не отображается.");
    }

    @Test(description = "Ошибка при пустых полях логина и пароля")
    public void testEmptyFields() {
        LOG.info("Начало теста: testEmptyFields");
        openLoginPage();

        leaveFieldsEmpty();
        verifyEmptyFieldMessages();
    }

    @Step("Оставляем поля логина и пароля пустыми и кликаем на логин")
    public void leaveFieldsEmpty() {
        LOG.info("Шаг: Оставляем поля логина и пароля пустыми и кликаем на логин");
        LoginPage loginPage = new LoginPage();
        loginPage.leaveUsernameEmpty();
        loginPage.leavePasswordEmpty();
        loginPage.clickLogin();
    }

    @Step("Проверяем, что сообщение 'Missing required field' отображается для полей логина и пароля")
    public void verifyEmptyFieldMessages() {
        LOG.info("Шаг: Проверяем, что сообщения 'Missing required field' для логина и пароля отображаются");
        LoginPage loginPage = new LoginPage();
        Assert.assertTrue(loginPage.isUsernameRequiredMessageVisible(),
                "Сообщение 'Missing required field' для поля логина не отображается.");
        Assert.assertTrue(loginPage.isPasswordRequiredMessageVisible(),
                "Сообщение 'Missing required field' для поля пароля не отображается.");
    }
}
