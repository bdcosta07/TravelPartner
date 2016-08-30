package com.kichukkhon.android.travelpartner.Util;

import android.content.Context;
import android.text.format.Time;

import com.kichukkhon.android.travelpartner.R;
import com.kichukkhon.android.travelpartner.Settings.SettingsUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.EnumSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class AppUtils {
    // Format used for storing dates in the database.  ALso used for converting those strings
    // back into date objects for comparison/processing.
    public static final String DATE_FORMAT = "yyyyMMdd";

    /**
     * Helper method to convert the database representation of the date into something to display
     * to users.  As classy and polished a user experience as "20140102" is, we can do better.
     *
     * @param context      Context to use for resource localization
     * @param dateInMillis The date in milliseconds
     * @return a user-friendly representation of the date.
     */
    public static String getFriendlyDayString(Context context, long dateInMillis) {
        // The day string for forecast uses the following logic:
        // For today: "Today, June 8"
        // For tomorrow:  "Tomorrow"
        // For the next 5 days: "Wednesday" (just the day name)
        // For all days after that: "Mon Jun 8"

        Time time = new Time();
        time.setToNow();
        long currentTime = System.currentTimeMillis();
        int julianDay = Time.getJulianDay(dateInMillis, time.gmtoff);
        int currentJulianDay = Time.getJulianDay(currentTime, time.gmtoff);

        // If the date we're building the String for is today's date, the format
        // is "Today, June 24"
        if (julianDay == currentJulianDay) {
            String today = context.getString(R.string.today);
            int formatId = R.string.format_full_friendly_date;
            return String.format(context.getString(
                    formatId,
                    today,
                    getFormattedMonthDay(context, dateInMillis)));
        } else if (julianDay < currentJulianDay + 7) {
            // If the input date is less than a week in the future, just return the day name.
            return getDayName(context, dateInMillis);
        } else {
            // Otherwise, use the form "Mon Jun 3"
            SimpleDateFormat shortenedDateFormat = new SimpleDateFormat("EEE MMM dd");
            return shortenedDateFormat.format(dateInMillis);
        }
    }

    /**
     * Given a day, returns just the name to use for that day.
     * E.g "today", "tomorrow", "wednesday".
     *
     * @param context      Context to use for resource localization
     * @param dateInMillis The date in milliseconds
     * @return
     */
    public static String getDayName(Context context, long dateInMillis) {
        // If the date is today, return the localized version of "Today" instead of the actual
        // day name.

        Time t = new Time();
        t.setToNow();
        int julianDay = Time.getJulianDay(dateInMillis, t.gmtoff);
        int currentJulianDay = Time.getJulianDay(System.currentTimeMillis(), t.gmtoff);
        if (julianDay == currentJulianDay) {
            return context.getString(R.string.today);
        } else if (julianDay == currentJulianDay + 1) {
            return context.getString(R.string.tomorrow);
        } else {
            Time time = new Time();
            time.setToNow();
            // Otherwise, the format is just the day of the week (e.g "Wednesday".
            SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE");
            return dayFormat.format(dateInMillis);
        }
    }

    /**
     * Converts db date format to the format "Month day", e.g "June 24".
     *
     * @param context      Context to use for resource localization
     * @param dateInMillis The db formatted date string, expected to be of the form specified
     *                     in Utility.DATE_FORMAT
     * @return The day in the form of a string formatted "December 6"
     */
    public static String getFormattedMonthDay(Context context, long dateInMillis) {
        Time time = new Time();
        time.setToNow();
        SimpleDateFormat dbDateFormat = new SimpleDateFormat(AppUtils.DATE_FORMAT);
        SimpleDateFormat monthDayFormat = new SimpleDateFormat("MMMM dd");
        String monthDayString = monthDayFormat.format(dateInMillis);
        return monthDayString;
    }

    public static String getFormattedDate(Context context, long dateInMillis) {
        Time time = new Time();
        time.setToNow();
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM dd, yyyy");
        String dateString = dateFormat.format(dateInMillis);
        return dateString;
    }

    public static String parseYahooWeatherDate(Context context, String date) {
        //ex: Tue, 30 Aug 2016 01:00 PM BDT
        DateFormat format = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm z", Locale.US);
        Date convertedDate = new Date();
        try {
            convertedDate = format.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String desiredDateString = getFriendlyDayString(context, convertedDate.getTime());
        return desiredDateString;
    }

    public static String getFormattedTime(Context context, long dateInMillis) {
//        SimpleDateFormat monthDayFormat = new SimpleDateFormat("h:mm a");
//        String timeString = monthDayFormat.format(dateInMillis);
        Date date = new Date(dateInMillis);
        DateFormat formatter = new SimpleDateFormat("h:mm a");
        String timeString = formatter.format(date);
        return timeString;
    }

    public static long convertDateStringToMillis(String dateString, String expectedPattern) {
        long timeInMillis = 0l;
        SimpleDateFormat formatter = new SimpleDateFormat(expectedPattern);
        try {
            Date date = formatter.parse(dateString);

            timeInMillis = date.getTime();
        } catch (ParseException e) {
            // execution will come here if the String that is given
            // does not match the expected format.
            e.printStackTrace();
        }

        return timeInMillis;
    }

    public static String getFriendlyDateDiff(long baseDate, long compareDate) {
        Map<TimeUnit, Long> mapDiff = computeDiffBetweenTwoDates(baseDate, compareDate);
        String result = "";
        long day = 0;

        for (Map.Entry<TimeUnit, Long> entry : mapDiff.entrySet()) {
            if (entry.getKey() == TimeUnit.DAYS) {
                day = entry.getValue();
                break;
            }
        }

        if (baseDate < compareDate)
            result = day + " day(s) to go";
        else
            result = day + " day(s) ago";

        return result;
    }

    public static Map<TimeUnit, Long> computeDiffBetweenTwoDates(long date1, long date2) {
        long diffInMillis = Math.abs(date1 - date2);
        List<TimeUnit> units = new ArrayList<TimeUnit>(EnumSet.allOf(TimeUnit.class));
        Collections.reverse(units);
        Map<TimeUnit, Long> result = new LinkedHashMap<TimeUnit, Long>();
        long millisRest = diffInMillis;
        for (TimeUnit unit : units) {
            long diff = unit.convert(millisRest, TimeUnit.MILLISECONDS);
            long diffInMillisForUnit = unit.toMillis(diff);
            millisRest = millisRest - diffInMillisForUnit;
            result.put(unit, diff);
        }
        return result;
    }

    public static String formatTemperature(Context context, double temperature) {
        // Data fetched in Censius by default. If user prefers to see in Fahrenheit or Kelvin, convert the values here.
        String suffix = "";
        if (!SettingsUtils.PreferredTemperatureUnit(context).equals(context.getString(R.string.temperature_unit_value_k)))
            suffix = "\u00B0";

        if (SettingsUtils.PreferredTemperatureUnit(context).equals(context.getString(R.string.temperature_unit_value_f))) {
            temperature = (temperature * 1.8) + 32;
        } else if (SettingsUtils.PreferredTemperatureUnit(context).equals(context.getString(R.string.temperature_unit_value_k))) {
            temperature += 273.15;
        }

        // For presentation, assume the user doesn't care about tenths of a degree.
        return String.format(context.getString(R.string.format_temperature), temperature, suffix);
    }

    public static String getIconForYahooWeatherCondition(Context context, int weatherId) {
        if (weatherId == 3)
            return context.getString(R.string.wi_yahoo_3);
        if (weatherId == 4)
            return context.getString(R.string.wi_yahoo_4);
        else if (weatherId == 9)
            return context.getString(R.string.wi_yahoo_9);
        else if (weatherId == 8)
            return context.getString(R.string.wi_yahoo_8);
        else if (weatherId == 11)
            return context.getString(R.string.wi_yahoo_11);
        else if (weatherId == 27)
            return context.getString(R.string.wi_yahoo_27);
        else if (weatherId == 28)
            return context.getString(R.string.wi_yahoo_28);
        else if (weatherId == 26)
            return context.getString(R.string.wi_yahoo_26);
        else if (weatherId == 32)
            return context.getString(R.string.wi_yahoo_32);
        else if (weatherId == 36)
            return context.getString(R.string.wi_yahoo_36);
        else if (weatherId == 31)
            return context.getString(R.string.wi_yahoo_31);
        else if (weatherId == 37)
            return context.getString(R.string.wi_yahoo_37);
        else if (weatherId == 38)
            return context.getString(R.string.wi_yahoo_38);
        else if (weatherId == 39)
            return context.getString(R.string.wi_yahoo_39);
        else if (weatherId == 44)
            return context.getString(R.string.wi_yahoo_44);
        else if (weatherId == 47)
            return context.getString(R.string.wi_yahoo_47);
        else if (weatherId == 35)
            return context.getString(R.string.wi_yahoo_35);
        else if (weatherId == 33)
            return context.getString(R.string.wi_yahoo_33);
        else if (weatherId == 34)
            return context.getString(R.string.wi_yahoo_34);
        else if (weatherId == 29)
            return context.getString(R.string.wi_yahoo_29);
        else if (weatherId == 30)
            return context.getString(R.string.wi_yahoo_30);
        else if (weatherId == 24)
            return context.getString(R.string.wi_yahoo_24);
        else if (weatherId == 22)
            return context.getString(R.string.wi_yahoo_22);
        else if (weatherId == 23)
            return context.getString(R.string.wi_yahoo_23);
        else if (weatherId == 21)
            return context.getString(R.string.wi_yahoo_21);
        else return context.getString(R.string.wi_yahoo_0);
    }

    public static String BuildOpenWeatherURL(String cityName, String type) {
        StringBuilder sb = new StringBuilder(Constants.OPENWEATHER_BASE_URL);
        sb.append(type);
        sb.append("?q=" + cityName);
        sb.append("&mode=json");
        sb.append("&units=metric");
        sb.append("&appid=" + Constants.OPENWEATHER_API_KEY);

        return sb.toString();
    }

    public static String BuildYahooURL(String cityName) {
        StringBuilder sb = new StringBuilder(Constants.YAHOO_BASE_URL);
        String yql = String.format("select * from weather.forecast where woeid in (select woeid from geo.places(1) where text=\"%s\") and u='c'", cityName);
        try {
            sb.append("?q=" + URLEncoder.encode(yql, "utf8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        sb.append("&format=json");

        return sb.toString();
    }
}
