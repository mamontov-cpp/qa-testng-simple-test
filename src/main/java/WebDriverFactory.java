import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

/**
 * A factory, for creating a drivers by enum
 */
public class WebDriverFactory {

    public WebDriverFactory()
    {

    }

    /**
     * Returns driver factory
     * @param type of web driver
     * @throws IllegalArgumentException in case of coding mistake, when some driver is not handled
     * @return WebDriver a driver data
     */
    public WebDriver create(WebDriverType type)
    {
        switch (type)
        {
            case FIREFOX: return new FirefoxDriver();
            case CHROME: return new ChromeDriver();
        }
        throw new IllegalArgumentException("Unknown driver type");
    }
}
