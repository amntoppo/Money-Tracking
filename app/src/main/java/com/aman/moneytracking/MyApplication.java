package com.aman.moneytracking;

import android.app.Application;

public class MyApplication extends Application {

    AppNotificationManager appNotificationManager;

    @Override
    public void onCreate() {
        super.onCreate();
        appNotificationManager = appNotificationManager.getInstance(this);
        appNotificationManager.registerNotificationChannel(getString(R.string.channel_id), getString(R.string.channel_name), getString(R.string.channel_description));

    }

    public void triggerNotification(Class targetActivity,String channelID, String title, String text, String bigText, int priority, int notificationID, int pendingIntentFlag) {
        appNotificationManager.triggerNotification(targetActivity, channelID, title, text,bigText,priority, notificationID, pendingIntentFlag);
    }

    public void cancelNotification(int notificationID) {
        appNotificationManager.cancelNotification(notificationID);
    }
}
