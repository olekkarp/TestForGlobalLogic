package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HomePage extends BasePage {
    @FindBy(xpath = "//div[@class='master-wrapper-page']")
    private WebElement mainPage;

    @FindBy(xpath = "//div[contains(@class,'category-navigation')]")
    private WebElement categoriesBlock;

    @FindBy(xpath = "//ul[@class='list']//a[@href='/computers']")
    private WebElement computersButton;

    @FindBy(xpath = "//span[contains(text(),'Shopping cart')]")
    private WebElement shoppingCartButton;

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public void clickComputersButton() {
        computersButton.click();
    }

    public boolean isMajorComponentsVisible(){
        mainPage.isDisplayed();
        categoriesBlock.isDisplayed();
        shoppingCartButton.isDisplayed();
        return computersButton.isDisplayed();
    }
}
