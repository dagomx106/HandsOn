package javaPackage;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v125.network.Network;
import org.openqa.selenium.devtools.v125.network.model.ConnectionType;

import java.time.Duration;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestEmulateNetworkConditions {
    static WebDriver driver;
    static DevTools devTools;

    @BeforeAll
    public static void setup() {
        driver = new ChromeDriver();
        devTools = ((ChromeDriver) driver).getDevTools();
        devTools.createSession();
    }

    @AfterAll
    public static void tearDown() {
        devTools.close();
        driver.quit();
    }

    @Test
    void testEmulateNetworkConditions() {
        devTools.send(Network.enable(Optional.empty(), Optional.empty(), Optional.empty()));
        devTools.send(Network.emulateNetworkConditions(false, 150, 50 * 1024,
                50 * 1024, Optional.of(ConnectionType.CELLULAR3G),
                Optional.empty(), Optional.empty(), Optional.empty()));
        long initMillis = System.currentTimeMillis();
        driver.get("https://bonigarcia.dev/selenium-webdriver-java/index.html");
        Duration elapsed = Duration.ofMillis(System.currentTimeMillis() - initMillis);
        System.out.println("Page loaded in " + elapsed.toMillis() + " milliseconds");
        assertTrue(driver.getTitle().contains("Selenium WebDriver"));
    }

}
