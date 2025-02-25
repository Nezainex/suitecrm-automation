package tests.ui;

import io.qameta.allure.Step;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.HomePage;

import java.util.List;

import static com.codeborne.selenide.Selenide.$x;

public class HomeTest extends BaseUITest {

    private static final Logger logger = LoggerFactory.getLogger(HomeTest.class);
    private HomePage homePage;
}
