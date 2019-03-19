import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;


/**
 *  A basic test for registration page
 */
@Test()
public class RegistrationTest {

    /**
     * Sets up test
     * @throws Exception on error
     */
    @BeforeClass
    public void setUp() throws Exception {
        System.setProperty("webdriver.gecko.driver", "D:\\saddy\\geckodriver.exe");
        System.setProperty("webdriver.chrome.driver", "D:\\saddy\\chromedriver.exe");
        mDriver = new ChromeDriver();
    }

    /**
     * Runs a simple test
     */
    @Test()
    public void test()
    {
        mDriver.get("https://google.com");
        System.out.println(mDriver.getTitle());
        Assert.assertEquals("1", "1");
    }


    /**
     * Tear downs a test
     * @throws Exception on error
     */
    @AfterClass
    public void tearDown() throws Exception {
        mDriver.quit();
    }

    /**
     * A driver for running page
     */
    private WebDriver mDriver;

}
