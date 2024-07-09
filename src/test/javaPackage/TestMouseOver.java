package javaPackage;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.locators.RelativeLocator;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestMouseOver {
    public static WebDriver driver;

    @BeforeAll
    public static void setup() throws InterruptedException {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get("https://bonigarcia.dev/selenium-webdriver-java/mouse-over.html");
        Thread.sleep(3000);
    }

    @AfterAll
    public static void tearDown() {
        driver.quit();
    }

    @Test
    void testMouseOver() throws InterruptedException {
        Actions actions = new Actions(driver);
        List<String> imgList = Arrays.asList("compass","calendar","award","landscape");
        for (String imageName: imgList) {
        String xpath = String.format("//img[@src='img/%s.png']", imageName);
        WebElement image = driver.findElement(By.xpath(xpath));
        actions.moveToElement(image).perform();
        WebElement caption = driver.findElement(RelativeLocator.with(By.tagName("div")).near(image));
        assertEquals(imageName, caption.getText().toLowerCase());
        }
    }
}
