package javaPackage;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v125.dom.model.Rect;
import org.openqa.selenium.devtools.v125.page.Page;
import org.openqa.selenium.devtools.v125.page.model.Viewport;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestFullPageScreenshot {

    static WebDriver driver;
    static DevTools devTools;

    @Test

    void testPageScreenshotChrome() throws IOException {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        devTools = ((ChromeDriver) driver).getDevTools();
        devTools.createSession();
        driver.get("https://bonigarcia.dev/selenium-webdriver-java/long-page.html");
        WebDriverWait webDriverWait = new WebDriverWait(driver, Duration.ofSeconds(10));
        webDriverWait.until(ExpectedConditions.presenceOfNestedElementLocatedBy(By.className("container"), By.tagName("p")));
        Page.GetLayoutMetricsResponse metrics = devTools.send(Page.getLayoutMetrics());
        Rect contentSize = metrics.getContentSize();
        String pngScreenshot = devTools.send(Page.captureScreenshot(Optional.of(Page.CaptureScreenshotFormat.PNG), Optional.empty(),
                Optional.of(new Viewport(0, 0, contentSize.getWidth(), contentSize.getHeight(), 1)),
                Optional.empty(), Optional.of(true), Optional.empty()));
        String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy_MM_dd_hh_mm_ss"));
        Path destination = Path.of("src/Screenshots/"+this.getClass().getSimpleName()+"screenshot_" + date + ".png");
        Files.write(destination, Base64.getDecoder().decode(pngScreenshot));
        assertTrue(destination.toFile().exists());
        devTools.close();
        driver.quit();
    }

    @Test
    void testPageScreenshotFireFox() throws IOException {
        driver = new FirefoxDriver();
        driver.manage().window().maximize();
        driver.get("https://bonigarcia.dev/selenium-webdriver-java/long-page.html");
        WebDriverWait webDriverWait = new WebDriverWait(driver, Duration.ofSeconds(10));
        webDriverWait.until(ExpectedConditions.presenceOfNestedElementLocatedBy(By.className("container"), By.tagName("p")));
        byte[] fullPageScreenshot = ((FirefoxDriver) driver).getFullPageScreenshotAs(OutputType.BYTES);
        String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy_MM_dd_hh_mm_ss"));
        Path destination = Path.of("src/Screenshots/"+this.getClass().getSimpleName()+"screenshot_" + date + ".png");
        Files.write(destination,fullPageScreenshot);
        assertTrue(destination.toFile().exists());
        driver.quit();
    }
}
