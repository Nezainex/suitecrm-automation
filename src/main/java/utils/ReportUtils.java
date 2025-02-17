package utils;

import base.BaseUtils;
import org.testng.ITestContext;
import org.testng.ITestResult;
import utils.constants.ConsoleColors;

import java.lang.reflect.Method;

public class ReportUtils {

    private final static String H_LINE = ConsoleColors.CYAN + "\n==========================================================================================\n";
    public final static String END_LINE = ConsoleColors.PURPLE_BRIGHT + "\n______________________________________________________________________________________________________________________________" + ConsoleColors.RESET;
    private static boolean headerLogged = false; // Флаг для предотвращения повторного логирования заголовка

    private static String getTestStatus(ITestResult result) {
        int status = result.getStatus();

        return switch (status) {
            case 1 -> "\u001B[32m" + "PASS" + "\u001B[0m";
            case 2 -> "\u001B[31m" + "FAIL" + "\u001B[0m";
            default -> "UNDEFINED";
        };
    }

    private static String getTestRunTime(ITestResult result) {
        final long time = result.getEndMillis() - result.getStartMillis();

        return DateTimeUtils.getTimeInMinSecFormat(time);
    }

    public static String getReportHeader(ITestContext context) {
        if (headerLogged) {
            return ""; // Если заголовок уже логировался, возвращаем пустую строку
        }
        headerLogged = true; // Устанавливаем флаг, чтобы избежать повторного логирования

        String header = "\tTest Report\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t" + "\n";
        String currentDate = "\tDate: "
                + DateTimeUtils.getCurrentDateTime()
                + "\t\t\t\t\t\t\t\t\t\t\t\t\t\t" + "\n";
        String projectName = "\tProject: Suitecrm-automation" + "\n";
        String baseURL = "\tBASE_URL: " + BaseUtils.getBaseUrl()
                + "\t\t\t\t\t\t\t\t\t\t\t" + "\n";

        return H_LINE + header + currentDate + projectName + baseURL + H_LINE;
    }

    public static String getClassNameTestName(Method method, ITestResult result) {
        String className = result.getTestClass().toString();
        String testName = method.getName();

        return className.substring(22, className.length() - 1) + "/" + testName;
    }

    public static String getTestStatistics(Method method, ITestResult result) {

        return getClassNameTestName(method, result)
                + "   ----   " + getTestStatus(result)
                + "\t Run Time: " + getTestRunTime(result)
                + END_LINE;
    }
}