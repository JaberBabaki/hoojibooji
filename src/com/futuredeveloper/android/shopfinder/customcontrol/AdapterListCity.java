package com.futuredeveloper.android.shopfinder.customcontrol;

import java.util.ArrayList;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import com.futuredeveloper.android.database.StrucOstan;
import com.futuredeveloper.android.shopfinder.G;
import com.futuredeveloper.android.shopfinder.R;


public class AdapterListCity extends ArrayAdapter<StrucOstan> {

    public AdapterListCity(ArrayList<StrucOstan> array) {
        super(G.context, R.layout.item_ostan, array);

    }


    private static class ViewHolder {

        NewControlTextView txtNameStore;


        public ViewHolder(View view) {

            txtNameStore = (NewControlTextView) view.findViewById(R.id.txt_name_ostan);

        }


        public void fill(ArrayAdapter<StrucOstan> adapter, final StrucOstan item, int position) {
            //Toast.makeText(G.context, "jj" + item.name, Toast.LENGTH_LONG).show();
            txtNameStore.setText(" " + item.name);

            txtNameStore.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    G.btnCity.setText(txtNameStore.getText().toString());
                    G.btnCity.setTag(item.id);
                    G.dialogCity.dismiss();

                }
            });

        }
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        StrucOstan item = getItem(position);
        if (convertView == null) {
            convertView = G.inflater.inflate(R.layout.item_ostan, parent, false);
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