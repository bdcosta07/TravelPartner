package com.kichukkhon.android.travelpartner.BackgroundService;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;

import com.kichukkhon.android.travelpartner.Activity.AlarmActivity;
import com.kichukkhon.android.travelpartner.BroadcastReceiver.AlarmReceiver;
import com.kichukkhon.android.travelpartner.R;

/**
 * This {@code IntentService} does the app's actual work.
 * {@code SampleAlarmReceiver} (a {@code WakefulBroadcastReceiver}) holds a
 * partial wake lock for this service while the service does its work. When the
 * service is finished, it calls {@code completeWakefulIntent()} to release the
 * wake lock.
 */
public class AlarmService extends IntentService {
    public AlarmService() {
        super("travelPartnerAlarmService");
    }

    public static final String TAG = "Travel Partner";
    //An ID used to post the notification
    public static final int NOTIFICATION_ID = 1;
    private NotificationManager mNotificationManager;

    @Override
    protected void onHandleIntent(Intent intent) {
        sendNotification("alarm alarm alarm .....");

        // Release the wake lock provided by the BroadcastReceiver.
        AlarmReceiver.completeWakefulIntent(intent);
        // END_INCLUDE(service_onhandle)
    }

    //Post a notification for alarm
    private void sendNotification(String msg) {
        mNotificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);

        PendingIntent contentIntent = PendingIntent.getActivity(this, 192837, new Intent(this, AlarmActivity.class), 0);

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setContentTitle(getString(R.string.app_name))
                        .setContentText(msg)
                        .setColor(ContextCompat.getColor(this,R.color.colorPrimary))
                        .setSmallIcon(R.drawable.ic_alarm_black_24dp)
                        .setOngoing(true)
                        .setAutoCancel(true)
                        .setDefaults(NotificationCompat.DEFAULT_LIGHTS)
                        .setWhen(0)
                        .setCategory(NotificationCompat.CATEGORY_ALARM)
                        .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                        .setLocalOnly(true)
                        .setStyle(new NotificationCompat.BigTextStyle()
                                .bigText(msg));


        mBuilder.setContentIntent(contentIntent);
        mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
    }
}
