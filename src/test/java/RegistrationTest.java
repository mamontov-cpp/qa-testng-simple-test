import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
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
     * Returns list of drivers to test with
     * @return Object[][] list of drivers for registration
     */
    @DataProvider(name = "drivers")
    public static Object[][]  drivers()
    {
        return new Object[][]{ {WebDriverType.CHROME}, {WebDriverType.FIREFOX}};
    }

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
    @Test(dataProvider =  "drivers")
    public void testRegistrationFormShouldNotAcceptEmptyRequiredFields(WebDriverType type)
    {
        Page page = Page.makePageWithDriver(type);
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
        // The second condition is added due to FF behaviour
        Assert.assertTrue(pageUrl.equals("http://automationpractice.com/index.php?controller=authentication")
        || pageUrl.equals("http://automationpractice.com/index.php?controller=authentication#account-creation")
        );
        // 2. Page must contain red block with classes "alert alert-danger", that describes errors
        Assert.assertTrue(isErrorsBlockPresent);
    }

    /**
     * Tests case "2. Registration form should accept filled form"
     * see https://docs.google.com/spreadsheets/d/1IVFe6YQjt-EqlOz0cQtIywSuMyhNGb6HUAyz4dpvw58/edit#gid=0 for details
     */
    @Test(dataProvider =  "drivers")
    public void testReqistrationShouldAcceptFilledForm(WebDriverType type)
    {
        Page page = Page.makePageWithDriver(type);
        String email = Utils.makeUniqueEmail();
        String firstName  = "John";
        String lastName = "Doe";
        // 1. Go to page http://automationpractice.com/index.php?controller=authentication
        page.navigate("http://automationpractice.com/index.php?controller=authentication");
        // 2. Inside "Create an account" form  in field "Email  address"
        // write "testmail{day}{month}{year}{hour}{minute}{seconds}@testmail.com" ,
        // where in figure brackets should be numerical values of corresponding date and time
        page.fillFormFieldWith("#email_create", email);
        // Wait for JS to work with filling field
        page.waitForElement("#create-account_form .form-ok, #create-account_form .form-error");
        // 3. Inside "Create an account" form, click on "Create an account" button
        page.clickOnFormFieldWith("#SubmitCreate");
        // 4. Wait for next page to load
        page.waitForAjaxToFinish();
        // 5. Fill first field "First name" with value "John"
        page.fillFormFieldWith("#customer_firstname", firstName);
        // 6. Fill first field "Last name" with value "Doe"
        page.fillFormFieldWith("#customer_lastname", lastName);
        // 7. Fill field "Password" with value "pwd12$90"
        page.fillFormFieldWith("#passwd", "pwd12$90");
        // 8. Fill field "Company" with value "Company"
        page.fillFormFieldWith("#company", "Company");
        // 9.  Fill field "Address" with value "Address"
        page.fillFormFieldWith("#address1", "Address");
        // 10.  Fill field "City" with value "Aliceville"
        page.fillFormFieldWith("#city", "Aliceville");
        // 11. Fill field "State" with value "Alabama"
        page.fillSelectFormFieldWith("#id_state", "Alabama");
        // 12. Fill field "ZipPostal" with value "12345"
        page.fillFormFieldWith("#postcode", "12345");
        // 13. Fill field "Mobile phone" with value "89285230482"
        page.fillFormFieldWith("#phone_mobile", "89285230482");
        // 14. Fill field "Assign an address alias for future reference. " with value "1"
        page.fillFormFieldWith("#alias", "1");
        // 15. Press "Register" button
        page.clickOnFormFieldWith("#submitAccount");
        // Wait for form to work
        page.waitForAjaxToFinish();

        // Desired results:
        String pageUrl = page.getUrl();

        page.navigate("http://automationpractice.com/index.php?controller=identity");
        boolean nameMatches = page.getFieldValue("#firstname").equals(firstName);
        boolean lastNameMatches = page.getFieldValue("#lastname").equals(lastName);
        boolean emailMatches = page.getFieldValue("#email").equals(email);

        page.finish();
        // 1. User must be redirected on page http://automationpractice.com/index.php?controller=my-account
        Assert.assertEquals(pageUrl, "http://automationpractice.com/index.php?controller=my-account");
        // 2. 2. When user goes on page http://automationpractice.com/index.php?controller=identity,
        // field "First name" should be filled with "John", field "Last name" should be filled with "Doe",
        // field "Email address" should be filled with previously set email
        Assert.assertTrue(nameMatches && lastNameMatches && emailMatches);
    }


}
