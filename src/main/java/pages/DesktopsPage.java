package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class DesktopsPage extends BasePage {

    private static final long DEFAULT_TIMEOUT = 90;
    @FindBy(xpath = "//h1[contains(text(),'Desktops')]")
    private WebElement pageName;

    @FindBy(xpath = "//select[@id='products-orderby']")
    private WebElement sortByButton;

    @FindBy(xpath = "//select[@id='products-pagesize']")
    private WebElement selectPageSize;

    @FindBy(xpath = "//div[@class='product-grid']//div[@class='item-box']")
    private List<WebElement> productsList;

    @FindBy(xpath = "//span[@class='price actual-price']")
    private List<WebElement> pricesList;

    public List<WebElement> getPricesList(){
        System.out.println(pricesList);
        return pricesList;
    }

     public int getTheBiggestPrice(){
      return getPricesList().stream().map(WebElement::getText).map(Integer::parseInt).max(Integer::compareTo).orElse(0);
    }

    @FindBy(xpath = "//h2//a[contains(@href,'expensive')]")
    private WebElement expensiveProduct;

    @FindBy(xpath = "//select[@id='products-orderby']//option[contains(text(),'Price: High to Low')]")
    private WebElement highToLowPrice;

    @FindBy(xpath = "//option[contains(@value,'pagesize=4')]")
    private WebElement pageSizeButton;

    public DesktopsPage(WebDriver driver) {
        super(driver);
    }

    public boolean isMainPageVisible() {
        return pageName.isDisplayed();
    }

    public String getMainPageName() {
        return pageName.getText();
    }

    public void clickTheMostExpensiveProduct() {
        expensiveProduct.click();
    }
    public void setSortBy(final String orderBy){
        Actions builder = new Actions(driver);
        WebElement element = driver.findElement(By.xpath("//select[@id='products-orderby']"));
        builder.moveToElement(element).build().perform();
        waitForPageLoadComplete(DEFAULT_TIMEOUT);
        waitForAjaxToComplete(DEFAULT_TIMEOUT);
        waitVisibilityOfElement(DEFAULT_TIMEOUT, getSortByButton());
        clickOnOrderOption(orderBy);
    }
    public void clickOnOrderOption(String optionName) {
        String xpath = "//select[@id='products-orderby']//option[contains(text(),'%s')]".formatted(optionName);
        driver.findElement(By.xpath(xpath)).click();
    }
    public void setSelectPageSize(final String pageSize){
        Actions builder = new Actions(driver);
        WebElement element = driver.findElement(By.xpath("//select[@id='products-pagesize']"));
        builder.moveToElement(element).build().perform();
        waitForPageLoadComplete(DEFAULT_TIMEOUT);
        waitForAjaxToComplete(DEFAULT_TIMEOUT);
        waitVisibilityOfElement(DEFAULT_TIMEOUT, getSelectPageSize());
        clickOnPageSizeOption(pageSize);
    }
    public void clickOnPageSizeOption(String pageSize) {
        String xpath = String.format("//select[@id='products-pagesize']//option[contains(text(),'%s')]", pageSize);
        driver.findElement(By.xpath(xpath)).click();
    }

    private WebElement getSelectPageSize() { return pageSizeButton;}

    private WebElement getSortByButton() { return highToLowPrice;}

    public List<WebElement> getProductsList(){ return productsList;}
    public boolean countPageSize(int size){
        int count = getProductsList().size();
        return count == size;
    }
}
