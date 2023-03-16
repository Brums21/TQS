import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

public class HomePage {

    private static String PAGE_URL = "https://blazedemo.com/";

    private WebDriver driver;

    @FindBy(name="fromPort")
    private WebElement fromPort;

    @FindBy(xpath = "//option[. = 'Portland']")
    private WebElement dropdown;

    @FindBy(name = "toPort")
    private WebElement toPort;

    @FindBy(css = ".btn-primary")
    private WebElement buttonConfirmation;

    public HomePage(WebDriver driver){
        this.driver = driver;
        driver.get(PAGE_URL);
        PageFactory.initElements(driver, this);
    }

    public void selectFromPort(String origin){
        fromPort.click();
        Select selection = new Select(fromPort);
        selection.selectByValue(origin);
    }

    public void selectToPort(String destination){
        toPort.click();
        Select selection = new Select(toPort);
        selection.selectByValue(destination);

    }

    public void clickConfirm(){
        buttonConfirmation.click();
    }





}
