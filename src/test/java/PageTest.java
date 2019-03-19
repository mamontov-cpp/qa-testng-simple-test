import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

/**
 * A test for page
 */
public class PageTest {
    /**
     * Setups basic test properties
     * @throws Exception exception in case of error
     */
    public PageTest() throws IOException {
        FileInputStream fis = new FileInputStream("drivers.properties");
        Properties props = new Properties();
        props.load(fis);
        fis.close();

        System.setProperty("webdriver.gecko.driver", props.getProperty("webdriver.gecko.driver"));
        System.setProperty("webdriver.chrome.driver", props.getProperty("webdriver.chrome.driver"));
    }
}
