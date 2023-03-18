package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ShoppingCartPage extends BasePage {
    @FindBy(xpath = "//tr[@class='cart-item-row']")
    private WebElement productBlock;

    @FindBy(xpath = "//span[@class='product-unit-price']")
    private WebElement price;

    @FindBy(xpath = "//td//div[@class='attributes']")
    private WebElement description;

    @FindBy(xpath = "//input[@name='removefromcart']")
    private WebElement removeButton;

    @FindBy(xpath = "//input[@name='updatecart']")
    private WebElement updateButton;

    @FindBy(xpath = "//div[@class='order-summary-content']")
    private WebElement shoppingCartMessage;

    public ShoppingCartPage(WebDriver driver) {
        super(driver);
    }

    public String getShoppingCartMessage(){ return shoppingCartMessage.getText();}
    public void clickUpdateButton(){updateButton.click();}
    public void clickRemoveButton(){removeButton.click();}
    public String getPrice(){return price.getText();}
    public boolean isProductVisible() {return productBlock.isDisplayed();}
}
