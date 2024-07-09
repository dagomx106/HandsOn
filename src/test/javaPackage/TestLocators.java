package javaPackage;

import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.SessionId;
import org.openqa.selenium.support.ByIdOrName;
import org.openqa.selenium.support.locators.RelativeLocator;
import org.openqa.selenium.support.pagefactory.ByChained;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestLocators {

    public static WebDriver driver;

    @BeforeAll
    public static void setup() throws InterruptedException {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get("https://bonigarcia.dev/selenium-webdriver-java/web-form.html");
        Thread.sleep(3000);

    }

    @AfterAll
    public static void tearDown() {
        driver.quit();
    }

    @Test
    @Order(1)
    void testBasicLocators() throws InterruptedException {

        By fileLocator = By.xpath("//input[@type='file']");
        driver.findElement(fileLocator).sendKeys("C:\\test\\TCS.txt");
        boolean contains = driver.getPageSource().contains("</html>");
        assertTrue(contains);

        SessionId sessionId = ((RemoteWebDriver) driver).getSessionId();
        assertNotNull(sessionId);
        System.out.println(sessionId);

        By textArea = By.tagName("textarea");
        String rows = driver.findElement(textArea).getDomAttribute("rows");
        driver.findElement(textArea).sendKeys(rows);
        assertEquals(3, Integer.parseInt(rows));

        By linkText = By.linkText("Return to index");
        String cursor = driver.findElement(linkText).getCssValue("cursor");

        assertEquals("pointer", cursor);

        By partialLinkText = By.partialLinkText("index");

        assertEquals(driver.findElement(linkText).getLocation(), driver.findElement(partialLinkText).getLocation());
        assertEquals(driver.findElement(linkText).getRect(), driver.findElement(partialLinkText).getRect());

        Thread.sleep(3000);
    }

    @Test
    @Order(2)
    void testCCSelectors() {

        By cssHidden = By.cssSelector("input[type=hidden]");
        boolean isVisible = driver.findElement(cssHidden).isDisplayed();
        assertFalse(isVisible);
    }

    @Test
    @Order(3)
    void testCCSAdvanced() {
        By checked = By.cssSelector("input[type=checkbox]:checked");
        String idCheckbox = driver.findElement(checked).getAttribute("id");
        assertEquals("my-check-1", idCheckbox);
        assertTrue(driver.findElement(checked).isSelected());

    }

    @Test
    @Order(4)
    void xpathAdvanced() throws InterruptedException {

        By radioChecked = By.xpath("//input[@type='radio'][@checked]");
        Thread.sleep(2000);
        assertEquals("my-radio-1", driver.findElement(radioChecked).getAttribute("id"));
        assertTrue(driver.findElement(radioChecked).isSelected());

    }

    @Test
    @Order(5)
    void compoundLocators() {
        By myFile = new ByIdOrName("my-file");
        assertTrue(driver.findElement(myFile).getAttribute("id").isBlank());
        assertFalse(driver.findElement(myFile).getAttribute("name").isBlank());
    }

    @Test
    @Order(6)
    void chainedLocators() {
        List<WebElement> rowsForm = driver.findElements(new ByChained(By.tagName("form"), By.className("row")));
        assertEquals(1, rowsForm.size());
    }

    @Test
    @Order(7)
    void relativeLocators() {
        By link = By.linkText("Return to index");
        WebElement relativeElement = driver.findElement(link);
        RelativeLocator.RelativeBy relativeBy = RelativeLocator.with(By.tagName("input"));
        By readOnlyLocator = relativeBy.above(relativeElement);
        assertEquals("my-readonly", driver.findElement(readOnlyLocator).getAttribute("name"));
    }

    @Test
    @Order(8)
    void testDatePicker() {
        LocalDate today = LocalDate.now();
        int currentYear = today.getYear();
        int currentDay = today.getDayOfMonth();
        System.out.println(today + "  " + currentDay + "  " + currentYear);

        WebElement datePicker = driver.findElement(By.name("my-date"));
        datePicker.click();
        //Click on current month by searching by text
        WebElement monthElement = driver.findElement(By.xpath(String.format("//th[contains(text(),'%d')]", currentYear)));
        monthElement.click();
        //Click on left arrow using relative locator
        WebElement arrowLeft = driver.findElement(RelativeLocator.with(By.tagName("th")).toRightOf(monthElement));
        arrowLeft.click();
        //Click on the current month of that year using relative locator
        WebElement monthPastYear = driver.findElement(RelativeLocator.with(By.cssSelector("span[class$=focused]")).below(arrowLeft));
        monthPastYear.click();
        //Click on the present day of that month
        WebElement dayElement = driver.findElement(By.xpath(String.format("//td[@class='day' and contains(text(),'%d')]",currentDay)));
        dayElement.click();
        //Get the final date on the input text
        String oneYearBack = datePicker.getAttribute("value");
        System.out.println(oneYearBack);

        LocalDate previousYear = today.minusYears(1);
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        String expectedDate = previousYear.format(dateFormat);
        System.out.println(expectedDate);

        assertEquals(expectedDate, oneYearBack);
    }

    @Test
    @Order(9)
    void testSlider() {
        WebElement slider = driver.findElement(By.cssSelector("input[type=range]"));
        String initial = slider.getAttribute("value");
        System.out.println(initial);
        for (int i = 0; i < 5; i++) {
            slider.sendKeys(Keys.ARROW_RIGHT);
        }
        slider.sendKeys(Keys.ARROW_LEFT);
        String finalValue = slider.getAttribute("value");
        System.out.println(finalValue);
        assertNotEquals(initial,finalValue);
    }

    @Test
    @Order(10)
    void testNavigation() throws InterruptedException {
        driver.get("https://bonigarcia.dev/selenium-webdriver-java/navigation1.html");
        Thread.sleep(2000);
        driver.findElement(By.xpath("//a[contains(text(),'Next')]")).click();
        driver.findElement(By.linkText("3")).click();
        driver.findElement(By.xpath("//a[text()='2']")).click();
        driver.findElement(By.linkText("Previous")).click();
        Thread.sleep(2000);
        String text = driver.findElement(By.xpath("//p")).getText();
        assertTrue(text.contains("consectetur"));
    }
}