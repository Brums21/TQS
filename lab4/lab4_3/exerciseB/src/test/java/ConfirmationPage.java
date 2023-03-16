import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ConfirmationPage {
    
    @FindBy(tagName = "title")
    WebElement title;

    private WebDriver driver;

    public ConfirmationPage(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public boolean assertInPage(){
        return title.getText().matches("BlazeDemo Confirmation");
    }
    
}
