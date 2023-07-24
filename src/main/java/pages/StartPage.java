package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.Properties;

import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertTrue;

public class StartPage extends BasePage {

    private final Logger logger = LoggerFactory.getLogger(StartPage.class);

    // Locators
    By button_pencilBasePrice = By.xpath(properties.getProperty("Locator.StartPage.button_pencilBasePrice"));
    By inputNumber_BasePrice = By.xpath(properties.getProperty("Locator.StartPage.inputNumber_BasePrice"));
    By text_TotalPrice = By.xpath(properties.getProperty("Locator.StartPage.text_TotalPrice"));

    public StartPage(WebDriver driver, Properties properties) {
        super(driver, properties);
    }

    public void moveCursorToBestPrice() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='BasePrice']")));
        Actions actions = new Actions(driver);
        //WebElement basePriceElement = driver.findElement(By.xpath("//div[@id='BasePrice']"));
        actions.moveToElement(element).perform();
    }

    public void clickByLocator(String locator) {
        By xpathElement = By.xpath(locator);
        assertTrue("Element is not present.", isElementPresent(xpathElement));
        click(xpathElement);
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
}
