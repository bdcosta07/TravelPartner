package com.kichukkhon.android.travelpartner.BroadcastReceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.kichukkhon.android.travelpartner.BackgroundService.AlarmService;

/**
 * Created by Ratul on 8/26/2016.
 */
public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String state = intent.getExtras().getString("extra");
        Log.e("MyActivity", "In the receiver with " + state);

        String richard_id = intent.getExtras().getString("quote id");
        Log.e("Richard quote is" , richard_id);

        Intent serviceIntent = new Intent(context,AlarmService.class);
        serviceIntent.putExtra("extra", state);
        serviceIntent.putExtra("quote id", richard_id);

        context.startService(serviceIntent);
    }
}
