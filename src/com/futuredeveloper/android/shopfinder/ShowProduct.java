package com.futuredeveloper.android.shopfinder;

import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.futuredeveloper.android.shopfinder.customcontrol.AdapterCommodityTiles;
import com.futuredeveloper.android.shopfinder.customcontrol.AdapterDetilsProduct;
import com.futuredeveloper.android.shopfinder.internet.CheckInternet;
import com.futuredeveloper.android.shopfinder.internet.StrucDetialsProduct;
import com.futuredeveloper.android.shopfinder.internet.StrucShowProduct;
import com.futuredeveloper.android.shopfinder.internet.WebServiceUrl;


public class ShowProduct extends Enhance {

    TextView                              txtLoading;

    int                                   id;

    ImageLoader                           imageLoader       = G.getInstance().getImageLoader();
    NetworkImageView                      thumbNail;
    ImageView                             imageSliderDefault;

    TextView                              txtName;
    TextView                              price;
    TextView                              state;
    TextView                              nameStore;
    TextView                              addrressStore;

    public static ArrayList<StructKala>   DataKalaDiscount  = new ArrayList<StructKala>();

    public ArrayList<StrucShowProduct>    DataShowProduct   = new ArrayList<StrucShowProduct>();
    public ArrayList<StrucDetialsProduct> DataDetilsProduct = new ArrayList<StrucDetialsProduct>();

    ArrayAdapter                          adapterListDiscount;

    ArrayAdapter                          adapterListDetils;
    ListView                              lstAll;


