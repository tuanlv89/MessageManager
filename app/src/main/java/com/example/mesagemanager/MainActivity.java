package com.example.mesagemanager;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Telephony;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final int PERMISSIONS_REQUEST_READ_MESSAGE = 1412;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        showMessages();


    }

    private void showMessages() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_SMS}, PERMISSIONS_REQUEST_READ_MESSAGE);
            //After this point you wait for callback in onRequestPermissionsResult(int, String[], int[]) overriden method
        }
        // render list
//        ContactManager contactManager = new ContactManager(this);
//        ContectAdapter contectAdapter = new ContectAdapter(MainActivity.this, (ArrayList<ContactModel>) contactManager.getListContacts());
//        list_contact.setAdapter(contectAdapter);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        if (requestCode == PERMISSIONS_REQUEST_READ_MESSAGE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission is granted
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    final String myPackageName = getPackageName();
                    if (Telephony.Sms.getDefaultSmsPackage(this).equals(myPackageName)) {

                        List<Sms> lst = getAllSms();
                    }
                }
                List<Sms> smsList = new ArrayList<Sms>();

                Uri uri = Uri.parse("content://sms/inbox");
                Cursor c= getContentResolver().query(uri, null, null ,null,null);
                startManagingCursor(c);


                // Read the sms data and store it in the list
                if(c.moveToFirst()) {
                    for(int i=0; i < c.getCount(); i++) {
                        Sms sms = new Sms();
                        sms.setId(c.getString(c.getColumnIndexOrThrow("_id")));
                        sms.setMsg(c.getString(c.getColumnIndexOrThrow("body")).toString());
                        sms.setAddress(c.getString(c.getColumnIndexOrThrow("address")).toString());
                        sms.setReadState(c.getString(c.getColumnIndex("read")));
                        sms.setTime(c.getString(c.getColumnIndexOrThrow("date")));
                        if (c.getString(c.getColumnIndexOrThrow("type")).contains("1")) {
                            sms.setFolderName("inbox");
                        } else {
                            sms.setFolderName("sent");
                        }
                        smsList.add(sms);
                        c.moveToNext();
                    }
                }
                c.close();
                for(int i = 0; i< 10; i++) {
                    Log.d("AAA", smsList.get(i).toString());
                }
            } else {
                Toast.makeText(this, "Until you grant the permission, we canot display the names", Toast.LENGTH_SHORT).show();
            }
        }
    }


    public List<Sms> getAllSms() {
        List<Sms> lstSms = new ArrayList<Sms>();
        Sms objSms = new Sms();
        Uri message = Uri.parse("content://sms/");
        ContentResolver cr = this.getContentResolver();

        Cursor c = cr.query(message, null, null, null, null);
        this.startManagingCursor(c);
        int totalSMS = c.getCount();

        if (c.moveToFirst()) {
            for (int i = 0; i < totalSMS; i++) {

                objSms = new Sms();
                objSms.setId(c.getString(c.getColumnIndexOrThrow("_id")));
                objSms.setAddress(c.getString(c
                        .getColumnIndexOrThrow("address")));
                objSms.setMsg(c.getString(c.getColumnIndexOrThrow("body")));
                objSms.setReadState(c.getString(c.getColumnIndex("read")));
                objSms.setTime(c.getString(c.getColumnIndexOrThrow("date")));
                if (c.getString(c.getColumnIndexOrThrow("type")).contains("1")) {
                    objSms.setFolderName("inbox");
                } else {
                    objSms.setFolderName("sent");
                }

                lstSms.add(objSms);
                c.moveToNext();
            }
        }
        // else {
        // throw new RuntimeException("You have no SMS");
        // }
        c.close();

        return lstSms;
    }
}
