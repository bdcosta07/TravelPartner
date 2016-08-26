package com.kichukkhon.android.travelpartner.Activity;

import android.annotation.TargetApi;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import com.kichukkhon.android.travelpartner.BroadcastReceiver.AlarmReceiver;
import com.kichukkhon.android.travelpartner.R;
import com.kichukkhon.android.travelpartner.Util.Preference;

public class AlarmActivity extends BaseDrawerActivity {

    private TimePicker alarmTimePicker;
    private TextView alarmTextView;
    private AlarmReceiver alarmReceiver;
    private Preference preference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);

        InitCommonUIElements();

        alarmReceiver = new AlarmReceiver();
        preference = new Preference(this);

        alarmTextView = (TextView) findViewById(R.id.alarmText);

        final Calendar calendar = Calendar.getInstance();
        alarmTimePicker = (TimePicker) findViewById(R.id.alarmTimePicker);

        Button start_alarm = (Button) findViewById(R.id.start_alarm);
        start_alarm.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {

                calendar.set(java.util.Calendar.HOUR_OF_DAY, alarmTimePicker.getHour());
                calendar.set(java.util.Calendar.MINUTE, alarmTimePicker.getMinute());

                final int hour = alarmTimePicker.getHour();
                final int minute = alarmTimePicker.getMinute();
                ;

                String minute_string = String.valueOf(minute);
                String hour_string = String.valueOf(hour);

                if (minute < 10) {
                    minute_string = "0" + String.valueOf(minute);
                }

                if (hour > 12) {
                    hour_string = String.valueOf(hour - 12);
                }

                long alarmTime=calendar.getTimeInMillis()-21600000;

                alarmReceiver.setAlarm(AlarmActivity.this, alarmTime);
                preference.saveAlarmTime(alarmTime);

                setAlarmText("Alarm set to " + hour_string + ":" + minute_string);
            }
        });

        Button stop_alarm = (Button) findViewById(R.id.stop_alarm);
        stop_alarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alarmReceiver.cancelAlarm(AlarmActivity.this);
            }
        });
    }

    public void setAlarmText(String alarmText) {
        alarmTextView.setText(alarmText);
    }
}
