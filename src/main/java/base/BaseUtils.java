package base;

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

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.Properties;

import static com.codeborne.selenide.Selenide.screenshot;

public final class BaseUtils {

    private static final Logger logger = LoggerFactory.getLogger(BaseUtils.class);
    private static final String CONFIG_FILE = "config.properties";
    private static final Properties properties = new Properties();

    static {
        initProperties();
    }

    private BaseUtils() {
    }

    /**
     * Инициализация файла конфигурации.
     */
    private static void initProperties() {
        try (InputStream inputStream = BaseUtils.class.getClassLoader().getResourceAsStream(CONFIG_FILE)) {
            if (inputStream == null) {
                throw new IllegalStateException("Файл конфигурации " + CONFIG_FILE + " не найден.");
            }
            properties.load(inputStream);
            logger.info("Файл конфигурации '{}' успешно загружен.", CONFIG_FILE);
        } catch (Exception e) {
            logger.error("Ошибка при загрузке конфигурации: {}", e.getMessage());
            throw new RuntimeException("Ошибка загрузки " + CONFIG_FILE, e);
        }
    }

    /**
     * Получение базового URL из файла конфигурации.
     */
    public static String getBaseUrl() {
        return properties.getProperty("BASE_URL", "http://localhost:8080");
    }

    /**
     * Настройка браузера перед запуском тестов.
     */
    public static void configureBrowser(String browser) {
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

        // Общие настройки Selenide
        Configuration.timeout = 5000;
        Configuration.reopenBrowserOnFail = true;
        Configuration.pageLoadStrategy = "normal";
        Configuration.pageLoadTimeout = 30000;
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

            // Прикрепляем скриншот к Allure-отчёту
            assert screenshotBytes != null;
            Allure.addAttachment("Скриншот при ошибке: " + method.getName(),
                    "image/png", new ByteArrayInputStream(screenshotBytes), "png");

            logger.info("Скриншот '{}' сохранен и добавлен в Allure для упавшего теста '{}'", screenshotName, method.getName());
        }
    }

}
