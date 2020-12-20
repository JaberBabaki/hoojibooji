package com.futuredeveloper.android.shopfinder;

import java.util.ArrayList;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.futuredeveloper.android.database.NotificationDb;
import com.futuredeveloper.android.database.StrucNotification;


public class ShowNotification extends Enhance {

    ArrayList<StrucNotification> Data        = new ArrayList<StrucNotification>();
    int                          id;

    ImageLoader                  imageLoader = G.getInstance().getImageLoader();
    NetworkImageView             thumbNail;


    @Override
    protected void onResume() {
        super.onResume();
        G.currentActivity = this;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_notification_root);

        Bundle extras = getIntent().getExtras();
        id = 0;
        if (extras != null) {
            id = extras.getInt("id");
        }
        NotificationDb no = new NotificationDb();
        no.setId(id);
        Data = no.selectOne();

        TextView txtTitleShow = (TextView) findViewById(R.id.txt_title_show);
        TextView txtText = (TextView) findViewById(R.id.txtMainMain);
        TextView txtDate = (TextView) findViewById(R.id.txtDateMain);

        Button btnDelete = (Button) findViewById(R.id.btn_delete_noti);
        ConnectivityManager cm = (ConnectivityManager) G.currentActivity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        thumbNail = (NetworkImageView) findViewById(R.id.img_notific_do_show);
        if ( !Data.get(0).image.equals("n") && netInfo != null && netInfo.isConnectedOrConnecting()) {
            imageLoader.get(Data.get(0).image, ImageLoader.getImageListener(thumbNail, R.drawable.loading, R.drawable.default_produ));

            thumbNail.setImageUrl(Data.get(0).image, imageLoader);
        } else {
            thumbNail.setVisibility(View.GONE);
        }
        //Toast.makeText(G.context, Data.get(0).title, Toast.LENGTH_LONG).show();
        txtTitleShow.setText("" + Data.get(0).title);
        txtText.setText("" + Data.get(0).text);
        txtDate.setText("" + Data.get(0).date);
        txtText.setLineSpacing(1, (float) 1.25);

        btnDelete.setTypeface(G.font1);
        btnDelete.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                final Dialog dialog2 = new Dialog(G.currentActivity);
                dialog2.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog2.setContentView(R.layout.dialog_delete_noti);
                Button btnBack = (Button) dialog2.findViewById(R.id.btn_back);
                Button btnDeleteOk = (Button) dialog2.findViewById(R.id.btn_delete_ok);
                btnBack.setTypeface(G.font1);
                btnDeleteOk.setTypeface(G.font1);
                btnBack.setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View arg0) {
                        dialog2.dismiss();
                    }
                });
                btnDeleteOk.setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View arg0) {
                        NotificationDb no = new NotificationDb();
                        no.setId(id);
                        no.deleteOne();
                        dialog2.dismiss();
                        Intent intent = new Intent(G.currentActivity, ListNotification.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        finish();
                        G.currentActivity.startActivity(intent);
                    }
                });
                //dialog2.setCancelable(false);
                dialog2.show();
            }
        });
        menu();

    }
}
