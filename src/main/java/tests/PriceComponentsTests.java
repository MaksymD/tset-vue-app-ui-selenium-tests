package tests;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.BasePage;
import pages.StartPage;
import utils.WebDriverFactory;

import java.io.IOException;

import static org.testng.AssertJUnit.assertEquals;

public class PriceComponentsTests extends BasePage {

    @Parameters({"browser", "property_file"})
    public PriceComponentsTests(String browser, String propertyFile) throws IOException {
        super(browser);
        this.properties = loadProperties(propertyFile);
    }

    @BeforeMethod
    @Parameters("browser")
    public void setUp(String browser) {
        driver = WebDriverFactory.createInstance(browser);
        super.setUp(driver);
    }

    @AfterMethod
    public void tearDown() {
        super.tearDown();
    }

    @Test(description = "TF-01: Change Base Price value to 5")
    public void changeBasePrice_positiveTest() {
        driver.get(properties.getProperty("url.base"));

        // check page Title
        StartPage startPage = new StartPage(driver, properties);
        logInfo("Page title: " + startPage.getTitle());
        assertEquals("Vite App", startPage.getTitle());

        // click on 'Pencil' icon for 'BasePrice'
        startPage.moveCursorToLocator("//div[@id='BasePrice']");
        startPage.clickByLocator(properties.getProperty("Locator.StartPage.button_pencilBasePrice"));

        // enter new value in BasePrice input number
        startPage.setNumber_BasePrice("5");
        startPage.clickByLocator(properties.getProperty("Locator.StartPage.button_checkBasePrice"));

        // verify results
        startPage.checkTotalPrice("5.00");
    }

    @Test(description = "TF-02: Add all price components from Testdata")
    public void addAllPriceComponents_positiveTest() {
        driver.get(properties.getProperty("url.base"));

        // check page Title
        StartPage startPage = new StartPage(driver, properties);
        logInfo("Page title: " + startPage.getTitle());
        assertEquals("Vite App", startPage.getTitle());

        // click on label input
        startPage.clickByLocator(properties.getProperty("Locator.StartPage.inputNewLabel_LabelName"));

        // enter new value and price for Label 'Alloy surcharge'
        startPage.setName_NewLabelName("Alloy surcharge");
        startPage.setName_NewLabelPrice("2.15");
        startPage.clickByLocator(properties.getProperty("Locator.StartPage.button_checkNewLabelPrice"));
        startPage.checkAddedLabelPrice("Alloy surcharge", "2.15");

        // enter new value and price for Label 'Scrap surcharge'
        startPage.setName_NewLabelName("Scrap surcharge");
        startPage.setName_NewLabelPrice("3.14");
        startPage.clickByLocator(properties.getProperty("Locator.StartPage.button_checkNewLabelPrice"));
        startPage.checkAddedLabelPrice("Scrap surcharge", "3.14");

        // enter new value and price for Label 'Internal surcharge'
        startPage.setName_NewLabelName("Internal surcharge");
        startPage.setName_NewLabelPrice("0.7658");
        startPage.clickByLocator(properties.getProperty("Locator.StartPage.button_checkNewLabelPrice"));
        startPage.checkAddedLabelPrice("Internal surcharge", "0.77");

        // enter new value and price for Label 'External surcharge'
        startPage.setName_NewLabelName("External surcharge");
        startPage.setName_NewLabelPrice("1");
        startPage.clickByLocator(properties.getProperty("Locator.StartPage.button_checkNewLabelPrice"));
        startPage.checkAddedLabelPrice("External surcharge", "1.0");

        // enter new value and price for Label 'Storage surcharge'
        startPage.setName_NewLabelName("Storage surcharge");
        startPage.setName_NewLabelPrice("0.3");
        startPage.clickByLocator(properties.getProperty("Locator.StartPage.button_checkNewLabelPrice"));
        startPage.checkAddedLabelPrice("Storage surcharge", "0.3");

        // verify results
        startPage.checkTotalPrice("8.36");
    }

