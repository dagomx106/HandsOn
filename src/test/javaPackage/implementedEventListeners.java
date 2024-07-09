package javaPackage;

import org.openqa.selenium.*;
import org.openqa.selenium.support.events.WebDriverListener;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

public class implementedEventListeners implements WebDriverListener {
    @Override
    public void afterGet(WebDriver driver, String url) {
        System.out.println("Opening... " + url);
    }

    @Override
    public void beforeMaximize(WebDriver.Window window) {
        System.out.println("Now the windows is maximized");
    }

    @Override
    public void afterQuit(WebDriver driver) {
        System.out.println("Test execution completed");
    }

    @Override
    public void beforeQuit(WebDriver driver) {
        TakesScreenshot tk = (TakesScreenshot) driver;
        WebElement form = driver.findElement(By.className("container"));
        File elementScreenshot = form.getScreenshotAs(OutputType.FILE);
        String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy_MM_dd_hh_mm_ss"));
        Path destination = Path.of("src/Screenshots/"+this.getClass().getSimpleName()+"screenshot_" + date + ".png");
        try {
            Files.move(elementScreenshot.toPath(), destination, REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}