package com.futuredeveloper.android.shopfinder;

import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.Application;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.location.LocationManager;
import android.os.Environment;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.Button;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.futuredeveloper.android.database.DataAccess;
import com.futuredeveloper.android.database.DataLogin;
import com.futuredeveloper.android.shopfinder.internet.CheckInternet;
import com.futuredeveloper.android.shopfinder.internet.LruBitmapCache;
import com.futuredeveloper.android.shopfinder.internet.StrucCategories;
import com.futuredeveloper.android.shopfinder.internet.StrucStore;
import com.futuredeveloper.android.shopfinder.internet.StrucSubCat1List;
import com.futuredeveloper.android.shopfinder.internet.StrucSubCat2List;
import com.futuredeveloper.android.shopfinder.internet.WebServiceUrl;


public class G extends Application {

    public static Context                     context;
    public static final String                DIR_SDCARD         = Environment.getExternalStorageDirectory().getAbsolutePath();
    public static String                      DB_PATH            = DIR_SDCARD + "/hobo/";
    public static String                      DB_NAME_Ass        = "db/hobo4.db";
    public static String                      DB_NAME            = "hobo4.db";
    public static String                      FONT_NAME1         = "font/yekan.ttf";
    public static String                      FONT_NAME2         = "font/koodak.otf";
    public static Typeface                    font1;
    public static Typeface                    font2;
    public static LayoutInflater              inflater;
    public static Activity                    currentActivity;
    public static String                      access_token;

    public static String                      verApp             = "2";

    public static Dialog                      dialogOstan;
    public static Dialog                      dialogCity;

    public static Button                      btnOstan;
    public static Button                      btnCity;

    public static SharedPreferences           preferences;

    public static final Handler               HANDLER            = new Handler();

    public static String[]                    DATALOGIN          = new String[6];
    public static String                      LoginOk            = "0";

    private static G                          mInstance;
    private ImageLoader                       mImageLoader;
    private RequestQueue                      mRequestQueue;
    public static final String                TAG                = G.class.getSimpleName();

    public static ArrayList<StrucStore>       DataStore          = new ArrayList<StrucStore>();
    public static int                         getLa              = 1;

    public static ArrayList<StructKala>       DataKalaNew        = new ArrayList<StructKala>();
    public static int                         getLaKalaNew       = 1;

    public static ArrayList<StructKala>       DataKalaDiscount   = new ArrayList<StructKala>();
    public static int                         getLaKalaDiscount  = 1;

    public static ArrayList<StructKala>       DataKalaSearch     = new ArrayList<StructKala>();
    public static int                         getLaSearch        = 1;

    public static AlarmManager                alarmManager;

    public static String                      countyRecive       = "20";

    public static ArrayList<StrucCategories>  Datacategory       = new ArrayList<StrucCategories>();

    public static ArrayList<StrucSubCat1List> DataSubCat1List    = new ArrayList<StrucSubCat1List>();

    public static ArrayList<StrucSubCat2List> DataSubCat2List    = new ArrayList<StrucSubCat2List>();

    public static int                         idSearchCat        = 0;
    public static int                         idSearchCat1       = 0;
    public static int                         idSearchCat2       = 0;

    public static int                         attrbiuteTableCode = 0;
    public static int                         subCat2Code        = 0;
    public static LocationManager             lManager;


    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        context = getApplicationContext();
        font1 = Typeface.createFromAsset(context.getAssets(), FONT_NAME2);
        font2 = Typeface.createFromAsset(context.getAssets(), FONT_NAME1);

        DataAccess dataAccess = new DataAccess();
        dataAccess.copyDatabase();

        inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        lManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Log.i("LOG", "P1 " + G.DATALOGIN[0]);
        Log.i("LOG", "P2 " + G.DATALOGIN[5]);

        DataLogin dataLogin = new DataLogin();
        dataLogin.getDateLogin();
        if (G.DATALOGIN[5].equals("1")) {
            LoginOk = "1";
        } else {
            LoginOk = "0";
        }
        /// String G.DATALOGIN[4].split(",", 11);
        Log.i("LOG", "P4 " + G.DATALOGIN[5]);
        Log.i("LOG", "P3 " + G.DATALOGIN[4]);

