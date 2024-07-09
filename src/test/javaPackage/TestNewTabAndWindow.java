package javaPackage;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WindowType;
import org.openqa.selenium.chrome.ChromeDriver;
import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestNewTabAndWindow {

    WebDriver driver;
    @Test
    void testNewTab() throws InterruptedException {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get("https://bonigarcia.dev/selenium-webdriver-java/index.html");

        String initHandle = driver.getWindowHandle();
        System.out.println(initHandle);

        driver.switchTo().newWindow(WindowType.TAB);
        Thread.sleep(3000);
        driver.getWindowHandles().forEach(System.out::println);
        driver.get("https://bonigarcia.dev/selenium-webdriver-java/web-form.html");
        assertEquals(2,driver.getWindowHandles().size());
        driver.close();
        driver.switchTo().window(initHandle);
        assertEquals(1,driver.getWindowHandles().size());
        driver.quit();
    }

    @Test
    void testNewWindow() throws InterruptedException {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get("https://bonigarcia.dev/selenium-webdriver-java/index.html");

        String initHandle = driver.getWindowHandle();
        System.out.println(initHandle);
        driver.switchTo().newWindow(WindowType.WINDOW);
        Thread.sleep(3000);
        driver.getWindowHandles().forEach(System.out::println);
        driver.get("https://bonigarcia.dev/selenium-webdriver-java/web-form.html");
        assertEquals(2,driver.getWindowHandles().size());
        driver.close();
        driver.switchTo().window(initHandle);
        assertEquals(1,driver.getWindowHandles().size());
        driver.quit();
    }
}
