package javaPackage;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

public class TestDeviceEmulation {

	static WebDriver driver;

	@BeforeAll
	public static void setup() {
		ChromeOptions options = new ChromeOptions();
		Map<String, Object> mobileEmulation = new HashMap<>();
		mobileEmulation.put("deviceName", "Pixel 7");
		options.setExperimentalOption("mobileEmulation", mobileEmulation);
		driver = new ChromeDriver(options);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.get("https://bonigarcia.dev/selenium-webdriver-java/index.html");
		System.out.println(options.getBrowserName());
		System.out.println(options.getPlatformName());
		options.getCapabilityNames().forEach(System.out::println);
	}

	@AfterAll
	public static void tearDown() {
		driver.quit();
	}


	@Test
	public void testDeviceEmulation() throws InterruptedException {

		System.out.println(driver.getTitle());
		System.out.println(driver.getCurrentUrl());
		Thread.sleep(5000);
	}

}
