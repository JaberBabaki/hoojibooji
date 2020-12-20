package com.futuredeveloper.android.shopfinder.customcontrol;

import java.util.ArrayList;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.futuredeveloper.android.shopfinder.G;
import com.futuredeveloper.android.shopfinder.R;
import com.futuredeveloper.android.shopfinder.internet.StrucSubCat1List;


public class AdapterLitCat1 extends ArrayAdapter<StrucSubCat1List> {

    public AdapterLitCat1(ArrayList<StrucSubCat1List> array) {
        super(G.context, R.layout.item_filter, array);

    }


    private static class ViewHolder {

        NewControlTextView txtNameStore;
        ImageView          imgOkFilter;

        LinearLayout       layOkFilter;


        public ViewHolder(View view) {

            txtNameStore = (NewControlTextView) view.findViewById(R.id.txt_name_filter);
            imgOkFilter = (ImageView) view.findViewById(R.id.img_ok_filter);
            layOkFilter = (LinearLayout) view.findViewById(R.id.lay_ok_filter);

        }


        public void fill(ArrayAdapter<StrucSubCat1List> adapter, final StrucSubCat1List item, int position) {
            //Toast.makeText(G.context, "jj" + item.name, Toast.LENGTH_LONG).show();
            txtNameStore.setText(" " + item.name);
            /* layOkFilter.setOnClickListener(new OnClickListener() {

                 @Override
                 public void onClick(View arg0) {
                     G.idSearch = item.id;
                     // imgOkFilter.setImageResource(R.drawable.pppp);
                     // imgWeak.setImageResource(R.drawable.ppp);
                 }
             });*/

        }
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        StrucSubCat1List item = getItem(position);
        if (convertView == null) {
            convertView = G.inflater.inflate(R.layout.item_filter, parent, false);

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