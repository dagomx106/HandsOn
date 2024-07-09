package javaPackage;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestWaits {

    static WebDriver driver;
    @BeforeAll
    static void setup(){
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://bonigarcia.dev/selenium-webdriver-java/loading-images.html");
    }

    @AfterAll
    static void tearDown(){
        driver.quit();
    }

    @Test
    void testImplicitWait(){
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(12));
        List<String> listNames = Arrays.asList("compass","calendar","award","landscape");
        for (String name: listNames) {
            String xpath = String.format("//img[@id='%s']", name);
            WebElement ele = driver.findElement(By.xpath(xpath));
            assertTrue(ele.getAttribute("alt").contains(name));
        }
        assertTrue(driver.findElement(By.id("text")).isDisplayed());
    }

    @Test
    void testExplicitWait(){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(14));
        boolean doneText = wait.until(ExpectedConditions.textToBe(By.id("text"), "Done!"));
        assertTrue(doneText);
    }

    @Test
    void testFluentWait(){
        Wait<WebDriver> fluentWait = new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(10))
                .pollingEvery(Duration.ofSeconds(1))
                .ignoring(NoSuchElementException.class);

        WebElement landscape = fluentWait.until(ExpectedConditions.presenceOfElementLocated(By.id("landscape")));
        assertTrue(landscape.isDisplayed());

    }
}
