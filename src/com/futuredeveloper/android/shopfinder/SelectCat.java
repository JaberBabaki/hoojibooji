package com.futuredeveloper.android.shopfinder;

import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.futuredeveloper.android.shopfinder.customcontrol.AdapterLitCat;
import com.futuredeveloper.android.shopfinder.customcontrol.AdapterLitCat1;
import com.futuredeveloper.android.shopfinder.customcontrol.AdapterLitCat2;
import com.futuredeveloper.android.shopfinder.internet.CheckInternet;
import com.futuredeveloper.android.shopfinder.internet.StrucCategories;
import com.futuredeveloper.android.shopfinder.internet.StrucSubCat1List;
import com.futuredeveloper.android.shopfinder.internet.StrucSubCat2List;
import com.futuredeveloper.android.shopfinder.internet.WebServiceUrl;


public class SelectCat extends Enhance {

    TextView                                  txtLoading;

    int                                       id;

    ImageLoader                               imageLoader = G.getInstance().getImageLoader();
    NetworkImageView                          thumbNail;
    ImageView                                 imageSliderDefault;

    TextView                                  txtName;
    TextView                                  price;
    TextView                                  state;
    TextView                                  nameStore;
    TextView                                  addrressStore;

    // public static ArrayList<StrucCategories>  Datacategory    = new ArrayList<StrucCategories>();

    // public static ArrayList<StrucSubCat1List> DataSubCat1List = new ArrayList<StrucSubCat1List>();

    // public static ArrayList<StrucSubCat2List> DataSubCat2List = new ArrayList<StrucSubCat2List>();

    public static ArrayList<StrucCategories>  list        = new ArrayList<StrucCategories>();
    public static ArrayList<StrucCategories>  Data2       = new ArrayList<StrucCategories>();
    ArrayAdapter                              adapterList;
    String                                    name;
    Dialog                                    dialog3;
    int                                       cat         = 1;

    public static ArrayList<StrucSubCat1List> list1       = new ArrayList<StrucSubCat1List>();
    public static ArrayList<StrucSubCat1List> DataSub1    = new ArrayList<StrucSubCat1List>();
    ArrayAdapter                              adapterCat1List;
    int                                       cat1        = 0;

    public static ArrayList<StrucSubCat2List> list2       = new ArrayList<StrucSubCat2List>();
    public static ArrayList<StrucSubCat2List> DataSub2    = new ArrayList<StrucSubCat2List>();
    ArrayAdapter                              adapterCat2List;
    int                                       cat2        = 0;

    int                                       idExtras    = -1;
    ListView                                  cs;
    LinearLayout                              layFilter;


