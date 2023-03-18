package Tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import pages.ComputersPage;
import pages.DesktopsPage;
import pages.HomePage;
import pages.ProductPage;
import pages.ShoppingCartPage;

import static io.github.bonigarcia.wdm.WebDriverManager.chromedriver;

public abstract class BaseTest {
    private WebDriver driver;

    private static final String DEMOWEBSHOP_URL = "http://demowebshop.tricentis.com/";
    private static final String EXPENS_URL  = "http://demowebshop.tricentis.com/build-your-own-expensive-computer-2";

    @BeforeTest
    public void profileSetUp() {
        chromedriver().setup();
    }

    @BeforeMethod
    public void testsSetUp() {
        ChromeOptions chromeOptions = new ChromeOptions();

        chromeOptions.addArguments("--remote-allow-origins=*", "ignore-certificate-errors");

        this.driver = new ChromeDriver(chromeOptions);
        this.driver.manage().window().maximize();
    }

    @AfterMethod
    public void tearDown() {
        driver.close();
    }

    public WebDriver getDriver() {
        return driver;
    }

    public HomePage getHomePage() {
        return new HomePage(getDriver());
    }

    public ComputersPage getComputersPage() {
        return new ComputersPage(getDriver());
    }

    public DesktopsPage getDesktopsPage() {
        return new DesktopsPage(getDriver());
    }

    public ProductPage getProductPage() {
        return new ProductPage(getDriver());
    }

    public ShoppingCartPage getShoppingCartPage() {
        return new ShoppingCartPage(getDriver());
    }
}
