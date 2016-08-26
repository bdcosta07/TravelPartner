package com.kichukkhon.android.travelpartner.Util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Ratul on 8/26/2016.
 */
public class Preference {
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private Context context;

    private static final String PREFERENCE_NAME = "travel_partner_pref";

    //when the alarm is set key for shared pref
    public static final String ALARM_TIME_IN_MILLISECOND_KEY = "alarm_time_in_millis_key";

    public Preference(Context context) {
        this.context = context;

        sharedPreferences = context.getSharedPreferences(PREFERENCE_NAME, context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void saveAlarmTime(long timeInMillis) {
        editor.putLong(ALARM_TIME_IN_MILLISECOND_KEY, timeInMillis);

        editor.commit();
    }

    public long getAlarmTime() {
        long alarmTime = sharedPreferences.getLong(ALARM_TIME_IN_MILLISECOND_KEY, 0l);
        return alarmTime;
    }
}
