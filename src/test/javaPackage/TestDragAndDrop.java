package javaPackage;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class TestDragAndDrop {

    static WebDriver driver;

    @BeforeAll
    static void setup() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get("https://bonigarcia.dev/selenium-webdriver-java/drag-and-drop.html");
    }

    @AfterAll
    static void tearDown() throws InterruptedException {
        Thread.sleep(3000);
        driver.quit();
    }

    @Test
    void testDragAndDrop() throws InterruptedException {
        Actions actions = new Actions(driver);
        WebElement draggable = driver.findElement(By.id("draggable"));
        WebElement droppable = driver.findElement(By.id("target"));
        Point location =  draggable.getLocation();


        actions.dragAndDropBy(draggable, 100, 100).perform();
        Thread.sleep(3000);
        actions.dragAndDropBy(draggable, 100, -100).perform();
        Thread.sleep(3000);
        actions.dragAndDropBy(draggable, -100, -100).perform();
        Thread.sleep(3000);
        actions.dragAndDropBy(draggable, 100, 100).perform();
        Thread.sleep(3000);

        actions.dragAndDrop(draggable, droppable).perform();

        assertNotEquals(location, draggable.getLocation());
    }
}