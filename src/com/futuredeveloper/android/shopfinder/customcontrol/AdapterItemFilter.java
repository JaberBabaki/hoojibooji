package com.futuredeveloper.android.shopfinder.customcontrol;

import java.util.ArrayList;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import com.futuredeveloper.android.shopfinder.G;
import com.futuredeveloper.android.shopfinder.R;
import com.futuredeveloper.android.shopfinder.internet.StrucFilter;


public class AdapterItemFilter extends ArrayAdapter<StrucFilter> {

    public AdapterItemFilter(ArrayList<StrucFilter> array) {
        super(G.context, R.layout.item_item_filter, array);

    }


    private static class ViewHolder {

        NewControlTextView txtNameFilter;
        ImageView          imgOk;


        public ViewHolder(View view) {

            txtNameFilter = (NewControlTextView) view.findViewById(R.id.txt_name_filter);
            imgOk = (ImageView) view.findViewById(R.id.img_ok_filter);

        }


        public void fill(ArrayAdapter<StrucFilter> adapter, final StrucFilter item, int position) {
            //Toast.makeText(G.context, "jj" + item.name, Toast.LENGTH_LONG).show();
            Log.i("DDD", "" + item.header);
            txtNameFilter.setText(" " + item.header);
            if (item.select == 1) {
                imgOk.setVisibility(View.VISIBLE);
            } else {
                imgOk.setVisibility(View.GONE);
            }

        }
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        StrucFilter item = getItem(position);
        if (convertView == null) {
            convertView = G.inflater.inflate(R.layout.item_item_filter, parent, false);
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