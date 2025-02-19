package utils;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.Allure;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestResult;
import utils.config.ConfigReader;
import utils.config.ProjectConfig;

import java.io.ByteArrayInputStream;
import java.lang.reflect.Method;

import static com.codeborne.selenide.Selenide.screenshot;

public final class BaseUtils {

    private static final Logger logger = LoggerFactory.getLogger(BaseUtils.class);

    // Подгружаем конфигурацию из project-config.yml
    private static final ProjectConfig CONFIG = ConfigReader.getConfig();

    private BaseUtils() {
        // Приватный конструктор, чтобы не создавать экземпляр утилитного класса
    }

    /**
     * Получение базового URL из YAML-конфига.
     */
    public static String getBaseUrl() {
        // Брали из properties, теперь берём из YAML:
        return ConfigReader.getConfig().getBaseUrl();
    }

    /**
     * Настройка браузера перед запуском тестов.
     * Если браузер не передается внешне, можно брать "по умолчанию" из YAML.
     */
    public static void configureBrowser(String browser) {
        // Если в метод передали null — берем значение из YAML (например, browserChrome)
        if (browser == null || browser.isEmpty()) {
            browser = CONFIG.getBrowserChrome();
        }

        logger.info("Настройка браузера: {}", browser);

        switch (browser.toLowerCase()) {
            case "chrome":
                WebDriverManager.chromedriver().setup();
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.addArguments("--start-maximized");
                chromeOptions.addArguments("--remote-allow-origins=*");
                Configuration.browserCapabilities = chromeOptions;
                Configuration.browser = "chrome";
                break;

            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                firefoxOptions.addArguments("--start-maximized");
                Configuration.browserCapabilities = firefoxOptions;
                Configuration.browser = "firefox";
                break;

            default:
                throw new IllegalArgumentException("Браузер " + browser + " не поддерживается.");
        }

        // Общие настройки Selenide (тоже можем брать из YAML, например defaultTimeout, pageLoadTimeout)
        Configuration.timeout = CONFIG.getDefaultTimeout();        // например, 5000
        Configuration.pageLoadTimeout = CONFIG.getPageLoadTimeout(); // например, 30000
        Configuration.reopenBrowserOnFail = true;
        Configuration.pageLoadStrategy = "normal";
        Configuration.headless = false;

        logger.info("Браузер '{}' настроен. (timeout={}, pageLoadTimeout={})",
                browser, CONFIG.getDefaultTimeout(), CONFIG.getPageLoadTimeout());
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

            // Прикрепляем скриншот к Allure-отчёту
            if (screenshotBytes != null) {
                Allure.addAttachment("Скриншот при ошибке: " + method.getName(),
                        "image/png", new ByteArrayInputStream(screenshotBytes), "png");
            }

            logger.info("Скриншот '{}' сохранен и добавлен в Allure для упавшего теста '{}'",
                    screenshotName, method.getName());
        }
    }

}
