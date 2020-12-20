package com.futuredeveloper.android.shopfinder.customcontrol;

import java.util.ArrayList;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.futuredeveloper.android.shopfinder.G;
import com.futuredeveloper.android.shopfinder.R;
import com.futuredeveloper.android.shopfinder.ShowStore;
import com.futuredeveloper.android.shopfinder.internet.StrucStore;


public class AdapterStoreList extends ArrayAdapter<StrucStore> {

    public AdapterStoreList(ArrayList<StrucStore> array) {
        super(G.context, R.layout.item_store, array);

    }


    private static class ViewHolder {

        NewControlTextView txtNameStore;
        NewControlTextView txtLocation;
        NewControlTextView txtCategory;
        NewControlTextView txtRate;
        rateBar            rateBar;

        ImageView          imgStore;

        ImageLoader        imageLoader = G.getInstance().getImageLoader();
        NetworkImageView   thumbNail;

        LinearLayout       layMain;


        //ImageView          calNumber;
        //ImageView          favoratie;

        public ViewHolder(View view) {

            txtNameStore = (NewControlTextView) view.findViewById(R.id.txt_name_store);
            txtLocation = (NewControlTextView) view.findViewById(R.id.txt_location);
            txtCategory = (NewControlTextView) view.findViewById(R.id.txt_category);
            txtRate = (NewControlTextView) view.findViewById(R.id.txt_rate);
            //  rateBar = (rateBar) view.findViewById(R.id.rateBar1);

            //imgStore = (ImageView) view.findViewById(R.id.img_store);

            thumbNail = (NetworkImageView) view.findViewById(R.id.img_st);
            /*  addContact = (ImageView) view.findViewById(R.id.txt_address);
              calNumber = (ImageView) view.findViewById(R.id.txt_address);
              favoratie = (ImageView) view.findViewById(R.id.txt_address);*/
            layMain = (LinearLayout) view.findViewById(R.id.lay_item_store);

        }


        public void fill(ArrayAdapter<StrucStore> adapter, final StrucStore item, int position) {
            //Toast.makeText(G.context, "jj" + item.name, Toast.LENGTH_LONG).show();
            txtNameStore.setText(" " + item.name);
            txtLocation.setText(" " + item.location);
            txtCategory.setText(" " + item.phone);
            String link = item.picture.replaceAll("\\\\", "/");
            Log.i("LPL", "" + link);

            imageLoader.get("http://" + link, ImageLoader.getImageListener(thumbNail, R.drawable.loading, R.drawable.no_store_pic));
            thumbNail.setImageUrl("http://" + link, imageLoader);
            // txtRate.setText(" " + item.rate);
            // rateBar.rate = item.rate;
            //rateBar.setRate();

            //imgStore.setImageResource(img[1]);

            layMain.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    //  G.currentActivity.finish();
                    Log.i("LPL", "t " + item.id);
                    Intent intentm = new Intent(G.currentActivity, ShowStore.class);
                    // intentm.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | IntentCompat.FLAG_ACTIVITY_CLEAR_TASK);
                    intentm.putExtra("id", item.id);
                    G.currentActivity.startActivity(intentm);
                }
            });

        }
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        StrucStore item = getItem(position);
        if (convertView == null) {
            convertView = G.inflater.inflate(R.layout.item_store, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.fill(this, item, position);
        Animation animation = AnimationUtils.loadAnimation(G.context,
                android.R.anim.slide_in_left);
        convertView.startAnimation(animation);
        return convertView;
    }
}