import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.FileInputStream;
import java.util.Properties;


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
        FileInputStream fis = new FileInputStream("drivers.properties");
        Properties props = new Properties();
        props.load(fis);
        fis.close();

        System.setProperty("webdriver.gecko.driver", props.getProperty("webdriver.gecko.driver"));
        System.setProperty("webdriver.chrome.driver", props.getProperty("webdriver.chrome.driver"));

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
     */
    @AfterClass
    public void tearDown() {
        mDriver.quit();
    }

    /**
     * A driver for running page
     */
    private WebDriver mDriver;

}
