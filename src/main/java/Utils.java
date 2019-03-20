import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Utility functions
 */
public class Utils {

    /**
     * Makes unique Email for storing it
     * @return unique email string
     */
    public static String makeUniqueEmail()
    {
        String pattern = "ddMMyyyyHHmmss";
        DateFormat df = new SimpleDateFormat(pattern);
        Date  today = Calendar.getInstance().getTime();
        String todayAsString = df.format(today);
        return "testmail".concat(todayAsString).concat("@testmail.com");
    }
}
