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
import com.futuredeveloper.android.shopfinder.ShowProduct;
import com.futuredeveloper.android.shopfinder.StructKala;


public class AdapterCommodityTiles extends ArrayAdapter<StructKala> {

    public AdapterCommodityTiles(ArrayList<StructKala> array) {
        super(G.context, R.layout.item_tiles, array);

    }


    private static class ViewHolder {

        public NewControlTextView txtName;
        public NewControlTextView txtLocation;
        public NewControlTextView txtPrice;
        public NewControlTextView txtCategory;
        public ImageView          imgBaker;
        public LinearLayout       linName;
        public LinearLayout       layMain;
        public LinearLayout       linAll;
        ImageLoader               imageLoader = G.getInstance().getImageLoader();
        NetworkImageView          thumbNail;


        public ViewHolder(View view) {

            txtName = (NewControlTextView) view.findViewById(R.id.txt_name_tiles);
            txtPrice = (NewControlTextView) view.findViewById(R.id.txt_price_tiles);
            txtCategory = (NewControlTextView) view.findViewById(R.id.txt_category_tiles);
            linName = (LinearLayout) view.findViewById(R.id.lin_name);
            layMain = (LinearLayout) view.findViewById(R.id.lay_main_dis);

            thumbNail = (NetworkImageView) view.findViewById(R.id.img_baker);

            // txtLocation = (NewControlTextView) view.findViewById(R.id.txt_location_tiles);
            // imgBaker = (ImageView) view.findViewById(R.id.img_baker);
            // linAll = (LinearLayout) view.findViewById(R.id.lin_all);

        }


        public void fill(ArrayAdapter<StructKala> adapter, final StructKala item, int position) {
            txtName.setText(" " + item.name);
            txtCategory.setText(" " + item.category);
            txtPrice.setText(" " + item.price);
            layMain.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    G.currentActivity.finish();
                    Log.i("LPL", "" + "link");
                    Intent intentm = new Intent(G.currentActivity, ShowProduct.class);
                    // intentm.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | IntentCompat.FLAG_ACTIVITY_CLEAR_TASK);
                    intentm.putExtra("id", item.id);
                    G.currentActivity.startActivity(intentm);
                }
            });

            String link = item.picture.replaceAll("\\\\", "/");
            Log.i("LPL", "" + link);
            imageLoader.get("http://" + link, ImageLoader.getImageListener(thumbNail, R.drawable.loading, R.drawable.no_product_pic));
            thumbNail.setImageUrl("http://" + link, imageLoader);

            //txtLocation.setText(" " + item.location);
            // txtRate.setText(" " + item.rate);
            // rateBar.rate = item.rate;
            //rateBar.setRate();
            //imgBaker.setImageResource(continent[item.pic]);

        }
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        StructKala item = getItem(position);
        if (convertView == null) {
            convertView = G.inflater.inflate(R.layout.item_tiles, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.fill(this, item, position);
        Animation animation = AnimationUtils.loadAnimation(G.currentActivity,
                android.R.anim.slide_in_left);
        convertView.startAnimation(animation);
        return convertView;
    }
}