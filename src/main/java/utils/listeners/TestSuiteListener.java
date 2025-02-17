package utils.listeners;

import io.qameta.allure.Allure;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import utils.ReportUtils;

public class TestSuiteListener implements ITestListener {

    @Override
    public void onStart(ITestContext context) {
        Allure.step("Тестовый набор начат: " + context.getName(), () -> {
            // Логируем заголовок тестового набора через ReportUtils
            System.out.println(ReportUtils.getReportHeader(context));
        });
    }

    @Override
    public void onFinish(ITestContext context) {
        Allure.step("Тестовый набор завершён: " + context.getName(), () ->
                System.out.println("Test suite finished: " + context.getName()));
    }

    @Override
    public void onTestStart(ITestResult result) {
        Allure.step("Тест начат: " + result.getName(), () ->
                System.out.println("Test started: " + result.getName()));
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        Allure.step("Тест успешно пройден: " + result.getName(), () ->
                System.out.println("Test passed: " + result.getName()));
    }

    @Override
    public void onTestFailure(ITestResult result) {
        Allure.step("Тест провален: " + result.getName(), () ->
                System.out.println("Test failed: " + result.getName()));
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        Allure.step("Тест пропущен: " + result.getName(), () ->
                System.out.println("Test skipped: " + result.getName()));
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        Allure.step("Тест частично прошел: " + result.getName(), () ->
                System.out.println("Test failed but within success percentage: " + result.getName()));
    }
}
