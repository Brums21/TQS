package io.cucumber.skeleton;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.bonigarcia.wdm.WebDriverManager;

public class BlazeDemo {
    
    private WebDriver driver;

    @Given("a website with the url {string}")
    public void initializeWebsite(String url){
        driver = WebDriverManager.firefoxdriver().create();
        driver.get(url);
    }

    @When("select {string} as origin and {string} as destination")
    public void selectOriginAndDestination(String origin, String destination){

        // origin
        driver.findElement(By.name("fromPort")).click();
        {
        WebElement dropdown = driver.findElement(By.name("fromPort"));
        dropdown.findElement(By.xpath("//option[. = '" + origin + "']")).click();
        }

        // destination
        driver.findElement(By.name("toPort")).click();
        {
        WebElement dropdown = driver.findElement(By.name("toPort"));
        dropdown.findElement(By.xpath("//option[. = '" + destination + "']")).click();
        }

        //confirm 
        driver.findElement(By.cssSelector(".btn-primary")).click();
    }

    @Then("the shown page title is {string}")
    public void titleShownPage(String title){
        assertEquals(driver.findElement(By.cssSelector("h3")).getText(), title);
    }


}
