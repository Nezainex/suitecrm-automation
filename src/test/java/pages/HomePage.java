package pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;

import java.time.Duration;
import java.util.Objects;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverRunner.url;

public class HomePage extends BasePage {
    private static final Logger LOG = LoggerFactory.getLogger(HomePage.class);

    // Локатор для проверки домашней страницы (аналог "SUITECRM DASHBOARD")
    private final SelenideElement homeMenuItem =
            $x("//scrm-home-menu-item");
    /**
     * Дашлеты (Dashboard Blocks)
     */
    private final SelenideElement myCallsBlock =
            $x("//span[text()='My Calls' and ancestor::div[@class='dashboard-title']]");

    private final SelenideElement myMeetingsBlock =
            $x("//span[text()='My Meetings' and ancestor::div[@class='dashboard-title']]");

    private final SelenideElement myTopOpenOppBlock =
            $x("//span[text()='My Top Open Opportunities' and ancestor::div[@class='dashboard-title']]");

    private final SelenideElement myAccountsBlock =
            $x("//span[text()='My Accounts' and ancestor::div[@class='dashboard-title']]");

    private final SelenideElement myLeadsBlock =
            $x("//span[text()='My Leads' and ancestor::div[@class='dashboard-title']]");

    private final SelenideElement myActivityStreamBlock =
            $x("//span[text()='My Activity Stream' and ancestor::div[@class='dashboard-title']]");
    /**
     * Меню "Accounts"
     */
    private final SelenideElement accountsMenu =
            $x("//a[contains(@data-toggle,'dropdown') and contains(text(), 'Accounts')]");

    private final SelenideElement createAccountItem =
            $x("//li/a[contains(text(),'Create Account')]");
    private final SelenideElement viewAccountsItem =
            $x("//li/a[contains(text(),'View Accounts')]");
    private final SelenideElement importAccountsItem =
            $x("//li/a[contains(text(),'Import Accounts')]");
    /**
     * Меню "Contacts"
     */
    private final SelenideElement contactsMenu =
            $x("//a[contains(@data-toggle,'dropdown') and contains(text(), 'Contacts')]");

    private final SelenideElement createContactItem =
            $x("//li/a[contains(text(),'Create Contact')]");
    private final SelenideElement createContactFromVCardItem =
            $x("//li/a[contains(text(),'Create Contact From vCard')]");
    private final SelenideElement viewContactsItem =
            $x("//li/a[contains(text(),'View Contacts')]");
    private final SelenideElement importContactsItem =
            $x("//li/a[contains(text(),'Import Contacts')]");
    /**
     * Меню "Opportunities"
     */
    private final SelenideElement opportunitiesMenu =
            $x("//a[contains(@data-toggle,'dropdown') and contains(text(), 'Opportunities')]");

    private final SelenideElement createOpportunityItem =
            $x("//li/a[contains(text(),'Create Opportunity')]");
    private final SelenideElement viewOpportunitiesItem =
            $x("//li/a[contains(text(),'View Opportunities')]");
    private final SelenideElement importOpportunitiesItem =
            $x("//li/a[contains(text(),'Import Opportunities')]");
    /**
     * Меню "Leads"
     */
    private final SelenideElement leadsMenu =
            $x("//a[contains(@data-toggle,'dropdown') and contains(text(), 'Leads')]");

    private final SelenideElement createLeadItem =
            $x("//li/a[contains(text(),'Create Lead')]");
    private final SelenideElement createLeadFromVCardItem =
            $x("//li/a[contains(text(),'Create Lead From vCard')]");
    private final SelenideElement viewLeadsItem =
            $x("//li/a[contains(text(),'View Leads')]");
    private final SelenideElement importLeadsItem =
            $x("//li/a[contains(text(),'Import Leads')]");
    /**
     * Меню "Quotes"
     */
    private final SelenideElement quotesMenu =
            $x("//a[contains(@data-toggle,'dropdown') and contains(text(), 'Quotes')]");

    private final SelenideElement createQuoteItem =
            $x("//li/a[contains(text(),'Create Quote')]");
    private final SelenideElement viewQuotesItem =
            $x("//li/a[contains(text(),'View Quotes')]");
    private final SelenideElement importQuoteItem =
            $x("//li/a[contains(text(),'Import') and not(contains(text(),'Line Items'))]");
    private final SelenideElement importLineItemsItem =
            $x("//li/a[contains(text(),'Import Line Items')]");
    /**
     * Меню "Calendar"
     */
    private final SelenideElement calendarMenu =
            $x("//a[contains(@data-toggle,'dropdown') and contains(text(), 'Calendar')]");

    private final SelenideElement scheduleMeetingItem =
            $x("//li/a[contains(text(),'Schedule Meeting')]");
    private final SelenideElement scheduleCallItem =
            $x("//li/a[contains(text(),'Schedule Call')]");
    private final SelenideElement createTaskItem =
            $x("//li/a[contains(text(),'Create Task')]");
    private final SelenideElement todayItem =
            $x("//li/a[contains(text(),'Today')]");
    /**
     * Меню "Documents"
     */
    private final SelenideElement documentsMenu =
            $x("//a[contains(@data-toggle,'dropdown') and contains(text(), 'Documents')]");

