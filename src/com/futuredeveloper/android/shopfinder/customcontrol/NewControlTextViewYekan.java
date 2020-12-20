package com.futuredeveloper.android.shopfinder.customcontrol;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;
import com.futuredeveloper.android.shopfinder.G;


public class NewControlTextViewYekan extends TextView {

    public NewControlTextViewYekan(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initialize();
    }


    public NewControlTextViewYekan(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize();
    }


    public NewControlTextViewYekan(Context context) {
        super(context);
        initialize();
    }


    private void initialize() {
        if ( !isInEditMode()) {
            setTypeface(G.font2);
        }
    }
}