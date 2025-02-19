package utils.listeners;

import io.qameta.allure.Allure;
import io.qameta.allure.model.Status;
import io.qameta.allure.model.TestResultContainer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import utils.ReportUtils;

import java.lang.reflect.Method;
import java.util.UUID;

public class TestListener implements ITestListener {

    private static final Logger logger = LogManager.getLogger(TestListener.class);
    private String suiteUuid;
    private String testUuid;

    @Override
    public void onStart(ITestContext context) {
        suiteUuid = UUID.randomUUID().toString();
        Allure.getLifecycle().startTestContainer(suiteUuid, new TestResultContainer().setUuid(suiteUuid));

        // Логируем заголовок тестового набора через ReportUtils
        logger.info(ReportUtils.getReportHeader(context));
    }

    @Override
    public void onFinish(ITestContext context) {
        Allure.getLifecycle().stopTestContainer(suiteUuid);
        logger.info("Test suite finished: {}", context.getName());
    }

    @Override
    public void onTestStart(ITestResult result) {
        testUuid = UUID.randomUUID().toString();
        Allure.getLifecycle().startTestCase(testUuid);
        logger.info("Test started: {}", result.getName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        Allure.getLifecycle().updateTestCase(testUuid, testResult -> testResult.setStatus(Status.PASSED));
        Allure.getLifecycle().stopTestCase(testUuid);

        // Логируем статистику успешного теста через ReportUtils
        Method method = result.getMethod().getConstructorOrMethod().getMethod();
        logger.info(ReportUtils.getTestStatistics(method, result));
    }

    @Override
    public void onTestFailure(ITestResult result) {
        Allure.getLifecycle().updateTestCase(testUuid, testResult -> testResult.setStatus(Status.FAILED));
        Allure.getLifecycle().stopTestCase(testUuid);

        // Логируем статистику проваленного теста через ReportUtils
        Method method = result.getMethod().getConstructorOrMethod().getMethod();
        logger.error(ReportUtils.getTestStatistics(method, result));
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        Allure.getLifecycle().updateTestCase(testUuid, testResult -> testResult.setStatus(Status.SKIPPED));
        Allure.getLifecycle().stopTestCase(testUuid);

        // Логируем информацию о пропущенном тесте
        logger.warn("Test skipped: {}", result.getName());
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        Allure.getLifecycle().updateTestCase(testUuid, testResult -> testResult.setStatus(Status.BROKEN));
        Allure.getLifecycle().stopTestCase(testUuid);

        // Логируем информацию о частично успешном тесте
        logger.warn("Test failed but within success percentage: {}", result.getName());
    }
}