    @Test(description = "TF-03: Remove price component: Internal surcharge")
    public void removePriceComponent_positiveTest() {
        driver.get(properties.getProperty("url.base"));

        // check page Title
        StartPage startPage = new StartPage(driver, properties);
        logInfo("Page title: " + startPage.getTitle());
        assertEquals("Vite App", startPage.getTitle());

        // click on label input
        startPage.clickByLocator(properties.getProperty("Locator.StartPage.inputNewLabel_LabelName"));

        // enter new value and price for Label 'Alloy surcharge'
        startPage.setName_NewLabelName("Alloy surcharge");
        startPage.setName_NewLabelPrice("2.15");
        startPage.clickByLocator(properties.getProperty("Locator.StartPage.button_checkNewLabelPrice"));

        // enter new value and price for Label 'Scrap surcharge'
        startPage.setName_NewLabelName("Scrap surcharge");
        startPage.setName_NewLabelPrice("3.14");
        startPage.clickByLocator(properties.getProperty("Locator.StartPage.button_checkNewLabelPrice"));

        // enter new value and price for Label 'Internal surcharge'
        startPage.setName_NewLabelName("Internal surcharge");
        startPage.setName_NewLabelPrice("0.7658");
        startPage.clickByLocator(properties.getProperty("Locator.StartPage.button_checkNewLabelPrice"));

        // enter new value and price for Label 'External surcharge'
        startPage.setName_NewLabelName("External surcharge");
        startPage.setName_NewLabelPrice("1");
        startPage.clickByLocator(properties.getProperty("Locator.StartPage.button_checkNewLabelPrice"));

        // enter new value and price for Label 'Storage surcharge'
        startPage.setName_NewLabelName("Storage surcharge");
        startPage.setName_NewLabelPrice("0.3");
        startPage.clickByLocator(properties.getProperty("Locator.StartPage.button_checkNewLabelPrice"));

        // click on '‘Trash’ icon for 'Internal surcharge'
        startPage.moveCursorToLocator("//span[normalize-space(text()) = 'Internal surcharge']/../..");
        startPage.clickByLocator("//span[normalize-space(text()) = 'Internal surcharge']/../../div[4]/span[1]");

        // verify results
        startPage.checkTotalPrice("7.59");
    }

    @Test(description = "TF-04: Edit price component: Storage surcharge")
    public void editNameComponent_positiveTest() {
        driver.get(properties.getProperty("url.base"));

        // check page Title
        StartPage startPage = new StartPage(driver, properties);
        logInfo("Page title: " + startPage.getTitle());
        assertEquals("Vite App", startPage.getTitle());

        // click on label input
        startPage.clickByLocator(properties.getProperty("Locator.StartPage.inputNewLabel_LabelName"));

        // enter new value and price for Label 'Alloy surcharge'
        startPage.setName_NewLabelName("Alloy surcharge");
        startPage.setName_NewLabelPrice("2.15");
        startPage.clickByLocator(properties.getProperty("Locator.StartPage.button_checkNewLabelPrice"));

        // enter new value and price for Label 'Scrap surcharge'
        startPage.setName_NewLabelName("Scrap surcharge");
        startPage.setName_NewLabelPrice("3.14");
        startPage.clickByLocator(properties.getProperty("Locator.StartPage.button_checkNewLabelPrice"));

        // enter new value and price for Label 'Internal surcharge'
        startPage.setName_NewLabelName("Internal surcharge");
        startPage.setName_NewLabelPrice("0.7658");
        startPage.clickByLocator(properties.getProperty("Locator.StartPage.button_checkNewLabelPrice"));

        // enter new value and price for Label 'External surcharge'
        startPage.setName_NewLabelName("External surcharge");
        startPage.setName_NewLabelPrice("1");
        startPage.clickByLocator(properties.getProperty("Locator.StartPage.button_checkNewLabelPrice"));

        // enter new value and price for Label 'Storage surcharge'
        startPage.setName_NewLabelName("Storage surcharge");
        startPage.setName_NewLabelPrice("0.3");
        startPage.clickByLocator(properties.getProperty("Locator.StartPage.button_checkNewLabelPrice"));

        // click on 'Pencil’ icon for 'Storage surcharge'
        startPage.moveCursorToLocator("//span[normalize-space(text()) = 'Storage surcharge']/../../div[4]/span");
        startPage.setName_NewLabelName("T", "(//input[normalize-space(text()) = ''])[1]");
        startPage.checkByLocator(properties.getProperty("Locator.StartPage.text_errorSmallText"));
        startPage.clickByLocator(properties.getProperty("Locator.StartPage.button_checkNewLabelPrice"));

        // verify results
        startPage.checkTotalPrice("8.36");
    }

