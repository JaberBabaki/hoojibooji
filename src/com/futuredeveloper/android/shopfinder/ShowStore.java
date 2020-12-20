package com.futuredeveloper.android.shopfinder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.futuredeveloper.android.shopfinder.customcontrol.AdapterCommodityTiles;
import com.futuredeveloper.android.shopfinder.customcontrol.MyGridView;
import com.futuredeveloper.android.shopfinder.internet.CheckInternet;
import com.futuredeveloper.android.shopfinder.internet.StrucStore;
import com.futuredeveloper.android.shopfinder.internet.WebServiceUrl;


public class ShowStore extends Enhance {

    TextView                            txtLoading;

    int                                 id;

    ImageLoader                         imageLoader      = G.getInstance().getImageLoader();
    NetworkImageView                    thumbNail;
    ImageView                           imageSliderDefault;

    TextView                            txtName;
    TextView                            salaryName;
    TextView                            cityStore;
    TextView                            typeStore;
    TextView                            webStore;
    TextView                            addrressStore;

    MyGridView                          griAll;
    ArrayAdapter                        adapterListDiscount;

    public static ArrayList<StructKala> DataKalaDiscount = new ArrayList<StructKala>();

    public ArrayList<StrucStore>        DataShowStore    = new ArrayList<StrucStore>();

    int                                 ErrorName        = 0;
    int                                 ErrorText        = 0;
    int                                 ErrorChangr      = 0;

    String                              P1l;
    String                              P2l;
    String                              P3l;
    String                              P4l;
    String                              P5l;


