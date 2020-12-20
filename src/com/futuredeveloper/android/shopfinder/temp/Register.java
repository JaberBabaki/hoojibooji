package com.futuredeveloper.android.shopfinder.temp;

import android.app.Activity;


public class Register extends Activity {
    /*
        public static RequestQueue        queue;

        public static Button              tct;
        public static Button              c;
        public static Button              in;
        public int                        pos = 0;
        public static ArrayList<States>   sts = new ArrayList<States>();
        public static ArrayList<Category> cat = new ArrayList<Category>();


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            // TODO Auto-generated method stub
            super.onCreate(savedInstanceState);
            setContentView(R.layout.register);
            G.context = this;
            G.currentActivity = this;
            tct = (Button) findViewById(R.id.reg_txt_token);
            in = (Button) findViewById(R.id.in);
            c = (Button) findViewById(R.id.c);
            c.setMovementMethod(new ScrollingMovementMethod());
            tct.setMovementMethod(new ScrollingMovementMethod());
            in.setMovementMethod(new ScrollingMovementMethod());
            queue = Volley.newRequestQueue(this);
            readData();
            tct.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    final Dialog dg = new Dialog(Register.this);
                    Adapter_Category adapter = new Adapter_Category(cat);
                    dg.setContentView(R.layout.register_dialog);

                    ListView lst = (ListView) dg.findViewById(R.id.lst_dialog);
                    lst.setOnItemClickListener(new OnItemClickListener()
                    {

                        @Override
                        public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3)
                        {
                            Toast.makeText(Register.this, "" + cat.get(position).Name, Toast.LENGTH_SHORT).show();
                            //  handling_Cityes(position);
                            tct.setText(cat.get(position).Name);
                            dg.dismiss();

                        }
                    });
                    lst.setAdapter(adapter);
                    dg.show();
                }
            });
            c.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    // TODO Auto-generated method stub
                    final Dialog dg = new Dialog(Register.this);
                    Adapter_City adapter = new Adapter_City(sts.get(pos).ArrayListCities);
                    dg.setContentView(R.layout.register_dialog);

                    ListView lst = (ListView) dg.findViewById(R.id.lst_dialog);
                    lst.setOnItemClickListener(new OnItemClickListener()
                    {

                        @Override
                        public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3)
                        {
                            Toast.makeText(Register.this, "" + sts.get(pos).ArrayListCities.get(position).Neme, Toast.LENGTH_SHORT).show();
                            //  handling_Cityes(position);
                            c.setText(sts.get(pos).ArrayListCities.get(position).Neme + "");
                            dg.dismiss();

                        }
                    });
                    lst.setAdapter(adapter);
                    dg.show();
                }
            });
            in.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    // TODO Auto-generated method stub
                    final Dialog dg = new Dialog(Register.this);
                    Adapter_States adapter = new Adapter_States(sts);
                    dg.setContentView(R.layout.register_dialog);

                    ListView lst = (ListView) dg.findViewById(R.id.lst_dialog);
                    lst.setOnItemClickListener(new OnItemClickListener()
                    {

                        @Override
                        public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3)
                        {
                            Toast.makeText(Register.this, "" + sts.get(position).Name, Toast.LENGTH_SHORT).show();
                            in.setText(sts.get(position).Name);
                            pos = position;
                            //  handling_Cityes(position);
                            c.setText(sts.get(position).ArrayListCities.get(0).Neme + "");
                            dg.dismiss();

                        }
                    });
                    lst.setAdapter(adapter);
                    dg.show();
                }
            });

            //for POST requests, only the following line should be changed to 

        }


        public static void readData() {
            StringRequest sr = new StringRequest(Request.Method.GET, "http://hogibogi.ir/api/store/getStatesAndCategories",
                    new Response.Listener<String>() {

                        @Override
                        public void onResponse(String response) {
                            Log.e("HttpClient", "success! response: " + response.toString());
                            tct.setText(response);
                            Handleing(response);

                        }
                    },
                    new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e("HttpClient", "error: " + error.toString());
                            tct.setText(error + "");
                        }
                    })
            {

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    //  params.put("Content-Type", "application/x-www-form-urlencoded");
                    return params;
                }
            };
            queue.add(sr);
        }


        // posation entekhabi vared in methode shode va array list sharestan refresh misgavad 
        public void handling_Cityes(int posation) {
            c.setText("");
            for (int k = 0; k < sts.get(posation).ArrayListCities.size(); k++) {
                c.setText(c.getText() +
                        sts.get(posation).ArrayListCities.get(k).Neme +
                        sts.get(posation).ArrayListCities.get(k).Id +
                        "\n");
            }
        }


        // json koli vared in methode mishavad k haman string daryafty az srver ast
        public static void Handleing(String data) {
            tct.setText("");
            c.setText("");
            in.setText("");
            if (data != null) {
                try {
                    JSONObject tasks = new JSONObject(data);
                    JSONArray Statess = tasks.getJSONArray("States");

                    // baraye list az state ha ast
                    for (int i = 0; i < Statess.length(); i++) {
                        JSONObject json_data = Statess.getJSONObject(i);
                        States s = new States();
                        s.id = json_data.getString("Id");
                        s.Name = json_data.getString("Name");
                        s.JsonArrayCities = json_data.getJSONArray("Cities");
                        // baraye har item az list yani haman sharestan haei har ostan 
                        for (int j = 0; j < s.JsonArrayCities.length(); j++) {
                            JSONObject js = s.JsonArrayCities.getJSONObject(j);
                            City city = new City();
                            city.Id = js.getString("Id");
                            city.Neme = js.getString("Name");
                            city.StateCode = js.getString("StateCode");

                            s.ArrayListCities.add(city);
                        }

                        sts.add(s);

                    }

                }
                catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }*/
}
