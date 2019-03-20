import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import javax.rmi.CORBA.Util;
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
     * Tests case "1. Registation form should not accept empty required fields"
     * see https://docs.google.com/spreadsheets/d/1IVFe6YQjt-EqlOz0cQtIywSuMyhNGb6HUAyz4dpvw58/edit#gid=0 for details
     */
    @Test()
    public void testRegistrationFormShouldNotAcceptEmptyRequiredFields()
    {
        Page page = Page.makePageWithDriver(WebDriverType.CHROME);
        // 1. Go to page http://automationpractice.com/index.php?controller=authentication
        page.navigate("http://automationpractice.com/index.php?controller=authentication");
        // 2. Inside "Create an account" form  in field "Email  address"
        // write "testmail{day}{month}{year}{hour}{minute}{seconds}@testmail.com" ,
        // where in figure brackets should be numerical values of corresponding date and time
        page.fillFormFieldWith("#email_create", Utils.makeUniqueEmail());
        // Wait for JS to work with filling field
        page.waitForElement("#create-account_form .form-ok, #create-account_form .form-error");
        // 3. Inside "Create an account" form, click on "Create an account" button
        page.clickOnFormFieldWith("#SubmitCreate");
        // 4. Wait for next page to load
        page.waitForAjaxToFinish();
        // 5. Press "Register" button
        page.clickOnFormFieldWith("#submitAccount");
        // Wait for form to work
        page.waitForAjaxToFinish();

        // Desired results:
        String pageUrl = page.getUrl();
        boolean isErrorsBlockPresent = page.isElementPresent(".alert.alert-danger");
        page.finish();
        // 1. The form must reload page and be on same page
        Assert.assertEquals(pageUrl, "http://automationpractice.com/index.php?controller=authentication");
        // 2. Page must contain red block with classes "alert alert-danger", that describes errors
        Assert.assertTrue(isErrorsBlockPresent);
    }


}
