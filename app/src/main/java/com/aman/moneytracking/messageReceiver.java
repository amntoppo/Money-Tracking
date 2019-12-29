package com.aman.moneytracking;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

public class messageReceiver extends BroadcastReceiver {

    final SmsManager sms = SmsManager.getDefault();
    int value ;

    @Override
    public void onReceive(Context context, Intent intent) {

        Log.e("broadcast", "message received");
        Toast.makeText(context, "message", Toast.LENGTH_LONG).show();

        final Bundle bundle = intent.getExtras();
        try {
            if(bundle!=null) {
                final Object[] pduPbject = (Object[]) bundle.get("pdus");
                assert pduPbject != null;
                for(int i = 0; i<pduPbject.length; i++) {
                    SmsMessage currentMessage = SmsMessage.createFromPdu((byte[]) pduPbject[i]);
                    String message = currentMessage.getDisplayMessageBody();
                    String target = "debited";
                    int index = message.indexOf(target);
                    int subIndex = index + target.length();
                    String finalMessage = message.substring(subIndex, subIndex+15);
                    String intvalue = finalMessage.replaceAll("[^0-9]", "");
                    value = Integer.parseInt(intvalue);

                    Log.e("SmsReceiver", "senderNum: " + currentMessage.getDisplayOriginatingAddress() +"; message: " + intvalue);
                }

            }
        }
        catch (Exception e){

        }
        MyApplication myApplication = (MyApplication) context.getApplicationContext();
        myApplication.triggerNotification(DetailsActivity.class, context.getString(R.string.channel_id), "Money Tracking",
                "Current balance:", "Balance remaining:",  NotificationCompat.PRIORITY_DEFAULT, context.getResources().getInteger(R.integer.notification_id),
                PendingIntent.FLAG_UPDATE_CURRENT, value);


    }


}


