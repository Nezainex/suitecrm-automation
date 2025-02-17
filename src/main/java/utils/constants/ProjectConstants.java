package utils.constants;

public class ProjectConstants {
    // Configurations
    public static final String BROWSER_CHROME = "chrome";
    public static final String BROWSER_FIREFOX = "firefox";
    // URLs
    public static final String BASE_URL = "http://localhost:8080";
    public static final String LOGIN_ENDPOINT = "/login";
    // Логин и пароль
    public static final String VALID_USERNAME = "user";
    public static final String VALID_PASSWORD = "bitnami";
    // Test Data
    public static final String SALT_UPPER_AND_LOWER_LETTERS = "AaBbCcDdEeFfGgHhIiJjKkLlMmNnOoPpQqRrSsTtUuVvWwXxYyZz";
    public static final String SALT_NUMBERS = "1234567890";
    // Timeouts
    public static final int DEFAULT_TIMEOUT = 5000; // in milliseconds
    public static final int PAGE_LOAD_TIMEOUT = 30000; // in milliseconds
    // Messages
    public static final String ERROR_PAGE_NOT_FOUND = "Page not found.";
    public static final String ERROR_ELEMENT_NOT_FOUND = "Element not found.";
    public static final String ERROR_INVALID_URL = "Invalid URL format.";
    // Other Constants
    public static final String SCREENSHOT_PATH = "target/screenshots/";
}
