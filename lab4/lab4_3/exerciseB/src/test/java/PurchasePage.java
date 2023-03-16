import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class PurchasePage {

    private WebDriver driver;

    @FindBy(tagName = "title")
    WebElement title;

    @FindBy(css = ".btn-primary")
    WebElement button;
    
    public PurchasePage(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public boolean inPage(){
        return title.getText().matches("BlazeDemo Purchase");
    }

    public void nextPageClick(){
        button.click();
    }
}
