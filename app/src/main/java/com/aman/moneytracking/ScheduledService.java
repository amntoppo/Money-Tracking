package com.aman.moneytracking;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.preference.Preference;
import android.preference.PreferenceManager;

import java.util.Date;
import java.util.HashMap;

public class ScheduledService extends Service {

    SharedPreferences sharedPreferences;

    public ScheduledService() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        setSharedPreferences();
        return super.onStartCommand(intent, flags, startId);
    }

    public void setSharedPreferences() {
        Context context;
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
