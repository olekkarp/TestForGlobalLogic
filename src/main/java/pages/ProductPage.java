package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ProductPage extends BasePage {

    @FindBy(xpath = "//input[@id='add-to-cart-button-74']")
    private WebElement addToCartButton;

    @FindBy(xpath = "//span[@class='cart-qty']")
    private WebElement cartQty;

    @FindBy(xpath = "//input[@value='Go to cart']")
    private WebElement goToCartButton;

    @FindBy(xpath = "//li[@id='topcartlink']")
    private WebElement shoppingCartButton;
    public ProductPage(WebDriver driver) {
        super(driver);
    }

    public void clickShoppingCartButton(){ shoppingCartButton.click();}
    public String getCartQty(){
        return cartQty.getText();
    }
    public void clickOnOption(String optionName) {
        String xpath = String.format("//label[contains(text(),'%s')]", optionName);
        driver.findElement(By.xpath(xpath)).click();
    }
    public void clickAddToCartButton() {
        addToCartButton.click();
    }

    public void clickGoToCart(){
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].click();", goToCartButton);
    }
}
