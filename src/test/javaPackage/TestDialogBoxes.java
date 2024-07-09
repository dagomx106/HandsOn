package javaPackage;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestDialogBoxes {

    @Test
    void testAlert() {
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.get("https://bonigarcia.dev/selenium-webdriver-java/dialog-boxes.html");

        driver.findElement(By.id("my-alert")).click();
        wait.until(ExpectedConditions.alertIsPresent());
        Alert alert = driver.switchTo().alert();
        assertEquals("Hello world!", alert.getText());
        alert.accept();
        driver.quit();
    }
    @Test
    void testConfirm() {
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.get("https://bonigarcia.dev/selenium-webdriver-java/dialog-boxes.html");
        driver.findElement(By.id("my-confirm")).click();
        Alert alert = wait.until(ExpectedConditions.alertIsPresent());
        assertEquals("Is this correct?", alert.getText());
        alert.dismiss();
        boolean confirmText = driver.findElement(By.id("confirm-text")).getText().contains("false");
        assertTrue(confirmText);
        driver.quit();
    }

    @Test
    void testPrompt() {
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.get("https://bonigarcia.dev/selenium-webdriver-java/dialog-boxes.html");
        driver.findElement(By.id("my-prompt")).click();
        Alert alert = wait.until(ExpectedConditions.alertIsPresent());
        assertEquals("Please enter your name", alert.getText());
        alert.sendKeys("Test prompt");
        alert.accept();
        boolean promptText = driver.findElement(By.id("prompt-text")).getText().contains("Test prompt");
        assertTrue(promptText);
        driver.quit();
    }

    @Test
    void testModal() {
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.get("https://bonigarcia.dev/selenium-webdriver-java/dialog-boxes.html");
        driver.findElement(By.id("my-modal")).click();
        By closeButton = By.xpath("//button[text()='Close']");
        wait.until(ExpectedConditions.elementToBeClickable(closeButton));
        WebElement modalText = driver.findElement(By.xpath("//div[contains(text(),'modal')]"));
        WebElement saveButton = driver.findElement(By.xpath("//button[contains(text(),'Save')]"));
        assertTrue(saveButton.isEnabled());
        assertTrue(modalText.isDisplayed());
        driver.findElement(closeButton).click();
        driver.quit();

    }
}