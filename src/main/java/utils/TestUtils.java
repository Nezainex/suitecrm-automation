package utils;

import base.BaseUtils;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.testng.Reporter;

import java.security.SecureRandom;

import static com.codeborne.selenide.Selenide.$x;

public class TestUtils {

    private static final SelenideElement LOGIN_PAGE_IDENTIFIER = $x("//img[@src='legacy/themes/default/images/company_logo.png']");

    public static void loadBaseUrlPage() {
        Selenide.open(BaseUtils.getBaseUrl());
        waitForPageLoaded();
    }

    public static void waitForPageLoaded() {
        LOGIN_PAGE_IDENTIFIER.shouldBe(Condition.visible);
    }

    public static void reLoadBaseUrlPage() {
        int attempts = 0;
        while (attempts < 3) {
            try {
                loadBaseUrlPage();
                LOGIN_PAGE_IDENTIFIER.shouldBe(Condition.visible);
                return; // Успешно загружена, выходим из цикла
            } catch (Exception e) {
                Reporter.log("Попытка загрузки страницы: " + (attempts + 1), true);
                attempts++;
            }
        }
        Reporter.log("!!!!! Ошибка !!!!! Страница логина не загружена после 3 попыток. Повторите запуск.", true);
    }

    public static String getRandomName(int length) {
        final String characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        SecureRandom random = new SecureRandom();

        return random.ints(length, 0, characters.length())
                .mapToObj(characters::charAt)
                .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
                .toString();
    }

    public static String getRandomName() {
        return getRandomName(7);
    }

    public static int convertStringToInt(String text) {
        return Integer.parseInt(text);
    }

    public static String getSubstring(String text, String separator) {
        int index = text.indexOf(separator);
        return text.substring(0, index);
    }

    public static String getRandomNumber(int length) {
        SecureRandom random = new SecureRandom();
        StringBuilder number = new StringBuilder(length);

        number.append(random.nextInt(9) + 1);
        for (int i = 1; i < length; i++) {
            number.append(random.nextInt(10));
        }

        return number.toString();
    }
}