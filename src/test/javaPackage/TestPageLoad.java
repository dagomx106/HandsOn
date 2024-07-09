package javaPackage;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.time.Duration;

public class TestPageLoad {

    static WebDriver driver;
    @BeforeAll
    static void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.setPageLoadStrategy(PageLoadStrategy.EAGER);
        options.addArguments("--headless=new");
        driver = new ChromeDriver(options);
    }

    @AfterAll
    static void tearDown() {
        driver.quit();
    }

    @Test
    void testPageLoad() {
        long initMillis = System.currentTimeMillis();
        driver.get("https://bonigarcia.dev/selenium-webdriver-java/index.html");
        Duration elapsed = Duration.ofMillis(System.currentTimeMillis() - initMillis);
        Capabilities capabilities = ((RemoteWebDriver)driver).getCapabilities();
        Object loadCapability = capabilities.getCapability(CapabilityType.PAGE_LOAD_STRATEGY);
        String browserName = capabilities.getBrowserName();
        capabilities.getCapabilityNames().forEach(System.out::println);
        System.out.println();
        System.out.println("Page load strategy: " + loadCapability);
        System.out.println("Browser name: " + browserName);
        System.out.println("Page loaded in " + elapsed.toMillis() + " milliseconds");
    }
}