    @Override
    protected void onResume() {
        super.onResume();
        G.currentActivity = this;
    }


    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_product_root);

        thumbNail = (NetworkImageView) findViewById(R.id.img_product_show);
        imageSliderDefault = (ImageView) findViewById(R.id.img_default_show);
        txtName = (TextView) findViewById(R.id.txt_name_product_show);
        price = (TextView) findViewById(R.id.txt_price_product_show);
        state = (TextView) findViewById(R.id.txt_state_product_show);
        nameStore = (TextView) findViewById(R.id.txt_name_store_product_show);
        addrressStore = (TextView) findViewById(R.id.txt_addrress_store_product_show);

        adapterListDiscount = new AdapterCommodityTiles(G.DataKalaDiscount);

        lstAll = (ListView) findViewById(R.id.lst_all);
        adapterListDetils = new AdapterDetilsProduct(DataDetilsProduct);

        Bundle extras = getIntent().getExtras();
        id = 0;
        if (extras != null) {
            id = extras.getInt("id");
        }

        //final Button btnNewProduct = (Button) findViewById(R.id.btn_new_product);

        LinearLayout LayCheck = (LinearLayout) findViewById(R.id.lay_check_internet);
        LinearLayout LayMain = (LinearLayout) findViewById(R.id.lay_main_show_product);

        LinearLayout LayRefresh = (LinearLayout) findViewById(R.id.lay_check_refresh);
        LinearLayout LayMobile = (LinearLayout) findViewById(R.id.lay_check_mobile);
        LinearLayout LayWifi = (LinearLayout) findViewById(R.id.lay_check_wifi);

        txtLoading = (TextView) findViewById(R.id.txt_waite_show_product);

        // btnNewProduct.setTypeface(G.font1);

        final CheckInternet CheckInternet = new CheckInternet();
        if (CheckInternet.Access()) {

            reciveNewProduct(id);

            LinearLayout LayGoToStore = (LinearLayout) findViewById(R.id.lay_go_store);
            // LinearLayout LayPhone = (LinearLayout) findViewById(R.id.lay_call_store);
            LinearLayout LayReport = (LinearLayout) findViewById(R.id.lay_report_product);
            LinearLayout LayShaer = (LinearLayout) findViewById(R.id.lay_shaer_product);

            LayGoToStore.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    Intent intentm = new Intent(G.currentActivity, ShowStore.class);
                    intentm.putExtra("id", DataShowProduct.get(0).idStore);
                    G.currentActivity.finish();
                    G.currentActivity.startActivity(intentm);
                }
            });

            LayReport.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    if (G.LoginOk.equals("1")) {

                        sendReport("M", id);
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

            LayShaer.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                    sharingIntent.setType("text/plain");
                    String shareBody = "نام محصول : " + DataShowProduct.get(0).name + "\n" + " قیمت" + DataShowProduct.get(0).price + " \n" + "نام فروشگاه : " + DataShowProduct.get(0).storeName + "\n آدرس فروشگاه : " + DataShowProduct.get(0).storeAddrress + "\n هوجی بوجی" + "\n" + "http://www.hoojibooji.ir";
                    sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject Here");
                    sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                    G.currentActivity.startActivity(Intent.createChooser(sharingIntent, "ارسال برای دیگران"));
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
                //Toast.makeText(G.context, "text", Toast.LENGTH_SHORT).show();
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
                reciveNewProduct(id);
            }
        });

        menu();
    }


    private void showDetailsProduct() {

        Log.i("SHO", " k " + "object");
        if ( !DataShowProduct.get(0).picture.equals(null)) {

            thumbNail.setVisibility(View.VISIBLE);
            imageSliderDefault.setVisibility(View.GONE);

            String link = DataShowProduct.get(0).picture.replaceAll("\\\\", "/");

            imageLoader.get("http://" + link, ImageLoader.getImageListener(thumbNail, R.drawable.loading, R.drawable.default_produ));

            thumbNail.setImageUrl("http://" + link, imageLoader);
        } else {
            imageSliderDefault.setVisibility(View.VISIBLE);
            thumbNail.setVisibility(View.GONE);

            Log.i("PPP", "http:// no");
            imageSliderDefault.setImageResource(R.drawable.default_produ);

        }

        txtName.setText(DataShowProduct.get(0).name);

        if (DataShowProduct.get(0).state == null || DataShowProduct.get(0).state.equals("")) {
            state.setText("---");
        } else {
            if (DataShowProduct.get(0).state == true) {
                state.setText("موجود");
            } else {
                state.setText("ناموجود");
            }
        }

        if (DataShowProduct.get(0).storeName.equals(null)) {
            nameStore.setText("---");
        } else {
            nameStore.setText(DataShowProduct.get(0).storeName);
        }

        if (DataShowProduct.get(0).storeAddrress.equals(null)) {
            addrressStore.setText("---");
        } else {
            addrressStore.setText(DataShowProduct.get(0).storeAddrress);
        }

        price.setText("" + DataShowProduct.get(0).price);

        if (DataDetilsProduct.size() == 0) {
            lstAll.setVisibility(View.GONE);
        } else {

            //  Data.set(1, id) int id = getResources().getIdentifier("yourpackagename:drawable/" + Data.get(1).bread, null, null);
            lstAll.setVisibility(View.VISIBLE);
            lstAll.setAdapter(adapterListDetils);
            adapterListDetils.notifyDataSetChanged();
            //lstAll.setSelection(l);
        }

    }


    public void reciveNewProduct(final int id) {
        try {

            txtLoading.setVisibility(View.VISIBLE);
            String url = WebServiceUrl.GetShowProduct + "productId=" + id;

            StringRequest postRequest = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {

                        @Override
                        public void onResponse(String response) {

                            try {
                                JSONObject object = new JSONObject(response);
                                JSONObject jsonobjectP = object.getJSONObject("Product");
                                JSONObject jsonobjectS = object.getJSONObject("StoreSummery");
                                JSONArray jsonA = object.getJSONArray("DynamicProductProperties");
                                Log.i("SHO", " k " + object);

                                StrucShowProduct kala = new StrucShowProduct();
                                Log.i("SHO", " k 1");
                                kala.name = jsonobjectP.getString("Name");
                                Log.i("SHO", kala.name);
                                kala.price = jsonobjectP.getInt("Price");
                                Log.i("SHO", kala.price + " ");
                                kala.state = jsonobjectP.getBoolean("IsExist");
                                Log.i("SHO", kala.state + " ");
                                kala.picture = "www.hoojibooji.ir/" + jsonobjectP.getString("ImgAddress");
                                Log.i("SHO", kala.picture + " ");

                                kala.storeName = jsonobjectS.getString("Name");
                                Log.i("SHO", kala.storeName + " ");
                                kala.storeAddrress = jsonobjectS.getString("Place");
                                Log.i("SHO", kala.storeAddrress + " ");
                                kala.idStore = jsonobjectS.getInt("Id");
                                Log.i("SHO", kala.idStore + " ");
                                DataShowProduct.add(kala);

                                for (int i = 0; i < jsonA.length(); i++) {

                                    StrucDetialsProduct Dekala = new StrucDetialsProduct();
                                    Dekala.header = jsonA.getJSONObject(i).getString("Header");
                                    Dekala.value = jsonA.getJSONObject(i).getString("Value");
                                    Log.i("QQQ", "Dekala.header  " + Dekala.header);
                                    DataDetilsProduct.add(Dekala);

                                }
                                txtLoading.setVisibility(View.GONE);
                                showDetailsProduct();
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

}