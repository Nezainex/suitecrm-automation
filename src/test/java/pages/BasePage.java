package pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;
import org.openqa.selenium.NoSuchFrameException;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Reporter;

import java.security.SecureRandom;
import java.util.Map;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverRunner.url;
import static java.util.concurrent.ThreadLocalRandom.current;
import static com.codeborne.selenide.Selenide.switchTo;

public abstract class BasePage {

    private static final SelenideElement LOGIN_PAGE_IDENTIFIER = $x("//img[@src='legacy/themes/default/images/company_logo.png']");

    private static final Logger LOG = LoggerFactory.getLogger(BasePage.class);
    /**
     * Получение заголовка страницы
     */
    protected String getTitle() {
        return title(); // Получение заголовка страницы через Selenide
    }

    /**
     * Получение текущего URL
     */
    protected String getCurrentURL() {
        return url(); // Получение текущего URL через Selenide
    }

    /**
     * Наведение мыши на элемент.
     */
    protected void hover(SelenideElement element) {
        element.shouldBe(visible).hover();
    }

    /**
     * Клик по элементу с ожиданием его видимости
     */
    protected void click(SelenideElement element) {
        element.shouldBe(visible).click(); // Ожидание видимости элемента и клик
    }

    /**
     * Проверка видимости элемента.
     */
    protected boolean isElementVisible(SelenideElement element) {
        return element.shouldBe(visible).exists();
    }

    /**
     * Ввод текста в поле
     */
    protected void inputText(SelenideElement element, String text) {
        element.shouldBe(visible).setValue(text); // Ожидание видимости элемента и ввод текста
    }

    /**
     * Очистка поля и ввод текста
     */
    protected void clearAndInputText(SelenideElement element, String text) {
        element.shouldBe(visible).clear(); // Ожидание видимости элемента и очистка
        element.setValue(text); // Ввод текста
    }

    /**
     * Прокрутка к элементу
     */
    protected void scrollToElement(SelenideElement element) {
        element.scrollIntoView(true); // Прокрутка элемента в область видимости
    }

    /**
     * Drag-and-Drop элемента
     */
    protected void dragAndDrop(SelenideElement source, SelenideElement target) {
        actions().dragAndDrop(source, target).perform(); // Выполнение операции Drag-and-Drop через Selenide
    }

    /**
     * Переключение на iframe по id или name.
     * @param idOrName - значение атрибута id или name iframe
     */
    protected void switchToIframeByIdOrName(String idOrName) {
        WebDriver driver = WebDriverRunner.getWebDriver();
        driver.switchTo().defaultContent(); // Сбрасываем контекст
        try {
            driver.switchTo().frame(idOrName); // Переключение на iframe
        } catch (NoSuchFrameException e) {
            throw new IllegalStateException("Iframe with id or name '" + idOrName + "' not found.", e);
        }
    }

    /**
     * Возврат в основной контент.
     */
    protected void switchToDefaultContent() {
        WebDriver driver = WebDriverRunner.getWebDriver();
        driver.switchTo().defaultContent();
    }

    /**
     * Проверка авторизации и выполнение логина, если нужно.
     */
    protected void ensureLoggedIn(String username, String password) {
        String currentUrl = url();
        assert currentUrl != null;
        if (currentUrl.contains("/Login")) {
            LoginPage loginPage = new LoginPage();
            loginPage.inputUsername(username)
                    .inputPassword(password)
                    .clickLogin();
            // Проверяем, что пользователь успешно залогинился
            if (!new LoginPage().isHomeMenuItemVisible()) {
                throw new IllegalStateException("Не удалось залогиниться. Проверьте логин и пароль.");
            }
        }
    }


    /**
     * Работа с alert: получение текста
     */
    protected String getAlertText() {
        return switchTo().alert().getText(); // Получение текста alert через Selenide
    }

    /**
     * Работа с alert: подтверждение
     */
    protected void acceptAlert() {
        switchTo().alert().accept(); // Подтверждение alert через Selenide
    }

    /**
     * Работа с alert: отклонение
     */
    protected void dismissAlert() {
        switchTo().alert().dismiss(); // Отклонение alert через Selenide
    }

    /**
     * Работа с alert: ввод текста
     */
    protected void enterTextInAlert(String text) {
        switchTo().alert().sendKeys(text); // Ввод текста в alert через Selenide
        switchTo().alert().accept(); // Подтверждение после ввода
    }

    /**
     * Генерация случайной строки заданной длины
     */
    protected String getRandomString(String alphabet, int length) {
        StringBuilder result = new StringBuilder(); // Инициализация StringBuilder
        while (result.length() < length) {
            int index = current().nextInt(alphabet.length()); // Генерация случайного индекса
            result.append(alphabet.charAt(index)); // Добавление символа из алфавита
        }
        return result.toString(); // Возврат сгенерированной строки
    }

    /**
     * Поиск и выполнение действия по имени.
     */
    protected void performActionByName(String name, Map<String, Runnable> actions) {
        if (actions.containsKey(name)) {
            actions.get(name).run();
        } else {
            throw new IllegalArgumentException("Unknown name: " + name);
        }
    }

    public static void loadBaseUrlPage() {
        Selenide.open("");
        waitForPageLoaded();
    }

    public static void waitForPageLoaded() {
        LOGIN_PAGE_IDENTIFIER.shouldBe(Condition.visible);
    }

    public static void reLoadBaseUrlPage() {
        int attempts = 0;
        while (attempts < 3) {
            try {
                loadBaseUrlPage();
                LOGIN_PAGE_IDENTIFIER.shouldBe(Condition.visible);
                return; // Успешно загружена, выходим из цикла
            } catch (Exception e) {
                Reporter.log("Попытка загрузки страницы: " + (attempts + 1), true);
                attempts++;
            }
        }
        Reporter.log("!!!!! Ошибка !!!!! Страница логина не загружена после 3 попыток. Повторите запуск.", true);
    }

    public static String getRandomName(int length) {
        final String characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        SecureRandom random = new SecureRandom();

        return random.ints(length, 0, characters.length())
                .mapToObj(characters::charAt)
                .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
                .toString();
    }

    public static String getRandomName() {
        return getRandomName(7);
    }

    public static int convertStringToInt(String text) {
        return Integer.parseInt(text);
    }

    public static String getSubstring(String text, String separator) {
        int index = text.indexOf(separator);
        return text.substring(0, index);
    }

    public static String getRandomNumber(int length) {
        SecureRandom random = new SecureRandom();
        StringBuilder number = new StringBuilder(length);

        number.append(random.nextInt(9) + 1);
        for (int i = 1; i < length; i++) {
            number.append(random.nextInt(10));
        }

        return number.toString();
    }
}
