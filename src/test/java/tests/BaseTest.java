package tests;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestContext;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import utils.ReportUtils;

/**
 * Базовый абстрактный класс для тестов с общей логикой.
 */
public abstract class BaseTest {

    private static final Logger logger = LoggerFactory.getLogger(BaseTest.class);

    @BeforeClass(alwaysRun = true)
    public void logSuiteStart(ITestContext context) {
        logger.info(ReportUtils.getReportHeader(context));
    }

    @AfterClass(alwaysRun = true)
    public void logSuiteEnd(ITestContext context) {
        logger.info("Тестовый набор завершён: {}", context.getName());
    }

    // Сюда потом можно запихнуть утилиты, общие и для UI, и для API
}
