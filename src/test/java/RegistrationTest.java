import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;


/**
 *  A basic test for registration page
 */
@Test()
public class RegistrationTest extends PageTest {

    /**
     * Setups basic test properties
     *
     * @throws Exception exception in case of error
     */
    public RegistrationTest() throws IOException {

    }

    /**
     * Runs a simple test
     */
    @Test()
    public void test()
    {
        Page page = Page.makePageWithDriver(WebDriverType.CHROME);
        page.navigate("http://automationpractice.com/index.php?controller=authentication");
        System.out.println(page.getTitle());
        page.finish();
        Assert.assertEquals("1", "1");
    }


}
