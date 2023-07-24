package tests;

import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.BasePage;
import pages.StartPage;

import java.io.IOException;

import static org.testng.AssertJUnit.assertEquals;

public class PriceComponentsTests extends BasePage {

    @Parameters({"browser", "property_file"})
    public PriceComponentsTests(String browser, String propertyFile) throws IOException {
        super(browser);
        this.properties = loadProperties(propertyFile);
    }

    @Test(description = "TF-01: Change Base Price value to 5")
    public void changeBasePrice() {
        driver.get(properties.getProperty("url.base"));

        // check page Title
        StartPage startPage = new StartPage(driver, properties);
        logInfo("Page title: " + startPage.getTitle());
        assertEquals("Vite App", startPage.getTitle());

        // click on 'Pencil'
        startPage.moveCursorToBestPrice();
        startPage.clickByLocator(properties.getProperty("Locator.StartPage.button_pencilBasePrice"));

        // enter new value in BasePrice input number
        startPage.setNumber_BasePrice("5");
        startPage.clickByLocator(properties.getProperty("Locator.StartPage.button_checkBasePrice"));

        // verify results
        startPage.checkTotalPrice("5.00");
    }
}
