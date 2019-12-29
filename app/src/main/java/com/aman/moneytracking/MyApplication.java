package com.aman.moneytracking;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

public class MyApplication extends Application {

    AppNotificationManager appNotificationManager;
    SharedPreferences sharedPreferences;
    int Currentbalance;

    @Override
    public void onCreate() {
        super.onCreate();;
        appNotificationManager = appNotificationManager.getInstance(this);
        appNotificationManager.registerNotificationChannel(getString(R.string.channel_id), getString(R.string.channel_name), getString(R.string.channel_description));

    }

    public void triggerNotification(Class targetActivity, String channelID, String title, String text, String bigText, int priority, int notificationID, int pendingIntentFlag, int debit) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        Currentbalance = Integer.parseInt(sharedPreferences.getString("pref_balance", "0"));
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("pref_balance", String.valueOf(Currentbalance-debit));
        editor.apply();

        text = text + String.valueOf(Currentbalance-debit);
        Log.e("balance", text);
        bigText = bigText + String.valueOf(Currentbalance-debit);
        appNotificationManager.triggerNotification(targetActivity, channelID, title, text, bigText, priority, notificationID, pendingIntentFlag);
    }

    public void cancelNotification(int notificationID) {
        appNotificationManager.cancelNotification(notificationID);
    }
}
