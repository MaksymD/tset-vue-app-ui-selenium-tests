package utils;

import exceptions.BrowserNotSupportedException;
import org.openqa.selenium.WebDriver;
import utils.manager.ChromeDriverManager;
import utils.manager.FirefoxDriverManager;

public class WebDriverFactory {
    public WebDriver createInstance(String browser) {
        WebDriver driver;
        BrowserList browserType = BrowserList.valueOf(browser.toUpperCase());

        switch (browserType) {

            case CHROME:
                driver = new ChromeDriverManager().createDriver();
                break;
            case FIREFOX:
                driver = new FirefoxDriverManager().createDriver();
                break;
            default:
                throw new BrowserNotSupportedException(browser);
        }
        return driver;
    }
}

