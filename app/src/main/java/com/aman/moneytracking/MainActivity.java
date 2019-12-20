package com.aman.moneytracking;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {
    final int  permissionCode = 1980;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS) != PackageManager.PERMISSION_GRANTED) {
           // requestPermissions(new String[]{Manifest.permission.READ_SMS}, MY_PERMISSIONS_REQUEST_READ_CONTACTS);
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.RECEIVE_SMS}, permissionCode);


            return;
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permission, int[] grantResult) {
        switch (requestCode) {
            case permissionCode: {
                if(grantResult[0] == PackageManager.PERMISSION_GRANTED) {
                    //GRANTED
                    Log.e("permission", "granted");
                }

                else {
                    //not granted
                }
            }
        }
    }
}
