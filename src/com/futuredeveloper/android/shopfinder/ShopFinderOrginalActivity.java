package com.futuredeveloper.android.shopfinder;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.futuredeveloper.android.database.Check;
import com.futuredeveloper.android.shopfinder.customcontrol.AdapterCommodityList;
import com.futuredeveloper.android.shopfinder.customcontrol.AdapterStoreList;
import com.futuredeveloper.android.shopfinder.customcontrol.ImagePagerAdapter;
import com.futuredeveloper.android.shopfinder.customcontrol.PageIndicator;
import com.futuredeveloper.android.shopfinder.internet.CheckInternet;
import com.futuredeveloper.android.shopfinder.internet.StrucStore;
import com.futuredeveloper.android.shopfinder.internet.StructSlider;
import com.futuredeveloper.android.shopfinder.internet.WebServiceUrl;


public class ShopFinderOrginalActivity extends Enhance {

    LinearLayout                        login;
    public ViewPager                    vpPager;

    ArrayAdapter                        adapterListNew;
    ArrayAdapter                        adapterListDiscount;
    ArrayAdapter                        adapterListStore;
    ArrayAdapter                        adapterListSearch;
    ListView                            lstAll;
    ListView                            griAll;
    TextView                            txtLoading;

    int                                 lastItemPosition        = 0;
    int                                 l                       = 0;

    public static ArrayList<StrucStore> DataStore               = new ArrayList<StrucStore>();
    public static int                   getCountStoreNew        = 1;

    public static ArrayList<StructKala> DataKalaNew             = new ArrayList<StructKala>();
    public static int                   getCountProductNew      = 1;

    public static ArrayList<StructKala> DataKalaDiscount        = new ArrayList<StructKala>();
    public static int                   getCountProductDiscount = 1;

    public static ArrayList<StructKala> DataKalaSearch          = new ArrayList<StructKala>();
    public static int                   getLaSearch             = 1;

    int                                 ProductNew              = 1;
    int                                 ProductDiscount         = 0;
    int                                 StoreNew                = 0;

    public int                          numberIndicator         = 0;
    public static int                   numberOfImages;
    PageIndicator                       indicator;
    ViewPager                           pager;
    ArrayList<StructSlider>             imageIds;

    ViewGroup                           laySlider;

    int                                 isTouch                 = 0;

    boolean                             userScrolled            = false;
    int                                 listViewTouchAction;

    ViewGroup                           header;

    private long                        backPressTime           = 0L;

    public int                          countGet                = 20;                          ;


    @Override
    protected void onResume() {
        super.onResume();
        G.currentActivity = this;
    }


