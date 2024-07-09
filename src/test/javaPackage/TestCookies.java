package javaPackage;

import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestCookies {

    static WebDriver driver;
    @BeforeAll
    static void setup() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get("https://bonigarcia.dev/selenium-webdriver-java/cookies.html");
    }
    @AfterAll
    static void tearDown() {
        driver.quit();
    }
    @Test
    @Order(1)
    void testReadCookie() {
        WebDriver.Options option = driver.manage();
        Set<Cookie> cookies = option.getCookies();
        assertEquals(2, cookies.size());
        for (Cookie cookie : cookies) {
            System.out.println(cookie);
        }
        Cookie username = option.getCookieNamed("username");
        assertEquals("John Doe", username.getValue());
        assertEquals("/", username.getPath());

        driver.findElement(By.id("refresh-cookies")).click();
    }

    @Test
    @Order(2)
    void testAddCookie() {
        WebDriver.Options option = driver.manage();
        Cookie newCookie = new Cookie("cookie-key", "cookie-value");
        option.addCookie(newCookie);
        String readValue = option.getCookieNamed("cookie-key").getValue();
        assertEquals(newCookie.getValue(), readValue);
    }

    @Test
    @Order(3)
    void testEditCookie() {
        WebDriver.Options options = driver.manage();
        Cookie username = options.getCookieNamed("username");
        Cookie editedCookie = new Cookie(username.getName(),"new-value");
        options.addCookie(editedCookie);
        Cookie readCookie = options.getCookieNamed(username.getName());
        assertEquals(readCookie,editedCookie);

        driver.findElement(By.id("refresh-cookies")).click();
    }

    @Test
    @Order(4)
    void testDeleteCookie() {
        WebDriver.Options options = driver.manage();
        Set<Cookie> cookies = options.getCookies();
        for (Cookie cookie : cookies) {
            System.out.println(cookie);
        }
        Cookie username = options.getCookieNamed("username");
        options.deleteCookie(username);

        driver.findElement(By.id("refresh-cookies")).click();
    }
}