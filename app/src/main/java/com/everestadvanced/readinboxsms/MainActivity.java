package com.everestadvanced.readinboxsms;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Cursor cur = getContentResolver().query(
                Uri.parse("content://sms/inbox"),
                new String[] {
                        "count(_id)",
                },
                "read = 0",
                null,
                null
        );
        cur.moveToFirst();
        int unreadMessagesCount = cur.getInt(0);


        final ListView lViewSMS = (ListView) findViewById(R.id.listViewSMS);


        if(fetchInbox()!=null)
        {
            @SuppressWarnings("unchecked")
            ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, fetchInbox());
            lViewSMS.setAdapter(adapter);
        }
    }
    ArrayList fetchInbox (){

        final Uri SMS_INBOX = Uri.parse("content://sms/inbox");

        //Retrieves all SMS (if you want only unread SMS, put "read = 0" for the 3rd parameter)
        Cursor cursor = getContentResolver().query(SMS_INBOX, null, "read=0", null, null);
        ArrayList sms = new ArrayList();
        //Get all lines
        while (cursor.moveToNext()) {
            //Gets the SMS information
            String address = cursor.getString(cursor.getColumnIndex("address"));
            String person = cursor.getString(cursor.getColumnIndex("person"));
            String date = cursor.getString(cursor.getColumnIndex("date"));
            String protocol = cursor.getString(cursor.getColumnIndex("protocol"));
            String read = cursor.getString(cursor.getColumnIndex("read"));
            String status = cursor.getString(cursor.getColumnIndex("status"));
            String type = cursor.getString(cursor.getColumnIndex("type"));
            String subject = cursor.getString(cursor.getColumnIndex("subject"));
            String body = cursor.getString(cursor.getColumnIndex("body"));

            sms.add(address+"\n"+body);
            //Do what you want
        }
        return sms;
    }
}
