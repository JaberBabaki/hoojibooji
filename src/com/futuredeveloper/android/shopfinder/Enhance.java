package com.futuredeveloper.android.shopfinder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
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
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.futuredeveloper.android.database.Check;
import com.futuredeveloper.android.database.DataLogin;
import com.futuredeveloper.android.database.Ostan;
import com.futuredeveloper.android.database.StrucOstan;
import com.futuredeveloper.android.shopfinder.customcontrol.AdapterListCity;
import com.futuredeveloper.android.shopfinder.customcontrol.AdapterLitOstan;
import com.futuredeveloper.android.shopfinder.internet.CheckInternet;
import com.futuredeveloper.android.shopfinder.internet.MD5;
import com.futuredeveloper.android.shopfinder.internet.WebServiceUrl;


public class Enhance extends FragmentActivity {

    EditText                edtUserName;
    EditText                edtPas;
    EditText                edtPasConfim;

    boolean                 state               = true;

    int                     ErrorUserName       = 0;
    int                     ErrorPassword       = 0;
    int                     ErrorConfimPassword = 0;

    public static ViewGroup layLogin;

    public static ViewGroup layDigital;
    public static ViewGroup layBeau;
    public static ViewGroup layClo;
    public static ViewGroup layAnother;
    public static ViewGroup layContact;

    public static TextView  txtLogin;
    public static ViewGroup layOstan;
    public static ViewGroup layPanel;
    public static ViewGroup layAdvanceSearch;
    public static TextView  txtUserName;

    private Dialog          dialog2;

    private int             op1                 = 0;
    private int             op2                 = 0;
    private int             op3                 = 0;
    private int             op4                 = 0;

    private int             veryGoog            = 0;
    private int             Goog                = 0;
    private int             notBad              = 0;
    private int             weak                = 0;

    int                     ErrorName           = 0;
    int                     ErrorText           = 0;
    int                     ErrorChangr         = 0;

    String                  P1l;
    String                  P2l;
    String                  P3l;
    String                  P4l;
    String                  P5l;

