package javaPackage;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.events.EventFiringDecorator;
import org.openqa.selenium.support.events.WebDriverListener;

import java.time.Duration;

public class TestEventListeners {

    @Test
    void testEventListeners() {
        WebDriver webDriver = new ChromeDriver();
        WebDriverListener listener = new implementedEventListeners();
        WebDriver driver = new EventFiringDecorator(listener).decorate(webDriver);

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();
        driver.get("https://bonigarcia.dev/selenium-webdriver-java/index.html");
        System.out.println("Inside test");
        driver.quit();
    }
}
