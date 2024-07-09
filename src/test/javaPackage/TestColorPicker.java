package javaPackage;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.Color;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class TestColorPicker {

    static WebDriver driver;
    @BeforeAll
    static void setup() {
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();
        driver.get("https://bonigarcia.dev/selenium-webdriver-java/web-form.html");
    }
    @AfterAll
    static void tearDown() {
        driver.quit();
    }

    @Test
    void testColor() throws InterruptedException {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        WebElement colorPicker = driver.findElement(By.name("my-colors"));
        String initialColor = colorPicker.getAttribute("value");
        System.out.println("Initial color is: " + initialColor);
        Color red = new Color(255, 0, 0,1);
        String script = String.format("arguments[0].setAttribute('value','%s');",red.asHex());
        js.executeScript(script, colorPicker);
        Thread.sleep(3000);
        String finalColor = colorPicker.getAttribute("value");
        System.out.println("Final color is: " + finalColor);
        assertNotEquals(initialColor, finalColor);
        assertEquals(Color.fromString(finalColor),red);

    }


}