    String                  url                 = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // checkLoginOk();

    }


    public static void getWel() {
        /*final Check check = new Check();
        int y = check.getWelcom();
        if (y == 0) {
            // Toast.makeText(G.context, "sghl  " + y, Toast.LENGTH_SHORT).show();
            final Dialog dialogChangeRecent = new Dialog(G.currentActivity);
            dialogChangeRecent.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
            dialogChangeRecent.setContentView(R.layout.dialog_welcom);
            dialogChangeRecent.getOwnerActivity();
            dialogChangeRecent.onBackPressed();
            Button btn_back = (Button) dialogChangeRecent.findViewById(R.id.btn_ok_welcom);
            btn_back.setTypeface(G.font1);
            btn_back.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    check.updateWelcom();
                    dialogChangeRecent.dismiss();
                }
            });

            dialogChangeRecent.setCancelable(false);
            //if (G.dialogChangeRecent.isShowing()) {
            dialogChangeRecent.show();
            //}
        } else {

        }*/
    }


    public void sendNazar(final int p) {

        final Dialog dialog2 = new Dialog(G.currentActivity);
        dialog2.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog2.setContentView(R.layout.dialog_survey);

        LinearLayout layVeryGood = (LinearLayout) dialog2.findViewById(R.id.lay_very_good);
        LinearLayout layGood = (LinearLayout) dialog2.findViewById(R.id.lay_good);
        LinearLayout layNotBad = (LinearLayout) dialog2.findViewById(R.id.lay_not_bad);
        LinearLayout layWeak = (LinearLayout) dialog2.findViewById(R.id.lay_weak);

        final ImageView imgVeryGood = (ImageView) dialog2.findViewById(R.id.img_very_goood);
        final ImageView imgGood = (ImageView) dialog2.findViewById(R.id.img_goood);
        final ImageView imgNotBad = (ImageView) dialog2.findViewById(R.id.img_not_bad);
        final ImageView imgWeak = (ImageView) dialog2.findViewById(R.id.img_weak);

        Button btnSendIdea = (Button) dialog2.findViewById(R.id.btn_send);
        Button btnExit = (Button) dialog2.findViewById(R.id.btn_exit);

        btnSendIdea.setTypeface(G.font1);
        btnExit.setTypeface(G.font1);

        layVeryGood.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

                veryGoog = 1;
                Goog = 0;
                notBad = 0;
                weak = 0;

                imgVeryGood.setImageResource(R.drawable.pppp);

                imgWeak.setImageResource(R.drawable.ppp);
                imgGood.setImageResource(R.drawable.ppp);
                imgNotBad.setImageResource(R.drawable.ppp);

            }
        });

        layGood.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

                veryGoog = 0;
                Goog = 1;
                notBad = 0;
                weak = 0;

                imgGood.setImageResource(R.drawable.pppp);

                imgVeryGood.setImageResource(R.drawable.ppp);
                imgWeak.setImageResource(R.drawable.ppp);
                imgNotBad.setImageResource(R.drawable.ppp);

            }
        });

        layNotBad.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

                veryGoog = 0;
                Goog = 0;
                notBad = 1;
                weak = 0;

                imgNotBad.setImageResource(R.drawable.pppp);

                imgVeryGood.setImageResource(R.drawable.ppp);
                imgWeak.setImageResource(R.drawable.ppp);
                imgGood.setImageResource(R.drawable.ppp);

            }
        });

        layWeak.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

                veryGoog = 0;
                Goog = 0;
                notBad = 0;
                weak = 1;

                imgWeak.setImageResource(R.drawable.pppp);

                imgVeryGood.setImageResource(R.drawable.ppp);
                imgGood.setImageResource(R.drawable.ppp);
                imgNotBad.setImageResource(R.drawable.ppp);

            }
        });
        btnExit.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

                int pid = android.os.Process.myPid();
                android.os.Process.killProcess(pid);
                dialog2.dismiss();
            }
        });

        btnSendIdea.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                String val = null;
                if (veryGoog == 1) {
                    val = "1";
                } else if (Goog == 1) {
                    val = "2";
                }
                else if (notBad == 1) {
                    val = "3";

                } else if (weak == 1) {
                    val = "4";
                } else {
                    val = "5";
                }

                if (val.equals("5")) {
                    Toast.makeText(G.context, "لطفا گزینه ای را انتخاب کنید", Toast.LENGTH_SHORT).show();
                } else {

                    Log.i("LOG", val);

                    sendSur(val);

                    dialog2.dismiss();
                    Log.i("LOG", "val");

                }
            }
        });
        //dialog2.setCancelable(false);
        dialog2.show();

    }


    public void sendReport(final String p, final int code) {

        final Dialog dialog2 = new Dialog(G.currentActivity);
        dialog2.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog2.setContentView(R.layout.dialog_report);

        LinearLayout layVeryGood = (LinearLayout) dialog2.findViewById(R.id.lay_very_good);
        LinearLayout layGood = (LinearLayout) dialog2.findViewById(R.id.lay_good);
        LinearLayout layNotBad = (LinearLayout) dialog2.findViewById(R.id.lay_not_bad);
        LinearLayout layWeak = (LinearLayout) dialog2.findViewById(R.id.lay_weak);

        final ImageView imgVeryGood = (ImageView) dialog2.findViewById(R.id.img_very_goood);
        final ImageView imgGood = (ImageView) dialog2.findViewById(R.id.img_goood);
        final ImageView imgNotBad = (ImageView) dialog2.findViewById(R.id.img_not_bad);
        final ImageView imgWeak = (ImageView) dialog2.findViewById(R.id.img_weak);

        final TextView txt1 = (TextView) dialog2.findViewById(R.id.txt_1);
        final TextView txt2 = (TextView) dialog2.findViewById(R.id.txt_2);
        final TextView txt3 = (TextView) dialog2.findViewById(R.id.txt_3);
        final TextView txt4 = (TextView) dialog2.findViewById(R.id.txt_4);

        Button btnSendIdea = (Button) dialog2.findViewById(R.id.btn_send);
        Button btnExit = (Button) dialog2.findViewById(R.id.btn_exit);

        if (p == "S") {
            txt1.setText("تصویر فروشگاه نامناسب است");
            txt2.setText("توضیحات فروشگاه نامناسب است");
            txt3.setText("فروشگاه در دسته بندی مناسب قرار نگرفته");
            txt4.setText("موارد دیگر");
        } else if (p == "M") {
            txt1.setText("تصویر محصول نامناسب است");
            txt2.setText("توضیحات محصول نامناسب است");
            txt3.setText("محصول در دسته بندی مناسب قرار نگرفته");
            txt4.setText("موارد دیگر");
        }
        btnSendIdea.setTypeface(G.font1);
        btnExit.setTypeface(G.font1);

        layVeryGood.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

                op1 = 1;
                op2 = 0;
                op3 = 0;
                op4 = 0;

                imgVeryGood.setImageResource(R.drawable.pppp);

                imgWeak.setImageResource(R.drawable.ppp);
                imgGood.setImageResource(R.drawable.ppp);
                imgNotBad.setImageResource(R.drawable.ppp);

            }
        });

        layGood.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

                op1 = 0;
                op2 = 1;
                op3 = 0;
                op4 = 0;

                imgGood.setImageResource(R.drawable.pppp);

                imgVeryGood.setImageResource(R.drawable.ppp);
                imgWeak.setImageResource(R.drawable.ppp);
                imgNotBad.setImageResource(R.drawable.ppp);

            }
        });

        layNotBad.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

                op1 = 0;
                op2 = 0;
                op3 = 1;
                op4 = 0;

                imgNotBad.setImageResource(R.drawable.pppp);

                imgVeryGood.setImageResource(R.drawable.ppp);
                imgWeak.setImageResource(R.drawable.ppp);
                imgGood.setImageResource(R.drawable.ppp);

            }
        });

        layWeak.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

                op1 = 0;
                op2 = 0;
                op3 = 0;
                op4 = 1;

                imgWeak.setImageResource(R.drawable.pppp);

                imgVeryGood.setImageResource(R.drawable.ppp);
                imgGood.setImageResource(R.drawable.ppp);
                imgNotBad.setImageResource(R.drawable.ppp);

            }
        });
        btnExit.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

                dialog2.dismiss();
            }
        });

        btnSendIdea.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                String val = null;
                if (op1 == 1) {
                    val = "1";
                } else if (op2 == 1) {
                    val = "2";
                }
                else if (op3 == 1) {
                    val = "3";

                } else if (op4 == 1) {
                    val = "4";
                } else {
                    val = "5";
                }

                if (val.equals("5")) {
                    Toast.makeText(G.context, "لطفا گزینه ای را انتخاب کنید", Toast.LENGTH_SHORT).show();
                } else {

                    if (p == "S") {

                        url = WebServiceUrl.sendReportStore + "storeCode=" + code + "&reportCode=" + val;
                    } else if (p == "M") {
                        url = WebServiceUrl.sendReportProduct + "productCode=" + code + "&reportCode=" + val;

                    }

                    Log.i("LOG", val);
                    sendReportPS(url);

                    dialog2.dismiss();
                    Log.i("LOG", "val");

                }
            }
        });
        //dialog2.setCancelable(false);
        dialog2.show();

    }


    public void menu() {
        // login = (LinearLayout) findViewById(R.id.lin);

        ImageView imgMen = (ImageView) findViewById(R.id.men);
        ImageView imgNotific = (ImageView) findViewById(R.id.img_notific_header);
        ImageView imgHome = (ImageView) findViewById(R.id.img_home);
        final DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        layLogin = (ViewGroup) findViewById(R.id.lay_login);
        txtLogin = (TextView) findViewById(R.id.txt_login_register_sliding);
        layOstan = (ViewGroup) findViewById(R.id.lay_ostan);
        layPanel = (ViewGroup) findViewById(R.id.lay_panel_mangment);

        layDigital = (ViewGroup) findViewById(R.id.lay_digital);
        layBeau = (ViewGroup) findViewById(R.id.lay_beau);
        layClo = (ViewGroup) findViewById(R.id.lay_clo);
        layAnother = (ViewGroup) findViewById(R.id.lay_another);

        layContact = (ViewGroup) findViewById(R.id.lay_contact_us);

        layAdvanceSearch = (ViewGroup) findViewById(R.id.lay_advance_search);
        txtUserName = (TextView) findViewById(R.id.txt_usename_sliding);

        if (G.Datacategory.size() < 2) {
            G.reciveFilter();
        } else {

            layDigital.setVisibility(View.VISIBLE);
            layBeau.setVisibility(View.VISIBLE);
            layClo.setVisibility(View.VISIBLE);
            layAnother.setVisibility(View.VISIBLE);

            layDigital.setTag(G.Datacategory.get(0).id);
            layBeau.setTag(G.Datacategory.get(1).id);
            layClo.setTag(G.Datacategory.get(2).id);
            layAnother.setTag(G.Datacategory.get(3).id);

        }

        checkLoginOk();
        layLogin.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

                drawerLayout.closeDrawers();

                if (G.LoginOk.equals("0")) {
                    Log.i("GET", "dialogLogin");
                    dialogLogin();

                } else {
                    Log.i("GET", "dialogExit");
                    dialogExit();

                }
            }
        });

        layOstan.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                drawerLayout.closeDrawers();
                showSelectOstan();
            }
        });

        layAdvanceSearch.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                drawerLayout.closeDrawers();
                Intent intent = new Intent(G.currentActivity, AdvanceSearch.class);
                G.currentActivity.startActivity(intent);
            }
        });

        layContact.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                drawerLayout.closeDrawers();
                contactUs();
            }
        });

        OnClickListener onClicl = new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                int id = (Integer) arg0.getTag();
                if (id != 0 || id != 1 || id != 2 || id != 3) {
                    Log.i("LPL", "" + "link");
                    Intent intentm = new Intent(G.currentActivity, SelectCat.class);
                    intentm.putExtra("id", id);
                    G.currentActivity.startActivity(intentm);
                } else {

                    Toast.makeText(G.context, "ارتباط ضعیف است", Toast.LENGTH_LONG).show();
                    G.reciveFilter();
                }

            }
        };

        layDigital.setOnClickListener(onClicl);
        layBeau.setOnClickListener(onClicl);
        layClo.setOnClickListener(onClicl);
        layAnother.setOnClickListener(onClicl);
        try {
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
        }
        catch (Exception e) {
            Log.i("MEN", e.toString());
        }

        imgNotific.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(G.currentActivity, ListNotification.class);
                G.currentActivity.startActivity(intent);
            }
        });

        imgHome.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                G.currentActivity.finish();
                Intent intent = new Intent(G.currentActivity, ShopFinderOrginalActivity.class);
                // intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | IntentCompat.FLAG_ACTIVITY_CLEAR_TASK);
                G.currentActivity.startActivity(intent);
            }
        });
    }


    public static void checkLoginOk() {
        if (G.LoginOk.equals("0")) {

            //dialogLogin();
            txtLogin.setText("ورود و ثبت نام");
            layPanel.setVisibility(View.GONE);
            txtUserName.setText("هوجی بوجی");

            G.DATALOGIN[5] = "0";

        } else {

            //dialogExit();
            txtLogin.setText("خروج");
            layPanel.setVisibility(View.VISIBLE);
            txtUserName.setText(G.DATALOGIN[2]);

            G.DATALOGIN[5] = "1";
        }
    }


    public void showSelectOstan() {
        final Dialog dialog2 = new Dialog(G.currentActivity);
        dialog2.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog2.setContentView(R.layout.dialog_ostan);
        G.btnOstan = (Button) dialog2.findViewById(R.id.edt_ostan);
        G.btnCity = (Button) dialog2.findViewById(R.id.edt_shar);
        Button btnSet = (Button) dialog2.findViewById(R.id.btn_ostan);
        G.btnOstan.setTypeface(G.font1);
        G.btnCity.setTypeface(G.font1);
        btnSet.setTypeface(G.font1);

        //edtOstan.setEnabled(false);

        btnSet.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                dialog2.hide();
            }
        });
        G.btnOstan.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                ArrayList<StrucOstan> Data = new ArrayList<StrucOstan>();
                Ostan ostan = new Ostan();
                Data = ostan.getAllOfOstan();
                G.dialogOstan = new Dialog(G.currentActivity);
                G.dialogOstan.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                G.dialogOstan.setContentView(R.layout.dialog_list_ostan_or_shar);
                TextView edtOstan = (TextView) G.dialogOstan.findViewById(R.id.txt_header_ls);
                ListView cs = (ListView) G.dialogOstan.findViewById(R.id.lst_st);

                ArrayAdapter adapterList = new AdapterLitOstan(Data);
                cs.setAdapter(adapterList);
                cs.setTextFilterEnabled(true);
                edtOstan.setTypeface(G.font1);
                // dialog3.setCancelable(false);
                G.dialogOstan.show();
            }
        });
        G.btnCity.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                ArrayList<StrucOstan> Data = new ArrayList<StrucOstan>();
                Ostan ostan = new Ostan();
                Log.i("LOG", "+18++" + G.btnOstan.getTag());
                ostan.setOstan((Integer) G.btnOstan.getTag());
                Data = ostan.getAllOfShahr();

                G.dialogCity = new Dialog(G.currentActivity);
                G.dialogCity.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                G.dialogCity.setContentView(R.layout.dialog_list_ostan_or_shar);
                TextView edtOstan = (TextView) G.dialogCity.findViewById(R.id.txt_header_ls);
                ListView cs = (ListView) G.dialogCity.findViewById(R.id.lst_st);

                ArrayAdapter adapterList = new AdapterListCity(Data);
                cs.setAdapter(adapterList);
                cs.setTextFilterEnabled(true);
                edtOstan.setTypeface(G.font1);
                // dialog3.setCancelable(false);
                G.dialogCity.show();
            }
        });
        dialog2.setCancelable(true);
        dialog2.show();

    }


    public void dialogLogin() {

        state = true;

        dialog2 = new Dialog(G.currentActivity);
        dialog2.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        // dialog2.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog2.setContentView(R.layout.dialog_login_register);

        edtUserName = (EditText) dialog2.findViewById(R.id.edt_username_dialog_login);
        edtPas = (EditText) dialog2.findViewById(R.id.edt_pas_dialog_login);
        edtPasConfim = (EditText) dialog2.findViewById(R.id.edt_confim_pas_dialog_login);
        LinearLayout layForgot = (LinearLayout) dialog2.findViewById(R.id.lay_forgot_dialog_login);
        LinearLayout layRegister = (LinearLayout) dialog2.findViewById(R.id.lay_register_dialog_login);
        final Button btnLogin = (Button) dialog2.findViewById(R.id.btn_login_dialog_login);
        final Button btnBack = (Button) dialog2.findViewById(R.id.btn_login_and_register_back);
        final TextView txtRegister = (TextView) dialog2.findViewById(R.id.txt_register_dialog_login);
        final LinearLayout layForget = (LinearLayout) dialog2.findViewById(R.id.lay_forgot_dialog_login);
        final LinearLayout layConfim = (LinearLayout) dialog2.findViewById(R.id.lay_confim_pas_dialog_login);

        btnLogin.setTypeface(G.font1);
        btnBack.setTypeface(G.font1);
        edtUserName.setTypeface(G.font1);
        edtPas.setTypeface(G.font1);
        edtPasConfim.setTypeface(G.font1);
        edtPas.clearFocus();

        layRegister.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                if (state == true) {

                    txtRegister.setText("* ورود به هوجی بوجی");
                    layForget.setVisibility(View.GONE);
                    btnLogin.setText("عضویت");
                    layConfim.setVisibility(View.VISIBLE);
                    state = false;

                } else {

                    txtRegister.setText("* عضویت در هوجی بوجی");
                    layForget.setVisibility(View.VISIBLE);
                    btnLogin.setText("ورود");
                    layConfim.setVisibility(View.GONE);
                    state = true;

                }

            }
        });

        btnLogin.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Log.i("GET", "loginAndRegister");
                loginAndRegister();

            }
        });

        btnBack.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Log.i("GET", "loginAndRegister");
                dialog2.dismiss();
            }
        });

        dialog2.setCancelable(true);
        dialog2.show();

    }


    private void loginAndRegister() {

        edtUserName.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                ErrorUserName = 0;
            }


            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                ErrorUserName = 0;
            }


            @Override
            public void afterTextChanged(Editable arg0) {
                ErrorUserName = 0;
            }
        });

        edtPas.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                ErrorPassword = 0;
            }


            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                ErrorPassword = 0;
            }


            @Override
            public void afterTextChanged(Editable arg0) {
                ErrorPassword = 0;
            }
        });
        edtPasConfim.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                ErrorConfimPassword = 0;
            }


            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                ErrorConfimPassword = 0;
            }


            @Override
            public void afterTextChanged(Editable arg0) {
                ErrorConfimPassword = 0;
            }
        });

        CheckInternet checkInternet = new CheckInternet();
        if (checkInternet.Access()) {

            final String grant_type = "password";
            final String userName = edtUserName.getText().toString().trim();
            final String password = edtPas.getText().toString().trim();

            if (userName.length() < 5 && userName.length() >= 0) {
                ErrorUserName = 1;
            }

            if (password.length() < 5 && password.length() >= 0) {
                ErrorPassword = 1;
            }

            final Dialog dialog5 = new Dialog(G.currentActivity);
            dialog5.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog5.setContentView(R.layout.dialog_error);
            TextView txtHeaderError = (TextView) dialog5.findViewById(R.id.txt_header_error);
            final TextView txtUserNameError = (TextView) dialog5.findViewById(R.id.txt_name_error);
            final TextView txtPassword = (TextView) dialog5.findViewById(R.id.txt_family_eror);
            final TextView txtConfimPas = (TextView) dialog5.findViewById(R.id.txt_address_error);
            final TextView txtChangeError = (TextView) dialog5.findViewById(R.id.txt_change_error);
            Button btnOk = (Button) dialog5.findViewById(R.id.btn_ok);

            btnOk.setTypeface(G.font1);
            txtHeaderError.setTypeface(G.font1);
            btnOk.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    txtUserNameError.setVisibility(View.GONE);
                    txtPassword.setVisibility(View.GONE);
                    txtConfimPas.setVisibility(View.GONE);
                    txtChangeError.setVisibility(View.GONE);
                    dialog5.dismiss();
                }
            });
            dialog5.setCancelable(false);

            if (ErrorUserName == 1) {
                txtUserNameError.setVisibility(View.VISIBLE);
                txtUserNameError.setText("نام کاربری حداقل باید بیش از 4 حرف باشد");
            }

            if (ErrorPassword == 1) {
                txtPassword.setVisibility(View.VISIBLE);
                txtPassword.setText("رمز عبور حداقل بیش از 4 حرف باید باشد");
            }

            if (state == false) {
                final String confimPas = edtPasConfim.getText().toString().trim();

                if ( !confimPas.equals(password)) {
                    ErrorConfimPassword = 1;
                }

                if (ErrorConfimPassword == 1) {
                    txtConfimPas.setVisibility(View.VISIBLE);
                    txtConfimPas.setText("رمز عبور وارده مطابقت ندارد");
                }

                if (ErrorUserName == 0 && ErrorPassword == 0 && ErrorConfimPassword == 0) {

                    sendRegister(userName, password, confimPas);

                    Log.i("GET", "" + userName + "   " + MD5.crypt(password) + "   " + confimPas);

                    // dialog2.dismiss();

                } else {
                    dialog5.show();
                }

            } else {

                if (ErrorUserName == 0 && ErrorPassword == 0) {

                    Log.i("GET", "ErrorUserName");

                    reciveLogin(userName, password);

                    // dialog2.dismiss();
                    Log.i("GET", " dialog2.dismiss()");

                } else {
                    dialog5.show();
                }

            }
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


    public void dialogExit() {

        final Dialog dialog5 = new Dialog(G.currentActivity);
        dialog5.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog5.setContentView(R.layout.dialog_exit_panel);

        TextView txtHeaderError = (TextView) dialog5.findViewById(R.id.txt_header_error);

        Button btnOk = (Button) dialog5.findViewById(R.id.btn_back);
        Button btnNo = (Button) dialog5.findViewById(R.id.btn_setting);

        btnOk.setTypeface(G.font1);
        txtHeaderError.setTypeface(G.font1);
        btnNo.setTypeface(G.font1);

        btnOk.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                txtLogin.setText("ورود و ثبت نام");
                layPanel.setVisibility(View.GONE);
                txtUserName.setText("هوجی بوجی");

                G.DATALOGIN[5] = "0";
                G.LoginOk = "0";

                DataLogin DataLogin = new DataLogin();
                DataLogin.updateExitLogin();

                checkLoginOk();

                Toast.makeText(G.context, "شما با موفقیت خارج شدید", Toast.LENGTH_SHORT).show();
                dialog5.dismiss();
            }
        });

        btnNo.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                dialog5.dismiss();
            }
        });
        dialog5.setCancelable(false);
        dialog5.show();

    }


    public void reciveLogin(final String userName, final String password) {
        try {

            // txtLoading.setVisibility(View.VISIBLE);
            String url = WebServiceUrl.LoginAddress;

            StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {

                        @Override
                        public void onResponse(String response) {

                            try {

                                Log.i("LOG", " " + "reader response");
                                //            JSONArray array = new JSONArray(respone);
                                JSONObject object = new JSONObject(response);
                                String accessToken = object.getString("access_token");
                                String tokenType = object.getString("token_type");
                                String expiresIn = object.getString("expires_in");
                                String userName = object.getString("userName");
                                String issUed = object.getString(".issued");
                                String expires = object.getString(".expires");

                                G.DATALOGIN[0] = accessToken;
                                G.DATALOGIN[1] = tokenType;
                                G.DATALOGIN[2] = userName;
                                G.DATALOGIN[3] = issUed;
                                G.DATALOGIN[4] = expires;
                                G.DATALOGIN[5] = "1";
                                G.LoginOk = "1";

                                DataLogin DataLogin = new DataLogin();

                                DataLogin.setAccessToken(accessToken);
                                DataLogin.setTokenType(tokenType);
                                DataLogin.setExpiresIn(expiresIn);
                                DataLogin.setUserName(userName);
                                DataLogin.setIssUed(issUed);
                                DataLogin.setExpires(expires);

                                Log.i("LOG", " " + G.DATALOGIN[0] + "***" + G.DATALOGIN[1] + "****" + G.DATALOGIN[2] + "****" + G.DATALOGIN[3] + "****" + G.DATALOGIN[4]);
                                DataLogin.updateDateLogin();

                                Enhance.checkLoginOk();

                                Toast.makeText(G.context, "شما با موفقیت وارد شدید", Toast.LENGTH_SHORT).show();
                                dialog2.dismiss();
                            }

                            catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(G.context, "رمز عبور یا نام کاربری اشتباه است", Toast.LENGTH_LONG).show();
                            }

                        }
                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.i("ALL", error.toString());
                            Toast.makeText(G.context, "رمز عبور یا نام کاربری اشتباه است", Toast.LENGTH_LONG).show();
                            //txtWaite.setVisibility(View.GONE);
                            //Toast.makeText(G.context, "لطفا دوباره تلاش کنید", Toast.LENGTH_SHORT).show();
                        }
                    }) {

                @Override
                protected Map<String, String> getParams()
                {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("grant_type", "password");
                    params.put("randomFieldFilledWithAwkwardCharacters", "{{%stuffToBe Escaped/");
                    params.put("username", userName);
                    params.put("password", MD5.crypt(password));
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
            Toast.makeText(G.context, "رمز عبور یا نام کاربری اشتباه است", Toast.LENGTH_LONG).show();
        }

    }


    public void sendRegister(final String userName, final String password, final String confimPas) {
        try {

            // txtLoading.setVisibility(View.VISIBLE);
            String url = WebServiceUrl.registerCustomer;

            StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {

                        @Override
                        public void onResponse(String response) {

                            try {
                                //            JSONArray array = new JSONArray(respone);
                                JSONObject object = new JSONObject(response);
                                Log.i("LOG", " " + "respone2");
                                String accessToken = object.getString("message");

                                Toast.makeText(G.context, accessToken, Toast.LENGTH_SHORT).show();
                                dialog2.dismiss();
                            }
                            catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(G.context, "خطادر عملیات ثبت ! لطفا دوباره تلاش کنید", Toast.LENGTH_LONG).show();
                            }

                        }
                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.i("ALL", error.toString());
                            Toast.makeText(G.context, "خطادر عملیات ثبت ! لطفا دوباره تلاش کنید", Toast.LENGTH_LONG).show();
                            //txtWaite.setVisibility(View.GONE);
                            //Toast.makeText(G.context, "لطفا دوباره تلاش کنید", Toast.LENGTH_SHORT).show();
                        }
                    }) {

                @Override
                protected Map<String, String> getParams()
                {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("Content-Type", "application/json");
                    params.put("UserName", userName);
                    params.put("Password", MD5.crypt(password));
                    params.put("ConfirmPassword", MD5.crypt(confimPas));//aleki
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
            Toast.makeText(G.context, "خطادر عملیات ثبت ! لطفا دوباره تلاش کنید", Toast.LENGTH_LONG).show();
        }

    }


    public void exit() {
        final Dialog dialog5 = new Dialog(G.currentActivity);
        dialog5.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog5.setContentView(R.layout.dialog_exit);

        TextView txtHeaderError = (TextView) dialog5.findViewById(R.id.txt_header_error);
        final TextView txtUserNameError = (TextView) dialog5.findViewById(R.id.txt_name_error);
        final TextView txtPassword = (TextView) dialog5.findViewById(R.id.txt_family_eror);
        final TextView txtConfimPas = (TextView) dialog5.findViewById(R.id.txt_address_error);
        final TextView txtChangeError = (TextView) dialog5.findViewById(R.id.txt_change_error);
        Button btnOk = (Button) dialog5.findViewById(R.id.btn_ok);

        btnOk.setTypeface(G.font1);
        txtHeaderError.setTypeface(G.font1);
        btnOk.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                int pid = android.os.Process.myPid();
                android.os.Process.killProcess(pid);
                dialog5.dismiss();
            }
        });
        //dialog5.setCancelable(false);
        dialog5.show();
    }


    public void contactUs() {

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
                        sendIdea(P1, P2, P4);
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


    public void sendIdea(final String name, final String email, final String desc) {
        try {
            Log.i("IDEA", "jaber   " + "response");
            //txtLoading.setVisibility(View.VISIBLE);
            String url = WebServiceUrl.sendContact;
            Log.i("IDEA", "jaber   " + url);

            StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {

                        @Override
                        public void onResponse(String response) {

                            try {
                                JSONObject object = new JSONObject(response);
                                Log.i("IDEA", "response " + object);
                                Toast.makeText(G.context, " پیام شما با موفقیت ثبت شد،تشکر از شما کاربر گرامی", Toast.LENGTH_LONG).show();
                                /* JSONArray jsonArray = object.getJSONArray("CompleteProductsForList");

                                 for (int i = 0; i < jsonArray.length(); i++) {

                                     StructKala kala = new StructKala();
                                     kala.name = jsonArray.getJSONObject(i).getJSONObject("ProductSummary").getString("Name");
                                     kala.price = jsonArray.getJSONObject(i).getJSONObject("ProductSummary").getString("Price");
                                     kala.id = jsonArray.getJSONObject(i).getJSONObject("ProductSummary").getInt("Id");
                                     kala.picture = "www.hoojibooji.ir/" + jsonArray.getJSONObject(i).getJSONObject("ProductSummary").getString("ImgAddress");
                                     kala.state = jsonArray.getJSONObject(i).getJSONObject("ProductSummary").getBoolean("IsExist");
                                     G.DataKalaDiscount.add(kala);
                                 }*/

                                //showListDiscountProduct();
                                // txtLoading.setVisibility(View.GONE);
                            }
                            catch (JSONException e) {
                                Toast.makeText(G.context, " خطا در ارتباط", Toast.LENGTH_LONG).show();
                                Log.i("IDEA", "" + e.toString());
                                e.printStackTrace();
                            }

                        }
                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.i("IDEA", error.toString());
                            // txtLoading.setVisibility(View.GONE);
                            Toast.makeText(G.context, " خطا در ارتباط", Toast.LENGTH_LONG).show();
                        }
                    }) {

                @Override
                protected Map<String, String> getParams()
                {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("Content-Type", "application/json");
                    params.put("FullName", name);
                    params.put("Email", email);
                    params.put("Subject", "پیشناد و انتقاد");
                    params.put("Description", desc);

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
            Toast.makeText(G.context, " خطا در ارتباط", Toast.LENGTH_LONG).show();
        }

    }


    public void sendReportPS(String URL) {

        Log.i("LOG", "val");

        ConnectivityManager cm = (ConnectivityManager) G.context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            try {
                Log.i("LOG", "val3");
                String url = URL;

                RequestQueue queue = Volley.newRequestQueue(G.context);
                StringRequest postRequest = new StringRequest(Request.Method.GET, url,
                        new Response.Listener<String>()
                        {

                            @Override
                            public void onResponse(String response) {

                                try {
                                    JSONObject object = new JSONObject(response);
                                    String ok = object.getString("message");
                                    if (ok.equals("عملیات با موفقیت انجام شد")) {
                                        Log.i("LOG", response);

                                        Toast.makeText(G.context, " گزارش شما با موفقیت ثبت شد", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(G.context, " خطا در ارتباط!", Toast.LENGTH_SHORT).show();
                                    }
                                }
                                catch (JSONException e) {
                                    // TODO Auto-generated catch block
                                    e.printStackTrace();
                                }

                            }
                        },
                        new Response.ErrorListener()
                        {

                            @Override
                            public void onErrorResponse(VolleyError error) {
                                // prg.hide();
                                Toast.makeText(G.context, "لطفا دوباره تلاش کنید", Toast.LENGTH_LONG).show();
                            }
                        });
                queue.add(postRequest);
            }
            catch (Exception e) {
                Log.i("LOG", " kjk" + e.toString());
            }
        } else {
            final Dialog dialog2 = new Dialog(G.currentActivity);
            dialog2.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
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
                    G.currentActivity.startActivity(intent);
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


    public void sendSur(String val) {

        Log.i("LOG", "val");

        final Check ostan = new Check();
        int y = ostan.getSur();
        Log.i("LOG", y + "val");

        ConnectivityManager cm = (ConnectivityManager) G.context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            try {
                Log.i("LOG", "val3");
                String url = WebServiceUrl.sendNazar + "?scoreCode=" + val;

                RequestQueue queue = Volley.newRequestQueue(G.context);
                StringRequest postRequest = new StringRequest(Request.Method.GET, url,
                        new Response.Listener<String>()
                        {

                            @Override
                            public void onResponse(String response) {

                                try {
                                    JSONObject object = new JSONObject(response);
                                    String ok = object.getString("message");
                                    if (ok.equals("عملیات با موفقیت انجام شد")) {
                                        Log.i("LOG", response);
                                        ostan.updateSur();

                                        Toast.makeText(G.context, " نظر شما با موفقیت ثبت شد", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(G.context, " خطا در ارتباط!", Toast.LENGTH_SHORT).show();
                                    }
                                }
                                catch (JSONException e) {
                                    // TODO Auto-generated catch block
                                    e.printStackTrace();
                                }

                            }
                        },
                        new Response.ErrorListener()
                        {

                            @Override
                            public void onErrorResponse(VolleyError error) {
                                // prg.hide();
                                Toast.makeText(G.context, "لطفا دوباره تلاش کنید", Toast.LENGTH_LONG).show();
                            }
                        });
                queue.add(postRequest);
            }
            catch (Exception e) {
                Log.i("LOG", " kjk" + e.toString());
            }
        } else {
            final Dialog dialog2 = new Dialog(G.currentActivity);
            dialog2.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
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
                    G.currentActivity.startActivity(intent);
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


    public static void getNeweV() {
        try {

            String url = WebServiceUrl.GetUpdate + "currentVersion=" + Float.parseFloat(G.verApp);

            RequestQueue queue = Volley.newRequestQueue(G.context);
            StringRequest postRequest = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>()
                    {

                        @Override
                        public void onResponse(final String response) {
                            Log.i("NEW", "" + response);

                            try {
                                JSONObject object = new JSONObject(response);
                                final String ok = object.getString("message");
                                if ( !ok.equals("there is no new version")) {
                                    final Dialog dialog2 = new Dialog(G.currentActivity);
                                    dialog2.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                    dialog2.setContentView(R.layout.dialog_update);
                                    Button btn_back = (Button) dialog2.findViewById(R.id.btn_download);
                                    btn_back.setTypeface(G.font1);
                                    btn_back.setOnClickListener(new OnClickListener() {

                                        @Override
                                        public void onClick(View arg0) {
                                            String url = response;
                                            Intent i = new Intent(Intent.ACTION_VIEW);
                                            i.setData(Uri.parse("http://www.hoojibooji.ir/" + ok));
                                            G.currentActivity.startActivity(i);
                                            dialog2.dismiss();
                                        }
                                    });
                                    dialog2.setCancelable(false);
                                    dialog2.show();
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
