package tests;

import utils.BaseUtils;
import org.testng.ITestResult;
import org.testng.annotations.*;
import utils.ReportUtils;
import utils.TestUtils;

import java.lang.reflect.Method;

import static utils.BaseUtils.captureFailureShot;
import static com.codeborne.selenide.Selenide.closeWebDriver;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

/**
 * Базовый класс для UI-тестов.
 * Здесь инициализируем/закрываем браузер.
 */
public abstract class BaseUITest extends BaseTest {

    @BeforeMethod(alwaysRun = true)
    @Parameters("browser")
    public void setUpBrowser(@Optional("chrome") String browser) {
        // Настраиваем браузер
        BaseUtils.configureBrowser(browser);
        // Открываем базовый URL (например, если нужно)
        TestUtils.loadBaseUrlPage();
        getWebDriver().manage().window().maximize();
    }

    @AfterMethod(alwaysRun = true)
    public void tearDownBrowser(ITestResult result, Method method) {
        try {
            // Выводим статистику по тесту
            System.out.println(ReportUtils.getTestStatistics(method, result));

            // Если тест упал, делаем скриншот
            if (!result.isSuccess()) {
                captureFailureShot(result, method);
            }
            // Закрываем браузер
            closeWebDriver();
        } catch (Exception e) {
            System.err.println("Ошибка при завершении сессии WebDriver: " + e.getMessage());
        }
    }
}

