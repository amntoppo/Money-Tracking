package com.aman.moneytracking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import androidx.preference.PreferenceManager;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;

public class MainActivity extends AppCompatActivity {
    final int permissionCode = 1980;

    @Override
    protected void onRestart() {
        super.onRestart();
        switchNotification();
    }

    Toolbar toolbar;
    Switch aSwitch;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbarManagement();
        permissionCheck();
        //triggerNotification();
        sharedPreferenceSetup();
        switchNotification();



    }

    private void switchNotification() {
        aSwitch = findViewById(R.id.notification_switch);
        aSwitch.setChecked(sharedPreferences.getBoolean("pref_notification", true));
        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("pref_notification", isChecked);
                editor.apply();
                if(isChecked) {
                    triggerNotification();
                }

            }
        });

    }


    private void sharedPreferenceSetup() {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        //sharedPreferences.registerOnSharedPreferenceChangeListener();
        if( sharedPreferences.getBoolean("pref_notification", true)) {
            String balance = sharedPreferences.getString("pref_balance", "0");
            Log.e("pref", balance);

            triggerNotification();
        }
    }



    private void permissionCheck() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS) != PackageManager.PERMISSION_GRANTED) {
            // requestPermissions(new String[]{Manifest.permission.READ_SMS}, MY_PERMISSIONS_REQUEST_READ_CONTACTS);
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECEIVE_SMS}, permissionCode);
            return;
        }
    }

    public void triggerNotification() {
        //trigger Notification from MyApplication
        ((MyApplication) getApplication()).triggerNotification(DetailsActivity.class, getString(R.string.channel_id), "Money Tracking",
                "Current balance:", "Balance remaining:",  NotificationCompat.PRIORITY_DEFAULT, getResources().getInteger(R.integer.notification_id),
                PendingIntent.FLAG_UPDATE_CURRENT, 0);

    }

    private void toolbarManagement() {
        toolbar = findViewById(R.id.my_toolbar);

        setSupportActionBar(toolbar);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuSettings:
                Log.e("toolbar:", "settings");
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                return true;



            case R.id.menuLogout:
                return true;


                default:
                    return super.onOptionsItemSelected(item);


        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permission, int[] grantResult) {
        switch (requestCode) {
            case permissionCode: {
                if (grantResult[0] == PackageManager.PERMISSION_GRANTED) {
                    //GRANTED
                    Log.e("permission", "granted");
                } else {
                    //not granted
                }
            }
        }
    }

    public void test(View view) {
        startActivity(new Intent(this, SettingsActivity.class));
    }
}
