package javaPackage;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

public class TestExecutionScripts {
    static WebDriver driver;

    @BeforeAll
    static void setup() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get("https://bonigarcia.dev/selenium-webdriver-java/");
    }

    @AfterAll
    static void tearDown() {
        driver.quit();
    }

    @Test
    void testPinned() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        ScriptKey linkKey = js.pin("return document.getElementsByTagName('a')[2];");
        ScriptKey firstArgKey = js.pin("return arguments[0];");
        Set<ScriptKey> pinnedScripts = js.getPinnedScripts();
        assertEquals(2, pinnedScripts.size());
        String initialURL = driver.getCurrentUrl();

        WebElement formLink = (WebElement) js.executeScript(linkKey);
        formLink.click();
        assertNotEquals(initialURL, driver.getCurrentUrl());

        String message = "Hello from pinned script";
        String scriptReturnString = (String) js.executeScript(firstArgKey, message);
        assertEquals(message, scriptReturnString);

        js.unpin(linkKey);
        assertEquals(1, js.getPinnedScripts().size());
    }

    @Test
    void testAsyncScript() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        Duration pause = Duration.ofSeconds(3);
        String script = "const callback = arguments[arguments.length - 1];\n"
                + "window.setTimeout(callback, " + pause.toMillis() + ");";
        long initMills = System.currentTimeMillis();
        js.executeAsyncScript(script);
        Duration elapsed = Duration.ofMillis(System.currentTimeMillis() - initMills);
        System.out.println("Script took " + elapsed.toMillis() + " to be executed");
    }

    @Test
    void testPageLoadTimeout() {
        driver.manage().timeouts().pageLoadTimeout(Duration.ofMillis(1));
        assertThatThrownBy(() -> driver.get("https://bonigarcia.dev/selenium-webdriver-java/"))
                .isInstanceOf(TimeoutException.class);
    }

    @Test
    void testScriptTimeout() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        driver.manage().timeouts().scriptTimeout(Duration.ofSeconds(3));
        long waitMills = Duration.ofSeconds(6).toMillis();
        String script = "const callback = arguments[arguments.length - 1];\n"
                + "window.setTimeout(callback, " + waitMills + ");";
        assertThatThrownBy(()-> {js.executeAsyncScript(script);
        }).isInstanceOf(ScriptTimeoutException.class);
    }
}