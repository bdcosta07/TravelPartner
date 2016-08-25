package com.kichukkhon.android.travelpartner.Activity;

import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import com.kichukkhon.android.travelpartner.BroadcastReceiver.AlarmReceiver;
import com.kichukkhon.android.travelpartner.R;

public class AlarmActivity extends BaseDrawerActivity{

    AlarmManager alarmManager;
    private PendingIntent pendingIntent;

    private TimePicker alarmTimePicker;
    private TextView alarmTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);

        InitCommonUIElements();

        alarmTextView=(TextView)findViewById(R.id.alarmText);

        final Intent myIntent=new Intent(this, AlarmReceiver.class);

        alarmManager=(AlarmManager)getSystemService(ALARM_SERVICE);

        final Calendar calendar=Calendar.getInstance();
        alarmTimePicker=(TimePicker)findViewById(R.id.alarmTimePicker);

        Button start_alarm= (Button) findViewById(R.id.start_alarm);
        start_alarm.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.M)

            @Override
            public void onClick(View v) {

                calendar.set(java.util.Calendar.HOUR_OF_DAY, alarmTimePicker.getHour());
                calendar.set(java.util.Calendar.MINUTE, alarmTimePicker.getMinute());

                final int hour = alarmTimePicker.getHour();
                final int minute = alarmTimePicker.getMinute();;

                String minute_string = String.valueOf(minute);
                String hour_string = String.valueOf(hour);

                if (minute < 10) {
                    minute_string = "0" + String.valueOf(minute);
                }

                if (hour > 12) {
                    hour_string = String.valueOf(hour - 12) ;
                }

                myIntent.putExtra("extra", "yes");
                pendingIntent = PendingIntent.getBroadcast(AlarmActivity.this, 0, myIntent, PendingIntent.FLAG_UPDATE_CURRENT);

                alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);

                setAlarmText("Alarm set to " + hour_string + ":" + minute_string);
            }

        });

        Button stop_alarm= (Button) findViewById(R.id.stop_alarm);
        stop_alarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                myIntent.putExtra("extra", "no");
                sendBroadcast(myIntent);

                alarmManager.cancel(pendingIntent);
                setAlarmText("Alarm canceled");

                //setAlarmText("ID is " + richard_quote);
            }
        });
    }

    public void setAlarmText(String alarmText) {
        alarmTextView.setText(alarmText);
    }
}
