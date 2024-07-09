package javaPackage;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestScrollingJS {

    static WebDriver driver;

    @BeforeAll
    static void setup() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

    }

    @AfterAll
    static void tearDown() {
        driver.quit();
    }
    @Test
    void testScrolling() throws InterruptedException {
        driver.get("https://bonigarcia.dev/selenium-webdriver-java/long-page.html");
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0, 1000)");
        Thread.sleep(3000);
        js.executeScript("window.scrollBy(0, 1000)");
        Thread.sleep(3000);
    }

    @Test
    void testScrollToElement() throws InterruptedException {
        driver.get("https://bonigarcia.dev/selenium-webdriver-java/long-page.html");
        JavascriptExecutor js = (JavascriptExecutor) driver;
        WebElement element = driver.findElement(By.cssSelector("p:last-child"));
        js.executeScript("arguments[0].scrollIntoView();", element);
        Thread.sleep(3000);
        String text = driver.findElement(By.xpath("//span//a")).getText();

        assertEquals("Boni García", text);
    }

    @Test
    void infiniteScroll(){
        driver.get("https://bonigarcia.dev/selenium-webdriver-java/infinite-scroll.html");
        JavascriptExecutor js = (JavascriptExecutor) driver;
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        By plocator = By.tagName("p");
        List<WebElement> paragraphs = wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(plocator,0));
        int numberOfParagraphs = paragraphs.size();
        WebElement lastParagraph = driver.findElement(By.xpath(String.format("//p[%d]", numberOfParagraphs)));
        js.executeScript("arguments[0].scrollIntoView();", lastParagraph);
        wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(plocator, numberOfParagraphs+1));
    }

}
