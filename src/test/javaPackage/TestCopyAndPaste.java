package javaPackage;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestCopyAndPaste {

    static WebDriver driver;

    @BeforeAll
    static void setup() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://bonigarcia.dev/selenium-webdriver-java/web-form.html");
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    @AfterAll
    static void tearDown() {
        driver.quit();
    }

    @Test
    void testCopyPaste() throws InterruptedException {
        Actions actions = new Actions(driver);
        WebElement input = driver.findElement(By.id("my-text-id"));
        WebElement textArea = driver.findElement(By.name("my-textarea"));

        String os = System.getProperty("os.name");
        Keys controlKey = os.contains("Mac") ? Keys.COMMAND : Keys.CONTROL;

        actions.sendKeys(input, "Copy this text").keyDown(controlKey);
        actions.sendKeys(input,"a").sendKeys(input,"c").sendKeys(textArea,"v").build().perform();
        Thread.sleep(3000);
        assertEquals(input.getAttribute("value"), textArea.getAttribute("value"));
    }
}
