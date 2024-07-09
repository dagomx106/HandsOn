package javaPackage;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.SessionId;

import static org.junit.jupiter.api.Assertions.*;

public class TestHeadlessBrowser {
    static WebDriver driver;

    @BeforeAll
    static void setup() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless=new");
        driver = new ChromeDriver(options);
    }

    @AfterAll
    static void tearDown() {
        driver.quit();
    }

    @Test
    @DisplayName("Test Headless Browser")
    void testHeadlessBrowser() {
        driver.get("https://bonigarcia.dev/selenium-webdriver-java/index.html");
        String title = driver.getTitle();
        assertEquals("Hands-On Selenium WebDriver with Java", title);
    }

    @Test
    @DisplayName("Test Headless Browser Locators")
    void testHeadlessBrowserLocators() {
            driver.get("https://bonigarcia.dev/selenium-webdriver-java/web-form.html");
            By fileLocator = By.xpath("//input[@type='file']");
            driver.findElement(fileLocator).sendKeys("C:\\test\\TCS.txt");
            boolean contains = driver.getPageSource().contains("</html>");
            assertTrue(contains);

            SessionId sessionId = ((RemoteWebDriver) driver).getSessionId();
            assertNotNull(sessionId);
            System.out.println(sessionId);

            By textArea = By.tagName("textarea");
            String rows = driver.findElement(textArea).getDomAttribute("rows");
            driver.findElement(textArea).sendKeys(rows);
            assertEquals(3, Integer.parseInt(rows));

            By linkText = By.linkText("Return to index");
            String cursor = driver.findElement(linkText).getCssValue("cursor");

            assertEquals("pointer", cursor);



            By partialLinkText = By.partialLinkText("index");

            assertEquals(driver.findElement(linkText).getLocation(), driver.findElement(partialLinkText).getLocation());
            assertEquals(driver.findElement(linkText).getRect(), driver.findElement(partialLinkText).getRect());
        }
    }