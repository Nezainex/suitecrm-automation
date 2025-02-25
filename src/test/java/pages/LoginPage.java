package pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import tests.ui.LoginTest;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverRunner.url;

import java.time.Duration;
import java.util.Objects;

@Getter
public class LoginPage extends BasePage {

    private static final Logger LOG = LoggerFactory.getLogger(LoginPage.class);

    // Локаторы на странице логина
    private final SelenideElement usernameField =
            $x("//input[@name='username']");

    private final SelenideElement passwordField =
            $x("//input[@name='password']");

    private final SelenideElement loginButton =
            $x("//button[@id='login-button']");

    // Локатор для проверки домашней страницы (аналог "SUITECRM DASHBOARD")
    private final SelenideElement homeMenuItem =
            $x("//scrm-home-menu-item");

    // Локатор для сообщения об ошибке
    private final SelenideElement errorMessage =
            $x("//div[@role='alert' and contains(@class, 'alert-danger')]");

    // Локатор для проверки сообщений о пустых полях
    private final SelenideElement usernameRequiredMessage =
            $x("//input[@name='username']/following-sibling::div[contains(@class, 'invalid-feedback')]");

    private final SelenideElement passwordRequiredMessage =
            $x("//input[@name='password']/following-sibling::div[contains(@class, 'invalid-feedback')]");

    /**
     * Проверка, что сообщение "Missing required field" отображается для имени пользователя.
     */
    public boolean isUsernameRequiredMessageVisible() {
        return usernameRequiredMessage
                .shouldBe(Condition.visible, Duration.ofSeconds(10))
                .getText().contains("Missing required field");
    }

    /**
     * Проверка, что сообщение "Missing required field" отображается для пароля.
     */
    public boolean isPasswordRequiredMessageVisible() {
        return passwordRequiredMessage
                .shouldBe(Condition.visible, Duration.ofSeconds(10))
                .getText().contains("Missing required field");
    }

    /**
     * Метод для открытия страницы логина.
     */
    public void openLoginPage() {
        open("http://localhost:8080/#/Login");
        // При желании можно тут же делать maximize()
        webdriver().driver().getWebDriver().manage().window().maximize();
    }

    /**
     * Ввод имени пользователя.
     */
    public LoginPage inputUsername(String username) {
        usernameField.shouldBe(Condition.visible, Duration.ofSeconds(10))
                .setValue(username);
        return this;
    }

    /**
     * Ввод пароля.
     */
    public LoginPage inputPassword(String password) {
        passwordField.shouldBe(Condition.visible, Duration.ofSeconds(10))
                .setValue(password);
        return this;
    }

    /**
     * Клик по кнопке "Login".
     */
    public void clickLogin() {
        loginButton.shouldBe(Condition.visible, Duration.ofSeconds(10))
                .click();
    }

    /**
     * Проверка, что мы видим элемент домашней страницы (scrm-home-menu-item).
     * Если он есть, значит мы успешно залогинились и оказались на /home.
     */
    public boolean isHomeMenuItemVisible() {
        return homeMenuItem
                .shouldBe(Condition.visible, Duration.ofSeconds(10))
                .isDisplayed();
    }

    /**
     * Проверка, что есть сообщение об ошибке (например, "Login credentials incorrect, please try again.").
     */
    public boolean isErrorMessageVisible(String expectedText) {
        // Проверяем, что элемент с сообщением виден
        errorMessage.shouldBe(Condition.visible, Duration.ofSeconds(10));
        // Проверяем текст сообщения
        return errorMessage.getText().contains(expectedText);
    }

    /**
     * Если нужно проверить, что фрейм существует:
     * (здесь пример, если бы у вас был <iframe id="#_yuiResizeMonitor">)
     */
    public boolean isIFrameExists() {
        SelenideElement frame = $x("//iframe[@id='#_yuiResizeMonitor']");
        return frame.exists();
    }

    // При желании — методы "leaveUsernameEmpty()" и т.п.,
    // аналогичные вашим шагам:
    public void leaveUsernameEmpty() {
        usernameField.shouldBe(Condition.visible, Duration.ofSeconds(10))
                .setValue("");
    }

    public void leavePasswordEmpty() {
        passwordField.shouldBe(Condition.visible, Duration.ofSeconds(10))
                .setValue("");
    }

    /**
     * Выполняет логин с указанными логином и паролем.
     */
    public void performLogin(String username, String password) {
        inputUsername(username);
        inputPassword(password);
        clickLogin();

        // Убедимся, что мы авторизовались
        Assert.assertFalse(Objects.requireNonNull(url()).contains("/Login"), "Логин не выполнен, всё ещё на странице логина.");
    }
}
