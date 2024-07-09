package javaPackage;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestDropDown {
    static WebDriver driver;
    @BeforeAll
    static void setup() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get("https://bonigarcia.dev/selenium-webdriver-java/web-form.html");
    }
    @AfterAll
    static void tearDown() {
        driver.quit();
    }

    @Test
    void testDropDown() {
        Select selectDropDown = new Select(driver.findElement(By.name("my-select")));
        List<WebElement> allOptions = selectDropDown.getOptions();
        allOptions.stream().map(WebElement::getText).forEach(System.out::println);
        selectDropDown.selectByVisibleText("Two");
        assertEquals("Two", selectDropDown.getFirstSelectedOption().getText());
    }

    @Test
    void testDataList() {
        WebElement dataList = driver.findElement(By.name("my-datalist"));
        dataList.click();
        int size = driver.findElements(By.xpath("//datalist//option")).size();
        System.out.println(size);

        for (WebElement element : driver.findElements(By.xpath("//datalist//option"))) {
            System.out.println(element.getAttribute("value"));
        }

        String valueOption = driver.findElement(By.xpath("//dataList//option[3]")).getAttribute("value");
        dataList.sendKeys(valueOption);
        assertEquals("Seattle", valueOption);

    }
}
