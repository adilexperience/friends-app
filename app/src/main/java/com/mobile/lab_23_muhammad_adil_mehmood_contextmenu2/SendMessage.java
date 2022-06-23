package com.mobile.lab_23_muhammad_adil_mehmood_contextmenu2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

public class SendMessage extends AppCompatActivity {

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
        });

    }

    void sendSMS(String number, String message) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            //Getting intent and PendingIntent instance
            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
            PendingIntent pi = PendingIntent.getActivity(getApplicationContext(), 0, intent,0);

            //Get the SmsManager instance and call the sendTextMessage method to send message
            SmsManager sms = SmsManager.getDefault();
            sms.sendTextMessage(number, null, message, pi,null);
        }else {
            Toast.makeText(getApplicationContext(), "send text", Toast.LENGTH_LONG).show();
        }
    }
}