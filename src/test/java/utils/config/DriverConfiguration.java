
package utils.config;

import com.codeborne.selenide.Configuration;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.chrome.ChromeOptions;

public class DriverConfiguration {

    public static void setup() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--start-maximized");
        chromeOptions.addArguments("--remote-allow-origins=*");
        Configuration.baseUrl = ConfigReader.getConfig().getBaseUrl();
        Configuration.browserCapabilities = chromeOptions;
        Configuration.browser = ConfigReader.getConfig().getBrowserChrome();
        Configuration.timeout = ConfigReader.getConfig().getDefaultTimeout();
        Configuration.pageLoadTimeout = ConfigReader.getConfig().getPageLoadTimeout();
        Configuration.reopenBrowserOnFail = true;
        Configuration.pageLoadStrategy = "normal";
        Configuration.headless = false;
    }
}
