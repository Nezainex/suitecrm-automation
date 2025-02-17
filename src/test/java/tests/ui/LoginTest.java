package tests.ui;

import base.BaseTest;
import io.qameta.allure.Allure;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pages.LoginPage;
import utils.listeners.TestListener;
import utils.listeners.TestSuiteListener;

@Listeners({TestListener.class, TestSuiteListener.class})
public class LoginTest extends BaseTest {

    @Test(description = "Успешный вход с валидными учетными данными")
    public void testValidLogin() {
        Allure.step("Открываем страницу логина", () -> {
            LoginPage loginPage = new LoginPage();
            loginPage.openLoginPage();

            Allure.step("Вводим валидные учетные данные", () -> loginPage.inputUsername("user")
                    .inputPassword("bitnami")
                    .clickLogin());

            Allure.step("Проверяем, что мы находимся на главной странице", () -> Assert.assertTrue(loginPage.isHomeMenuItemVisible(), "Не удалось войти в систему. Главная страница недоступна."));
        });
    }

    @Test(description = "Ошибка при входе с неверными учетными данными")
    public void testInvalidLogin() {
        Allure.step("Открываем страницу логина", () -> {
            LoginPage loginPage = new LoginPage();
            loginPage.openLoginPage();

            Allure.step("Вводим неверные учетные данные", () -> loginPage.inputUsername("invalid")
                    .inputPassword("invalid")
                    .clickLogin());

            Allure.step("Проверяем, что сообщение об ошибке отображается", () ->
                    Assert.assertTrue(loginPage.isErrorMessageVisible("Login credentials incorrect, please try again."),
                            "Ожидаемое сообщение об ошибке не отображается."));
        });
    }

    @Test(description = "Ошибка при пустых полях логина и пароля")
    public void testEmptyFields() {
        Allure.step("Открываем страницу логина", () -> {
            LoginPage loginPage = new LoginPage();
            loginPage.openLoginPage();

            Allure.step("Оставляем поля логина и пароля пустыми", () -> {
                loginPage.leaveUsernameEmpty();
                loginPage.leavePasswordEmpty();
                loginPage.clickLogin();
            });

            Allure.step("Проверяем, что сообщение 'Missing required field' отображается для поля логина", () ->
                    Assert.assertTrue(loginPage.isUsernameRequiredMessageVisible(),
                            "Сообщение 'Missing required field' для поля логина не отображается."));

            Allure.step("Проверяем, что сообщение 'Missing required field' отображается для поля пароля", () ->
                    Assert.assertTrue(loginPage.isPasswordRequiredMessageVisible(),
                            "Сообщение 'Missing required field' для поля пароля не отображается."));
        });
    }

    @Test(description = "Вход через iframe, если он существует")
    public void testLoginWithIframe() {
        Allure.step("Открываем страницу логина", () -> {
            LoginPage loginPage = new LoginPage();
            loginPage.openLoginPage();

            Allure.step("Проверяем наличие iframe", () -> {
                if (loginPage.isIFrameExists()) {
                    Allure.step("Переключаемся в iframe", () -> loginPage.switchToIframeByWebElement("//iframe[@id='#_yuiResizeMonitor']"));
                }
            });

            Allure.step("Вводим учетные данные и кликаем по кнопке логина", () -> loginPage.inputUsername("user")
                    .inputPassword("bitnami")
                    .clickLogin());

            Allure.step("Проверяем, что мы находимся на главной странице", () -> Assert.assertTrue(loginPage.isHomeMenuItemVisible(), "Не удалось войти в систему. Главная страница недоступна."));
        });
    }
}
