package javaPackage;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.util.HashMap;
import java.util.Map;

public class TestNotifications {

    WebDriver driver;
@Test
    void testChromeNotification() {
        ChromeOptions options = new ChromeOptions();
        Map<String, Object> prefs = new HashMap<>();
        prefs.put("profile.default_content_setting_values.notifications", 2);
        options.setExperimentalOption("prefs", prefs);
        driver = new ChromeDriver(options);

        driver.manage().window().maximize();
        driver.get("https://bonigarcia.dev/selenium-webdriver-java/notifications.html");
        driver.findElement(By.id("notify-me")).click();
        driver.quit();
    }

    @Test
    void testFirefoxNotification() {
        FirefoxOptions options = new FirefoxOptions();
        options.addPreference("permissions.default.desktop-notification", 2);
        driver = new FirefoxDriver(options);
        driver.manage().window().maximize();
        driver.get("https://bonigarcia.dev/selenium-webdriver-java/notifications.html");
        driver.findElement(By.id("notify-me")).click();
        driver.quit();
    }
}
