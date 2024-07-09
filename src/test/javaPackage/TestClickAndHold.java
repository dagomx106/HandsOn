package javaPackage;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

import java.time.Duration;

public class TestClickAndHold
{
    static WebDriver driver;
    @BeforeAll
    static void setup(){
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://bonigarcia.dev/selenium-webdriver-java/draw-in-canvas.html");
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    @AfterAll
    static void tearDown(){
        driver.quit();
    }

    @Test
    void testClickAndHold(){
        Actions actions = new Actions(driver);
        WebElement canvas =  driver.findElement(By.id("my-canvas"));
        actions.moveToElement(canvas).clickAndHold();
        int points = 40;
        int radius = 10;

        for (int i = 0; i < points; i++) {
            double angle = Math.toRadians(360 * i/points);
            double x = radius * Math.sin(angle);
            double y = radius * Math.cos(angle);
            actions.moveByOffset((int) x,(int) y);
        }
        actions.release().build().perform();
    }
}
