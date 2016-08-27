package com.kichukkhon.android.travelpartner.BackgroundService;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by Ratul on 8/28/2016.
 */
public class LocationTrackerService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
