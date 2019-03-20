import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

/**
 * A page for abstracting work with WebDriver
 */
public class Page {

    /**
     * Makes new driver
     * @param driver a driver to work with
     */
    @org.jetbrains.annotations.Contract("null -> fail")
    public Page(WebDriver driver)
    {
        assert driver != null;
        mDriver = driver;
    }

    /**
     * Makes page with specified driver
     * @param type a type of driver
     * @return specified driver
     */
    public static Page makePageWithDriver(WebDriverType type)
    {
        WebDriverFactory factory = new WebDriverFactory();
        WebDriver driver = factory.create(WebDriverType.CHROME);
        return new Page(driver);
    }

    /**
     * Navigates to page, adding necessary hooks
     * @param url page URL
     */
    public void navigate(String url)
    {
        mDriver.get(url);
        hookAjaxRequests();
    }

    /**
     * Returns title for page
     * @return String title for page
     */
    public String getTitle()
    {
        return mDriver.getTitle();
    }

    /**
     * Fills form field with specified value
     * @param selector a selector  for a field
     * @param value a value, which field must be filled with
     */
    public void fillFormFieldWith(String selector, String value)
    {
        mDriver.findElement(By.cssSelector(selector)).sendKeys(value);
        // A simple code to ensure, that element will be blurred
        mDriver.findElement(By.cssSelector("body")).click();
    }

    /**
     * Clicks on form field
     * @param selector a selector  for a field
     */
    public void clickOnFormFieldWith(String selector)
    {
        mDriver.findElement(By.cssSelector(selector)).click();
    }

    /**
     * Returns whether element is present on a page
     * @param selector a selector
     * @return whether it's present on page
     */
    public boolean isElementPresent(String selector)
    {
        return mDriver.findElements(By.cssSelector(selector)).size() > 0;
    }

    /**
     * Waits for elements with selector to appear
     * @param selector a selector to wait
     */
    public void waitForElement(final String selector)
    {
        WebDriverWait wait = new WebDriverWait(mDriver, 10);
        wait.withMessage("Still not found element".concat(selector));
        wait.until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver driver) {
                int elementCount = driver.findElements(By.cssSelector(selector)).size();
                if (elementCount > 0)
                    return true;
                else
                    return false;
            }
        });
    }

    /**
     * Waits for all ajax requests to finish
     */
    public void waitForAjaxToFinish()
    {
        mDriver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
        WebDriverWait wait = new WebDriverWait(mDriver, 60);
        wait.until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver driver) {
                try {
                    Long activeRequests = (Long) (((JavascriptExecutor) mDriver).executeScript("return window[\"activeRequests\"];"));
                    if (activeRequests == null) {
                        // Hook requests if reloaded page
                        hookAjaxRequests();
                        return false;
                    }
                    return activeRequests == 0;
                } catch (org.openqa.selenium.JavascriptException ex) {
                    // It seems, that page has been reloaded too
                    hookAjaxRequests();
                    return false;
                }
            }
        });
    }

    /**
     * Returns URL for page
     * @return url  for specified page
     */
    public String getUrl()
    {
        return mDriver.getCurrentUrl();
    }


    /**
     * Hooks AJAX requests through jQuery on page
     */
    private void hookAjaxRequests()
    {
        // A simple hook to hook on AJAX calls, with jQuery
        ((JavascriptExecutor) mDriver).executeScript("var fn = $.ajax; window[\"activeRequests\"] = 0; $.ajax = function(o) { window[\"activeRequests\"]++; console.log(\"ajax\"); return fn(o).done(function() { window[\"activeRequests\"]--; }); };");
    }


    /**
     * Finishes working with page
     */
    void finish()
    {
        mDriver.quit();
    }

    /**
     * A driver for running page
     */
    private WebDriver mDriver;
}
