package com.aman.moneytracking;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class AppNotificationManager {
    private Context context;
    private static AppNotificationManager instance;
    private NotificationManagerCompat notificationManagerCompat;
    private NotificationManager notificationManager;

    private AppNotificationManager(Context context) {
        //Log.e("notify", "constructor");
        this.context = context;
        notificationManagerCompat = NotificationManagerCompat.from(context);
        notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
    }

    public static AppNotificationManager getInstance(Context context) {
        if (instance == null) {
            instance = new AppNotificationManager(context);
        }
        return instance;
    }

    public void registerNotificationChannel(String channelID, String channelName, String channelDesc) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(channelID, channelName, NotificationManager.IMPORTANCE_DEFAULT);
            notificationChannel.setDescription(channelDesc);
            notificationChannel.setShowBadge(true);
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(notificationChannel);
        }
    }

    public void triggerNotification(Class targetActivity, String channelID, String title, String text, String bigText, int priority, int notificationID, int pendingIntentFlag) {
        Intent intent = new Intent(context, DetailsActivity.class);
        intent.putExtra("title", title);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, pendingIntentFlag);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, channelID)
                .setContentTitle(title)
                .setContentText(text)
                .setSmallIcon(R.drawable.small_icon)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.large_icon))
                .setStyle(new NotificationCompat.BigTextStyle().bigText(bigText))
                .setPriority(priority)
                .setContentIntent(pendingIntent)
                .setChannelId(channelID)
                .setOngoing(true);

        notificationManagerCompat.notify(notificationID, builder.build());

    }

    public void updateNotification(Class targetActivity, String channelID, String title, String text, String bigtext, int priority, int notificationID) {
        Intent intent = new Intent(context, DetailsActivity.class);
        intent.putExtra("title", title);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, channelID)
                .setContentTitle(title)
                .setContentText(text)
                .setSmallIcon(R.drawable.small_icon)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.large_icon))
                .setStyle(new NotificationCompat.BigTextStyle().bigText(bigtext))
                .setPriority(priority)
                .setContentIntent(pendingIntent)
                .setChannelId(channelID)
                .setOngoing(true);

        notificationManagerCompat.notify(notificationID, builder.build());
    }

    public void cancelNotification(int notificationID) {
        notificationManager.cancel(notificationID);
    }


}

