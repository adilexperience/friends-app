package com.mobile.lab_23_muhammad_adil_mehmood_contextmenu2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

public class SendMessage extends AppCompatActivity {
    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS =0 ;

    private EditText etPhoneNumber, etMessage;
    private Button btnSendSMS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_message);

        etPhoneNumber = findViewById(R.id.etPhoneNumber);
        etMessage = findViewById(R.id.etMessage);
        btnSendSMS = findViewById(R.id.btnSendSMS);

        btnSendSMS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                processSMS();
            }
        });

    }

    void processSMS() {
        String phoneNumber = etPhoneNumber.getText().toString().trim();
        String message = etMessage.getText().toString().trim();
        if(phoneNumber.isEmpty() || phoneNumber.length() < 11) {
            Toast.makeText(getApplicationContext(), "Please enter valid phone number to proceed", Toast.LENGTH_LONG).show();
        }else if(message.isEmpty()){
            Toast.makeText(getApplicationContext(), "Please enter message to continue", Toast.LENGTH_LONG).show();
        }else {
            sendSMS(phoneNumber, message);
        }
    }

    void sendSMS(String number, String message) {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.SEND_SMS)) {
//                SmsManager smsManager = SmsManager.getDefault();
//                smsManager.sendTextMessage(number, null, message, null, null);
//                Toast.makeText(getApplicationContext(), "Message sent", Toast.LENGTH_LONG).show();

                Intent smsIntent = new Intent(Intent.ACTION_VIEW);
                smsIntent.setData(Uri.parse("smsto:"));
                smsIntent.setType("vnd.android-dir/mms-sms");
                smsIntent.putExtra("address"  , number);
                smsIntent.putExtra("sms_body"  , message);
                try {
                    startActivity(smsIntent);
                    finish();
                    Log.i("Finished sending SMS...", "");
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(SendMessage.this,
                            "SMS failed, please try again later.", Toast.LENGTH_SHORT).show();
                }
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.SEND_SMS},
                        MY_PERMISSIONS_REQUEST_SEND_SMS);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_SEND_SMS: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    processSMS();
                } else {
                    Toast.makeText(getApplicationContext(),
                            "SMS faild, please try again.", Toast.LENGTH_LONG).show();
                    return;
                }
            }
        }

    }

}