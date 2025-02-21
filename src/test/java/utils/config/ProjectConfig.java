package utils.config;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Setter
@Getter
public class ProjectConfig {
    private String browserChrome;
    private String browserFirefox;

    private String baseUrl;
    private String loginEndpoint;

    private String validUsername;
    private String validPassword;

    private String saltUpperAndLowerLetters;
    private String saltNumbers;

    private int defaultTimeout;
    private int pageLoadTimeout;

    private String errorPageNotFound;
    private String errorElementNotFound;
    private String errorInvalidUrl;

    private String screenshotPath;

    private Map<String, String> colors;
}