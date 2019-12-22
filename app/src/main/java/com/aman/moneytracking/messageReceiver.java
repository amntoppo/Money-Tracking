package com.aman.moneytracking;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

public class messageReceiver extends BroadcastReceiver {

    final SmsManager sms = SmsManager.getDefault();
    @Override
    public void onReceive(Context context, Intent intent) {

        Log.e("broadcast", "message received");
        Toast.makeText(context, "message", Toast.LENGTH_LONG).show();

        final Bundle bundle = intent.getExtras();
        try {
            if(bundle!=null) {
                final Object[] pduPbject = (Object[]) bundle.get("pdus");
                for(int i = 0; i<pduPbject.length; i++) {
                    SmsMessage currentMessage = SmsMessage.createFromPdu((byte[]) pduPbject[i]);
                    String message = currentMessage.getDisplayMessageBody();
                    String target = "debited";
                    int index = message.indexOf(target);
                    int subIndex = index + target.length();
                    String finalMessage = message.substring(subIndex, subIndex+15);
                    String intvalue = finalMessage.replaceAll("[^0-9]", "");

                    Log.e("SmsReceiver", "senderNum: " + currentMessage.getDisplayOriginatingAddress() +"; message: " + intvalue);
                }
            }
        }
        catch (Exception e){

        }

    }
}


