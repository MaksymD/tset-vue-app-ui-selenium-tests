package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.WebDriverFactory;

import java.io.IOException;
import java.io.InputStream;
import java.time.Duration;
import java.util.List;
import java.util.Properties;

import static org.testng.AssertJUnit.assertTrue;

public abstract class BasePage {
    protected Properties properties;
    protected WebDriver driver;
    protected WebDriverWait wait;
    private static final int TIMEOUT = 30;
    private static final int EACH_NEW_ATTEMPT = 5;
    private final Logger logger = LoggerFactory.getLogger(BasePage.class);

    protected BasePage(WebDriver driver, Properties properties) {
        this.driver = driver;
        this.properties = properties;
        wait = new WebDriverWait(driver, Duration.ofSeconds(TIMEOUT), Duration.ofSeconds(EACH_NEW_ATTEMPT));
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, TIMEOUT), this);
    }

    protected BasePage(String browser) {
        this.driver = WebDriverFactory.createInstance(browser);
    }

    protected void setUp(WebDriver driver) {
        this.driver = driver;
        driver.manage().window().maximize();
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(TIMEOUT));
    }

    protected void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    protected Properties loadProperties(String propertyFile) throws IOException {
        Properties properties = new Properties();
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(propertyFile)) {
            properties.load(inputStream);
        }
        return properties;
    }

    protected void getPageTitle(String expectedPageTitle) {
        String pageTitle = this.driver.getTitle();
        if (!pageTitle.equals(expectedPageTitle)) {
            throw new IllegalStateException(pageTitle + " -> This is not expected Page Title," +
                    " current page is: " + driver.getCurrentUrl());
        }
    }

    public String getTitle() {
        return this.driver.getTitle();
    }

    protected void logInfo(String message) {
        this.logger.info(message);
    }

    protected boolean isElementPresent(By locator) {
        return this.isElementPresent(locator, true);
    }

    public void clickByLocator(String locator) {
        By xpathElement = By.xpath(locator);
        assertTrue("Element is not present.", isElementPresent(xpathElement));
        click(xpathElement);
    }

    protected void click(By locator) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
        logger.debug("Clicking Element (" + locator.toString() + ").");
        element.click();
    }

    protected void fillInputField(By field, String text) {
        this.logger.info("Fill field ( " + field.toString() + " ) with ( " + text + " ).");
        WebElement element = this.find(field);
        element.click();
        element.clear();
        element = this.find(field);
        element.sendKeys(text);
    }

    protected boolean isElementPresent(By locator, boolean wait) {
        try {
            this.find(locator, wait);
        } catch (IllegalArgumentException var4) {
            this.logger.debug("Element with Locator '" + locator.toString() + "' not found!");
            return false;
        }

        this.logger.debug("Element with Locator '" + locator.toString() + "' found!");
        return true;
    }

    protected WebElement find(By locator) {
        return this.find(locator, true);
    }

    protected WebElement find(By locator, boolean wait) {
        List<WebElement> elements = this.findAllElements(locator, wait);
        if (elements.size() > 1) {
            throw new IllegalStateException("Locator (" + locator.toString() + ") finds more than one element.");
        } else if (elements.size() == 0) {
            throw new IllegalArgumentException("Locator (" + locator.toString() + ") does not find any element.");
        } else {
            return elements.get(0);
        }
    }

    protected List<WebElement> findAllElements(By locator) {
        return findAllElements(locator, true);
    }

    protected List<WebElement> findAllElements(By locator, boolean wait) {
        if (wait) {
            try {
                WebDriverWait webDriverWait = new WebDriverWait(this.driver, Duration.ofSeconds(30));
                webDriverWait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
            } catch (TimeoutException var4) {
                throw new IllegalArgumentException("Locator (" + locator.toString() + ") does not find any element.");
            }
        }

        List<WebElement> elements = this.driver.findElements(locator);
        return elements;
    }
}