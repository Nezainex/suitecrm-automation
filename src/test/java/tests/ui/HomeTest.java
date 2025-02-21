package tests.ui;

import io.qameta.allure.Step;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pages.HomePage;
import tests.BaseUITest;
import utils.listeners.TestListener;
import utils.listeners.TestSuiteListener;

import java.util.List;

import static com.codeborne.selenide.Selenide.$x;

@Listeners({TestListener.class, TestSuiteListener.class})
public class HomeTest extends BaseUITest {

    private static final Logger logger = LoggerFactory.getLogger(HomeTest.class);
    private HomePage homePage;

    @BeforeMethod
    public void setUp() {
        initializeHomePage();
    }

    @Step("Инициализация страницы и открытие главной страницы")
    public void initializeHomePage() {
        logger.info("Инициализация страницы и открытие главной страницы");
        homePage = new HomePage();
        homePage.openHomePage();
        switchToHomePageIframe();
    }

    @Step("Переключаемся на iframe домашней страницы")
    public void switchToHomePageIframe() {
        logger.info("Переключаемся на iframe домашней страницы");
        homePage.switchToHomePageIframe();
    }

    @Test(description = "Проверка отображения блоков на домашней странице")
    public void testVisibilityOfBlocks() {
        checkVisibilityOfBlocks();
    }

    @Step("Проверяем отображение всех блоков")
    public void checkVisibilityOfBlocks() {
        logger.info("Проверяем отображение всех блоков");
        List<String> blocks = List.of("My Calls", "My Meetings", "My Top Open Opportunities", "My Accounts", "My Leads", "My Activity Stream");
        for (String block : blocks) {
            checkVisibilityOfBlock(block);
        }
    }

    @Step("Проверяем отображение блока: {block}")
    public void checkVisibilityOfBlock(String block) {
        logger.info("Проверяем отображение блока: {}", block);
        switch (block) {
            case "My Calls" -> Assert.assertTrue(homePage.isMyCallsVisible(), "Блок 'My Calls' не отображается!");
            case "My Meetings" -> Assert.assertTrue(homePage.isMyMeetingsVisible(), "Блок 'My Meetings' не отображается!");
            case "My Top Open Opportunities" -> Assert.assertTrue(homePage.isMyTopOpenOppVisible(), "Блок 'My Top Open Opportunities' не отображается!");
            case "My Accounts" -> Assert.assertTrue(homePage.isMyAccountsVisible(), "Блок 'My Accounts' не отображается!");
            case "My Leads" -> Assert.assertTrue(homePage.isMyLeadsVisible(), "Блок 'My Leads' не отображается!");
            case "My Activity Stream" -> Assert.assertTrue(homePage.isActivityStreamBlockVisible(), "Блок 'My Activity Stream' не отображается!");
            default -> throw new IllegalArgumentException("Неизвестный блок: " + block);
        }
    }

    @Test(description = "Проверка отображения меню с подпунктами")
    public void testTopMenuWithSubItems() {
        hoverAccountsMenu();
        List<String> subItems = List.of("Create Account", "View Accounts", "Import Accounts");
        checkSubMenuVisibility(subItems);
    }

    @Step("Наводим курсор на меню: Accounts")
    public void hoverAccountsMenu() {
        logger.info("Наводим курсор на меню: Accounts");
        homePage.hoverAccountsMenu();
    }

    @Step("Проверяем отображение подпунктов меню")
    public void checkSubMenuVisibility(List<String> subItems) {
        logger.info("Проверяем отображение подпунктов меню");
        for (String subItem : subItems) {
            checkSubMenuItemVisible(subItem);
        }
    }

    @Step("Проверяем, что подпункт меню {subItem} отображается")
    public void checkSubMenuItemVisible(String subItem) {
        logger.info("Проверяем, что подпункт меню {} отображается", subItem);
        boolean visible = homePage.isElementVisible($x("//li/a[contains(text(),'" + subItem + "')]"));
        Assert.assertTrue(visible, "Подпункт меню '" + subItem + "' не отображается!");
    }

    @Test(description = "Проверка выбора подпункта меню")
    public void testSelectSubMenuItem() {
        selectSubMenuItem("Create Account");
        verifySubMenuPage();
    }

    @Step("Выбираем подпункт меню: {subMenuItem}")
    public void selectSubMenuItem(String subMenuItem) {
        logger.info("Выбираем подпункт меню: {}", subMenuItem);
        if ("Create Account".equals(subMenuItem)) {
            homePage.clickCreateAccount();
        } else {
            throw new IllegalArgumentException("Неизвестный подпункт меню: " + subMenuItem);
        }
    }

    @Step("Проверяем, что нужная страница открылась")
    public void verifySubMenuPage() {
        logger.info("Проверяем, что нужная страница открылась");
        String expectedUrl = "http://localhost:8080/#/accounts/create";
        Assert.assertTrue(homePage.getCurrentURL().contains(expectedUrl), "Нужная страница не открылась!");
    }

    @Test(description = "Проверка наведения на меню")
    public void testHoverOnMenu() {
        hoverMenu("Documents");
        verifyMenuActive("Documents");
    }

    @Step("Наводим курсор на меню: {menuName}")
    public void hoverMenu(String menuName) {
        logger.info("Наводим курсор на меню: {}", menuName);
        if ("Documents".equals(menuName)) {
            homePage.hoverDocumentsMenu();
        } else {
            throw new IllegalArgumentException("Неизвестное меню: " + menuName);
        }
    }

    @Step("Проверяем, что меню {menuName} активно")
    public void verifyMenuActive(String menuName) {
        logger.info("Проверяем, что меню {} активно", menuName);
        boolean visible = homePage.isElementVisible($x("//a[contains(text(), '" + menuName + "') and @class='active']"));
        Assert.assertTrue(visible, "Меню '" + menuName + "' не активно!");
    }
}
