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
import android.widget.TextView;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.futuredeveloper.android.shopfinder.G;
import com.futuredeveloper.android.shopfinder.R;
import com.futuredeveloper.android.shopfinder.ShowProduct;
import com.futuredeveloper.android.shopfinder.StructKala;


public class AdapterCommodityList extends ArrayAdapter<StructKala> {

    public AdapterCommodityList(ArrayList<StructKala> array) {
        super(G.context, R.layout.item_kala, array);

    }


    private static class ViewHolder {

        NewControlTextView txtNameStore;
        NewControlTextView txtLocation;
        NewControlTextView txtCategory;
        NewControlTextView txtRate;
        rateBar            rateBar;
        TextView           txtPricee;

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
            //rateBar = (rateBar) view.findViewById(R.id.rateBar1);
            txtPricee = (TextView) view.findViewById(R.id.txt_price_list);

            thumbNail = (NetworkImageView) view.findViewById(R.id.img_store);

            layMain = (LinearLayout) view.findViewById(R.id.lay_main_item_product);
        }


        public void fill(ArrayAdapter<StructKala> adapter, final StructKala item, int position) {
            txtNameStore.setText(" " + item.name);
            txtLocation.setText(" " + item.location);
            if (item.state == true) {

                txtCategory.setText("وضعیت : موجود");
            } else {
                txtCategory.setText("وضعیت : موجود نیست ");
            }
            //Log.i("LLL", "" + txtRate);
            // Toast.makext(G.context, "jj"txtPricee.price, Toast.LGTH_LONG).show();
            txtPricee.setText(" " + item.price);

            // ate.setText(" " + item.price);

            // Bar.rate = item.rate;
            //rateBar.setRate();

            String link = item.picture.replaceAll("\\\\", "/");
            Log.i("LPL", "" + link);
            imageLoader.get("http://" + link, ImageLoader.getImageListener(thumbNail, R.drawable.loading, R.drawable.no_product_pic));
            thumbNail.setImageUrl("http://" + link, imageLoader);

            layMain.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    /* if (G.currentActivity.getTitle() != "ShopFinderOrginalActivity") {
                         G.currentActivity.finish();
                     }*/
                    Log.i("LPL", "" + "AC  " + G.currentActivity);
                    Intent intentm = new Intent(G.currentActivity, ShowProduct.class);
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

        StructKala item = getItem(position);
        if (convertView == null) {
            convertView = G.inflater.inflate(R.layout.item_kala, parent, false);
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