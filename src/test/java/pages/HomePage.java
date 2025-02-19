package pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import lombok.Getter;

import java.time.Duration;
import java.util.Objects;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverRunner.url;

@Getter
public class HomePage extends BasePage {
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
     * Методы проверки дашлетов
     */
    public boolean isMyCallsVisible() {
        return myCallsBlock.shouldBe(Condition.visible, Duration.ofSeconds(10)).exists();
    }

    public boolean isMyMeetingsVisible() {
        return myMeetingsBlock.shouldBe(Condition.visible, Duration.ofSeconds(10)).exists();
    }

    public boolean isMyTopOpenOppVisible() {
        return myTopOpenOppBlock.shouldBe(Condition.visible, Duration.ofSeconds(10)).exists();
    }

    public boolean isMyAccountsVisible() {
        return myAccountsBlock.shouldBe(Condition.visible, Duration.ofSeconds(10)).exists();
    }

    public boolean isMyLeadsVisible() {
        return myLeadsBlock.shouldBe(Condition.visible, Duration.ofSeconds(10)).exists();
    }

    public boolean isActivityStreamBlockVisible() {
        return myActivityStreamBlock.shouldBe(Condition.visible, Duration.ofSeconds(10)).exists();
    }
    /**
     * Hover-методы для каждого меню
     */
    public void hoverAccountsMenu() {
        accountsMenu.shouldBe(Condition.visible, Duration.ofSeconds(10)).hover();
    }

    public void hoverContactsMenu() {
        contactsMenu.shouldBe(Condition.visible, Duration.ofSeconds(10)).hover();
    }

    public void hoverOpportunitiesMenu() {
        opportunitiesMenu.shouldBe(Condition.visible, Duration.ofSeconds(10)).hover();
    }

    public void hoverLeadsMenu() {
        leadsMenu.shouldBe(Condition.visible, Duration.ofSeconds(10)).hover();
    }

    public void hoverQuotesMenu() {
        quotesMenu.shouldBe(Condition.visible, Duration.ofSeconds(10)).hover();
    }

    public void hoverCalendarMenu() {
        calendarMenu.shouldBe(Condition.visible, Duration.ofSeconds(10)).hover();
    }

    public void hoverDocumentsMenu() {
        documentsMenu.shouldBe(Condition.visible, Duration.ofSeconds(10)).hover();
    }

    public void hoverEmailsMenu() {
        emailsMenu.shouldBe(Condition.visible, Duration.ofSeconds(10)).hover();
    }

    public void hoverMoreMenu() {
        moreMenu.shouldBe(Condition.visible, Duration.ofSeconds(10)).hover();
    }
    /**
     * Click-методы для "Accounts"
     */
    public void clickCreateAccount() {
        createAccountItem.shouldBe(Condition.visible, Duration.ofSeconds(10)).click();
    }

    public void clickViewAccounts() {
        viewAccountsItem.shouldBe(Condition.visible, Duration.ofSeconds(10)).click();
    }

    public void clickImportAccounts() {
        importAccountsItem.shouldBe(Condition.visible, Duration.ofSeconds(10)).click();
    }
    /**
     * Click-методы для "Contacts"
     */
    public void clickCreateContact() {
        createContactItem.shouldBe(Condition.visible, Duration.ofSeconds(10)).click();
    }

    public void clickCreateContactFromVCard() {
        createContactFromVCardItem.shouldBe(Condition.visible, Duration.ofSeconds(10)).click();
    }

    public void clickViewContacts() {
        viewContactsItem.shouldBe(Condition.visible, Duration.ofSeconds(10)).click();
    }

    public void clickImportContacts() {
        importContactsItem.shouldBe(Condition.visible, Duration.ofSeconds(10)).click();
    }
    /**
     * Click-методы для "Opportunities"
     */
    public void clickCreateOpportunity() {
        createOpportunityItem.shouldBe(Condition.visible, Duration.ofSeconds(10)).click();
    }

    public void clickViewOpportunities() {
        viewOpportunitiesItem.shouldBe(Condition.visible, Duration.ofSeconds(10)).click();
    }

    public void clickImportOpportunities() {
        importOpportunitiesItem.shouldBe(Condition.visible, Duration.ofSeconds(10)).click();
    }
    /**
     * Click-методы для "Leads"
     */
    public void clickCreateLead() {
        createLeadItem.shouldBe(Condition.visible, Duration.ofSeconds(10)).click();
    }

    public void clickCreateLeadFromVCard() {
        createLeadFromVCardItem.shouldBe(Condition.visible, Duration.ofSeconds(10)).click();
    }

    public void clickViewLeads() {
        viewLeadsItem.shouldBe(Condition.visible, Duration.ofSeconds(10)).click();
    }

    public void clickImportLeads() {
        importLeadsItem.shouldBe(Condition.visible, Duration.ofSeconds(10)).click();
    }
    /**
     * Click-методы для "Quotes"
     */
    public void clickCreateQuote() {
        createQuoteItem.shouldBe(Condition.visible, Duration.ofSeconds(10)).click();
    }

    public void clickViewQuotes() {
        viewQuotesItem.shouldBe(Condition.visible, Duration.ofSeconds(10)).click();
    }

