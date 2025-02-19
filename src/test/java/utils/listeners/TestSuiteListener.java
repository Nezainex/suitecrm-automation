package utils.listeners;

import io.qameta.allure.Allure;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import utils.ReportUtils;

public class TestSuiteListener implements ITestListener {

    private static final Logger logger = LogManager.getLogger(TestSuiteListener.class);
    @Override
    public void onStart(ITestContext context) {
        Allure.step("Тестовый набор начат: " + context.getName(), () -> {
            // Логируем заголовок тестового набора через ReportUtils
            logger.info(ReportUtils.getReportHeader(context));
        });
    }

    @Override
    public void onFinish(ITestContext context) {
        Allure.step("Тестовый набор завершён: " + context.getName(), () ->
                logger.info("Test suite finished: {}", context.getName()));
    }

    @Override
    public void onTestStart(ITestResult result) {
        Allure.step("Тест начат: " + result.getName(), () ->
                logger.info("Test started: {}", result.getName()));
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        Allure.step("Тест успешно пройден: " + result.getName(), () ->
                logger.info("Test passed: {}", result.getName()));
    }

    @Override
    public void onTestFailure(ITestResult result) {
        Allure.step("Тест провален: " + result.getName(), () ->
                logger.info("Test failed: {}", result.getName()));
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        Allure.step("Тест пропущен: " + result.getName(), () ->
                logger.info("Test skipped: {}", result.getName()));
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        Allure.step("Тест частично прошел: " + result.getName(), () ->
                logger.info("Test failed but within success percentage: {}", result.getName()));
    }
}
