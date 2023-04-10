package homework.HW1.SeleniumTests;

import org.openqa.selenium.By;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.junit.jupiter.api.Test;
import io.github.bonigarcia.seljup.SeleniumJupiter;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.Dimension;

import static org.junit.Assert.assertEquals;

@ExtendWith(SeleniumJupiter.class)
public class SeleniumTest {

    @Test
    public void testCurrentCorrect(FirefoxDriver driver) {
        driver.get("http://localhost:8082/");
        driver.manage().window().setSize(new Dimension(916, 694));
        driver.findElement(By.id("latCurrent")).click();
        driver.findElement(By.id("latCurrent")).sendKeys("50.0");
        driver.findElement(By.id("lonCurrent")).click();
        driver.findElement(By.id("lonCurrent")).sendKeys("-8.1");
        driver.findElement(By.cssSelector("form:nth-child(3) > button")).click();
        assertEquals(driver.findElement(By.cssSelector("tbody th:nth-child(2)")).getText(), "50.0");
        driver.close();
    }

    @Test
    public void testCurrentIncorrect(FirefoxDriver driver) {
        driver.get("http://localhost:8082/");
        driver.manage().window().setSize(new Dimension(916, 694));
        driver.findElement(By.id("latCurrent")).click();
        driver.findElement(By.id("latCurrent")).click();
        driver.findElement(By.id("latCurrent")).sendKeys("50.0");
        driver.findElement(By.id("lonCurrent")).click();
        driver.findElement(By.id("lonCurrent")).sendKeys("latitude");
        driver.findElement(By.cssSelector("form:nth-child(3) > button")).click();
        assertEquals(driver.findElement(By.cssSelector(".container:nth-child(4) > div")).getText(), "Only numbers accepted in the inputs");
        driver.close();
    }

    @Test
    public void testDatesIncorrectDate(FirefoxDriver driver) {
        driver.get("http://localhost:8082/");
        driver.manage().window().setSize(new Dimension(916, 694));
        driver.findElement(By.id("latDates")).click();
        driver.findElement(By.id("latDates")).sendKeys("50.0");
        driver.findElement(By.id("lonDates")).click();
        driver.findElement(By.id("lonDates")).sendKeys("-8.1");
        driver.findElement(By.id("startDate")).click();
        driver.findElement(By.id("startDate")).sendKeys("2023-03-02T23:03:21");
        driver.findElement(By.id("endDate")).click();
        driver.findElement(By.id("endDate")).sendKeys("bad formatted date");
        driver.findElement(By.cssSelector("button:nth-child(13)")).click();
        assertEquals(driver.findElement(By.cssSelector(".container:nth-child(8) > div")).getText(), "Date format is incorrect");
        driver.close();
    }

    @Test
    public void testDatesIncorrectLatLon(FirefoxDriver driver) {
        driver.get("http://localhost:8082/");
        driver.manage().window().setSize(new Dimension(916, 694));
        driver.findElement(By.id("latDates")).click();
        driver.findElement(By.id("latDates")).sendKeys("50.0");
        driver.findElement(By.id("lonDates")).click();
        driver.findElement(By.id("lonDates")).sendKeys("-8.1");
        driver.findElement(By.id("startDate")).click();
        driver.findElement(By.id("startDate")).click();
        driver.findElement(By.id("startDate")).sendKeys("2023-03-02T23:03:21");
        driver.findElement(By.id("endDate")).click();
        driver.findElement(By.id("endDate")).sendKeys("some date");
        driver.findElement(By.cssSelector("button:nth-child(13)")).click();
        driver.findElement(By.cssSelector("body")).click();
        assertEquals(driver.findElement(By.cssSelector(".container:nth-child(8) > div")).getText(), "Date format is incorrect");
        driver.close();
    }

    @Test
    public void testForecastCorrect(FirefoxDriver driver) {
        driver.get("http://localhost:8082/");
        driver.manage().window().setSize(new Dimension(916, 694));
        driver.findElement(By.id("latForecast")).click();
        driver.findElement(By.id("latForecast")).sendKeys("50.0");
        driver.findElement(By.id("lonForecast")).click();
        driver.findElement(By.id("lonForecast")).sendKeys("-8.1");
        driver.findElement(By.cssSelector("form:nth-child(5) > button")).click();
        assertEquals(driver.findElement(By.cssSelector("tr:nth-child(24) > th:nth-child(2)")).getText(), "50.0");
        driver.close();
    }

    @Test
    public void testForecastIncorrect(FirefoxDriver driver) {
        driver.get("http://localhost:8082/");
        driver.manage().window().setSize(new Dimension(916, 694));
        driver.findElement(By.id("latForecast")).click();
        driver.findElement(By.id("latForecast")).sendKeys("50.0");
        driver.findElement(By.id("lonForecast")).click();
        driver.findElement(By.id("lonForecast")).sendKeys("latitude");
        driver.findElement(By.cssSelector("form:nth-child(5) > button")).click();
        assertEquals(driver.findElement(By.cssSelector(".container:nth-child(6) > div")).getText(), "Only numbers accepted in the inputs");
        driver.close();
    }

}
