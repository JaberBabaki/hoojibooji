package com.futuredeveloper.android.shopfinder;

import java.net.URLEncoder;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.futuredeveloper.android.shopfinder.customcontrol.AdapterCommodityList;
import com.futuredeveloper.android.shopfinder.customcontrol.AdapterItemFilter;
import com.futuredeveloper.android.shopfinder.customcontrol.AdapterItemFilter2;
import com.futuredeveloper.android.shopfinder.internet.CheckInternet;
import com.futuredeveloper.android.shopfinder.internet.StrucCategories;
import com.futuredeveloper.android.shopfinder.internet.StrucFilter;
import com.futuredeveloper.android.shopfinder.internet.StrucItemFilter;
import com.futuredeveloper.android.shopfinder.internet.StrucSubCat1List;
import com.futuredeveloper.android.shopfinder.internet.StrucSubCat2List;
import com.futuredeveloper.android.shopfinder.internet.WebServiceUrl;


public class AdvanceSearch extends Enhance {

    TextView                           txtLoading;

    int                                id;

    ImageLoader                        imageLoader        = G.getInstance().getImageLoader();
    NetworkImageView                   thumbNail;
    ImageView                          imageSliderDefault;

    TextView                           txtName;
    TextView                           price;
    TextView                           state;
    TextView                           nameStore;
    TextView                           addrressStore;

    public ArrayList<StrucFilter>      DataFilte          = new ArrayList<StrucFilter>();

    public ArrayList<StrucFilter>      listTemp           = new ArrayList<StrucFilter>();
    public ArrayList<StrucItemFilter>  listTemp2          = new ArrayList<StrucItemFilter>();

    // public static ArrayList<StrucSubCat2List> DataSubCat2List = new ArrayList<StrucSubCat2List>();

    public ArrayList<StrucCategories>  list               = new ArrayList<StrucCategories>();
    public ArrayList<StrucCategories>  Data2              = new ArrayList<StrucCategories>();
    ArrayAdapter                       adapterList;
    String                             name;
    Dialog                             dialog3;
    int                                cat                = 1;

    public ArrayList<StrucSubCat1List> list1              = new ArrayList<StrucSubCat1List>();
    public ArrayList<StrucSubCat1List> DataSub1           = new ArrayList<StrucSubCat1List>();
    ArrayAdapter                       adapterCat1List;
    int                                cat1               = 0;

    public ArrayList<StrucSubCat2List> list2              = new ArrayList<StrucSubCat2List>();
    public ArrayList<StrucSubCat2List> DataSub2           = new ArrayList<StrucSubCat2List>();
    ArrayAdapter                       adapterCat2List;
    int                                cat2               = 0;

    public int                         attrbiuteTableCode = -1;
    public int                         idSearchCat2       = -1;
    ListView                           cs;
    LinearLayout                       layFilter;

    public int                         pos                = -1;

    private int                        ase                = 0;
    private int                        dease              = 0;

    private int                        newP               = 0;
    private int                        priceP             = 0;
    private int                        viewP              = 0;
    private int                        specialP           = 0;
    private int                        rateP              = 0;
    private int                        popularP           = 0;

    public String[]                    dataSend;
    public String                      jsonString;

    public Double                      latPoint           = 0.1;
    public Double                      lngPoint           = 0.1;

    boolean                            setImageLocation   = true;
    boolean                            setImageState      = true;

    int                                sortSelect         = -1;
    boolean                            aseSelect          = false;

    public EditText                    edtSearch;

    public ArrayList<StructKala>       DataKalaDiscount   = new ArrayList<StructKala>();
    public int                         getCountProductNew = 1;

    ArrayAdapter                       adapterListSearch;
    ListView                           lstAll;