    public void clickImportQuote() {
        // Обратите внимание, "Import" может совпадать с "Import Line Items" по части текста,
        // поэтому мы сделали отдельные локаторы
        importQuoteItem.shouldBe(Condition.visible, Duration.ofSeconds(10)).click();
    }

    public void clickImportLineItems() {
        importLineItemsItem.shouldBe(Condition.visible, Duration.ofSeconds(10)).click();
    }
    /**
     * Click-методы для "Calendar"
     */
    public void clickScheduleMeeting() {
        scheduleMeetingItem.shouldBe(Condition.visible, Duration.ofSeconds(10)).click();
    }

    public void clickScheduleCall() {
        scheduleCallItem.shouldBe(Condition.visible, Duration.ofSeconds(10)).click();
    }

    public void clickCreateTask() {
        createTaskItem.shouldBe(Condition.visible, Duration.ofSeconds(10)).click();
    }

    public void clickToday() {
        todayItem.shouldBe(Condition.visible, Duration.ofSeconds(10)).click();
    }
    /**
     * Click-методы для "Documents"
     */
    public void clickCreateDocument() {
        createDocumentItem.shouldBe(Condition.visible, Duration.ofSeconds(10)).click();
    }

    public void clickViewDocuments() {
        viewDocumentsItem.shouldBe(Condition.visible, Duration.ofSeconds(10)).click();
    }
    /**
     * Click-методы для "Emails"
     */
    public void clickComposeEmail() {
        composeEmailItem.shouldBe(Condition.visible, Duration.ofSeconds(10)).click();
    }

    public void clickViewEmail() {
        viewEmailItem.shouldBe(Condition.visible, Duration.ofSeconds(10)).click();
    }
    /**
     * Click-методы для "More"
     */
    public void clickMoreHome() {
        moreHomeItem.shouldBe(Condition.visible, Duration.ofSeconds(10)).click();
    }

    public void clickMoreCampaigns() {
        moreCampaignsItem.shouldBe(Condition.visible, Duration.ofSeconds(10)).click();
    }

    public void clickMoreCalls() {
        moreCallsItem.shouldBe(Condition.visible, Duration.ofSeconds(10)).click();
    }

    public void clickMoreMeetings() {
        moreMeetingsItem.shouldBe(Condition.visible, Duration.ofSeconds(10)).click();
    }

    public void clickMoreTasks() {
        moreTasksItem.shouldBe(Condition.visible, Duration.ofSeconds(10)).click();
    }

    public void clickMoreNotes() {
        moreNotesItem.shouldBe(Condition.visible, Duration.ofSeconds(10)).click();
    }

    public void clickMoreInvoices() {
        moreInvoicesItem.shouldBe(Condition.visible, Duration.ofSeconds(10)).click();
    }

    public void clickMoreContracts() {
        moreContractsItem.shouldBe(Condition.visible, Duration.ofSeconds(10)).click();
    }

    public void clickMoreCases() {
        moreCasesItem.shouldBe(Condition.visible, Duration.ofSeconds(10)).click();
    }

    public void clickMoreTargets() {
        moreTargetsItem.shouldBe(Condition.visible, Duration.ofSeconds(10)).click();
    }

    public void clickMoreTargetsLists() {
        moreTargetsListsItem.shouldBe(Condition.visible, Duration.ofSeconds(10)).click();
    }

    public void clickMoreProjects() {
        moreProjectsItem.shouldBe(Condition.visible, Duration.ofSeconds(10)).click();
    }

    public void clickMoreProjectsTemplates() {
        moreProjectsTemplatesItem.shouldBe(Condition.visible, Duration.ofSeconds(10)).click();
    }

    public void clickMoreEvents() {
        moreEventsItem.shouldBe(Condition.visible, Duration.ofSeconds(10)).click();
    }

    public void clickMoreLocations() {
        moreLocationsItem.shouldBe(Condition.visible, Duration.ofSeconds(10)).click();
    }

    public void clickMoreProducts() {
        moreProductsItem.shouldBe(Condition.visible, Duration.ofSeconds(10)).click();
    }

    public void clickMoreProductsCategories() {
        moreProductsCategoriesItem.shouldBe(Condition.visible, Duration.ofSeconds(10)).click();
    }

    public void clickMorePdfTemplates() {
        morePdfTemplatesItem.shouldBe(Condition.visible, Duration.ofSeconds(10)).click();
    }

    public void clickMoreReports() {
        moreReportsItem.shouldBe(Condition.visible, Duration.ofSeconds(10)).click();
    }

    public void clickMoreKnowledgeBase() {
        moreKnowledgeBaseItem.shouldBe(Condition.visible, Duration.ofSeconds(10)).click();
    }

    public void clickMoreKBCategories() {
        moreKBCategoriesItem.shouldBe(Condition.visible, Duration.ofSeconds(10)).click();
    }

    public void clickMoreEmailTemplate() {
        moreEmailTemplateItem.shouldBe(Condition.visible, Duration.ofSeconds(10)).click();
    }

    public void clickMoreSurveys() {
        moreSurveysItem.shouldBe(Condition.visible, Duration.ofSeconds(10)).click();
    }
}
