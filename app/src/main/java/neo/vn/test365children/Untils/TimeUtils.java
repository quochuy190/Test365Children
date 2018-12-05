package neo.vn.test365children.Untils;

import android.annotation.SuppressLint;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created with Android Studio.
 * User: ryan.hoo.j@gmail.com
 * Date: 9/2/16
 * Time: 6:07 PM
 * Desc: TimeUtils
 */
public class TimeUtils {

    /**
     * Parse the time in milliseconds into String with the format: hh:mm:ss or mm:ss
     *
     * @param duration The time needs to be parsed.
     */
    @SuppressLint("DefaultLocale")
    public static String formatDuration(int duration) {
        duration /= 1000; // milliseconds into seconds
        int minute = duration / 60;
        int hour = minute / 60;
        minute %= 60;
        int second = duration % 60;
        if (hour != 0)
            return String.format("%2d:%02d:%02d", hour, minute, second);
        else
            return String.format("%02d:%02d", minute, second);
    }

    /**
     * Parse the time in milliseconds into String with the format: hh:mm:ss or mm:ss
     *
     * @param duration The time needs to be parsed.
     */
    @SuppressLint("DefaultLocale")
    public static String formatTime_Complete(int duration) {
        duration /= 1000; // milliseconds into seconds
        int minute = duration / 60;
        int hour = minute / 60;
        minute %= 60;
        int second = duration % 60;
        if (hour != 0)
            return String.format("%2d giờ %02d phút %02d giây", hour, minute, second);
        else
            return String.format("%02d phút %02d giây", minute, second);
    }
    public static String convent_date(String sDateinput, String fomatDateinput, String fomatDateoutput) {
        String strDateTime = "";
        DateFormat inputFormatter = new SimpleDateFormat(fomatDateinput);
        Date da = null;
        try {
            da = (Date) inputFormatter.parse(sDateinput);
            System.out.println("==Date is ==" + da);
            DateFormat outputFormatter = new SimpleDateFormat(fomatDateoutput);
            strDateTime = outputFormatter.format(da);

            return strDateTime;
        } catch (ParseException e) {
            e.printStackTrace();

        }
        return strDateTime;
    }
}
