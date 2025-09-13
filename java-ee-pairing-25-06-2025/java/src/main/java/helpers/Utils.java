package helpers;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.Locale;

public class Utils {

    public static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
    public static final String SCREENSHOT_FORMAT = "yyyy-MM-dd'T'HH-mm-ss-SSS'Z'";

    public static String getCurrentDateInFormat(String dateFormat) {
        Date date = new Date(Instant.now().toEpochMilli());
        SimpleDateFormat format = new SimpleDateFormat(dateFormat, Locale.US);
        return format.format(date);
    }
}
