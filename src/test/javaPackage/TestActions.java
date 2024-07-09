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

    public class TestActions {
        public static WebDriver driver;

        @BeforeAll
        public static void setup() throws InterruptedException {
            driver = new ChromeDriver();
            driver.manage().window().maximize();
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
            driver.get("https://bonigarcia.dev/selenium-webdriver-java/dropdown-menu.html");
            Thread.sleep(3000);
        }

        @AfterAll
        public static void tearDown() {
            driver.quit();
        }

        @Test
        void testContextAndDoubleClick() throws InterruptedException {
            Actions actions = new Actions(driver);
            WebElement dropdown1 = driver.findElement(By.id("my-dropdown-1"));
            actions.click(dropdown1).perform();
            Thread.sleep(3000);
            WebElement actionDropdown = driver.findElement(By.xpath("//ul[@class='dropdown-menu show']//a[text()='Action']"));
            actions.click(actionDropdown);
            WebElement dropdown2 = driver.findElement(By.id("my-dropdown-2"));
            actions.contextClick(dropdown2).perform();
            Thread.sleep(3000);
            WebElement contextDropdown = driver.findElement(By.xpath("//ul[@id='context-menu-2']//a[contains(text(),'Another')]"));
            actions.click(contextDropdown).perform();
            WebElement dropdown3 = driver.findElement(By.id("my-dropdown-3"));
            actions.doubleClick(dropdown3).perform();
            Thread.sleep(3000);
            WebElement doubleDropdown = driver.findElement(By.xpath("//ul[@id='context-menu-3']//a[contains(text(),'Some')]"));
            actions.click(doubleDropdown).perform();
            Thread.sleep(3000);
        }


    }
