package Tests;

import org.testng.annotations.Test;
import static org.testng.Assert.assertTrue;

public class SmokeTest extends BaseTest{
    private static final String DEMOWEBSHOP_URL = "http://demowebshop.tricentis.com/";
    private static final String EXPENS_URL  = "http://demowebshop.tricentis.com/build-your-own-expensive-computer-2";

    String EXPECTED_CART_QTY = "(1)";
    @Test
    public void Test1(){
        getDriver().get(DEMOWEBSHOP_URL);
        getHomePage().waitForPageLoadComplete(20);
        getHomePage().isMajorComponentsVisible();
        getHomePage().clickComputersButton();

        getComputersPage().isMajorComponentsVisible();
        assertTrue(getComputersPage().getMainPageName().contains("Computers"));
        getComputersPage().clickDesktopButton();

        getDesktopsPage().isMainPageVisible();
        assertTrue(getDesktopsPage().getMainPageName().contains("Desktops"));
        getDesktopsPage().setSortBy("Price: High to Low");
        getDesktopsPage().setSelectPageSize("4");
        getDesktopsPage().countPageSize(4);
        getDesktopsPage().clickTheMostExpensiveProduct();

        getProductPage().clickAddToCartButton();
        getProductPage().clickShoppingCartButton();
        getShoppingCartPage().isProductVisible();
    }

    @Test
    public void Test2(){
        getDriver().get(EXPENS_URL);
        getProductPage().waitForAjaxToComplete(30);
        getProductPage().waitForPageLoadComplete(30);
        getProductPage().clickOnOption("Fast");
        getProductPage().clickOnOption("8GB");
        getProductPage().clickOnOption("Image Viewer");
        getProductPage().clickOnOption("Office Suite");
        getProductPage().clickOnOption("Other Office Suite");
        getProductPage().clickAddToCartButton();
        getProductPage().waitForPageLoadComplete(30);
        getProductPage().waitForAjaxToComplete(30);

        assertTrue(getProductPage().getCartQty().contains(EXPECTED_CART_QTY));

        getProductPage().clickGoToCart();
        getShoppingCartPage().isProductVisible();
        assertTrue(getShoppingCartPage().getPrice().contains("2105"));
        getShoppingCartPage().clickRemoveButton();
        getShoppingCartPage().clickUpdateButton();
        assertTrue(getShoppingCartPage().getShoppingCartMessage().contains("empty"));
    }
}
