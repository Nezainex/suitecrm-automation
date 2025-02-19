package utils;

import org.testng.ITestContext;
import org.testng.ITestResult;
import utils.config.ConfigReader;

import java.lang.reflect.Method;

public class ReportUtils {

    private static final String H_LINE = getColor("CYAN")
            + "\n==========================================================================================\n"
            + getColor("RESET");

    public static final String END_LINE = getColor("PURPLE_BRIGHT")
            + "\n______________________________________________________________________________________________________________________________"
            + getColor("RESET");

    private static boolean headerLogged = false; // Флаг для предотвращения повторного логирования заголовка

    /**
     * Возвращает цвет из YAML-файла по ключу (например, "GREEN" или "RED").
     * Если ключ не найден, возвращает пустую строку, чтобы избежать NPE.
     */
    private static String getColor(String colorKey) {
        // Берём из Map<String, String> colors, которая лежит в ConfigReader.getConfig().getColors()
        return ConfigReader.getConfig()
                .getColors()
                .getOrDefault(colorKey, "");
    }
    private static String getTestStatus(ITestResult result) {
        int status = result.getStatus();
        return switch (status) {
            case ITestResult.SUCCESS -> getColor("GREEN") + "PASS" + getColor("RESET");
            case ITestResult.FAILURE -> getColor("RED") + "FAIL" + getColor("RESET");
            default -> "UNDEFINED";
        };
    }

    private static String getTestRunTime(ITestResult result) {
        final long time = result.getEndMillis() - result.getStartMillis();
        return DateTimeUtils.getTimeInMinSecFormat(time);
    }

    public static String getReportHeader(ITestContext context) {
        if (headerLogged) {
            return "";
        }
        headerLogged = true;

        String header = "\tTest Report\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\n";
        String currentDate = "\tDate: " + DateTimeUtils.getCurrentDateTime() + "\n";
        String projectName = "\tProject: Suitecrm-automation\n";
        // Вместо config.properties, используем BaseUtils.getBaseUrl() (из YAML)
        String baseURL = "\tBASE_URL: " + BaseUtils.getBaseUrl() + "\n";

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