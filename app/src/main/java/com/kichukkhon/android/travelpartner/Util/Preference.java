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

    private static final String SESSION_ID_KEY = "current_session_id";
    private static final String TRACK_ALLOWED_KEY = "track_allowed";

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

    public void saveCurrentSession(int sessionId) {
        editor.putInt(SESSION_ID_KEY, sessionId);

        editor.commit();
    }

    public int getCurrentSession() {
        int sessionId = sharedPreferences.getInt(SESSION_ID_KEY, 0);
        return sessionId;
    }

    public void saveTrackAllowed(boolean trackAllowed) {
        editor.putBoolean(TRACK_ALLOWED_KEY, trackAllowed);
        editor.commit();
    }

    public boolean isTrackAllowed() {
        boolean trackAllowed = sharedPreferences.getBoolean(TRACK_ALLOWED_KEY, false);
        return trackAllowed;
    }

    public void saveCurrentSelectedTourId(int tourId) {
        editor.putInt(Constants.CURRENT_TOUR_ID_KEY, tourId);
        editor.commit();
    }

    public int getCurrentlySelectedTourId() {
        int currentlySelectedTourId = sharedPreferences.getInt(Constants.CURRENT_TOUR_ID_KEY, 1);
        return currentlySelectedTourId;
    }
}
