import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

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
        // A simple hook to hook on AJAX calls, with jQuery
        ((JavascriptExecutor) mDriver).executeScript("var fn = $.ajax; var activeRequests = 0; $.ajax = function(o) { activeRequests++; console.log(\"ajax\"); return fn(o).done(function() { activeRequests--; }); };");
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
