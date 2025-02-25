package tests;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestContext;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import utils.ReportUtils;

public abstract class BaseTest {

    private static final Logger LOG = LoggerFactory.getLogger(BaseTest.class);

    @BeforeClass(alwaysRun = true)
    public void logSuiteStart(ITestContext context) {
        LOG.info(ReportUtils.getReportHeader(context));
    }

    @AfterClass(alwaysRun = true)
    public void logSuiteEnd(ITestContext context) {
        LOG.info("Тестовый набор завершён: {}", context.getName());
    }
}
