package com.futuredeveloper.android.shopfinder.internet;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import com.futuredeveloper.android.shopfinder.G;


public class CheckInternet {

    public boolean Access() {
        ConnectivityManager cm = (ConnectivityManager) G.context.getSystemService(Context.CONNECTIVITY_SERVICE);
        final NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        }
        return false;
    }


    public void refresh() {
        // ShopFinderOrginalActivity.vpPager.setCurrentItem(4);
        //ShopFinderOrginalActivity.vpPager.re);
    }


    public void goToSettingWiFiNet() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.setClassName("com.android.settings", "com.android.settings.wifi.WifiSettings");
        G.currentActivity.startActivity(intent);

    }


    public void goToSettingMobileNet() {
        final Intent dataUsage = new Intent(Intent.ACTION_MAIN);
        dataUsage.setClassName("com.android.settings", "com.android.settings.Settings$DataUsageSummaryActivity");
        G.currentActivity.startActivity(dataUsage);
    }
}
