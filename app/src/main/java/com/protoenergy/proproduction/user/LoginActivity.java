package com.protoenergy.proproduction.user;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.protoenergy.proproduction.MainActivity;
import com.protoenergy.proproduction.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import static com.protoenergy.proproduction.common.Constants.Params.AREAID;
import static com.protoenergy.proproduction.common.Constants.Params.AREANAME;
import static com.protoenergy.proproduction.common.Constants.Params.FNAME;
import static com.protoenergy.proproduction.common.Constants.Params.KEY_MESSAGE;
import static com.protoenergy.proproduction.common.Constants.Params.KEY_STATUS;
import static com.protoenergy.proproduction.common.Constants.Params.OBJ;
import static com.protoenergy.proproduction.common.Constants.Params.PASSWORD;
import static com.protoenergy.proproduction.common.Constants.Params.PHONE;
import static com.protoenergy.proproduction.common.Constants.Params.USERID;
import static com.protoenergy.proproduction.common.Constants.Params.WORKID;
import static com.protoenergy.proproduction.common.Constants.Params.WORKTYPE;
import static com.protoenergy.proproduction.common.Constants.URLs.LOGIN;


public class LoginActivity extends AppCompatActivity {


    private EditText etPhoneNumber;
    private EditText etPassword;
    private String username;
    private String password;
    private ProgressDialog pDialog;
    PreferenceHelper preferenceHelper;
    int final_;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        preferenceHelper = new PreferenceHelper(this);

        etPhoneNumber = findViewById(R.id.et_phonenumber);
        etPassword = findViewById(R.id.et_password);

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat day = new SimpleDateFormat("yyyyMMdd");
        String joiningDay = day.format(cal.getTime());
        String current = joiningDay;


        if (preferenceHelper.getIsLogin()) {

            if (preferenceHelper.getWorkID().equals("1")) {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            } else if (preferenceHelper.getWorkID().equals("2")) {

                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();

            }
        }


        Button login = findViewById(R.id.btnLogin);


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Retrieve the data entered in the edit texts
                username = etPhoneNumber.getText().toString().toLowerCase().trim();
                password = etPassword.getText().toString().trim();

                login();

            }
        });
    }


    @Override
    public void onResume() {
        super.onResume();


    }


    @Override
    protected void onPause() {
        super.onPause();

    }

    private void login() {


        JSONObject request = new JSONObject();
        try {
            //Populate the request parameters
            request.put(PHONE, username);
            request.put(PASSWORD, password);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsArrayRequest = new JsonObjectRequest(Request.Method.POST, LOGIN, request, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("res", String.valueOf(response));

                try {
                    //Check if user got logged in successfully

                    if (response.getString(KEY_STATUS).equals("true")) {
                        JSONArray dataArray = response.getJSONArray(OBJ);
                        for (int i = 0; i < dataArray.length(); i++) {
                            preferenceHelper.putIsLogin(true);
                            Calendar cal = Calendar.getInstance();
                            SimpleDateFormat day = new SimpleDateFormat("yyyyMMdd");
                            String joiningDay = day.format(cal.getTime());

                            JSONObject dataobj = dataArray.getJSONObject(i);
                            preferenceHelper.putUserID(dataobj.getString(USERID));
                            preferenceHelper.putFullName(dataobj.getString(FNAME));
                            preferenceHelper.putAreaName(dataobj.getString(AREANAME));
                            preferenceHelper.putAreaID(dataobj.getString(AREAID));
                            preferenceHelper.putPhone(dataobj.getString(PHONE));
                            preferenceHelper.putWorkType(dataobj.getString(WORKTYPE));
                            preferenceHelper.putWorkID(dataobj.getString(WORKID));


                        }

                        if (preferenceHelper.getWorkID().equals("1")) {
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            finish();
                        } else if (preferenceHelper.getWorkID().equals("2")) {
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            finish();
                        }


                    } else {
                        Toast.makeText(getApplicationContext(), response.getString(KEY_MESSAGE), Toast.LENGTH_SHORT).show();

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(LoginActivity.this, "no connection switch on  your data", Toast.LENGTH_SHORT).show();


            }
        });

        // Access the RequestQueue through your singleton class.
        VolleySingleton.getInstance(this).addToRequestQueue(jsArrayRequest);
    }


}