    @Override
    protected void onResume() {
        super.onResume();
        G.currentActivity = this;

    }


    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_store_root);

        Log.i("SHO", " k " + "object");
        adapterListDiscount = new AdapterCommodityTiles(DataKalaDiscount);
        griAll = (MyGridView) findViewById(R.id.gri_all_store);

        thumbNail = (NetworkImageView) findViewById(R.id.img_store_show);
        imageSliderDefault = (ImageView) findViewById(R.id.img_default_show);

        txtName = (TextView) findViewById(R.id.txt_name_store_show_details);
        salaryName = (TextView) findViewById(R.id.txt_salary_name_store_show);
        cityStore = (TextView) findViewById(R.id.txt_city_store_show);
        typeStore = (TextView) findViewById(R.id.txt_type_store_show);
        webStore = (TextView) findViewById(R.id.txt_web_details_store);
        addrressStore = (TextView) findViewById(R.id.txt_addrress_store_store_show);

        Bundle extras = getIntent().getExtras();
        id = 0;
        if (extras != null) {
            id = extras.getInt("id");
        }
        Log.i("SHO", " k " + "object");
        //final Button btnNewProduct = (Button) findViewById(R.id.btn_new_product);

        LinearLayout LayCheck = (LinearLayout) findViewById(R.id.lay_check_internet);
        LinearLayout LayMain = (LinearLayout) findViewById(R.id.lay_main_show_product);

        LinearLayout LayRefresh = (LinearLayout) findViewById(R.id.lay_check_refresh);
        LinearLayout LayMobile = (LinearLayout) findViewById(R.id.lay_check_mobile);
        LinearLayout LayWifi = (LinearLayout) findViewById(R.id.lay_check_wifi);
        Log.i("SHO", " k   " + id);
        txtLoading = (TextView) findViewById(R.id.txt_waite_show_product);

        // btnNewProduct.setTypeface(G.font1);

        final CheckInternet CheckInternet = new CheckInternet();
        if (CheckInternet.Access()) {

            reciveNewProduct(id);
            reciveDiscountProduct(id);
            LinearLayout LayGoToIdea = (LinearLayout) findViewById(R.id.lay_go_idea);
            LinearLayout LayPhone = (LinearLayout) findViewById(R.id.lay_call_store_store);
            LinearLayout LayReport = (LinearLayout) findViewById(R.id.lay_report_store);
            LinearLayout LayShaer = (LinearLayout) findViewById(R.id.lay_shaer_store);

            Button btnMap = (Button) findViewById(R.id.btn_go_map);
            btnMap.setTypeface(G.font1);

            LayGoToIdea.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    if (G.LoginOk.equals("1")) {
                        sendIdeaStore();
                    } else {

                        final Dialog dialog2 = new Dialog(G.currentActivity);
                        dialog2.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        dialog2.setContentView(R.layout.dialog_no_login);
                        Button btnB = (Button) dialog2.findViewById(R.id.btn_no_login_go_back);
                        btnB.setTypeface(G.font1);

                        Button btnL = (Button) dialog2.findViewById(R.id.btn_no_login_go_login);
                        btnL.setTypeface(G.font1);

                        //edtOstan.setEnabled(false);

                        btnB.setOnClickListener(new OnClickListener() {

                            @Override
                            public void onClick(View arg0) {
                                dialog2.dismiss();
                            }
                        });

                        btnL.setOnClickListener(new OnClickListener() {

                            @Override
                            public void onClick(View arg0) {
                                dialogLogin();
                                dialog2.dismiss();
                            }
                        });
                        dialog2.show();
                    }
                }
            });

            LayReport.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    if (G.LoginOk.equals("1")) {

                        sendReport("S", id);
                    } else {

                        final Dialog dialog2 = new Dialog(G.currentActivity);
                        dialog2.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        dialog2.setContentView(R.layout.dialog_no_login);
                        Button btnB = (Button) dialog2.findViewById(R.id.btn_no_login_go_back);
                        btnB.setTypeface(G.font1);

                        Button btnL = (Button) dialog2.findViewById(R.id.btn_no_login_go_login);
                        btnL.setTypeface(G.font1);

                        //edtOstan.setEnabled(false);

                        btnB.setOnClickListener(new OnClickListener() {

                            @Override
                            public void onClick(View arg0) {
                                dialog2.dismiss();
                            }
                        });

                        btnL.setOnClickListener(new OnClickListener() {

                            @Override
                            public void onClick(View arg0) {
                                dialogLogin();
                                dialog2.dismiss();
                            }
                        });
                        dialog2.show();
                    }
                }
            });

            LayPhone.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    /* if (DataShowStore.get(0).phone == null) {
                          txtName.setText("---");
                     } else {
                          String j = DataShowStore.get(0).name;
                          txtName.setText("  " + j);
                      }*/
                    Intent callIntent = new Intent(Intent.ACTION_DIAL);
                    String str = "" + DataShowStore.get(0).phone;
                    callIntent.setData(Uri.parse("tel:" + str));
                    G.currentActivity.startActivity(callIntent);

                }
            });

            LayShaer.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                    sharingIntent.setType("text/plain");
                    String shareBody = "نام فروشگاه : " + DataShowStore.get(0).name + "\n" + " آدرس فروشگاه: " + DataShowStore.get(0).place + "\n هوجی بوجی" + "\n" + "http://www.hoojibooji.ir";
                    sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject Here");
                    sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                    G.currentActivity.startActivity(Intent.createChooser(sharingIntent, "ارسال برای دیگران"));
                }
            });

            btnMap.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    float d1 = Float.parseFloat(DataShowStore.get(0).Latitude);
                    float d2 = Float.parseFloat(DataShowStore.get(0).Longitude);
                    try {
                        String uri = String.format(Locale.ENGLISH, "geo:%f,%f", d1, d2);
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                        G.currentActivity.startActivity(intent);
                    }
                    catch (Exception e) {
                        Log.i("MAP", e.toString());
                        Toast.makeText(G.context, "متاسفانه این امکان فعلا در دسترس نیست", Toast.LENGTH_LONG).show();
                    }
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
                onResume();
                //CheckInternet.refresh();
            }
        });

        menu();
    }


    public void sendIdeaStore() {

        final Dialog dialog22 = new Dialog(G.currentActivity);
        dialog22.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog22.setContentView(R.layout.dialog_send_idea_store);

        final EditText edtName = (EditText) dialog22.findViewById(R.id.edt_name_send_store);
        final EditText edtEmail = (EditText) dialog22.findViewById(R.id.edt_email_send_store);
        final EditText edtText = (EditText) dialog22.findViewById(R.id.edt_text_send_store);

        Button btnRegister = (Button) dialog22.findViewById(R.id.btn_ok_send_store_idea);
        Button btnBack = (Button) dialog22.findViewById(R.id.btn_back_send_store_idea);

        edtName.setTypeface(G.font1);
        edtEmail.setTypeface(G.font1);
        edtText.setTypeface(G.font1);
        btnRegister.setTypeface(G.font1);
        btnBack.setTypeface(G.font1);

        edtName.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                ErrorName = 0;
                ErrorChangr = 0;

            }


            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                // TODO Auto-generated method stub

            }


            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub

            }
        });
        edtText.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                ErrorText = 0;
                ErrorChangr = 0;
            }


            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                // TODO Auto-generated method stub

            }


            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub

            }
        });

        btnRegister.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                ConnectivityManager cm = (ConnectivityManager) G.currentActivity.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo netInfo = cm.getActiveNetworkInfo();
                if (netInfo != null && netInfo.isConnectedOrConnecting()) {

                    final String P1 = edtName.getText().toString().trim();
                    final String P2 = edtEmail.getText().toString().trim();
                    //final String P3 = G.phoneNumber;
                    //Toast.makeText(G.context, "number :==" + P3, Toast.LENGTH_SHORT).show();
                    final String P4 = edtText.getText().toString().trim();

                    if (P1.length() < 3 && P1.length() >= 0) {
                        ErrorName = 1;
                    }

                    if (P4.length() < 5 && P4.length() >= 0) {
                        ErrorText = 1;
                    }

                    if (P1.equals(P1l) && P2.equals(P2l) && P4.equals(P4l)) {
                        ErrorChangr = 1;
                    }

                    final Dialog dialog5 = new Dialog(G.currentActivity);
                    dialog5.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    dialog5.setContentView(R.layout.dialog_error);
                    TextView txtHeaderError = (TextView) dialog5.findViewById(R.id.txt_header_error);
                    final TextView txtNameError = (TextView) dialog5.findViewById(R.id.txt_name_error);
                    final TextView txtFamilyError = (TextView) dialog5.findViewById(R.id.txt_family_eror);
                    final TextView txtAddressError = (TextView) dialog5.findViewById(R.id.txt_address_error);
                    final TextView txtChangeError = (TextView) dialog5.findViewById(R.id.txt_change_error);
                    Button btnOk = (Button) dialog5.findViewById(R.id.btn_ok);

                    btnOk.setTypeface(G.font1);
                    btnOk.setOnClickListener(new OnClickListener() {

                        @Override
                        public void onClick(View arg0) {
                            txtNameError.setVisibility(View.GONE);
                            txtFamilyError.setVisibility(View.GONE);
                            txtAddressError.setVisibility(View.GONE);
                            txtChangeError.setVisibility(View.GONE);
                            dialog5.dismiss();
                        }
                    });
                    dialog5.setCancelable(false);

                    if (ErrorName == 1) {
                        txtNameError.setVisibility(View.VISIBLE);
                        txtNameError.setText("نام وارد شده حداقل 3 حرفی باید باشد");
                    }
                    if (ErrorText == 1) {
                        txtFamilyError.setVisibility(View.VISIBLE);
                        txtFamilyError.setText("متن نظر باید بیشتر از 5 حرف باشد");
                    }

                    if (ErrorName == 0 && ErrorText == 0 && ErrorChangr == 1) {
                        txtChangeError.setVisibility(View.VISIBLE);
                        //txtChangeError.setBackgroundResource(R.drawable.selector_error);
                        txtChangeError.setText("لطفا داده های ورودی را تغییر دهید");
                    }
                    if (ErrorName == 0 && ErrorText == 0 && ErrorChangr == 0) {
                        //recive(P1, P2, P4);
                    } else {
                        dialog5.show();
                    }

                    P1l = P1;
                    P2l = P2;
                    P4l = P4;

                } else {
                    final Dialog dialog2 = new Dialog(G.currentActivity);
                    dialog2.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    //dialog2.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
                    dialog2.setContentView(R.layout.dialog_conect);
                    TextView txt_header_conect = (TextView) dialog2.findViewById(R.id.txt_header_conected);
                    TextView txt_message_conect = (TextView) dialog2.findViewById(R.id.txt_message_conected);
                    Button btn_setting = (Button) dialog2.findViewById(R.id.btn_setting);
                    Button btn_back = (Button) dialog2.findViewById(R.id.btn_back);
                    txt_header_conect.setTypeface(G.font1);
                    txt_message_conect.setTypeface(G.font1);
                    btn_back.setTypeface(G.font1);
                    btn_setting.setTypeface(G.font1);
                    btn_setting.setOnClickListener(new OnClickListener() {

                        @Override
                        public void onClick(View arg0) {
                            // img_sign.setVisibility(View.GONE);
                            Intent intent = new Intent(Intent.ACTION_MAIN);
                            intent.setClassName("com.android.settings", "com.android.settings.wifi.WifiSettings");
                            startActivity(intent);
                        }
                    });
                    btn_back.setOnClickListener(new OnClickListener() {

                        @Override
                        public void onClick(View arg0) {
                            // img_sign.setVisibility(View.GONE);
                            dialog2.dismiss();
                        }
                    });
                    dialog2.setCancelable(false);
                    dialog2.show();
                }

            }
        });
        btnBack.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                dialog22.dismiss();

            }
        });
        dialog22.show();
    }


    private void showDetailsStore() {

        Log.i("SHO", " showDetailsStore " + "object");
        if ( !DataShowStore.get(0).picture.equals("null") && !DataShowStore.get(0).picture.equals("")) {
            Log.i("SHO", " showDetailsStore222 " + "object");
            thumbNail.setVisibility(View.VISIBLE);
            imageSliderDefault.setVisibility(View.GONE);

            String link = DataShowStore.get(0).picture.replaceAll("\\\\", "/");

            imageLoader.get("http://" + link, ImageLoader.getImageListener(thumbNail, R.drawable.loading, R.drawable.default_produ));

            thumbNail.setImageUrl("http://" + link, imageLoader);
        } else {
            Log.i("SHO", " showDetailsStore2efe2 " + "object");
            imageSliderDefault.setVisibility(View.VISIBLE);
            thumbNail.setVisibility(View.GONE);
            imageSliderDefault.setImageResource(R.drawable.default_produ);

        }

        if (DataShowStore.get(0).name.equals("") || DataShowStore.get(0).name.equals("null")) {
            txtName.setText("---");
        } else {
            String j = DataShowStore.get(0).name;
            txtName.setText("  " + j);
        }
        Log.i("SHO", " kname " + DataShowStore.get(0).name);

        if (DataShowStore.get(0).cityName.equals("") || DataShowStore.get(0).cityName.equals("null")) {
            cityStore.setText("---");
        } else {
            cityStore.setText(DataShowStore.get(0).cityName);
        }

        Log.i("SHO", " salarName " + "object");
        if (DataShowStore.get(0).salarName.equals("null") || DataShowStore.get(0).salarName.equals("")) {
            salaryName.setText("---");
        } else {
            salaryName.setText(DataShowStore.get(0).salarName);
        }

        Log.i("SHO", " type " + "object");
        if (DataShowStore.get(0).type.equals("null") || DataShowStore.get(0).type.equals("")) {
            typeStore.setText("---");
        } else {
            typeStore.setText(DataShowStore.get(0).type);
        }

        Log.i("SHO", " site " + "object");
        if (DataShowStore.get(0).site.equals("null") || DataShowStore.get(0).site.equals("")) {
            webStore.setText("---");
        } else {
            webStore.setText(DataShowStore.get(0).site);
        }

        Log.i("SHO", " place " + "object");
        if (DataShowStore.get(0).place.equals("null") || DataShowStore.get(0).place.equals("")) {
            addrressStore.setText("---");
        } else {
            addrressStore.setText(DataShowStore.get(0).site);
        }

        //price.setText("" + DataShowStore.get(0).price);

    }


    public void reciveNewProduct(final int id) {
        try {

            txtLoading.setVisibility(View.VISIBLE);
            String url = WebServiceUrl.GetShowStore + "storeCode=" + id;

            StringRequest postRequest = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {

                        @Override
                        public void onResponse(String response) {

                            try {
                                JSONObject object = new JSONObject(response);
                                JSONArray jsonArray = object.getJSONArray("Images");
                                JSONArray jsonArray2 = object.getJSONArray("Tells");
                                JSONArray jsonArray3 = object.getJSONArray("Categories");
                                // JSONObject jsonobjectP = array.getJSONObject(0);
                                //JSONObject jsonobjectS = object.getJSONObject("StoreSummery");
                                Log.i("STR", " k " + object);
                                Log.i("STR", " k " + jsonArray);
                                Log.i("STR", " k " + jsonArray2);
                                Log.i("STR", " k " + jsonArray3);

                                StrucStore store = new StrucStore();
                                Log.i("STR", " k 1");

                                store.name = object.getString("Name");
                                Log.i("STR", store.name);
                                store.cityName = object.getString("CityName");
                                Log.i("STR", store.cityName + "   ");
                                if ( !(jsonArray2.length() == 0)) {
                                    store.cat = jsonArray3.getString(0);
                                    Log.i("STR", store.cat + " ");
                                } else {
                                    store.cat = "";
                                }
                                if ( !(jsonArray2.length() == 0)) {
                                    store.phone = jsonArray2.getInt(0);
                                    Log.i("STR", store.phone + " ");
                                } else {
                                    store.phone = 0;
                                }
                                if ( !(jsonArray.length() == 0)) {
                                    store.picture = "www.hoojibooji.ir/" + jsonArray.getString(0);
                                } else {
                                    store.picture = "";
                                }
                                Log.i("STR", store.picture + " ");
                                store.place = object.getString("Place");
                                Log.i("STR", store.place + " ");
                                store.logo = object.getString("LogoAddress");
                                Log.i("STR", store.logo + "logo ");
                                store.Latitude = object.getString("Latitude");
                                Log.i("STR", store.Latitude + " Latitude");
                                store.Longitude = object.getString("Longitude");
                                Log.i("STR", store.Latitude + "Longitude ");
                                store.email = object.getString("Email");
                                Log.i("STR", store.email + " ");
                                store.site = object.getString("Website");
                                Log.i("STR", store.site + " ");
                                store.type = object.getString("StoreTypeName");
                                Log.i("STR", store.type + " ");
                                store.salarName = object.getString("SallerName");
                                Log.i("STR", store.salarName + " ");

                                DataShowStore.add(store);

                                txtLoading.setVisibility(View.GONE);
                                showDetailsStore();
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
                    });

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


    public void reciveDiscountProduct(final int strCode) {
        try {
            Log.i("HHH", "jaber   " + "response");
            // txtLoading.setVisibility(View.VISIBLE);
            String url = WebServiceUrl.GetNewKala;
            Log.i("HHH", "jaber   " + url);

            StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {

                        @Override
                        public void onResponse(String response) {

                            try {
                                DataKalaDiscount.clear();
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
                                    DataKalaDiscount.add(kala);
                                }

                                showListDiscountProduct();
                                // txtLoading.setVisibility(View.GONE);
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
                            //txtLoading.setVisibility(View.GONE);
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
                    params.put("pageNumber", "1");
                    params.put("rowsPage", "10");
                    params.put("storeCode", "" + strCode);

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


    private void showListDiscountProduct() {
        //lstAll.setVisibility(View.GONE);
        griAll.setVisibility(View.VISIBLE);
        if (DataKalaDiscount.size() == 0) {
            griAll.setVisibility(View.GONE);

        } else {
            //  Data.set(1, id) int id = getResources().getIdentifier("yourpackagename:drawable/" + Data.get(1).bread, null, null);

            griAll.setAdapter(adapterListDiscount);
            adapterListDiscount.notifyDataSetChanged();
            // griAll.setSelection(l);
        }

    }

}