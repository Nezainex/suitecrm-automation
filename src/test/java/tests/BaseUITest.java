package tests;

import com.codeborne.selenide.Selenide;
import io.qameta.allure.Attachment;
import org.openqa.selenium.OutputType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestResult;
import org.testng.annotations.*;
import utils.ReportUtils;
import utils.TestUtils;
import com.codeborne.selenide.Configuration;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import utils.config.ConfigReader;

import java.lang.reflect.Method;

import static com.codeborne.selenide.Selenide.closeWebDriver;
import static com.codeborne.selenide.Selenide.screenshot;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

public abstract class BaseUITest extends BaseTest {

    private static final Logger logger = LoggerFactory.getLogger(BaseUITest.class);

    @BeforeMethod(alwaysRun = true)
    @Parameters("browser")
    public void setUpBrowser(@Optional("chrome") String browser) {
        // Настраиваем браузер
        configureBrowser(browser);
        // Открываем базовый URL (например, если нужно)
        TestUtils.loadBaseUrlPage();
        getWebDriver().manage().window().maximize();
    }

    /**
     * Получение базового URL из YAML-конфига.
     */
    public static String getBaseUrl() {
        return ConfigReader.getConfig().getBaseUrl();
    }

    private void configureBrowser(String browser) {
        if (browser == null || browser.isEmpty()) {
            browser = ConfigReader.getConfig().getBrowserChrome();
        }

        logger.info("Настройка браузера: {}", browser);

        switch (browser.toLowerCase()) {
            case "chrome":
                WebDriverManager.chromedriver().setup();
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.addArguments("--start-maximized");
                chromeOptions.addArguments("--remote-allow-origins=*");
                Configuration.browserCapabilities = chromeOptions;
                Configuration.browser = ConfigReader.getConfig().getBrowserChrome();
                break;

            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                firefoxOptions.addArguments("--start-maximized");
                Configuration.browserCapabilities = firefoxOptions;
                Configuration.browser = ConfigReader.getConfig().getBrowserFirefox();
                break;

            default:
                throw new IllegalArgumentException("Браузер " + browser + " не поддерживается.");
        }

        Configuration.timeout = ConfigReader.getConfig().getDefaultTimeout();
        Configuration.pageLoadTimeout = ConfigReader.getConfig().getPageLoadTimeout();
        Configuration.reopenBrowserOnFail = true;
        Configuration.pageLoadStrategy = "normal";
        Configuration.headless = false;

        logger.info("Браузер '{}' настроен.", browser);
    }

    /**
     * Захват скриншота в случае падения теста.
     */
    public static void captureFailureShot(ITestResult result, Method method) {
        if (!result.isSuccess()) {
            String screenshotName = "failed-" + method.getName();

            // Получаем скриншот в виде байтов
            byte[] screenshotBytes = Selenide.screenshot(OutputType.BYTES);

            // Сохраняем в файл (для логов и отладки)
            screenshot(screenshotName);

            // Прикрепляем скриншот к Allure-отчёту через @Attachment
            if (screenshotBytes != null) {
                saveScreenshot("Скриншот при ошибке: " + method.getName(), screenshotBytes);
            }

            logger.info("Скриншот '{}' сохранен и добавлен в Allure для упавшего теста '{}'",
                    screenshotName, method.getName());
        }
    }

    @Attachment(value = "{0}", type = "image/png")
    public static byte[] saveScreenshot(String name, byte[] screenshot) {
        return screenshot;
    }

    @AfterMethod(alwaysRun = true)
    public void tearDownBrowser(ITestResult result, Method method) {
        try {
            logger.info(ReportUtils.getTestStatistics(method, result));

            if (!result.isSuccess()) {
                captureFailureShot(result, method);
            }

            closeWebDriver();
        } catch (Exception e) {
            logger.info("Ошибка при завершении сессии WebDriver: {}", e.getMessage());
        }
    }
}
