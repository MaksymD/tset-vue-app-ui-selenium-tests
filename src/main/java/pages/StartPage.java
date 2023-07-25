package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertTrue;

public class StartPage extends BasePage {

    private final Logger logger = LoggerFactory.getLogger(StartPage.class);

    // Locators
    By button_pencilBasePrice = By.xpath(properties.getProperty("Locator.StartPage.button_pencilBasePrice"));
    By inputNumber_BasePrice = By.xpath(properties.getProperty("Locator.StartPage.inputNumber_BasePrice"));
    By text_TotalPrice = By.xpath(properties.getProperty("Locator.StartPage.text_TotalPrice"));
    By inputNewLabel_LabelName = By.xpath(properties.getProperty("Locator.StartPage.inputNewLabel_LabelName"));
    By inputNewLabel_LabelPrice = By.xpath(properties.getProperty("Locator.StartPage.inputNewLabel_LabelPrice"));

    public StartPage(WebDriver driver, Properties properties) {
        super(driver, properties);
    }


    public void checkPageTitle(){
        getPageTitle("Vite App");
    }

    public void moveCursorToLocator(String locator) {
        By xpathElement = By.xpath(locator);
        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(xpathElement));
        Actions actions = new Actions(driver);
        actions.moveToElement(element).perform();
    }

    public void setNumber_BasePrice(String number) {
        assertTrue("Element is not present.", isElementPresent(inputNumber_BasePrice));
        fillInputField(inputNumber_BasePrice, number);
    }

    public void checkTotalPrice(String number) {
        assertTrue("Element is not present.", isElementPresent(text_TotalPrice));
        WebElement elementTotal = driver.findElement(text_TotalPrice);
        String actualTotal = elementTotal.getText();
        assertEquals(number, actualTotal);
    }

    public void setName_NewLabelName(String name_newLabel) {
        assertTrue("Element is not present.", isElementPresent(inputNewLabel_LabelName));
        fillInputField(inputNewLabel_LabelName, name_newLabel);
    }

    public void setName_NewLabelName(String name_newLabel, String locator) {
        By xpathElement = By.xpath(locator);
        assertTrue("Element is not present.", isElementPresent(xpathElement));
        fillInputField(xpathElement, name_newLabel);
    }

    public void setName_NewLabelPrice(String price_newLabel) {
        assertTrue("Element is not present.", isElementPresent(inputNewLabel_LabelPrice));
        fillInputField(inputNewLabel_LabelPrice, price_newLabel);
    }

    public void setName_NewLabelPrice(String price_newLabel, String locator) {
        By xpathElement = By.xpath(locator);
        assertTrue("Element is not present.", isElementPresent(xpathElement));
        fillInputField(xpathElement, price_newLabel);
    }

    public void checkAddedLabelPrice(String name, String expectedPrice) {
        String xPathSample = properties.getProperty("Locator.StartPage.text_LabelPrice");
        String xPath_byLabelName = xPathSample.replaceAll("XXX", name);
        WebElement element_byLabelName = find(By.xpath(xPath_byLabelName));
        assertTrue("Element is not present.", isElementPresent(By.xpath(xPath_byLabelName)));
        String actualAddedLabelPrice = element_byLabelName.getText();
        assertEquals(expectedPrice, actualAddedLabelPrice);
    }

    public void checkErrorMsgByLocator(String locator_errorMsg) {
        By xpathElement = By.xpath(locator_errorMsg);
        assertTrue("Element is not present.", isElementPresent(xpathElement));
    }
}
