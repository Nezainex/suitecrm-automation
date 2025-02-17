package base;

import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.*;
import utils.ReportUtils;
import utils.TestUtils;

import java.lang.reflect.Method;

import static base.BaseUtils.captureFailureShot;
import static com.codeborne.selenide.Selenide.closeWebDriver;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

public abstract class BaseTest extends BasePage {

    @BeforeClass(alwaysRun = true)
    public void logSuiteStart(ITestContext context) {
        System.out.println(ReportUtils.getReportHeader(context));
    }

    @BeforeMethod(alwaysRun = true)
    @Parameters("browser")
    public void setUpBrowser(@Optional("chrome") String browser) {
        BaseUtils.configureBrowser(browser);
        TestUtils.loadBaseUrlPage();  // Загружаем страницу логина и ждем логотип
        getWebDriver().manage().window().maximize();
    }

    public void reLoadBasePageIfNeeded() {
        TestUtils.reLoadBaseUrlPage();
    }

    @AfterMethod(alwaysRun = true)
    public void tearDownBrowser(ITestResult result, Method method) {
        try {
            System.out.println(ReportUtils.getTestStatistics(method, result));

            if (!result.isSuccess()) {
                captureFailureShot(result, method);
            }

            closeWebDriver();
        } catch (Exception e) {
            System.err.println("Ошибка при завершении сессии WebDriver: " + e.getMessage());
        }
    }
}
