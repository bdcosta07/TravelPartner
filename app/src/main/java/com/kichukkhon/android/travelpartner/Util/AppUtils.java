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
import java.util.Date;

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

    public static int getArtResourceForYahooWeatherCondition(int weatherId){
        /*if (weatherId==3)
            return R.drawable.severelthunderstoms;
        if (weatherId==4)
            return R.drawable.thunderstorms;
        else if(weatherId==9)
            return R.drawable.drizzle;
        else if (weatherId==8)
            return R.drawable.freezingdrizzle;
        else if (weatherId==11)
            return R.drawable.showers;
        else if ((weatherId==27)||(weatherId==28))
            return R.drawable.mostlycloudy;
        else if (weatherId==26)
            return R.drawable.cloudy;
        else */if (weatherId==32)
            return R.drawable.sunny;
        /*else if (weatherId==36)
            return R.drawable.hot;
        else if (weatherId==31)
            return R.drawable.clear;
        else if (weatherId==37)
            return R.drawable.isolatedthunderstorms;
        else if ((weatherId==38)||(weatherId==39))
            return R.drawable.scatteredthunderstorms;
        else if (weatherId==44)
            return R.drawable.partlycloudy;
        else if (weatherId==47)
            return R.drawable.isolatedthundersowers;
        else if (weatherId==35)
            return R.drawable.mixedrainandhail;
        else if ((weatherId==33)||(weatherId==34))
            return R.drawable.fair;
        else if ((weatherId==29)||(weatherId==30))
            return R.drawable.partlycloudy;
        else if ((weatherId==24))
            return R.drawable.windy;
        else if ((weatherId==22))
            return R.drawable.smoke;
        else if ((weatherId==23))
            return R.drawable.blustery;
        else if ((weatherId==21))
            return R.drawable.haze;*/
        else return R.drawable.default_weather_icon;
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
