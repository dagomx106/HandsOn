package javaPackage;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestShadowDOM {

    @Test
    public void testShadowDOM() {
        WebDriver driver;
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get("https://bonigarcia.dev/selenium-webdriver-java/shadow-dom.html");
        WebElement content = driver.findElement(By.id("content"));
        SearchContext shadowRoot = content.getShadowRoot();
        WebElement textElement = shadowRoot.findElement(By.cssSelector("p"));
        assertEquals("Hello Shadow DOM", textElement.getText());
        driver.quit();
    }

    @Test
    void testSelectorsHubShadowDOM() throws InterruptedException, IOException {
        WebDriver driver;
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(80));

        driver.get("https://selectorshub.com/iframe-in-shadow-dom/");
        WebElement divUserName = driver.findElement(By.id("userName"));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("userName")));
        SearchContext userShadowRoot = divUserName.getShadowRoot();
        SearchContext shadowRootPizza = userShadowRoot.findElement(By.cssSelector("#app2")).getShadowRoot();

        userShadowRoot.findElement(By.cssSelector("#kils")).sendKeys("User Shadow DOM");
        shadowRootPizza.findElement(By.cssSelector("#pizza")).sendKeys("Pizza Shadow DOM");
        Thread.sleep(3000);
        WebElement pizzaTab = shadowRootPizza.findElement(By.cssSelector("#pizza"));
        Thread.sleep(3000);

        Actions actions = new Actions(driver);
        actions.keyDown(Keys.TAB).release().sendKeys("Test closed shadow Dom").build().perform();
        Thread.sleep(3000);
        actions.keyDown(Keys.TAB).release().keyDown(Keys.TAB).release().sendKeys("Password shadow Dom").build().perform();
        TakesScreenshot tk = (TakesScreenshot) driver;
        WebElement form = driver.findElement(By.xpath("//div[@class='elementor-widget-container']//div[@id='userName']"));
        File elementScreenshot = form.getScreenshotAs(OutputType.FILE);
        String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy_MM_dd_hh_mm_ss"));
        Path destination = Path.of("src/Screenshots/screenshot_" + date + ".png");
        Files.move(elementScreenshot.toPath(), destination, REPLACE_EXISTING);
        Thread.sleep(3000);
        driver.quit();
    }
}