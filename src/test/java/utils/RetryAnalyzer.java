package utils;

import utils.custom.annotations.RetryCountIfFailed;
import io.qameta.allure.Allure;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;
import org.testng.Reporter;

import java.util.concurrent.TimeUnit;

public class RetryAnalyzer implements IRetryAnalyzer {

    private int retryCount = 0;

    @Override
    public boolean retry(ITestResult result) {
        // Получение значения из аннотации, если она присутствует
        RetryCountIfFailed retryCountIfFailed = result.getMethod()
                .getConstructorOrMethod()
                .getMethod()
                .getAnnotation(RetryCountIfFailed.class);

        int maxRetryCount = (retryCountIfFailed != null) ? retryCountIfFailed.value() : 3; // По умолчанию 3

        if (retryCount < maxRetryCount) {
            try {
                TimeUnit.SECONDS.sleep(5); // Задержка перед повтором
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            retryCount++;
            Allure.step("Попытка #" + retryCount + " для метода: " + result.getMethod().getMethodName(), () ->
                    Reporter.log("Retrying test method " + result.getMethod().getMethodName() + " for the " + retryCount + " time.", true));
            return true;
        }
        return false;
    }
}