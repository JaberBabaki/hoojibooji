package com.futuredeveloper.android.shopfinder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationCompat.Builder;
import android.text.Html;
import android.util.Log;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.futuredeveloper.android.database.NotificationDb;
import com.futuredeveloper.android.shopfinder.internet.WebServiceUrl;


public class MyReceiver extends BroadcastReceiver {

    private static int idAll    = 1;
    private long       interval = 60 * 1000 * 60;


    @Override
    public void onReceive(final Context context, final Intent intent) {

        if (isOnline(context))
        {
            getNewev(context);
            //getAlarm(context);
            Intent myIntent = new Intent(G.context, MyReceiver.class);
            myIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(G.context, 0, myIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            G.alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + interval, interval, pendingIntent);

        }

    }


    public boolean isOnline(Context context) {

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        //should check null because in air plan mode it will be null
        return (netInfo != null && netInfo.isConnected());

    }


    public static void getAlarm(final Context context) {
        try {

            String url = ""; //G.url + "notification.aspx";

            RequestQueue queue = Volley.newRequestQueue(context);
            StringRequest postRequest = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>()
                    {

                        @Override
                        public void onResponse(final String response) {
                            if ( !response.equals("")) {

                                try {
                                    NotificationManager mNotifyManager;
                                    Builder mBuilder;

                                    mNotifyManager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
                                    mBuilder = new NotificationCompat.Builder(context);

                                    Intent intent = new Intent(context, ListNotification.class);
                                    PendingIntent contentIntent = PendingIntent.getActivity(context, idAll, intent, 0);

                                    JSONArray array = new JSONArray(response);

                                    for (int i = 0; i < array.length(); i++) {
                                        JSONObject object = array.getJSONObject(i);
                                        int id = object.getInt("ID");
                                        NotificationDb noti = new NotificationDb();
                                        noti.setId(id);
                                        Log.i("NOT", "id=  " + id);
                                        if ((noti.selectId() == 0)) {
                                            idAll++;
                                            String title = Html.fromHtml(object.getString("Title")).toString();
                                            String text = Html.fromHtml(object.getString("Body")).toString();
                                            //StringE
                                            String date = object.getString("Date");
                                            String image = Html.fromHtml(object.getString("Image")).toString();
                                            if (image.equals("") || image == null) {
                                                image = "n";
                                            }
                                            mBuilder.setContentTitle(title)
                                                    .setContentText(text)
                                                    .setSmallIcon(R.drawable.bullhorn)
                                                    .setContentIntent(contentIntent)
                                                    .setAutoCancel(true);

                                            mNotifyManager.notify(id, mBuilder.build());

                                            noti.setDate(date);
                                            noti.setTitle(title);
                                            noti.setText(text);
                                            noti.setImage(image);
                                            noti.inesrtNotic();

                                            updateAlarm(G.context, id);
                                        }
                                    }
                                }
                                catch (JSONException e) {
                                    // TODO Auto-generated catch block
                                    e.printStackTrace();
                                }
                            }
                        }
                    },
                    new Response.ErrorListener()
                    {

                        @Override
                        public void onErrorResponse(VolleyError error) {}
                    });
            queue.add(postRequest);
        }
        catch (Exception e) {
            Log.i("LOG", " kjk" + e.toString());
        }
    }


    public static void updateAlarm(final Context context, int id) {
        try {

            String url = "";/* G.url + "notification.aspx?" +
                            "&pid=" + URLEncoder.encode("" + id, "UTF-8") +
                            "&pe=" + URLEncoder.encode(G.GetDeviceID, "UTF-8") +
                            "&pv=" + URLEncoder.encode(G.verApp, "UTF-8");*/

            RequestQueue queue = Volley.newRequestQueue(context);
            StringRequest postRequest = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>()
                    {

                        @Override
                        public void onResponse(final String response) {}
                    },
                    new Response.ErrorListener()
                    {

                        @Override
                        public void onErrorResponse(VolleyError error) {}
                    });
            queue.add(postRequest);
        }
        catch (Exception e) {
            Log.i("LOG", " kjk" + e.toString());
        }
    }


    public static void getNewev(final Context context) {
        try {

            String url = WebServiceUrl.GetUpdate + "currentVersion=" + Float.parseFloat(G.verApp);

            RequestQueue queue = Volley.newRequestQueue(context);
            StringRequest postRequest = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>()
                    {

                        @Override
                        public void onResponse(final String response) {
                            Log.i("NEW", "" + response);

                            try {
                                JSONObject object = new JSONObject(response);
                                String ok = object.getString("message");
                                if ( !ok.equals("there is no new version")) {
                                    NotificationManager mNotifyManager;
                                    Builder mBuilder;

                                    mNotifyManager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
                                    mBuilder = new NotificationCompat.Builder(context);

                                    Intent intent = new Intent(context, ShopFinderOrginalActivity.class);
                                    PendingIntent contentIntent = PendingIntent.getActivity(context, 1, intent, 0);

                                    mBuilder.setContentTitle(" اپلیکشن هوجی بوجی ")
                                            .setContentText(" دریافت نسخه جدید ")
                                            .setSmallIcon(R.drawable.logo_noti)
                                            .setContentIntent(contentIntent)
                                            .setAutoCancel(true);
                                    // .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));

                                    mNotifyManager.notify(1, mBuilder.build());
                                }

                            }
                            catch (JSONException e) {
                                Log.i("NEW", e.toString());
                                e.printStackTrace();
                            }

                        }
                    },
                    new Response.ErrorListener()
                    {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // Toast.makeText(G.context, "لطفا دوباره تلاش کنید", Toast.LENGTH_LONG).show();
                        }
                    });
            queue.add(postRequest);
        }
        catch (Exception e) {
            Log.i("LOG", " kjk" + e.toString());
        }

    }

}