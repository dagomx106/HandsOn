package javaPackage;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v125.performance.Performance;
import org.openqa.selenium.devtools.v125.performance.model.Metric;

import java.time.Duration;
import java.util.List;
import java.util.Optional;

public class TestPerformanceMetrics {

    WebDriver driver;
    DevTools devTools;

    @Test
    void testPerformanceMetrics() {
        driver = new ChromeDriver();
        devTools = ((ChromeDriver) driver).getDevTools();
        devTools.createSession();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        devTools.send(Performance.enable(Optional.of(Performance.EnableTimeDomain.TIMETICKS)));
        driver.get("https://bonigarcia.dev/selenium-webdriver-java/index.html");
        List<Metric> metricList = devTools.send(Performance.getMetrics());

        for (Metric metric : metricList) {
            System.out.println(metric.getName() + " " + metric.getValue());
        }
        devTools.close();
        driver.quit();
    }


}
