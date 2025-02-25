package tests.ui;

import com.codeborne.selenide.Selenide;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import tests.BaseTest;
import utils.config.DriverConfiguration;
import utils.listeners.TestListener;

@Listeners({TestListener.class})
public abstract class BaseUITest extends BaseTest {

    @BeforeMethod(alwaysRun = true)
    public void setUpBrowser() {
        DriverConfiguration.setup();
    }

    @AfterMethod(alwaysRun = true)
    public void tearDownBrowser() {
        Selenide.closeWebDriver();
    }
}
