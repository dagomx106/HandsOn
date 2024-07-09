package javaPackage;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class TestWindow {

    @Test
    public void testWindow(){
        WebDriver driver;
        driver = new ChromeDriver();
        driver.get("https://bonigarcia.dev/selenium-webdriver-java/index.html");
        WebDriver.Window window = driver.manage().window();
        Point initialPosition = window.getPosition();
        Dimension initialSize = window.getSize();
        System.out.println("Initial position: " + initialPosition+" and initial size: "+initialSize);
        window.maximize();
        Dimension maxSize = window.getSize();
        Point maxPosition = window.getPosition();
        System.out.println("Max position: " + maxPosition+" and max size: "+maxSize);

        assertNotEquals(initialPosition, maxPosition);
        assertNotEquals(initialSize, maxSize);
    }
}
