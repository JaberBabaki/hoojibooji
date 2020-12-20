package com.futuredeveloper.android.shopfinder.temp;

import android.app.Activity;
import android.view.View;


public class login extends Activity implements View.OnClickListener {

    @Override
    public void onClick(View arg0) {
        // TODO Auto-generated method stub

    }
    /*
        private EditText editTextUsername;
        private TextView txt;
        private EditText editTextEmail;
        private EditText editTextPassword;

        private Button   buttonRegister;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            // TODO Auto-generated method stub
            super.onCreate(savedInstanceState);
            setContentView(R.layout.login);
            editTextUsername = (EditText) findViewById(R.id.editText1);
            editTextPassword = (EditText) findViewById(R.id.editText2);
            txt = (TextView) findViewById(R.id.textView1);

            buttonRegister = (Button) findViewById(R.id.button1);

            buttonRegister.setOnClickListener(this);

        }


        public void HttpPOSTRequestWithParameters() {
            try {
                RequestQueue queue = Volley.newRequestQueue(this);
                String url = WebServiceUrl.LoginAddress;
                final String username = editTextUsername.getText().toString().trim();
                final String password = editTextPassword.getText().toString().trim();

                StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>()
                        {

                            @Override
                            public void onResponse(String response) {
                                Toast.makeText(login.this, "خوش امدید", Toast.LENGTH_LONG).show();
                                //txt.setText(response);
                                // Intent intent = new Intent(login.this, add_image.class);

                                JSONObject jsonobjectResult;
                                String result = null;
                                try {
                                    jsonobjectResult = new JSONObject(response.toString().trim());
                                    result = (String) jsonobjectResult.get("access_token");
                                    //   Toast.makeText(login.this, result, Toast.LENGTH_LONG).show();
                                }
                                catch (JSONException e) {
                                    // TODO Auto-generated catch block
                                    e.printStackTrace();
                                }
                                //   intent.putExtra("access_token", result);
                                //  startActivity(intent);
                                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                                finish();
                            }
                        },
                        new Response.ErrorListener()
                        {

                            @Override
                            public void onErrorResponse(VolleyError error) {
                                String error_desc = null;
                                if (error.networkResponse != null && error.networkResponse.data != null) {
                                    VolleyError error1 = new VolleyError(new String(error.networkResponse.data));
                                    error = error1;
                                }
                                JSONObject jsonobjectResult;
                                try {
                                    jsonobjectResult = new JSONObject(error.getMessage().toString().trim());
                                    error_desc = (String) jsonobjectResult.get("error_description");
                                }
                                catch (JSONException e) {
                                    // TODO Auto-generated catch block
                                    e.printStackTrace();
                                }

                                Toast.makeText(login.this, error_desc, Toast.LENGTH_LONG).show();

                                txt.setText(error_desc + "");
                                Log.d("ERROR", "error => " + error.toString());
                            }
                        }
                        ) {

                            // this is the relevant method
                            @Override
                            protected Map<String, String> getParams()
                            {
                                Map<String, String> params = new HashMap<String, String>();
                                params.put("grant_type", "password");
                                // volley will escape this for you 
                                params.put("randomFieldFilledWithAwkwardCharacters", "{{%stuffToBe Escaped/");
                                params.put("username", username);
                                params.put("password", password);

                                return params;
                            }
                        };
                postRequest.setRetryPolicy((RetryPolicy) new DefaultRetryPolicy(
                        20000,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                queue.add(postRequest);
            }
            catch (Exception e) {}
        }


        @Override
        public void onClick(View v) {
            if (v == buttonRegister) {
                HttpPOSTRequestWithParameters();
            }
        }*/
}