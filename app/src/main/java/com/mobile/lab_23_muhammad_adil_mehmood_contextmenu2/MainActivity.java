package com.mobile.lab_23_muhammad_adil_mehmood_contextmenu2;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private String friends[] = {
            "Muhammad Adil Mehmood",
            "Zulkifil Rehman",
            "Adnan Khurshid",
            "Saif Ur Rehman",
            "Ayesha Latif",
    };

    private String contacts[] = {
            "+923087896991",
            "+923087896991",
            "+923087896991",
            "+923087896991",
            "+923087896991",
    };

    private Integer images[] = {
            R.drawable.adil,
            R.drawable.zulkifil,
            R.drawable.adnan,
            R.drawable.saif,
            R.drawable.ayesha,
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listViewFriends);


        FriendsListAdapter friendsListAdapter = new FriendsListAdapter(this, friends, images);
        listView.setAdapter(friendsListAdapter);


        registerForContextMenu(listView);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.friend_menu, menu);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.callFriend:
                AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
                int index = info.position;
                callFriend(index);
                Toast.makeText(getApplicationContext(), friends[index] + "", Toast.LENGTH_LONG).show();
                return true;
            case R.id.messageFriend:
                Toast.makeText(getApplicationContext(), "Message friend", Toast.LENGTH_LONG).show();
                startActivity(new Intent(MainActivity.this, SendMessage.class));
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    void callFriend(int position) {
        String number = contacts[position];

        if(checkSelfPermission(Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            if(shouldShowRequestPermissionRationale(Manifest.permission.CALL_PHONE)) {
                showAlertDialog("Permission Request Justification", "App requires this permission to place calls");
            } else {
                requestPermissions(new String[]{"android.permission.CALL_PHONE"}, 99);
            }
        }else {
            makeCall(number);
        }
    }

    void makeCall(String number) {
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:"+number));
        startActivity(callIntent);
    }

    void messageFriend(int position) {

    }

    public void showAlertDialog(String title, String text) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        // Add the buttons
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // request permission when user presses the OK button of dialog
                requestPermissions(new String[]{"android.permission.CALL_PHONE"}, 99);
            }
        });

        // do nothing when user presses cancel button on dialog
        builder.setNegativeButton("Cancel",null); builder.setCancelable(true); builder.setTitle(title);
        builder.setMessage(text);

        // Set other dialog properties
        // you can set other dialog properties too here
        // Create the AlertDialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }

}