    @Test(description = "TF-05: Edit price component: Scrap surcharge")
    public void editNegativePriceComponent_positiveTest() {
        driver.get(properties.getProperty("url.base"));

        // check page Title
        StartPage startPage = new StartPage(driver, properties);
        logInfo("Page title: " + startPage.getTitle());
        assertEquals("Vite App", startPage.getTitle());

        // click on label input
        startPage.clickByLocator(properties.getProperty("Locator.StartPage.inputNewLabel_LabelName"));

        // enter new value and price for Label 'Alloy surcharge'
        startPage.setName_NewLabelName("Alloy surcharge");
        startPage.setName_NewLabelPrice("2.15");
        startPage.clickByLocator(properties.getProperty("Locator.StartPage.button_checkNewLabelPrice"));

        // enter new value and price for Label 'Scrap surcharge'
        startPage.setName_NewLabelName("Scrap surcharge");
        startPage.setName_NewLabelPrice("3.14");
        startPage.clickByLocator(properties.getProperty("Locator.StartPage.button_checkNewLabelPrice"));

        // enter new value and price for Label 'Internal surcharge'
        startPage.setName_NewLabelName("Internal surcharge");
        startPage.setName_NewLabelPrice("0.7658");
        startPage.clickByLocator(properties.getProperty("Locator.StartPage.button_checkNewLabelPrice"));

        // enter new value and price for Label 'External surcharge'
        startPage.setName_NewLabelName("External surcharge");
        startPage.setName_NewLabelPrice("1");
        startPage.clickByLocator(properties.getProperty("Locator.StartPage.button_checkNewLabelPrice"));

        // enter new value and price for Label 'Storage surcharge'
        startPage.setName_NewLabelName("Storage surcharge");
        startPage.setName_NewLabelPrice("0.3");
        startPage.clickByLocator(properties.getProperty("Locator.StartPage.button_checkNewLabelPrice"));

        // click on 'Pencil’ icon for 'Scrap surcharge'
        startPage.moveCursorToLocator("//span[normalize-space(text()) = 'Scrap surcharge']/../..");
        startPage.moveCursorToLocator("(//span[normalize-space(text()) = 'Scrap surcharge']/../../div[4]/span)[1]");
        startPage.setName_NewLabelPrice("-2.15", "(//input[normalize-space(text()) = ''])[2]");
        startPage.checkByLocator(properties.getProperty("Locator.StartPage.text_errorNegativeNumber"));
        startPage.clickByLocator(properties.getProperty("Locator.StartPage.button_checkNewLabelPrice"));

        // verify results
        startPage.checkTotalPrice("8.36");
    }

    @Test(description = "TF-06: Edit price component: Alloy surcharge")
    public void editPositivePriceComponent_positiveTest() {
        driver.get(properties.getProperty("url.base"));

        // check page Title
        StartPage startPage = new StartPage(driver, properties);
        logInfo("Page title: " + startPage.getTitle());
        assertEquals("Vite App", startPage.getTitle());

        // click on label input
        startPage.clickByLocator(properties.getProperty("Locator.StartPage.inputNewLabel_LabelName"));

        // enter new value and price for Label 'Alloy surcharge'
        startPage.setName_NewLabelName("Alloy surcharge");
        startPage.setName_NewLabelPrice("2.15");
        startPage.clickByLocator(properties.getProperty("Locator.StartPage.button_checkNewLabelPrice"));

        // enter new value and price for Label 'Scrap surcharge'
        startPage.setName_NewLabelName("Scrap surcharge");
        startPage.setName_NewLabelPrice("3.14");
        startPage.clickByLocator(properties.getProperty("Locator.StartPage.button_checkNewLabelPrice"));

        // enter new value and price for Label 'Internal surcharge'
        startPage.setName_NewLabelName("Internal surcharge");
        startPage.setName_NewLabelPrice("0.7658");
        startPage.clickByLocator(properties.getProperty("Locator.StartPage.button_checkNewLabelPrice"));

        // enter new value and price for Label 'External surcharge'
        startPage.setName_NewLabelName("External surcharge");
        startPage.setName_NewLabelPrice("1");
        startPage.clickByLocator(properties.getProperty("Locator.StartPage.button_checkNewLabelPrice"));

        // enter new value and price for Label 'Storage surcharge'
        startPage.setName_NewLabelName("Storage surcharge");
        startPage.setName_NewLabelPrice("0.3");
        startPage.clickByLocator(properties.getProperty("Locator.StartPage.button_checkNewLabelPrice"));

        // click on 'Pencil’ icon for 'Alloy surcharge'
        startPage.moveCursorToLocator("//span[normalize-space(text()) = 'Alloy surcharge']/../..");
        startPage.clickByLocator("//span[normalize-space(text()) = 'Alloy surcharge']/../../div[1]");
        startPage.setName_NewLabelPrice("1.79", "(//input[normalize-space(text()) = ''])[2]");
        startPage.clickByLocator("(//span[normalize-space(text()) = '']/../../../div)[2]/div[4]/span[2]");

        // verify results
        startPage.checkTotalPrice("8.00");
    }
}
