package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import utils.WebDriverFactory;

import java.io.IOException;
import java.io.InputStream;
import java.time.Duration;
import java.util.List;
import java.util.Properties;

public abstract class BasePage {
    protected Properties properties;
    protected WebDriver driver;
    private final Logger logger = LoggerFactory.getLogger(BasePage.class);

    public BasePage(WebDriver driver, Properties properties) {
        this.driver = driver;
        this.properties = properties;
    }

    public BasePage(String browser) {
        this.driver = WebDriverFactory.createInstance(browser);
    }


    @BeforeMethod
    public void setUP() {
        driver.manage().window().maximize();
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(10));
    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
    }

    protected Properties loadProperties(String propertyFile) throws IOException {
        Properties properties = new Properties();
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(propertyFile)) {
            properties.load(inputStream);
        }
        return properties;
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

    protected void click(By locator) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
        logger.debug("Clicking Element (" + locator.toString() + ").");
        element.click();
    }

    protected void fillInputField(By field, String text) {
        this.logger.info("Fill field ( " + field.toString() + " ) with ( " + text + " ).");
        WebElement element = this.find(field);
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