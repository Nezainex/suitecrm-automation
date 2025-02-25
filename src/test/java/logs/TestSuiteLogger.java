package logs;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ITestContext;
import org.testng.ITestResult;

import java.util.Arrays;

public class TestSuiteLogger {
    private final static Logger LOG = LogManager.getLogger(TestSuiteLogger.class);

    public static void logTestStart(ITestResult result) {
        Object[] parameters = result.getParameters();
        String message = parameters.length > 0
                ? String.format("Запущен тест: %s с параметрами: %s", result.getName(), Arrays.toString(parameters))
                : String.format("Запущен тест: %s", result.getName());
        LOG.info(message);
    }

    public static void logTestSuccess(ITestResult result, long duration) {
        LOG.info("Пройден тест: {} за {} секунд", result.getName(), duration);
    }

    public static void logTestFailure(ITestResult result) {
        Throwable throwable = result.getThrowable();
        if (throwable != null) {
            LOG.error("Упал тест {} с исключением: ", result.getName(), throwable);
        } else {
            LOG.error("Упал тест: {}", result.getName());
        }
    }

    public static void logTestSkipped(ITestResult result) {
        LOG.info("Пропущен тест: {}", result.getName());
    }

    public static void logTestSuiteStart(ITestContext context) {
        LOG.info("Начало выполнения всех тестов в наборе: {}", context.getSuite().getName());
        logEnvironmentDetails();
    }

    public static void logTestSuiteFinish(ITestContext context) {
        LOG.info("Завершение выполнения всех тестов в наборе: {}", context.getSuite().getName());
        logTestSummary(context);
    }

    private static void logEnvironmentDetails() {
        String osName = System.getProperty("os.name");
        String osVersion = System.getProperty("os.version");
        String browserName = System.getProperty("browser.name", "Название браузера не указано");
        String browserVersion = System.getProperty("browser.version", "Версия браузера не указана");
        LOG.info("Окружение: {}, {}", osName, osVersion);
        LOG.info("Браузер: {}: {}", browserName, browserVersion);
    }

    private static void logTestSummary(ITestContext context) {
        LOG.info("Всего тестов: {}", context.getAllTestMethods().length);
        LOG.info("Пройдено тестов: {}", context.getPassedTests().size());
        LOG.info("Упало тестов: {}", context.getFailedTests().size());
        LOG.info("Пропущено тестов: {}", context.getSkippedTests().size());
    }
}
