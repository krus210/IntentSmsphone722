package com.example.intentsmsphone722;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText editPhone;
    EditText editMessage;
    private static final int MY_PERMISSIONS_REQUEST_PHONE = 10;
    private static final int MY_PERMISSIONS_REQUEST_SMS = 11;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
    }

    private void initViews() {
        editPhone = findViewById(R.id.editPhone);
        editMessage = findViewById(R.id.editMessage);
        Button buttonCall = findViewById(R.id.buttonCall);
        Button buttonSms = findViewById(R.id.buttonSms);

        buttonCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(MainActivity.this,
                        Manifest.permission.CALL_PHONE) !=
                        PackageManager.PERMISSION_GRANTED){

                    ActivityCompat.requestPermissions(MainActivity.this,
                            new String[]{Manifest.permission.CALL_PHONE},
                            MY_PERMISSIONS_REQUEST_PHONE);
                } else {
                    clickCall();
                }
            }
        });

        buttonSms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(MainActivity.this,
                        Manifest.permission.SEND_SMS) !=
                        PackageManager.PERMISSION_GRANTED){

                    ActivityCompat.requestPermissions(MainActivity.this,
                            new String[]{Manifest.permission.SEND_SMS},
                            MY_PERMISSIONS_REQUEST_SMS);
                } else {
                    clickSms();
                }
            }
        });
    }

    @SuppressLint("MissingPermission")
    private void clickCall() {
        String phoneNumber = editPhone.getText().toString();
        Uri uri = Uri.parse("tel:" + phoneNumber);
        Intent intent = new Intent(Intent.ACTION_CALL, uri);
        startActivity(intent);
    }

    @SuppressLint("MissingPermission")
    private void clickSms() {
        String phoneNumber = editPhone.getText().toString();
        String message = editMessage.getText().toString();
        SmsManager smgr = SmsManager.getDefault();
        smgr.sendTextMessage(phoneNumber,null,message,null,null);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_PHONE: {
                if (grantResults.length > 0 && grantResults[0] ==
                        PackageManager.PERMISSION_GRANTED) {
                    clickCall();
                } else {
                    toastNotGranted();
                }
            }
            case MY_PERMISSIONS_REQUEST_SMS: {
                if (grantResults.length > 0 && grantResults[0] ==
                        PackageManager.PERMISSION_GRANTED) {
                    clickSms();
                } else {
                    toastNotGranted();
                }
            }
        }
    }

    private void toastNotGranted() {
        Toast.makeText(this,
                getString(R.string.permission_not_granted),
                Toast.LENGTH_SHORT).show();
    }
}