    @Override
    protected void onResume() {
        super.onResume();
        G.currentActivity = this;
    }


    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.advance_search_root);

        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            attrbiuteTableCode = extras.getInt("attrbiuteTableCode");
            idSearchCat2 = extras.getInt("idSearchCat2");
        }

        LinearLayout LayCheck = (LinearLayout) findViewById(R.id.lay_check_internet);
        LinearLayout LayMain = (LinearLayout) findViewById(R.id.lay_main_advance_search);

        LinearLayout LayRefresh = (LinearLayout) findViewById(R.id.lay_check_refresh);
        LinearLayout LayMobile = (LinearLayout) findViewById(R.id.lay_check_mobile);
        LinearLayout LayWifi = (LinearLayout) findViewById(R.id.lay_check_wifi);

        txtLoading = (TextView) findViewById(R.id.txt_waite_show_product);
        layFilter = (LinearLayout) findViewById(R.id.lay_filter);
        // btnNewProduct.setTypeface(G.font1);

        if (attrbiuteTableCode != -1) {
            Log.i("LLL", attrbiuteTableCode + "  " + idSearchCat2);
            // attrbiuteTableCode = -1;
            reciveFilterEachProduct(attrbiuteTableCode, idSearchCat2);

        }
        final CheckInternet CheckInternet = new CheckInternet();
        if (CheckInternet.Access()) {

            final Button btnFilter = (Button) findViewById(R.id.btn_search_advance);
            edtSearch = (EditText) findViewById(R.id.edt_search_advance);
            final Button btnAsend = (Button) findViewById(R.id.btn_asen_filter);
            final Button btnSort = (Button) findViewById(R.id.btn_sort_filter);
            final Button btnSearchFilter = (Button) findViewById(R.id.btn_search_item_filter);

            final LinearLayout layGetLocation = (LinearLayout) findViewById(R.id.lay_get_location);
            final LinearLayout layState = (LinearLayout) findViewById(R.id.lay_state_filter);

            final ImageView imgGetLocation = (ImageView) findViewById(R.id.img_location_filter);
            final ImageView imgState = (ImageView) findViewById(R.id.img_state_filter);

            final ImageView imgLogoDetils = (ImageView) findViewById(R.id.img_logo_details_store);

            lstAll = (ListView) findViewById(R.id.lst_all);
            adapterListSearch = new AdapterCommodityList(DataKalaDiscount);
            //adapterListSearch.notifyDataSetChanged();

            btnFilter.setTypeface(G.font1);
            edtSearch.setTypeface(G.font1);
            edtSearch.clearFocus();
            btnSearchFilter.setTypeface(G.font1);
            btnAsend.setTypeface(G.font1);
            btnSort.setTypeface(G.font1);

            btnFilter.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    if (G.Datacategory.size() < 2) {
                        reciveFilter();
                    } else {
                        if (attrbiuteTableCode == -1) {

                            int id = -1;
                            Intent intentm = new Intent(G.currentActivity, SelectCat.class);
                            intentm.putExtra("id", id);
                            // intentm.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            // finish();
                            G.currentActivity.startActivity(intentm);
                        } else {
                            Log.i("VIS", "" + layFilter.VISIBLE);
                            if (layFilter.getVisibility() == 8) {

                                layFilter.setVisibility(View.VISIBLE);
                            } else {
                                layFilter.setVisibility(View.GONE);
                            }
                        }
                    }

                }
            });

            btnSort.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    selectSort();
                }
            });
            btnAsend.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    selectAse();
                }
            });
            btnSearchFilter.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    sendFilter();
                }
            });
            imgLogoDetils.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    sendFilter();
                }
            });
            edtSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {

                @Override
                public boolean onEditorAction(TextView arg0, int actionId, KeyEvent arg2) {
                    if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                        sendFilter();
                        return true;
                    }
                    return false;
                }
            });
            layGetLocation.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    LocationManager mLocationManager = (LocationManager) G.context.getSystemService(Context.LOCATION_SERVICE);
                    Location loc = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

                    G.lManager = (LocationManager) AdvanceSearch.this.getSystemService(Context.LOCATION_SERVICE);
                    Location location = G.lManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                    Log.i("LLL", "" + loc + "    " + location);
                    try {
                        latPoint = location.getLatitude();

                        latPoint = location.getLongitude();
                    }
                    catch (Exception e) {}

                    if (setImageLocation) {
                        imgGetLocation.setImageResource(R.drawable.pppp);
                    } else {
                        imgGetLocation.setImageResource(R.drawable.ppp);
                        latPoint = 0.1;
                    }
                    setImageLocation = !setImageLocation;

                }
            });
            layState.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View arg0) {

                    if (setImageState) {
                        imgState.setImageResource(R.drawable.pppp);
                    } else {
                        imgState.setImageResource(R.drawable.ppp);
                    }
                    setImageState = !setImageState;

                }
            });

            final DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

            ImageView imgMen = (ImageView) findViewById(R.id.men_left);
            ImageView imgMenRight = (ImageView) findViewById(R.id.men);

            imgMen.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (drawerLayout.isDrawerOpen(Gravity.RIGHT)) {
                        drawerLayout.closeDrawers();
                    } else {
                        drawerLayout.openDrawer(Gravity.RIGHT);
                    }
                }
            });

            //reciveNewProduct(id);

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
            public void onClick(View arg0) {}
        });

        menu();
    }


    public void reciveFilter() {

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
                                Intent intentm = new Intent(G.currentActivity, SelectCat.class);
                                intentm.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                finish();
                                G.currentActivity.startActivity(intentm);
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


    public void reciveFilterEachProduct(final int attrbiuteTableCode, final int subCat2Code) {
        Log.i("ADVE", " kjk" + attrbiuteTableCode + " ==" + subCat2Code);
        try {
            // attrbiuteTableCode=3018&subCat2Code=19&isForSearch=true
            String url = WebServiceUrl.GetFilterEachProduct +
                    "attrbiuteTableCode=" + URLEncoder.encode("" + attrbiuteTableCode, "UTF-8") +
                    "&subCat2Code=" + URLEncoder.encode("" + subCat2Code, "UTF-8") +
                    "&isForSearch=" + URLEncoder.encode("true", "UTF-8");

            StringRequest postRequest = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>()
                    {

                        @Override
                        public void onResponse(String response) {

                            try {

                                JSONArray jsonArray = new JSONArray(response);
                                Log.i("ADVRE", " " + jsonArray);
                                for (int i = 0; i < jsonArray.length(); i++) {

                                    StrucFilter filter = new StrucFilter();
                                    filter.header = jsonArray.getJSONObject(i).getString("Header");
                                    filter.propertyName = jsonArray.getJSONObject(i).getString("PropertyName");
                                    filter.JsonArrayFilter = jsonArray.getJSONObject(i).getJSONArray("Items");
                                    filter.select = 0;
                                    for (int b = 0; b < filter.JsonArrayFilter.length(); b++) {
                                        JSONObject js = filter.JsonArrayFilter.getJSONObject(b);
                                        StrucItemFilter item = new StrucItemFilter();
                                        if (filter.JsonArrayFilter.length() == 2) {}

                                        item.title = js.getString("Title");
                                        try {
                                            item.value = js.getInt("Value");
                                        }
                                        catch (Exception e) {}
                                        item.select = 0;
                                        Log.i("ADVRE", " " + item.title);
                                        filter.listItemFilter.add(item);
                                    }
                                    DataFilte.add(filter);
                                }
                                listTemp.clear();
                                listTemp.addAll(DataFilte);
                                dataSend = new String[DataFilte.size()];
                                createSpinner();

                            }
                            catch (JSONException e) {
                                Log.i("ADVRE", " " + e.toString());
                                e.printStackTrace();
                            }

                        }
                    },
                    new Response.ErrorListener()
                    {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.i("ADVRE", " " + "object.getStrin3");
                        }
                    });
            G.getInstance().addToRequestQueue(postRequest);
        }
        catch (Exception e) {
            Log.i("ADVRE", " kjk" + e.toString());
        }

    }


    public void sendFilter() {
        jsonString = " ";
        String url = WebServiceUrl.GetSearch;
        String edtSearchFilter = " ";
        jsonString = "{\"AtrCode\":\"" + attrbiuteTableCode + "\",\"SubCat\":\"" + idSearchCat2 + "\",\"PageNumber\":\"" + getCountProductNew + "\",\"RowsPage\":\"" + G.countyRecive + "\"";
        if (edtSearch.getText() != null && edtSearch.getText().toString().trim().length() > 0) {

            jsonString = jsonString + ",\"Name\":\"" + edtSearch.getText().toString().trim() + "\"";

        }

        jsonString = jsonString + ",\"JustExsisted\":\"" + !setImageState + "\"";
        if (sortSelect != -1) {
            jsonString = jsonString + ",\"SortBy\":\"" + sortSelect + "\"";
        }

        jsonString = jsonString + ",\"Ascending\":\"" + aseSelect + "\"";

        if (latPoint != 0.1) {
            jsonString = jsonString + ",\"Latitude\":\"" + latPoint + "\"";
            jsonString = jsonString + ",\"Longitude\":\"" + lngPoint + "\"";
        }
        jsonString = jsonString + ",";
        // RequestQueue requestQueue = Volley.newRequestQueue(this);
        int t = 1;
        if (dataSend != null) {

            for (int y = 0; y < dataSend.length; y++) {
                if (dataSend[y] != null && dataSend[y].trim().length() > 0) {
                    jsonString = jsonString + "\"" + DataFilte.get(y).propertyName + "\":[" + dataSend[y] + "],";
                    t = 0;
                }
            }
        }
        if (t == 0) {
            jsonString = jsonString.substring(0, jsonString.length() - 1);
        }

        jsonString = jsonString + "}";
        Log.i("SEND", jsonString);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.i("Response", response.toString());
                DataKalaDiscount.clear();
                //  JSONObject object = new JSONObject(response);
                JSONArray jsonArray;
                try {
                    jsonArray = response.getJSONArray("CompleteProductsForList");

                    Log.i("ADC", "response " + jsonArray);

                    for (int i = 0; i < jsonArray.length(); i++) {

                        StructKala kala = new StructKala();
                        kala.name = jsonArray.getJSONObject(i).getJSONObject("ProductSummary").getString("Name");
                        kala.price = jsonArray.getJSONObject(i).getJSONObject("ProductSummary").getString("Price");
                        kala.id = jsonArray.getJSONObject(i).getJSONObject("ProductSummary").getInt("Id");
                        kala.picture = "www.hoojibooji.ir/" + jsonArray.getJSONObject(i).getJSONObject("ProductSummary").getString("ImgAddress");
                        kala.state = jsonArray.getJSONObject(i).getJSONObject("ProductSummary").getBoolean("IsExist");
                        Log.i("ADC", "response " + kala.name);
                        DataKalaDiscount.add(kala);
                    }

                    showListNewProduct();
                }
                catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                //txtLoading.setVisibility(View.GONE);
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("Response", error.toString());
                // do something...
            }
        }) {

            @Override
            public byte[] getBody() {
                return jsonString.getBytes();
            }
        };
        G.getInstance().addToRequestQueue(jsonObjectRequest);

    }


    private void showListNewProduct() {
        //Log.i("ADC", "response " + DataKalaDiscount.get(0).picture);
        if (DataKalaDiscount.size() == 0) {
            lstAll.setVisibility(View.GONE);
        } else {

            //  Data.set(1, id) int id = getResources().getIdentifier("yourpackagename:drawable/" + Data.get(1).bread, null, null);
            layFilter.setVisibility(View.GONE);
            lstAll.setVisibility(View.VISIBLE);
            lstAll.setAdapter(adapterListSearch);
            adapterListSearch.notifyDataSetChanged();
            //lstAll.setSelection(l);
        }

    }


    private void createSpinner() {

        cs = (ListView) findViewById(R.id.lst_st);
        final LinearLayout imgLeft = (LinearLayout) findViewById(R.id.lay_next_l_filter);
        final LinearLayout imgRight = (LinearLayout) findViewById(R.id.lay_next_r_filter);
        final TextView txtTitle = (TextView) findViewById(R.id.txt_cat_filter);

        txtTitle.setText("[ دسته بندی ]");
        layFilter.setVisibility(View.VISIBLE);

        Log.i("DDD", "" + listTemp.get(1).header);
        adapterList = new AdapterItemFilter(listTemp);
        Log.i("DDD", "" + "position");
        cs.setAdapter(adapterList);
        adapterList.notifyDataSetChanged();
        cs.setTextFilterEnabled(true);

        cs.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //color change
                //selected item colored
                for (int j = 0; j < parent.getChildCount(); j++)
                    parent.getChildAt(j).setBackgroundColor(Color.TRANSPARENT);

                // change the background color of the selected element
                view.setBackgroundColor(Color.parseColor("#A3C3FA"));
                Log.i("FIL", "" + position);
                if (cat == 1) {

                    StrucFilter val = (StrucFilter) (cs.getItemAtPosition(position));
                    listTemp2 = val.listItemFilter;
                    name = val.header;
                    pos = position;
                    txtTitle.setText("[ " + name + " ]");

                } else if (cat1 == 1) {
                    StrucItemFilter val = (StrucItemFilter) (cs.getItemAtPosition(position));

                    ImageView im = (ImageView) view.findViewById(R.id.img_ok_filter);
                    if (val.select == 1) {
                        int value = val.value;
                        listTemp2.get(position).select = 0;
                        listTemp.get(pos).select = 0;
                        im.setVisibility(View.GONE);

                        String str = "" + value;

                        Log.i("SEND", " " + dataSend[pos]);
                        if (dataSend[pos].contains(",")) {
                            Log.i("SEND", "" + "yes");
                            dataSend[pos] = dataSend[pos].replaceAll(("," + str), " ");
                        } else {
                            Log.i("SEND", "" + "no");
                            dataSend[pos] = dataSend[pos].replaceAll(str, " ");

                        }

                    } else {
                        int value = val.value;
                        listTemp2.get(position).select = 1;
                        listTemp.get(pos).select = 1;
                        im.setVisibility(View.VISIBLE);
                        String str = "" + value;
                        Log.i("SEND", str);
                        Log.i("SEND", "" + pos);
                        if (dataSend[pos] == null) {
                            dataSend[pos] = " ";
                            dataSend[pos] = dataSend[pos] + str;
                        } else {
                            dataSend[pos] = dataSend[pos] + "," + str;
                        }
                    }
                    // listTemp2.get(0).select = 1;
                    //Log.i("FIL", "" + val.name + "=== >" + val.id);
                }
                next();

            }
        });

        //  dialog3.show();

        imgRight.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                txtTitle.setText("" + name);
                next();
            }
        });
        imgLeft.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

                if (cat1 == 1) {
                    txtTitle.setText("[ دسته بندی ]");
                    // listTemp2.clear();
                    //listTemp.clear();
                    cat1 = 0;
                    cat = 1;

                    adapterList = new AdapterItemFilter(listTemp);
                    cs.setAdapter(adapterList);
                }
            }
        });

    }


    public void next() {

        if (cat == 1) {
            // txtTitle.setText("" + name);
            //listTemp.clear();
            cat1 = 1;
            cat = 0;

            adapterCat1List = new AdapterItemFilter2(listTemp2);
            cs.setAdapter(adapterCat1List);
        }
    }


    public void selectSort() {

        final Dialog dialog2 = new Dialog(G.currentActivity);
        dialog2.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog2.setContentView(R.layout.dialog_sort);

        LinearLayout layNew = (LinearLayout) dialog2.findViewById(R.id.lay_new);
        LinearLayout layPrice = (LinearLayout) dialog2.findViewById(R.id.lay_price);
        LinearLayout layView = (LinearLayout) dialog2.findViewById(R.id.lay_view);
        LinearLayout laySpecial = (LinearLayout) dialog2.findViewById(R.id.lay_special);
        LinearLayout layRate = (LinearLayout) dialog2.findViewById(R.id.lay_rate);
        LinearLayout layPopular = (LinearLayout) dialog2.findViewById(R.id.lay_popular);

        final ImageView imgNew = (ImageView) dialog2.findViewById(R.id.img_new);
        final ImageView imgPrice = (ImageView) dialog2.findViewById(R.id.img_price);
        final ImageView imgView = (ImageView) dialog2.findViewById(R.id.img_view);
        final ImageView imgSpecial = (ImageView) dialog2.findViewById(R.id.img_special);
        final ImageView imgRate = (ImageView) dialog2.findViewById(R.id.img_rate);
        final ImageView imgPopular = (ImageView) dialog2.findViewById(R.id.img_popular);

        Button btnOk = (Button) dialog2.findViewById(R.id.btn_ok);
        Button btnBack = (Button) dialog2.findViewById(R.id.btn_back);

        btnOk.setTypeface(G.font1);
        btnBack.setTypeface(G.font1);

        layNew.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

                newP = 1;
                priceP = 0;
                viewP = 0;
                specialP = 0;
                rateP = 0;
                popularP = 0;

                imgNew.setImageResource(R.drawable.pppp);

                imgPrice.setImageResource(R.drawable.ppp);
                imgView.setImageResource(R.drawable.ppp);
                imgSpecial.setImageResource(R.drawable.ppp);
                imgRate.setImageResource(R.drawable.ppp);
                imgPopular.setImageResource(R.drawable.ppp);

                sortSelect = 1;

            }
        });

        layPrice.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

                newP = 0;
                priceP = 1;
                viewP = 0;
                specialP = 0;
                rateP = 0;
                popularP = 0;

                imgPrice.setImageResource(R.drawable.pppp);

                imgNew.setImageResource(R.drawable.ppp);
                imgView.setImageResource(R.drawable.ppp);
                imgSpecial.setImageResource(R.drawable.ppp);
                imgRate.setImageResource(R.drawable.ppp);
                imgPopular.setImageResource(R.drawable.ppp);

                sortSelect = 2;

            }
        });

        layView.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

                newP = 0;
                priceP = 0;
                viewP = 1;
                specialP = 0;
                rateP = 0;
                popularP = 0;

                imgView.setImageResource(R.drawable.pppp);

                imgPrice.setImageResource(R.drawable.ppp);
                imgNew.setImageResource(R.drawable.ppp);
                imgSpecial.setImageResource(R.drawable.ppp);
                imgRate.setImageResource(R.drawable.ppp);
                imgPopular.setImageResource(R.drawable.ppp);

                sortSelect = 3;

            }
        });

        laySpecial.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

                newP = 0;
                priceP = 0;
                viewP = 0;
                specialP = 1;
                rateP = 0;
                popularP = 0;

                imgSpecial.setImageResource(R.drawable.pppp);

                imgView.setImageResource(R.drawable.ppp);
                imgPrice.setImageResource(R.drawable.ppp);
                imgNew.setImageResource(R.drawable.ppp);
                imgRate.setImageResource(R.drawable.ppp);
                imgPopular.setImageResource(R.drawable.ppp);

                sortSelect = 4;

            }
        });
        layRate.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

                newP = 0;
                priceP = 0;
                viewP = 0;
                specialP = 0;
                rateP = 1;
                popularP = 0;

                imgRate.setImageResource(R.drawable.pppp);

                imgSpecial.setImageResource(R.drawable.ppp);
                imgView.setImageResource(R.drawable.ppp);
                imgPrice.setImageResource(R.drawable.ppp);
                imgNew.setImageResource(R.drawable.ppp);
                imgPopular.setImageResource(R.drawable.ppp);

                sortSelect = 5;

            }
        });
        layPopular.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

                newP = 0;
                priceP = 0;
                viewP = 0;
                specialP = 0;
                rateP = 0;
                popularP = 1;

                imgPopular.setImageResource(R.drawable.pppp);

                imgRate.setImageResource(R.drawable.ppp);
                imgSpecial.setImageResource(R.drawable.ppp);
                imgView.setImageResource(R.drawable.ppp);
                imgPrice.setImageResource(R.drawable.ppp);
                imgNew.setImageResource(R.drawable.ppp);

                sortSelect = 6;

            }
        });
        btnBack.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

                dialog2.hide();
            }
        });

        btnOk.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

                String val = null;
                if (newP == 1) {
                    val = "1";
                } else if (priceP == 1) {
                    val = "2";
                }
                else if (viewP == 1) {
                    val = "3";

                } else if (specialP == 1) {
                    val = "4";
                } else if (rateP == 1) {
                    val = "4";
                } else if (popularP == 1) {
                    val = "4";
                } else {
                    val = "5";
                }

                dialog2.hide();
                Log.i("LOG", "val");

            }
        });
        //dialog2.setCancelable(false);
        dialog2.show();

    }


    public void selectAse() {

        final Dialog dialog2 = new Dialog(G.currentActivity);
        dialog2.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog2.setContentView(R.layout.dialog_asen);

        LinearLayout layAse = (LinearLayout) dialog2.findViewById(R.id.lay_ase);
        LinearLayout layDease = (LinearLayout) dialog2.findViewById(R.id.lay_dease);

        final ImageView imgAse = (ImageView) dialog2.findViewById(R.id.img_ase);
        final ImageView imgDeas = (ImageView) dialog2.findViewById(R.id.img_dease);

        Button btnOk = (Button) dialog2.findViewById(R.id.btn_ok);
        Button btnBack = (Button) dialog2.findViewById(R.id.btn_back);

        btnOk.setTypeface(G.font1);
        btnBack.setTypeface(G.font1);

        layAse.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

                ase = 1;
                dease = 0;

                imgAse.setImageResource(R.drawable.pppp);

                imgDeas.setImageResource(R.drawable.ppp);

                aseSelect = true;

            }
        });

        layDease.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

                ase = 0;
                dease = 1;

                imgDeas.setImageResource(R.drawable.pppp);

                imgAse.setImageResource(R.drawable.ppp);

                aseSelect = false;
            }
        });

        btnBack.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

                dialog2.hide();
            }
        });

        btnOk.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

                String val = null;
                if (ase == 1) {
                    val = "1";
                } else if (dease == 1) {
                    val = "2";

                } else {
                    val = "5";
                }

                dialog2.hide();

            }
        });
        //dialog2.setCancelable(false);
        dialog2.show();

    }
}