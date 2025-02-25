package utils.listeners;

import logs.TestSuiteLogger;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import utils.AllureUtils;

public class TestListener implements ITestListener {

    @Override
    public void onStart(ITestContext context) {
    }

    @Override
    public void onFinish(ITestContext context) {
        TestSuiteLogger.logTestSuiteFinish(context);
    }

    @Override
    public void onTestStart(ITestResult result) {
        TestSuiteLogger.logTestStart(result);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        long duration = (result.getEndMillis() - result.getStartMillis()) / 1000;
        TestSuiteLogger.logTestSuccess(result, duration);
    }

    @Override
    public void onTestFailure(ITestResult result) {
        AllureUtils.screen();
        AllureUtils.pageSource();
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        TestSuiteLogger.logTestSkipped(result);
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {

    }
}