    @Override
    public void onBackPressed() {
        final Check ostan = new Check();
        int y = ostan.getSur();
        Log.i("LOG", y + "val");
        if (y == 1) {

            Toast toast = Toast.makeText(this, "برای خروج یکبار دیگر ضربه بزنید",
                    Toast.LENGTH_SHORT);
            if (backPressTime >= System.currentTimeMillis() - 2000L) {
                if (toast != null)
                    toast.cancel();
                super.onBackPressed();
                finish();
            } else {
                toast.show();
                backPressTime = System.currentTimeMillis();
            }

        } else {
            sendNazar(0);
        }

        //exit();

    }


    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_fake_root);

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {

            @Override
            public void run() {
                getNeweV();
            }
        }, 1000);

        Intent myIntent = new Intent(G.context, MyReceiver.class);
        myIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(G.context, 0, myIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        G.alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 3000, pendingIntent);
        // getWel();
        header = (ViewGroup) G.inflater.inflate(R.layout.head, lstAll, false);

        final Button btnNewProduct = (Button) header.findViewById(R.id.btn_new_product);
        final Button btnNewStore = (Button) header.findViewById(R.id.btn_new_store);
        final Button btnDiscountStore = (Button) header.findViewById(R.id.btn_discount_product);

        LinearLayout LayCheck = (LinearLayout) findViewById(R.id.lay_check_internet);
        LinearLayout LayMain = (LinearLayout) findViewById(R.id.lay_lst_main);

        LinearLayout LayRefresh = (LinearLayout) findViewById(R.id.lay_check_refresh);
        LinearLayout LayMobile = (LinearLayout) findViewById(R.id.lay_check_mobile);
        LinearLayout LayWifi = (LinearLayout) findViewById(R.id.lay_check_wifi);
        Button btnNoConnect = (Button) findViewById(R.id.btn_no_connect);

        ImageView imgSearch = (ImageView) findViewById(R.id.img_search);

        lstAll = (ListView) findViewById(R.id.lst_all);
        griAll = (ListView) findViewById(R.id.gri_all);

        txtLoading = (TextView) findViewById(R.id.txt_waite_main);

        btnNewProduct.setTypeface(G.font1);
        btnNewStore.setTypeface(G.font1);
        btnDiscountStore.setTypeface(G.font1);
        btnNoConnect.setTypeface(G.font1);

        btnNewProduct.setBackgroundResource(R.drawable.button_send_corner_select);
        btnNewProduct.setTextColor(Color.parseColor("#CE2458"));
        reciveNewProduct("1", Integer.toString(getCountProductNew), G.countyRecive);
        getCountProductNew++;
        ProductNew = 1;
        adapterListNew = new AdapterCommodityList(G.DataKalaNew);

        lstAll.addHeaderView(header, null, false);
        griAll.addHeaderView(header, null, false);

        final CheckInternet CheckInternet = new CheckInternet();
        if (CheckInternet.Access()) {

            imageIds = new ArrayList<StructSlider>();
            reciveSlider();

            btnNewProduct.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View arg0) {

                    if (ProductNew == 0) {

                        ProductNew = 1;

                        getCountProductNew = 1;
                        G.DataKalaNew.clear();
                        reciveNewProduct("1", Integer.toString(getCountProductNew), G.countyRecive);
                        // adapterListNew = new AdapterCommodityTiles(G.DataKalaNew);

                        btnNewProduct.setBackgroundResource(R.drawable.button_send_corner_select);
                        btnNewProduct.setTextColor(Color.parseColor("#CE2458"));

                        G.DataStore.clear();
                        btnNewStore.setBackgroundResource(R.drawable.button_send_corner);
                        btnNewStore.setTextColor(Color.parseColor("#ffffff"));
                        StoreNew = 0;

                        G.DataKalaDiscount.clear();
                        btnDiscountStore.setBackgroundResource(R.drawable.button_send_corner);
                        btnDiscountStore.setTextColor(Color.parseColor("#ffffff"));
                        ProductDiscount = 0;

                        lastItemPosition = 0;
                        l = 0;
                    }

                }
            });

            btnDiscountStore.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View arg0) {

                    if (ProductDiscount == 0) {
                        ProductDiscount = 1;

                        getCountProductDiscount = 1;
                        G.DataKalaDiscount.clear();
                        adapterListDiscount = new AdapterCommodityList(G.DataKalaDiscount);
                        reciveDiscountProduct("1", Integer.toString(getCountProductDiscount), G.countyRecive);

                        btnDiscountStore.setBackgroundResource(R.drawable.button_send_corner_select);
                        btnDiscountStore.setTextColor(Color.parseColor("#CE2458"));

                        G.DataKalaNew.clear();
                        btnNewProduct.setBackgroundResource(R.drawable.button_send_corner);
                        btnNewProduct.setTextColor(Color.parseColor("#ffffff"));
                        ProductNew = 0;

                        G.DataStore.clear();
                        btnNewStore.setBackgroundResource(R.drawable.button_send_corner);
                        btnNewStore.setTextColor(Color.parseColor("#ffffff"));
                        StoreNew = 0;

                    }

                }
            });

            btnNewStore.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View arg0) {

                    if (StoreNew == 0) {
                        StoreNew = 1;

                        getCountStoreNew = 1;
                        G.DataStore.clear();
                        adapterListStore = new AdapterStoreList(G.DataStore);
                        reciveStore(Integer.toString(getCountStoreNew), G.countyRecive);

                        btnNewStore.setBackgroundResource(R.drawable.button_send_corner_select);
                        btnNewStore.setTextColor(Color.parseColor("#CE2458"));

                        G.DataKalaNew.clear();
                        btnNewProduct.setBackgroundResource(R.drawable.button_send_corner);
                        btnNewProduct.setTextColor(Color.parseColor("#ffffff"));
                        ProductNew = 0;

                        G.DataKalaDiscount.clear();
                        adapterListStore.clear();
                        btnDiscountStore.setBackgroundResource(R.drawable.button_send_corner);
                        btnDiscountStore.setTextColor(Color.parseColor("#ffffff"));
                        ProductDiscount = 0;

                        lastItemPosition = 0;
                        l = 0;
                        // lstAll.setSelection(0);

                    }

                }
            });

            lstAll.setOnScrollListener(new OnScrollListener() {

                @Override
                public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                    // if (isTouch == 1) {
                    //isTouch = 0;
                    Log.i("FFF", "jaber ");
                    if (ProductNew == 1) {
                        int lastItem = firstVisibleItem + visibleItemCount;
                        l = lastItem;

                        Log.i("FFF", "lastK==  " + adapterListNew.getCount());
                        if (adapterListNew.getCount() >= 10 && lastItem > adapterListNew.getCount() - 3) {
                            boolean isLoading = false;
                            if ( !isLoading) {
                                if (lastItem > lastItemPosition) {
                                    getCountProductNew++;
                                    lastItemPosition = adapterListNew.getCount() + 3;
                                    reciveNewProduct("1", Integer.toString(getCountProductNew), G.countyRecive);
                                }
                                isLoading = true;
                            }
                        }
                    } else if (StoreNew == 1) {

                        int lastItem = firstVisibleItem + visibleItemCount;
                        l = lastItem;

                        Log.i("FFF", "adapterListStore.getCount()= " + adapterListStore.getCount());
                        Log.i("FFF", "lastItem= " + lastItem);
                        Log.i("FFF", "lastItemPosition= " + lastItemPosition);
                        if (adapterListStore.getCount() >= 10 && lastItem > adapterListStore.getCount() - 3) {
                            boolean isLoading = false;
                            if ( !isLoading) {
                                if (lastItem > lastItemPosition) {
                                    Log.i("FFF", " in ");
                                    getCountStoreNew++;
                                    lastItemPosition = adapterListStore.getCount() + 3;
                                    //lastItemPosition = lastItemPosition - lstAll.getHeaderViewsCount();
                                    reciveStore(Integer.toString(getCountStoreNew), G.countyRecive);
                                }
                                isLoading = true;
                            }

                        }
                    } else if (StoreNew == 0 && ProductDiscount == 0 && ProductNew == 0) {

                        int lastItem = firstVisibleItem + visibleItemCount;
                        l = lastItem;

                        Log.i("FFF", "adapterListStore.getCount()= " + adapterListSearch.getCount());
                        Log.i("FFF", "lastItem= " + lastItem);
                        Log.i("FFF", "lastItemPosition= " + lastItemPosition);
                        if (adapterListSearch.getCount() >= 10 && lastItem > adapterListSearch.getCount() - 3) {
                            boolean isLoading = false;
                            if ( !isLoading) {
                                if (lastItem > lastItemPosition) {
                                    Log.i("FFF", " in ");
                                    getLaSearch++;
                                    lastItemPosition = adapterListSearch.getCount() + 3;
                                    //lastItemPosition = lastItemPosition - lstAll.getHeaderViewsCount();
                                    reciveSearch("1", Integer.toString(getLaSearch), G.countyRecive);
                                }
                                isLoading = true;
                            }

                        }
                    }
                    // }
                }


                public void onScrollStateChanged(AbsListView view, int scrollState) {

                }

            });

            griAll.setOnScrollListener(new OnScrollListener() {

                @Override
                public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

                    if (StoreNew == 1) {

                        int lastItem = firstVisibleItem + visibleItemCount;
                        l = lastItem;

                        Log.i("FFF", "lastK==  " + lastItemPosition);
                        if (adapterListStore.getCount() >= 10 && lastItem > adapterListStore.getCount() - 3) {
                            boolean isLoading = false;
                            if ( !isLoading) {
                                if (lastItem > lastItemPosition) {
                                    getCountStoreNew++;
                                    lastItemPosition = adapterListStore.getCount();
                                    reciveNewProduct("1", Integer.toString(getCountStoreNew), G.countyRecive);
                                }
                                isLoading = true;
                            }
                        }
                    }
                }


                public void onScrollStateChanged(AbsListView view, int scrollState) {}

            });

            imgSearch.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    Intent intent = new Intent(G.currentActivity, AdvanceSearch.class);
                    G.currentActivity.startActivity(intent);
                }
            });

            LayMain.setVisibility(View.VISIBLE);
            LayCheck.setVisibility(View.GONE);
        } else {
            LayMain.setVisibility(View.GONE);
            LayCheck.setVisibility(View.VISIBLE);

        }
        LayWifi.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // Toast.makeText(G.context, "text", Toast.LENGTH_SHORT).show();
                CheckInternet.goToSettingWiFiNet();
            }
        });
        LayMobile.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                CheckInternet.goToSettingMobileNet();
            }
        });
        LayRefresh.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                btnNewProduct.setBackgroundResource(R.drawable.button_send_corner_select);
                btnNewProduct.setTextColor(Color.parseColor("#CE2458"));
                reciveNewProduct("1", Integer.toString(getCountProductNew), G.countyRecive);
                getCountProductNew++;
                ProductNew = 1;
                adapterListNew = new AdapterCommodityList(G.DataKalaNew);
            }
        });

        menu();

    }


    private void showListNewProduct() {
        lstAll.setVisibility(View.VISIBLE);
        griAll.setVisibility(View.GONE);
        if (G.DataKalaNew.size() == 0) {
            lstAll.setVisibility(View.GONE);

        } else {
            //  Data.set(1, id) int id = getResources().getIdentifier("yourpackagename:drawable/" + Data.get(1).bread, null, null);

            lstAll.setAdapter(adapterListNew);
            lstAll.setSelection(l);
        }

    }


    private void showListDiscountProduct() {
        lstAll.setVisibility(View.GONE);
        griAll.setVisibility(View.VISIBLE);
        if (G.DataKalaDiscount.size() == 0) {
            griAll.setVisibility(View.GONE);

        } else {
            //  Data.set(1, id) int id = getResources().getIdentifier("yourpackagename:drawable/" + Data.get(1).bread, null, null);

            griAll.setAdapter(adapterListDiscount);
            griAll.setSelection(l);
        }

    }


    private void showListSearch() {
        lstAll.setVisibility(View.VISIBLE);
        griAll.setVisibility(View.GONE);
        if (G.DataKalaSearch.size() == 0) {
            lstAll.setVisibility(View.GONE);

        } else {
            //  Data.set(1, id) int id = getResources().getIdentifier("yourpackagename:drawable/" + Data.get(1).bread, null, null);

            lstAll.setAdapter(adapterListNew);
            lstAll.setSelection(l);
        }
    }


    private void showListStore() {
        lstAll.setVisibility(View.VISIBLE);
        griAll.setVisibility(View.GONE);
        if (G.DataStore.size() == 0) {
            lstAll.setVisibility(View.GONE);

        } else {
            //  Data.set(1, id) int id = getResources().getIdentifier("yourpackagename:drawable/" + Data.get(1).bread, null, null);

            lstAll.setAdapter(adapterListStore);
            lstAll.setSelection(l);
        }

    }


    public void showSlider() {

        indicator = (PageIndicator) header.findViewById(R.id.indicator);
        pager = (ViewPager) header.findViewById(R.id.view_pager_slider);

        ImagePagerAdapter adapter = new ImagePagerAdapter(imageIds);
        if (imageIds.size() == 0) {
            StructSlider slider = new StructSlider();
            slider.id = -1;
            slider.imgAddress = "no";
            slider.link = "no";
            imageIds.add(slider);
        }
        Log.i("PPP", "  count " + (imageIds.size()));
        pager.setAdapter(adapter);
        indicator.setIndicatorsCount(imageIds.size());

        pager.setOnPageChangeListener(new OnPageChangeListener() {

            @Override
            public void onPageSelected(int index) {}


            @Override
            public void onPageScrolled(int startIndex, float percent, int pixel) {
                indicator.setPercent(percent);
                Log.i("LOG", "Percent: " + startIndex + ", " + percent);
                indicator.setCurrentPage(startIndex);
            }


            @Override
            public void onPageScrollStateChanged(int arg0) {

            }
        });

    }


    public void reciveNewProduct(final String n, final String page, final String count) {
        try {

            txtLoading.setVisibility(View.VISIBLE);
            String url = WebServiceUrl.GetNewKala;

            StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {

                        @Override
                        public void onResponse(String response) {

                            try {
                                JSONObject object = new JSONObject(response);
                                Log.i("ALL", " " + response);
                                JSONArray jsonArray = object.getJSONArray("CompleteProductsForList");

                                for (int i = 0; i < jsonArray.length(); i++) {

                                    StructKala kala = new StructKala();
                                    kala.name = jsonArray.getJSONObject(i).getJSONObject("ProductSummary").getString("Name");
                                    kala.price = jsonArray.getJSONObject(i).getJSONObject("ProductSummary").getString("Price");
                                    kala.id = jsonArray.getJSONObject(i).getJSONObject("ProductSummary").getInt("Id");
                                    kala.picture = "www.hoojibooji.ir/" + jsonArray.getJSONObject(i).getJSONObject("ProductSummary").getString("ImgAddress");
                                    kala.state = jsonArray.getJSONObject(i).getJSONObject("ProductSummary").getBoolean("IsExist");
                                    G.DataKalaNew.add(kala);
                                }

                                showListNewProduct();
                                txtLoading.setVisibility(View.GONE);
                            }
                            catch (JSONException e) {
                                // TODO Auto-generated catch block
                                Log.i("LOL", "" + e.toString());
                                e.printStackTrace();
                            }

                        }
                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.i("ALL", error.toString());
                            //txtWaite.setVisibility(View.GONE);
                            //Toast.makeText(G.context, "لطفا دوباره تلاش کنید", Toast.LENGTH_SHORT).show();
                        }
                    }) {

                @Override
                protected Map<String, String> getParams()
                {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("Content-Type", "application/json");
                    params.put("atrCode", "");
                    params.put("subCat", "");
                    params.put("name", "");
                    params.put("tileShow ", "true");
                    params.put("sortBy", "1");
                    params.put("pageNumber", page);
                    params.put("rowsPage", count);

                    return params;
                }
            };
            postRequest.setRetryPolicy((RetryPolicy) new DefaultRetryPolicy(
                    4000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            G.getInstance().addToRequestQueue(postRequest);
        }
        catch (Exception e) {
            Log.i("ALL", " kjk" + e.toString());
        }

    }


    public void reciveDiscountProduct(final String n, final String page, final String count) {
        try {
            Log.i("HHH", "jaber   " + "response");
            txtLoading.setVisibility(View.VISIBLE);
            String url = WebServiceUrl.GetNewKala;
            Log.i("HHH", "jaber   " + url);

            StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {

                        @Override
                        public void onResponse(String response) {

                            try {
                                JSONObject object = new JSONObject(response);
                                JSONArray jsonArray = object.getJSONArray("CompleteProductsForList");
                                Log.i("HHH", "response " + response);

                                for (int i = 0; i < jsonArray.length(); i++) {

                                    StructKala kala = new StructKala();
                                    kala.name = jsonArray.getJSONObject(i).getJSONObject("ProductSummary").getString("Name");
                                    kala.price = jsonArray.getJSONObject(i).getJSONObject("ProductSummary").getString("Price");
                                    kala.id = jsonArray.getJSONObject(i).getJSONObject("ProductSummary").getInt("Id");
                                    kala.picture = "www.hoojibooji.ir/" + jsonArray.getJSONObject(i).getJSONObject("ProductSummary").getString("ImgAddress");
                                    kala.state = jsonArray.getJSONObject(i).getJSONObject("ProductSummary").getBoolean("IsExist");
                                    G.DataKalaDiscount.add(kala);
                                }

                                showListDiscountProduct();
                                txtLoading.setVisibility(View.GONE);
                            }
                            catch (JSONException e) {
                                // TODO Auto-generated catch block
                                Log.i("LOL", "" + e.toString());
                                e.printStackTrace();
                            }

                        }
                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.i("ALL", error.toString());
                            txtLoading.setVisibility(View.GONE);
                            // Toast.makeText(G.context, "لطفا دوباره تلاش کنید", Toast.LENGTH_SHORT).show();
                        }
                    }) {

                @Override
                protected Map<String, String> getParams()
                {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("Content-Type", "application/json");
                    params.put("atrCode", "");
                    params.put("subCat", "");
                    params.put("name", "");
                    params.put("tileShow ", "true");
                    params.put("sortBy", "2");
                    params.put("pageNumber", page);
                    params.put("rowsPage", count);
                    params.put("ascending", "true");

                    return params;
                }
            };
            postRequest.setRetryPolicy((RetryPolicy) new DefaultRetryPolicy(
                    4000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            G.getInstance().addToRequestQueue(postRequest);
        }
        catch (Exception e) {
            Log.i("ALL", " kjk" + e.toString());
        }

    }


    public void reciveStore(String p1, String p2) {
        try {
            /* final Dialog prg = new Dialog(G.currentActivity);
             prg.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
             prg.getWindow().setGravity(Gravity.BOTTOM);
             prg.setContentView(R.layout.dialog_wait_list);
             TextView txt_header_conect = (TextView) prg.findViewById(R.id.txt_dialog);
             txt_header_conect.setTypeface(G.font2);
             prg.show();*/
            txtLoading.setVisibility(View.VISIBLE);
            // String url = "http://api.androidhive.info/json/movies.json";
            String url = WebServiceUrl.getNewStore +
                    "cityCode=" + URLEncoder.encode("null", "UTF-8") +
                    "&pageNumber=" + URLEncoder.encode(p1, "UTF-8") +
                    "&rowspPage=" + URLEncoder.encode(p2, "UTF-8");
            StringRequest postRequest = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>()
                    {

                        @Override
                        public void onResponse(String response) {
                            Log.i("LPL", " kjk + res" + response);
                            ;
                            try {
                                JSONArray array = new JSONArray(response);
                                for (int i = 0; i < array.length(); i++) {
                                    StrucStore store = new StrucStore();
                                    JSONObject object = array.getJSONObject(i);
                                    store.name = object.getString("Name");
                                    store.location = object.getString("CityName");
                                    store.id = object.getInt("Id");
                                    store.picture = "www.hoojibooji.ir/" + object.getString("LogoAddress");
                                    store.phone = object.getInt("PhoneNumber");
                                    //Toast.makeText(G.context, object.getString("CityName"), Toast.LENGTH_LONG).show();
                                    G.DataStore.add(store);
                                }

                                Log.i("FFF", " get==" + getCountStoreNew);
                                // G.getLa = get;

                                showListStore();
                                txtLoading.setVisibility(View.GONE);
                            }
                            catch (JSONException e) {
                                txtLoading.setVisibility(View.GONE);
                                e.printStackTrace();
                            }

                        }
                    },
                    new Response.ErrorListener()
                    {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            txtLoading.setVisibility(View.GONE);
                            // Toast.makeText(G.context, "لطفا دوباره تلاش کنید", Toast.LENGTH_LONG).show();
                        }
                    });
            postRequest.setRetryPolicy((RetryPolicy) new DefaultRetryPolicy(
                    4000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            G.getInstance().addToRequestQueue(postRequest);
        }
        catch (Exception e) {
            Log.i("LOG", " kjk" + e.toString());
        }

    }


    public void reciveSlider() {
        try {
            imageIds.clear();
            String url = WebServiceUrl.GetSlider;
            StringRequest postRequest = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>()
                    {

                        @Override
                        public void onResponse(String response) {
                            Log.i("PPP", " kjk" + response);
                            try {
                                JSONArray array = new JSONArray(response);
                                for (int i = 0; i < array.length(); i++) {
                                    StructSlider slider = new StructSlider();
                                    JSONObject object = array.getJSONObject(i);
                                    slider.id = object.getInt("Id");
                                    slider.imgAddress = "www.hoojibooji.ir/" + object.getString("ImgAddress");
                                    slider.link = object.getString("Link");
                                    //Toast.makeText(G.context, object.getString("CityName"), Toast.LENGTH_LONG).show();
                                    imageIds.add(slider);

                                }
                                showSlider();
                            }
                            catch (JSONException e) {
                                e.printStackTrace();
                                Log.i("PPP", " " + "object.getStrin2");
                            }

                        }
                    },
                    new Response.ErrorListener()
                    {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.i("PPP", " " + "object.getStrin3");
                        }
                    });
            postRequest.setRetryPolicy((RetryPolicy) new DefaultRetryPolicy(
                    4000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            G.getInstance().addToRequestQueue(postRequest);
        }
        catch (Exception e) {
            Log.i("LOG", " kjk" + e.toString());
        }

    }


    public void reciveSearch(final String count, final String page, final String name) {
        try {
            Log.i("HHH", "jaber   " + "response");
            txtLoading.setVisibility(View.VISIBLE);
            String url = WebServiceUrl.GetSearch;
            Log.i("HHH", "jaber   " + url);

            StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {

                        @Override
                        public void onResponse(String response) {

                            Log.i("RCS", "response " + response);
                            try {
                                JSONObject object = new JSONObject(response);
                                JSONArray jsonArray = object.getJSONArray("CompleteProductsForList");

                                for (int i = 0; i < jsonArray.length(); i++) {

                                    StructKala kala = new StructKala();
                                    kala.name = jsonArray.getJSONObject(i).getJSONObject("ProductSummary").getString("Name");
                                    kala.price = jsonArray.getJSONObject(i).getJSONObject("ProductSummary").getString("Price");
                                    kala.id = jsonArray.getJSONObject(i).getJSONObject("ProductSummary").getInt("Id");
                                    kala.picture = "www.hoojibooji.ir/" + jsonArray.getJSONObject(i).getJSONObject("ProductSummary").getString("ImgAddress");
                                    kala.state = jsonArray.getJSONObject(i).getJSONObject("ProductSummary").getBoolean("IsExist");
                                    G.DataKalaSearch.add(kala);
                                }

                                showListSearch();
                                txtLoading.setVisibility(View.GONE);
                            }
                            catch (JSONException e) {
                                // TODO Auto-generated catch block
                                Log.i("LOL", "" + e.toString());
                                e.printStackTrace();
                            }

                        }
                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.i("ALL", error.toString());
                            txtLoading.setVisibility(View.GONE);
                            // Toast.makeText(G.context, "لطفا دوباره تلاش کنید", Toast.LENGTH_SHORT).show();
                        }
                    }) {

                @Override
                protected Map<String, String> getParams()
                {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("Content-Type", "application/json");
                    params.put("atrCode", "");
                    params.put("subCat", "");
                    params.put("tileShow ", "true");
                    params.put("rowsPage", count);
                    params.put("pageNumber", page);
                    params.put("name", name);
                    params.put("sortBy", "");

                    return params;
                }
            };
            postRequest.setRetryPolicy((RetryPolicy) new DefaultRetryPolicy(
                    4000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            G.getInstance().addToRequestQueue(postRequest);
        }
        catch (Exception e) {
            Log.i("ALL", " kjk" + e.toString());
        }

    }

}