    @Override
    protected void onResume() {
        super.onResume();
        G.currentActivity = this;
    }


    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_cat_root);
        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            idExtras = extras.getInt("id");
        }

        LinearLayout LayCheck = (LinearLayout) findViewById(R.id.lay_check_internet);
        LinearLayout LayMain = (LinearLayout) findViewById(R.id.lay_main_advance_search);

        LinearLayout LayRefresh = (LinearLayout) findViewById(R.id.lay_check_refresh);
        LinearLayout LayMobile = (LinearLayout) findViewById(R.id.lay_check_mobile);
        LinearLayout LayWifi = (LinearLayout) findViewById(R.id.lay_check_wifi);

        //txtLoading = (TextView) findViewById(R.id.txt_waite_show_product);

        // btnNewProduct.setTypeface(G.font1);

        final CheckInternet CheckInternet = new CheckInternet();
        if (CheckInternet.Access()) {

            if (G.Datacategory.size() < 2) {
                reciveFilter();
            } else {
                if (idExtras != -1) {

                    cat1 = 1;
                    cat2 = 0;
                    cat = 0;

                    list1.clear();
                    for (int o = 0; o <= G.DataSubCat1List.size() - 1; o++) {
                        if (G.DataSubCat1List.get(o).catId == idExtras) {

                            list1.add(G.DataSubCat1List.get(o));

                        }
                    }
                    Log.i("SEC", "" + idExtras);
                    createSpinner();

                } else {
                    cat1 = 0;
                    cat2 = 0;
                    cat = 1;
                    list.clear();
                    list.addAll(G.Datacategory);
                    createSpinner();
                }
            }

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
                //  Toast.makeText(G.context, "text", Toast.LENGTH_SHORT).show();
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
                                list.clear();
                                list.addAll(G.Datacategory);
                                createSpinner();
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


    private void createSpinner() {

        //list.clear();
        //Data.clear();

        //  dialog3 = new Dialog(G.currentActivity);
        //  dialog3.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        // dialog3.setContentView(R.layout.dialog_filter);
        cs = (ListView) findViewById(R.id.lst_st);
        final EditText edtOstan = (EditText) findViewById(R.id.edt_search_filter);
        // final Button btnBack = (Button) findViewById(R.id.btn_back_filter);
        // final Button btnSearch = (Button) findViewById(R.id.btn_search_filter);
        final ImageView imgLeft = (ImageView) findViewById(R.id.img_l_filter);
        final ImageView imgRight = (ImageView) findViewById(R.id.img_r_filter);

        edtOstan.setHint("انتخاب دسته بندی");

        edtOstan.setTypeface(G.font1);
        edtOstan.clearFocus();

        if (idExtras != -1) {

            adapterCat1List = new AdapterLitCat1(list1);
            cs.setAdapter(adapterCat1List);
            cs.setTextFilterEnabled(true);

        } else {

            adapterList = new AdapterLitCat(list);
            cs.setAdapter(adapterList);
            cs.setTextFilterEnabled(true);
        }

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
                    StrucCategories val = (StrucCategories) (cs.getItemAtPosition(position));
                    G.idSearchCat = val.id;
                } else if (cat1 == 1) {
                    StrucSubCat1List val = (StrucSubCat1List) (cs.getItemAtPosition(position));
                    G.idSearchCat1 = val.id;
                    Log.i("FIL", "" + val.name + "=== >" + val.id);
                } else if (cat2 == 1) {
                    StrucSubCat2List val = (StrucSubCat2List) (cs.getItemAtPosition(position));
                    G.idSearchCat2 = val.id;
                    G.attrbiuteTableCode = val.attribTableCode;
                    G.subCat2Code = val.subCatId;
                }

                next();

            }
        });

        // dialog3.show();

        edtOstan.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int arg1, int arg2, int arg3) {
                String searchString = edtOstan.getText().toString().trim();

                int textLength = searchString.length();
                if (cat == 1) {
                    Data2.clear();
                    for (int i = 0; i <= (list.size() - 1); i++) {

                        name = list.get(i).name;

                        if (textLength <= name.length()) {

                            if (searchString.equalsIgnoreCase(name.substring(0, textLength)))
                                Data2.add(list.get(i));
                        }
                    }

                    adapterList = new AdapterLitCat(Data2);
                    cs.setAdapter(adapterList);
                    adapterList.notifyDataSetChanged();
                } else if (cat1 == 1) {
                    DataSub1.clear();
                    for (int i = 0; i <= (list1.size() - 1); i++) {

                        name = list1.get(i).name;

                        if (textLength <= name.length()) {

                            if (searchString.equalsIgnoreCase(name.substring(0, textLength)))
                                DataSub1.add(list1.get(i));
                        }
                    }

                    adapterCat1List = new AdapterLitCat1(DataSub1);
                    cs.setAdapter(adapterCat1List);
                    adapterCat1List.notifyDataSetChanged();

                } else if (cat2 == 1) {
                    DataSub2.clear();
                    for (int i = 0; i <= (list2.size() - 1); i++) {

                        name = list2.get(i).name;

                        if (textLength <= name.length()) {

                            if (searchString.equalsIgnoreCase(name.substring(0, textLength)))
                                DataSub2.add(list2.get(i));
                        }
                    }

                    adapterCat2List = new AdapterLitCat2(DataSub2);
                    cs.setAdapter(adapterCat2List);
                    adapterCat2List.notifyDataSetChanged();

                }
            }


            @Override
            public void beforeTextChanged(CharSequence s, int arg1, int arg2, int arg3) {

            }


            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        imgRight.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                next();

            }
        });
        imgLeft.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                name = " ";
                if (cat1 == 1) {
                    list.clear();
                    cat1 = 0;
                    cat = 1;
                    cat2 = 0;

                    list.addAll(G.Datacategory);
                    adapterList = new AdapterLitCat(list);
                    cs.setAdapter(adapterList);
                    cs.setTextFilterEnabled(true);
                } else if (cat2 == 1) {
                    list1.clear();

                    cat1 = 1;
                    cat = 0;
                    cat2 = 0;

                    for (int o = 0; o <= G.DataSubCat1List.size() - 1; o++) {
                        if (G.DataSubCat1List.get(o).catId == G.idSearchCat) {

                            list1.add(G.DataSubCat1List.get(o));
                            adapterCat1List = new AdapterLitCat1(list1);
                            cs.setAdapter(adapterCat1List);
                        }
                    }
                }
            }
        });

        /* btnBack.setOnClickListener(new OnClickListener() {

             @Override
             public void onClick(View arg0) {
                 cat1 = 0;
                 cat2 = 0;
                 cat = 1;
                 G.idSearchCat = -1;
                 G.idSearchCat1 = -1;
                 G.idSearchCat2 = -1;
                 // dialog3.hide();
             }
         });*/
    }


    public void next() {

        name = " ";
        if (cat == 1) {
            list1.clear();

            for (int o = 0; o <= G.DataSubCat1List.size() - 1; o++) {
                if (G.DataSubCat1List.get(o).catId == G.idSearchCat) {

                    list1.add(G.DataSubCat1List.get(o));
                    adapterCat1List = new AdapterLitCat1(list1);
                    cs.setAdapter(adapterCat1List);
                }
            }
            if ( !(list1.isEmpty())) {
                cat1 = 1;
                cat = 0;
                cat2 = 0;
            } else {
                Toast.makeText(G.context, "برای دسته بندی زیر مجموعه ای وجود ندارد", Toast.LENGTH_LONG).show();
            }

        } else if (cat1 == 1) {
            list2.clear();

            for (int o = 0; o <= G.DataSubCat2List.size() - 1; o++) {
                if (G.DataSubCat2List.get(o).subCatId == G.idSearchCat1) {

                    list2.add(G.DataSubCat2List.get(o));
                    adapterCat2List = new AdapterLitCat2(list2);
                    cs.setAdapter(adapterCat2List);
                }
            }
            if ( !(list2.isEmpty())) {
                cat1 = 0;
                cat = 0;
                cat2 = 1;
            } else {
                Toast.makeText(G.context, "برای دسته بندی زیر مجموعه ای وجود ندارد", Toast.LENGTH_LONG).show();
            }
        } else if (cat2 == 1) {

            int attrbiuteTableCode = G.attrbiuteTableCode;
            int idSearchCat2 = G.idSearchCat2;
            Log.i("SEE", "" + "link  " + attrbiuteTableCode + "  " + idSearchCat2);
            Intent intentm = new Intent(G.currentActivity, AdvanceSearch.class);
            intentm.putExtra("attrbiuteTableCode", attrbiuteTableCode);
            intentm.putExtra("idSearchCat2", idSearchCat2);
            intentm.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            finish();
            G.currentActivity.startActivity(intentm);

        }

    }

}