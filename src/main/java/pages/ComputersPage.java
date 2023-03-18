package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ComputersPage extends BasePage {

    @FindBy(xpath = "//div[@class='page-title']")
    private WebElement pageName;

    @FindBy(xpath = "//div[@class='sub-category-grid']//a[contains(text(),'Desktops')]")
    private WebElement desktopsButton;

    public ComputersPage(WebDriver driver) {
        super(driver);
    }

    public void clickDesktopButton() {
        desktopsButton.click();
    }

    public boolean isMajorComponentsVisible(){
        pageName.isDisplayed();
        return desktopsButton.isDisplayed();
    }

    public String getMainPageName() {
        return pageName.getText();
    }
}
