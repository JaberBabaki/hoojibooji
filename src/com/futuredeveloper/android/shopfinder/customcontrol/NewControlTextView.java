package com.futuredeveloper.android.shopfinder.customcontrol;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;
import com.futuredeveloper.android.shopfinder.G;


public class NewControlTextView extends TextView {

    public NewControlTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initialize();
    }


    public NewControlTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize();
    }


    public NewControlTextView(Context context) {
        super(context);
        initialize();
    }


    private void initialize() {
        if ( !isInEditMode()) {
            setTypeface(G.font1);
        }
    }
}