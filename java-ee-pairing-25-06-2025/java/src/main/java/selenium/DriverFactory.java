package selenium;

import helpers.TestDataReader;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;

import java.util.HashMap;
import java.util.Map;

public final class DriverFactory {

    public static WebDriver getDriver(String browserName) {
        String isMobile = TestDataReader.getInstance().getTestData().getOrDefault("isMobile", "false");
        return switch (browserName.toLowerCase()) {
            case "chrome" -> {
                WebDriverManager.chromedriver().setup();
                ChromeOptions options = getChromeOptions(isMobile);
                yield new ChromeDriver(options);
            }
            case "firefox" -> {
                WebDriverManager.firefoxdriver().setup();
                FirefoxOptions options = getFirefoxOptions(isMobile);
                yield new FirefoxDriver(options);
            }
            // Add more cases for other browsers
            default -> throw new IllegalArgumentException("Invalid browser name: " + browserName);
        };
    }

    private static FirefoxOptions getFirefoxOptions(String isMobile) {
        FirefoxOptions options = new FirefoxOptions();
        if(isMobile.equalsIgnoreCase("true")) {
            FirefoxProfile profile = new FirefoxProfile();
            profile.setPreference("general.useragent.override",
                    "Mozilla/5.0 (iPhone; CPU iPhone OS 13_5 like Mac OS X) " +
                            "AppleWebKit/605.1.15 (KHTML, like Gecko) Version/13.1.1 " +
                            "Mobile/15E148 Safari/604.1");
            options.setProfile(profile);
        }
        return options;
    }

    private static ChromeOptions getChromeOptions(String isMobile) {
        ChromeOptions options = new ChromeOptions();
        if(isMobile.equalsIgnoreCase("true")) {
            Map<String, String> mobileEmulation = new HashMap<>();
            mobileEmulation.put("deviceName", "iPhone X");
            options.setExperimentalOption("mobileEmulation", mobileEmulation);
        }
        return options;
    }
}
