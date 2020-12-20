package com.futuredeveloper.android.shopfinder;

import java.util.ArrayList;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.futuredeveloper.android.database.NotificationDb;
import com.futuredeveloper.android.database.StrucNotification;
import com.futuredeveloper.android.shopfinder.customcontrol.AdapterNotificationList;


public class ListNotification extends Enhance {

    ArrayList<StrucNotification> Data = new ArrayList<StrucNotification>();


    //String P5l;
    @Override
    protected void onResume() {
        super.onResume();
        G.currentActivity = this;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_notification_root);

        NotificationDb notific = new NotificationDb();
        Data = notific.getNotific();
        ListView listView = (ListView) findViewById(R.id.lst_notification);
        ArrayAdapter adapter = new AdapterNotificationList(Data);
        //Toast.makeText(G.context, Data.get(1).name, Toast.LENGTH_LONG).show();
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        listView.setTextFilterEnabled(true);

        menu();

    }

}
