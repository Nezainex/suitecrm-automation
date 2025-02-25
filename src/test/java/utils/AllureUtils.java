package utils;

import com.codeborne.selenide.WebDriverRunner;
import io.qameta.allure.Attachment;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class AllureUtils {
    @Attachment(value = "screen", type = "image/png")
    public static byte[] screen() {
        return ((TakesScreenshot) WebDriverRunner.getWebDriver()).getScreenshotAs(OutputType.BYTES);
    }

    @Attachment(value = "page source", type = "text/html")
    public static byte[] pageSource() {
        return Objects.requireNonNull(WebDriverRunner.getWebDriver().getPageSource()).getBytes(StandardCharsets.UTF_8);
    }
}
