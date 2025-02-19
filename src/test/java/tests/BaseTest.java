package tests;

import org.testng.ITestContext;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import utils.ReportUtils;

/**
 * Базовый абстрактный класс для тестов с общей логикой.
 */
public abstract class BaseTest {

    @BeforeClass(alwaysRun = true)
    public void logSuiteStart(ITestContext context) {
        System.out.println(ReportUtils.getReportHeader(context));
    }

    @AfterClass(alwaysRun = true)
    public void logSuiteEnd(ITestContext context) {
        System.out.println("Тестовый набор завершён: " + context.getName());
    }

    // Сюда потом можно запихнуть утилиты, общие и для UI, и для API
}
