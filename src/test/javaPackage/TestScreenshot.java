package javaPackage;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class TestScreenshot {

    static WebDriver driver;

    @BeforeAll
    static void setup() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get("https://bonigarcia.dev/selenium-webdriver-java/index.html");
    }

    @AfterAll
    static void tearDown() {
        driver.quit();
    }

    @Test
    void testScreenshotPng() throws IOException, InterruptedException {
        TakesScreenshot tk = (TakesScreenshot) driver;
        File screenshotAs = tk.getScreenshotAs(OutputType.FILE);
        System.out.println(screenshotAs);
        String dateString = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy_MM_dd_hh_mm_ss"));
        Path destination = Path.of("src/Screenshots/screenshot_" + dateString + ".png");
        Files.move(screenshotAs.toPath(), destination, REPLACE_EXISTING);
        System.out.println(destination.toFile().getName());
        Thread.sleep(3000);
    }

    @Test
    void testScreenshotBase64() {
        TakesScreenshot tk = (TakesScreenshot) driver;
        String base64 = tk.getScreenshotAs(OutputType.BASE64);
        System.out.println("Screenshot in base64 "
                + "(you can copy and paste it into a browser navigation bar to watch it)\n"
                + "data:image/png;base64," + base64);
        assertFalse(base64.isEmpty());
    }

    @Test
    void webElementScreenshot() throws IOException {
        driver.get("https://bonigarcia.dev/selenium-webdriver-java/web-form.html");
        TakesScreenshot tk = (TakesScreenshot) driver;
        WebElement form = driver.findElement(By.tagName("form"));
        File elementScreenshot = form.getScreenshotAs(OutputType.FILE);
        String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy_MM_dd_hh_mm_ss"));
        Path destination = Path.of("src/Screenshots/elementScreenshot_" + date + ".png");
        Files.move(elementScreenshot.toPath(), destination, REPLACE_EXISTING);


    }

}
