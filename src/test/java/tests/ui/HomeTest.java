package tests.ui;

import base.BaseTest;
import io.qameta.allure.Allure;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pages.HomePage;
import utils.listeners.TestListener;
import utils.listeners.TestSuiteListener;

import java.util.List;

import static com.codeborne.selenide.Selenide.$x;

@Listeners({TestListener.class, TestSuiteListener.class})
public class HomeTest extends BaseTest {

    private HomePage homePage;

    @BeforeMethod
    public void setUp() {
        Allure.step("Инициализация страницы и открытие главной страницы", () -> {
            homePage = new HomePage();
            homePage.openHomePage();
            Allure.step("Переключаемся на iframe домашней страницы", homePage::switchToHomePageIframe);
        });
    }

    @Test(description = "Проверка отображения блоков на домашней странице")
    public void testVisibilityOfBlocks() {
        Allure.step("Проверяем отображение всех блоков", () -> {
            List<String> blocks = List.of("My Calls", "My Meetings", "My Top Open Opportunities", "My Accounts", "My Leads", "My Activity Stream");

            for (String block : blocks) {
                Allure.step("Проверяем отображение блока: " + block, () -> {
                    switch (block) {
                        case "My Calls" -> Assert.assertTrue(homePage.isMyCallsVisible(), "Блок 'My Calls' не отображается!");
                        case "My Meetings" -> Assert.assertTrue(homePage.isMyMeetingsVisible(), "Блок 'My Meetings' не отображается!");
                        case "My Top Open Opportunities" -> Assert.assertTrue(homePage.isMyTopOpenOppVisible(), "Блок 'My Top Open Opportunities' не отображается!");
                        case "My Accounts" -> Assert.assertTrue(homePage.isMyAccountsVisible(), "Блок 'My Accounts' не отображается!");
                        case "My Leads" -> Assert.assertTrue(homePage.isMyLeadsVisible(), "Блок 'My Leads' не отображается!");
                        case "My Activity Stream" -> Assert.assertTrue(homePage.isActivityStreamBlockVisible(), "Блок 'My Activity Stream' не отображается!");
                        default -> throw new IllegalArgumentException("Неизвестный блок: " + block);
                    }
                });
            }
        });
    }

    @Test(description = "Проверка отображения меню с подпунктами")
    public void testTopMenuWithSubItems() {
        String menuName = "Accounts";
        List<String> subItems = List.of("Create Account", "View Accounts", "Import Accounts");

        Allure.step("Наводим курсор на меню: " + menuName, homePage::hoverAccountsMenu);

        Allure.step("Проверяем, что подпункты меню отображаются", () -> {
            for (String subItem : subItems) {
                boolean visible = homePage.isElementVisible($x("//li/a[contains(text(),'" + subItem + "')]"));
                Assert.assertTrue(visible, "Подпункт меню '" + subItem + "' не отображается!");
            }
        });
    }

    @Test(description = "Проверка выбора подпункта меню")
    public void testSelectSubMenuItem() {
        String subMenuItem = "Create Account";

        Allure.step("Выбираем подпункт меню: " + subMenuItem, homePage::clickCreateAccount);

        Allure.step("Проверяем, что нужная страница открылась", () -> {
            String expectedUrl = "http://localhost:8080/#/accounts/create";
            Assert.assertTrue(homePage.getCurrentURL().contains(expectedUrl), "Нужная страница не открылась!");
        });
    }

    @Test(description = "Проверка наведения на меню")
    public void testHoverOnMenu() {
        String menuName = "Documents";

        Allure.step("Наводим курсор на меню: " + menuName, homePage::hoverDocumentsMenu);

        Allure.step("Проверяем, что меню активно", () -> {
            boolean visible = homePage.isElementVisible($x("//a[contains(text(), 'Documents') and @class='active']"));
            Assert.assertTrue(visible, "Меню '" + menuName + "' не активно!");
        });
    }
}
