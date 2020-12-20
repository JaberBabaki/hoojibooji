package com.futuredeveloper.android.shopfinder.customcontrol;

import java.util.ArrayList;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import com.futuredeveloper.android.shopfinder.G;
import com.futuredeveloper.android.shopfinder.R;
import com.futuredeveloper.android.shopfinder.internet.StrucDetialsProduct;


public class AdapterDetilsProduct extends ArrayAdapter<StrucDetialsProduct> {

    public AdapterDetilsProduct(ArrayList<StrucDetialsProduct> array) {
        super(G.context, R.layout.item_detils_product, array);

    }


    private static class ViewHolder {

        NewControlTextView txtHeader;
        NewControlTextView txtValue;


        public ViewHolder(View view) {

            txtHeader = (NewControlTextView) view.findViewById(R.id.txt_header_detils);
            txtValue = (NewControlTextView) view.findViewById(R.id.txt_value_detils);

        }


        public void fill(ArrayAdapter<StrucDetialsProduct> adapter, final StrucDetialsProduct item, int position) {
            //Toast.makeText(G.context, "jj" + item.name, Toast.LENGTH_LONG).show();
            Log.i("DDD", "" + item.header);
            if (item.header != null && item.header != "") {
                txtHeader.setText(item.header);
            }
            if (item.value != null && item.value != "") {
                txtValue.setText(item.value);
            }

        }
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        StrucDetialsProduct item = getItem(position);
        if (convertView == null) {
            convertView = G.inflater.inflate(R.layout.item_detils_product, parent, false);
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