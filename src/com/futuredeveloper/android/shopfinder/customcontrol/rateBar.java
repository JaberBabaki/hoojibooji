package com.futuredeveloper.android.shopfinder.customcontrol;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.futuredeveloper.android.shopfinder.R;


public class rateBar extends LinearLayout {

    ImageView    img1;
    ImageView    img2;
    ImageView    img3;
    ImageView    img4;
    ImageView    img5;

    public float rate;


    public rateBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        if ( !isInEditMode()) {
            initialize(context);
        }
    }


    public rateBar(Context context) {
        super(context);
        if ( !isInEditMode()) {
            initialize(context);
        }
    }


    public void setRate() {

        float d = rate;
        int d1 = (int) (d * 10);
        String str = String.valueOf(d1);
        String st1 = str.substring(0, 1);
        String st2 = str.substring(1, 2);
        Log.i("LOG", "" + st1 + "   " + st2);
        //Log.i("LOG", "" + st.length);
        // Log.i("LOG", "" + st[0]);

        int i1 = Integer.parseInt(st1);
        int i2 = Integer.parseInt(st2);

        if (i1 == 1) {
            img1.setImageResource(R.drawable.ratefill);
            img3.setImageResource(R.drawable.rate);
            img4.setImageResource(R.drawable.rate);
            img5.setImageResource(R.drawable.rate);
            if (i2 == 0) {
                img2.setImageResource(R.drawable.rate);
            } else if (i2 == 5) {
                img2.setImageResource(R.drawable.rate50);
            } else if (i2 > 5) {
                img2.setImageResource(R.drawable.rate75);
            } else if (i2 < 5) {
                img2.setImageResource(R.drawable.rate25);
            }
        } else if (i1 == 2) {
            img2.setImageResource(R.drawable.ratefill);
            img1.setImageResource(R.drawable.ratefill);
            img4.setImageResource(R.drawable.rate);
            img5.setImageResource(R.drawable.rate);
            if (i2 == 0) {
                img3.setImageResource(R.drawable.rate);
            } else if (i2 == 5) {
                img3.setImageResource(R.drawable.rate50);
            } else if (i2 > 5) {
                img3.setImageResource(R.drawable.rate75);
            } else if (i2 < 5) {
                img3.setImageResource(R.drawable.rate25);
            }

        } else if (i1 == 3) {
            img3.setImageResource(R.drawable.ratefill);
            img2.setImageResource(R.drawable.ratefill);
            img1.setImageResource(R.drawable.ratefill);
            img5.setImageResource(R.drawable.rate);
            if (i2 == 0) {
                img4.setImageResource(R.drawable.rate);
            } else if (i2 == 5) {
                img4.setImageResource(R.drawable.rate50);
            } else if (i2 > 5) {
                img4.setImageResource(R.drawable.rate75);
            } else if (i2 < 5) {
                img4.setImageResource(R.drawable.rate25);
            }
        } else if (i1 == 4) {
            img4.setImageResource(R.drawable.ratefill);
            img3.setImageResource(R.drawable.ratefill);
            img2.setImageResource(R.drawable.ratefill);
            img1.setImageResource(R.drawable.ratefill);
            if (i2 == 0) {
                img5.setImageResource(R.drawable.rate);
            } else if (i2 == 5) {
                img5.setImageResource(R.drawable.rate50);
            } else if (i2 > 5) {
                img5.setImageResource(R.drawable.rate75);
            } else if (i2 < 5) {
                img5.setImageResource(R.drawable.rate25);
            }
        } else if (i1 == 5) {
            img5.setImageResource(R.drawable.ratefill);
            img4.setImageResource(R.drawable.ratefill);
            img3.setImageResource(R.drawable.ratefill);
            img2.setImageResource(R.drawable.ratefill);
            img1.setImageResource(R.drawable.ratefill);

        }
        /*mg2
         img3
         img4
         img5*/
    }


    private void initialize(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.rate, this, true);
        img1 = (ImageView) view.findViewById(R.id.img1);
        img2 = (ImageView) view.findViewById(R.id.img2);
        img3 = (ImageView) view.findViewById(R.id.img3);
        img4 = (ImageView) view.findViewById(R.id.img4);
        img5 = (ImageView) view.findViewById(R.id.img5);

        img1.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

                img1.setImageResource(R.drawable.ratefill);
                img2.setImageResource(R.drawable.rate);
                img3.setImageResource(R.drawable.rate);
                img4.setImageResource(R.drawable.rate);
                img5.setImageResource(R.drawable.rate);
            }
        });

        img2.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

                img1.setImageResource(R.drawable.ratefill);
                img2.setImageResource(R.drawable.ratefill);
                img3.setImageResource(R.drawable.rate);
                img4.setImageResource(R.drawable.rate);
                img5.setImageResource(R.drawable.rate);
            }
        });

        img3.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

                img1.setImageResource(R.drawable.ratefill);
                img2.setImageResource(R.drawable.ratefill);
                img3.setImageResource(R.drawable.ratefill);
                img4.setImageResource(R.drawable.rate);
                img5.setImageResource(R.drawable.rate);
            }
        });
        img4.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

                img1.setImageResource(R.drawable.ratefill);
                img2.setImageResource(R.drawable.ratefill);
                img3.setImageResource(R.drawable.ratefill);
                img4.setImageResource(R.drawable.ratefill);
                img5.setImageResource(R.drawable.rate);
            }
        });
        img5.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

                img1.setImageResource(R.drawable.ratefill);
                img2.setImageResource(R.drawable.ratefill);
                img3.setImageResource(R.drawable.ratefill);
                img4.setImageResource(R.drawable.ratefill);
                img5.setImageResource(R.drawable.ratefill);
                img5.setImageResource(R.drawable.ratefill);
            }
        });

    }
}