    private final SelenideElement createDocumentItem =
            $x("//li/a[contains(text(),'Create Document')]");
    private final SelenideElement viewDocumentsItem =
            $x("//li/a[contains(text(),'View Documents')]");
    /**
     * Меню "Emails"
     */
    private final SelenideElement emailsMenu =
            $x("//a[contains(@data-toggle,'dropdown') and contains(text(), 'Emails')]");

    private final SelenideElement composeEmailItem =
            $x("//li/a[contains(text(),'Compose')]");
    private final SelenideElement viewEmailItem =
            $x("//li/a[contains(text(),'View Email')]");
    /**
     * Меню "More"
     */
    private final SelenideElement moreMenu =
            $x("//a[contains(@data-toggle,'dropdown') and contains(text(), 'More')]");

    private final SelenideElement moreHomeItem =
            $x("//li/a[contains(text(),'Home')]");
    private final SelenideElement moreCampaignsItem =
            $x("//li/a[contains(text(),'Campaigns')]");
    private final SelenideElement moreCallsItem =
            $x("//li/a[contains(text(),'Calls')]");
    private final SelenideElement moreMeetingsItem =
            $x("//li/a[contains(text(),'Meetings')]");
    private final SelenideElement moreTasksItem =
            $x("//li/a[contains(text(),'Tasks')]");
    private final SelenideElement moreNotesItem =
            $x("//li/a[contains(text(),'Notes')]");
    private final SelenideElement moreInvoicesItem =
            $x("//li/a[contains(text(),'Invoices')]");
    private final SelenideElement moreContractsItem =
            $x("//li/a[contains(text(),'Contracts')]");
    private final SelenideElement moreCasesItem =
            $x("//li/a[contains(text(),'Cases')]");
    private final SelenideElement moreTargetsItem =
            $x("//li/a[contains(text(),'Targets')]");
    private final SelenideElement moreTargetsListsItem =
            $x("//li/a[contains(text(),'Targets - Lists')]");
    private final SelenideElement moreProjectsItem =
            $x("//li/a[contains(text(),'Projects') and not(contains(text(),'Templates'))]");
    private final SelenideElement moreProjectsTemplatesItem =
            $x("//li/a[contains(text(),'Projects - Templates')]");
    private final SelenideElement moreEventsItem =
            $x("//li/a[contains(text(),'Events')]");
    private final SelenideElement moreLocationsItem =
            $x("//li/a[contains(text(),'Locations')]");
    private final SelenideElement moreProductsItem =
            $x("//li/a[contains(text(),'Products') and not(contains(text(),'Categories'))]");
    private final SelenideElement moreProductsCategoriesItem =
            $x("//li/a[contains(text(),'Products - Categories')]");
    private final SelenideElement morePdfTemplatesItem =
            $x("//li/a[contains(text(),'PDF - Templates')]");
    private final SelenideElement moreReportsItem =
            $x("//li/a[contains(text(),'Reports')]");
    private final SelenideElement moreKnowledgeBaseItem =
            $x("//li/a[contains(text(),'Knowledge Base') and not(contains(text(),'Categories'))]");
    private final SelenideElement moreKBCategoriesItem =
            $x("//li/a[contains(text(),'KB - Categories')]");
    private final SelenideElement moreEmailTemplateItem =
            $x("//li/a[contains(text(),'Email - Template')]");
    private final SelenideElement moreSurveysItem =
            $x("//li/a[contains(text(),'Surveys')]");
    /**
     * Открытие домашней страницы с автоматическим вводом логина и пароля, если требуется.
     */
    public void openHomePage() {
        // Открываем страницу
        open("http://localhost:8080/#/home");

        // Проверяем, перенаправило ли на страницу логина
        if (Objects.requireNonNull(url()).contains("/Login")) {
            // Проверяем наличие полей логина, пароля и кнопки входа
            SelenideElement usernameField = $x("//input[@name='username']").shouldBe(Condition.visible, Duration.ofSeconds(5));
            SelenideElement passwordField = $x("//input[@name='password']").shouldBe(Condition.visible, Duration.ofSeconds(5));
            SelenideElement loginButton = $x("//button[@id='login-button']").shouldBe(Condition.visible, Duration.ofSeconds(5));

            usernameField.shouldBe(Condition.visible).setValue("user"); // Замените на актуальный логин
            passwordField.shouldBe(Condition.visible).setValue("bitnami"); // Замените на актуальный пароль
            loginButton.shouldBe(Condition.visible).click();
        }
    }
    /**
     * Переключение на iframe с дашбордом.
     */
    public void switchToHomePageIframe() {
        switchToIframeByWebElement("#_yuiResizeMonitor"); // Укажите актуальный id или name
    }


    /**
     * Переключение на iframe через WebElement.
     * @param iframeXPath - XPath локатор для поиска iframe
     */
    protected void switchToIframeByWebElement(String iframeXPath) {
        String iframeId = "#_yuiResizeMonitor";
        try {
            Selenide.webdriver().driver().switchTo().frame(iframeId);
            LOG.info("Успешно переключено на iframe с ID: " + iframeId);
            //TODO: заменить System.out.println на логгер
        } catch (Exception e) {
            throw new IllegalArgumentException("Не удалось найти iframe с ID: " + iframeId, e);
        }
    }
}
