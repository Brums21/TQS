import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ReservePage {
    
    private WebDriver driver;

    @FindBy(css="h3")
    private WebElement title;

    @FindBy(css = "tr:nth-child(3) .btn")
    private WebElement buton;

    public ReservePage(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public boolean assertInPage(){
        return title.getText().matches("Flights from Portland to Berlin:");
    }

    public void goToNextPage(){
        buton.click();
    }
}