        reciveFilter();

    }


    public static synchronized G getInstance() {
        return mInstance;
    }


    public ImageLoader getImageLoader() {
        getRequestQueue();
        if (mImageLoader == null) {
            mImageLoader = new ImageLoader(this.mRequestQueue,
                    new LruBitmapCache());
        }
        return this.mImageLoader;
    }


    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return mRequestQueue;
    }


    public <T> void addToRequestQueue(Request<T> req, String tag) {
        // set the default tag if tag is empty
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }


    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }


    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }


    public static void reciveFilter() {
        final CheckInternet CheckInternet = new CheckInternet();
        if (CheckInternet.Access()) {
            Log.i("ADV", " kjk" + "categories");
            try {
                String url = WebServiceUrl.GetCategory;
                StringRequest postRequest = new StringRequest(Request.Method.GET, url,
                        new Response.Listener<String>()
                        {

                            @Override
                            public void onResponse(String response) {

                                try {
                                    G.Datacategory.clear();
                                    G.DataSubCat1List.clear();
                                    G.DataSubCat2List.clear();
                                    JSONObject object = new JSONObject(response);
                                    Log.i("ADV", " kjk" + object);
                                    JSONArray categories = object.getJSONArray("Categories");
                                    Log.i("ADV", " Categories" + categories);
                                    JSONArray lstSubCat1 = object.getJSONArray("SubCat1List");
                                    Log.i("ADV", " LstSubCat1" + lstSubCat1);
                                    JSONArray lstSubCat2 = object.getJSONArray("SubCat2List");
                                    Log.i("ADV", " LstSubCat2" + lstSubCat2);

                                    for (int i = 0; i < categories.length(); i++) {
                                        StrucCategories cat = new StrucCategories();
                                        JSONObject obj = categories.getJSONObject(i);
                                        cat.id = obj.getInt("Id");
                                        cat.name = obj.getString("Name");
                                        G.Datacategory.add(cat);
                                        Log.i("ADV", " Id" + cat.id + " ==> " + cat.name);
                                    }
                                    Log.i("ADV", "FINISH1 ");
                                    for (int i = 0; i < lstSubCat1.length(); i++) {
                                        StrucSubCat1List cat1 = new StrucSubCat1List();
                                        JSONObject obj = lstSubCat1.getJSONObject(i);
                                        cat1.id = obj.getInt("Id");
                                        cat1.catId = obj.getInt("CatCode");
                                        cat1.name = obj.getString("Name");
                                        G.DataSubCat1List.add(cat1);
                                        Log.i("ADV", " Id" + cat1.id + " ==> " + cat1.name + "===>" + cat1.catId);
                                    }
                                    Log.i("ADV", "FINISH2 ");
                                    for (int i = 0; i < lstSubCat2.length(); i++) {
                                        StrucSubCat2List cat2 = new StrucSubCat2List();
                                        JSONObject obj = lstSubCat2.getJSONObject(i);
                                        cat2.id = obj.getInt("Id");
                                        cat2.subCatId = obj.getInt("SubCat1Code");
                                        cat2.name = obj.getString("Name");
                                        cat2.attribTableCode = obj.getInt("AttrbiuteTableCode");
                                        cat2.idWithAttrbiuteTableCode = obj.getString("IdWithAttrbiuteTableCode");
                                        G.DataSubCat2List.add(cat2);
                                        Log.i("ADV", " Id" + cat2.id + " ==> " + cat2.name + "===>" + cat2.subCatId + cat2.attribTableCode);
                                    }
                                    Log.i("ADV", "FINISH3 ");
                                    // list.clear();
                                    //  list.addAll(G.Datacategory);
                                    // createSpinner();
                                }
                                catch (JSONException e) {
                                    Log.i("ADVR", " " + e.toString());
                                    e.printStackTrace();
                                }

                            }
                        },
                        new Response.ErrorListener()
                        {

                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.i("ADVR", " " + "object.getStrin3");
                            }
                        });
                G.getInstance().addToRequestQueue(postRequest);
            }
            catch (Exception e) {
                Log.i("ADVR", " kjk" + e.toString());
            }

        }
    }

}
