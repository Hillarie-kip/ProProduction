package com.protoenergy.proproduction.activities.mapping;

import android.app.ProgressDialog;
import android.content.DialogInterface;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.novoda.merlin.MerlinsBeard;
import com.protoenergy.proproduction.MainActivity;
import com.protoenergy.proproduction.R;
import com.protoenergy.proproduction.user.LoginActivity;
import com.protoenergy.proproduction.user.PreferenceHelper;
import com.protoenergy.proproduction.user.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;


import static com.protoenergy.proproduction.common.Constants.Params.KEY_ERROR;
import static com.protoenergy.proproduction.common.Constants.Params.KEY_MESSAGED;
import static com.protoenergy.proproduction.common.Constants.Params.KEY_NETWORK;
import static com.protoenergy.proproduction.common.Constants.URLs.GET_ORDERNUMBERS;
import static com.protoenergy.proproduction.common.Constants.URLs.LOGIN;
import static com.protoenergy.proproduction.common.Constants.URLs.URL_UPLOADMAKERV2;

public class MakerActivity extends AppCompatActivity implements Spinner.OnItemSelectedListener {

    private Spinner SP_ProductionNumber, SP_Year, SP_Month;
    private ArrayList<String> outlets;
    private JSONArray result;
    ProgressDialog progressDialog;
    Button btnSale,btnClear;


    String initialstring = "Choose an Order Number";
    LinearLayout L_Details;
    MerlinsBeard merlinsBeard;

    String MaterialDescription, ExpectedQuantity, ProducedQuantity;
    String OrderNumber, Year, Month, SerialNumber, QRCode;
    EditText ETSerialNumber, ETQRCode, ETTare;
    TextView TVMaterialDescription, TVExpectedQuantity, TVProducedQuantity;

    double tareweight;
    PreferenceHelper preferenceHelper;

    String qrcode;
    String separated;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maker);
        outlets = new ArrayList<>();
        preferenceHelper = new PreferenceHelper(this);
        SP_ProductionNumber = findViewById(R.id.sp_productionnumber);
        SP_ProductionNumber.setOnItemSelectedListener(this);

        TVMaterialDescription = findViewById(R.id.tv_material);
        TVExpectedQuantity = findViewById(R.id.tv_expected);
        TVProducedQuantity = findViewById(R.id.tv_produced);

        ETSerialNumber = findViewById(R.id.et_serialnumber);
        ETQRCode = findViewById(R.id.et_qr);
        ETTare = findViewById(R.id.et_tare);
        L_Details = findViewById(R.id.l_detail);
        SP_Year = findViewById(R.id.sp_year);
        SP_Month = findViewById(R.id.sp_month);
        btnSale = findViewById(R.id.btn_OpenSale);
        btnClear = findViewById(R.id.btn_Clear);

        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ETSerialNumber.setText("");
                ETQRCode.setText("");
                ETTare.setText("");
            }
        });

        btnSale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SP_ProductionNumber.getSelectedItem().toString().equals(initialstring)) {
                    L_Details.setVisibility(View.GONE);
                    Toast.makeText(MakerActivity.this, "Please choose order number", Toast.LENGTH_SHORT).show();
                } else {
                    OrderNumber = SP_ProductionNumber.getSelectedItem().toString();

                    if (SP_Year.getSelectedItem().toString().equals("The Year")) {
                        Toast.makeText(MakerActivity.this, "choose the year", Toast.LENGTH_SHORT).show();
                    } else {
                        if (SP_Year.getSelectedItem().toString().equals("2018")) {
                            Year = "A";
                        }
                        if (SP_Year.getSelectedItem().toString().equals("2019")) {
                            Year = "B";
                        }
                        if (SP_Year.getSelectedItem().toString().equals("2020")) {
                            Year = "C";
                        }

                        if (SP_Month.getSelectedItem().toString().equals("The Month")) {
                            Toast.makeText(MakerActivity.this, "choose the Month", Toast.LENGTH_SHORT).show();
                        } else {
                            if (SP_Month.getSelectedItem().toString().equals("January")) {
                                Month = "JN";
                            }
                            if (SP_Month.getSelectedItem().toString().equals("February")) {
                                Month = "FB";
                            }
                            if (SP_Month.getSelectedItem().toString().equals("March")) {
                                Month = "MR";
                            }
                            if (SP_Month.getSelectedItem().toString().equals("April")) {
                                Month = "AP";
                            }
                            if (SP_Month.getSelectedItem().toString().equals("May")) {
                                Month = "MY";
                            }
                            if (SP_Month.getSelectedItem().toString().equals("June")) {
                                Month = "JU";
                            }
                            if (SP_Month.getSelectedItem().toString().equals("July")) {
                                Month = "JL";
                            }
                            if (SP_Month.getSelectedItem().toString().equals("August")) {
                                Month = "AU";
                            }
                            if (SP_Month.getSelectedItem().toString().equals("September")) {
                                Month = "SP";
                            }
                            if (SP_Month.getSelectedItem().toString().equals("October")) {
                                Month = "OC";
                            }
                            if (SP_Month.getSelectedItem().toString().equals("November")) {
                                Month = "NO";
                            }
                            if (SP_Month.getSelectedItem().toString().equals("December")) {
                                Month = "DC";
                            }

                            if (ETSerialNumber.getText().toString().length() != 6) {
                                Toast.makeText(MakerActivity.this, "Serial Number is 6 digit", Toast.LENGTH_SHORT).show();
                            } else {
                                SerialNumber = ETSerialNumber.getText().toString();
                                int   LQRCode = ETQRCode.getText().toString().trim().length();
                                if ((LQRCode != 25) && (LQRCode != 8)) {
                                    Toast.makeText(MakerActivity.this, "Check the QR Code", Toast.LENGTH_SHORT).show();
                                }else {
                                    QRCode = ETQRCode.getText().toString().trim();
                                    if (ETTare.getText().toString().isEmpty()) {
                                        Toast.makeText(MakerActivity.this, "Add Tare Weight", Toast.LENGTH_SHORT).show();

                                    } else {
                                        String currentString = QRCode.trim();
                                        if (QRCode.trim().length()==25){
                                            separated = currentString.replaceAll("www.pro.co.ke/ci/","");
                                        }
                                       else{
                                            separated = QRCode.trim();
                                        }

                                        qrcode=  separated;


                                        tareweight = Double.parseDouble(ETTare.getText().toString());
                                        if (MaterialDescription.contains("6KG")) {

                                            if (tareweight >= 7.5 && tareweight <= 10.0) {
                                                saveQR(OrderNumber, Year, Month, SerialNumber, qrcode, tareweight);
                                            } else {
                                                Toast.makeText(MakerActivity.this, "wrong tare weight of 6 KG", Toast.LENGTH_SHORT).show();
                                            }
                                        }

                                        if (MaterialDescription.contains("13KG")) {

                                            if (tareweight >= 11.0 && tareweight <= 14.0) {
                                                saveQR(OrderNumber, Year, Month, SerialNumber, qrcode, tareweight);
                                            } else {
                                                Toast.makeText(MakerActivity.this, "wrong tare weight of 13 KG", Toast.LENGTH_SHORT).show();
                                            }
                                        }

                                        if (MaterialDescription.contains("50KG")) {

                                            if (tareweight >= 36.0 && tareweight <= 40.0) {
                                                saveQR(OrderNumber, Year, Month, SerialNumber, qrcode, tareweight);
                                            } else {
                                                Toast.makeText(MakerActivity.this, "wrong tare of 50 KG", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    }


                                }
                            }


                        }


                    }
                }


            }


        });

        getData();

    }

    private void saveQR(String orderNumber, String year, String month, String serialNumber, String QRCode, double tareweight) {

        progressDialog = new ProgressDialog(MakerActivity.this);
        progressDialog.setMessage("Saving Maker Data");
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_UPLOADMAKERV2,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            JSONObject obj = new JSONObject(response);
                            boolean error = obj.getBoolean(KEY_ERROR);
                            String message = obj.getString(KEY_MESSAGED);
                            if (!error) {
                                ETSerialNumber.setText("");
                                ETQRCode.setText("");
                                ETTare.setText("");
                                SP_Year.getItemAtPosition(-1);
                                SP_Month.getItemAtPosition(-1);
                                Toast.makeText(MakerActivity.this, "" + message, Toast.LENGTH_SHORT).show();//updating the status in sqlite

                            } else {
                                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MakerActivity.this);
                                alertDialogBuilder.setTitle("Wrong Cylinder Info!!");
                                alertDialogBuilder.setMessage(message);
                                alertDialogBuilder.setIcon(R.mipmap.ic_launcher);
                                alertDialogBuilder.setMessage(message);
                                alertDialogBuilder.setCancelable(false)
                                        .setPositiveButton("Check", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                dialog.dismiss();
                                            }

                                        })
                                        .setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                dialog.dismiss();

                                            }
                                        });

                                AlertDialog alertDialog = alertDialogBuilder.create();
                                alertDialog.show();
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("ProductionOrder", String.valueOf(orderNumber));
                params.put("SerialNumber", serialNumber);
                params.put("CylinderCode", QRCode);
                params.put("Month", month);
                params.put("Year", year);
                params.put("EmptyWeight", String.valueOf(tareweight));
                params.put("CreatedBy", preferenceHelper.getUserID());
                return params;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                0,
                0,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        VolleySingleton.getInstance(MakerActivity.this).addToRequestQueue(stringRequest);
    }


    private void saveQR2(String orderNumber, String year, String month, String serialNumber, String QRCode, double tareweight) {


        JSONObject request = new JSONObject();
        try {
            //Populate the request parameters
            request.put("ProductionOrder", String.valueOf(orderNumber));
            request.put("SerialNumber", serialNumber);
            request.put("CylinderCode", QRCode);
            request.put("Month", month);
            request.put("Year", year);
            request.put("EmptyWeight", String.valueOf(tareweight));
            request.put("CreatedBy", preferenceHelper.getUserID());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsArrayRequest = new JsonObjectRequest(Request.Method.POST, LOGIN, request, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("res", String.valueOf(response));

                try {
                    //Check if user got logged in successfully

                    if (!response.getBoolean(KEY_ERROR)) {
                        ETSerialNumber.setText("");
                        ETQRCode.setText("");
                        ETTare.setText("");
                        SP_Year.getItemAtPosition(-1);
                        SP_Month.getItemAtPosition(-1);
                        Toast.makeText(MakerActivity.this, "" + response.getString(KEY_MESSAGED), Toast.LENGTH_SHORT).show();//updating the status in sqlite



                    } else {
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MakerActivity.this);
                        alertDialogBuilder.setTitle("Wrong Cylinder Info!!");
                        alertDialogBuilder.setMessage(response.getString(KEY_MESSAGED));
                        alertDialogBuilder.setIcon(R.mipmap.ic_launcher);
                        alertDialogBuilder.setCancelable(false)
                                .setPositiveButton("Check", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.dismiss();
                                    }

                                })
                                .setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.dismiss();

                                    }
                                });

                        AlertDialog alertDialog = alertDialogBuilder.create();
                        alertDialog.show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(MakerActivity.this, KEY_NETWORK, Toast.LENGTH_SHORT).show();


            }
        });

        // Access the RequestQueue through your singleton class.
        VolleySingleton.getInstance(this).addToRequestQueue(jsArrayRequest);
    }



    private void getData() {
        //Creating a string request
        progressDialog = new ProgressDialog(MakerActivity.this);
        progressDialog.setMessage("Loading ...");
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(GET_ORDERNUMBERS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            result = new JSONArray(response);
                            getOrderNumbers(result);
                            progressDialog.dismiss();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(MakerActivity.this, "Cant reach to server", Toast.LENGTH_SHORT).show();

                    }
                });

        //Creating a request queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        //Adding request to the queue
        requestQueue.add(stringRequest);
    }

    private void getOrderNumbers(JSONArray j) {
        outlets.add(initialstring);


        for (int i = 0; i < j.length(); i++) {
            try {
                //Getting json object
                JSONObject json = j.getJSONObject(i);
                //Adding the name of the student to array list
                outlets.add(json.getString("OrderNumber"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        SP_ProductionNumber.setAdapter(new ArrayAdapter<String>(MakerActivity.this, android.R.layout.simple_spinner_dropdown_item, outlets) {
            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {

                View v = null;

                if (position == 0) {
                    TextView tv = new TextView(getContext());
                    tv.setHeight(0);
                    tv.setVisibility(View.GONE);
                    v = tv;
                } else {

                    v = super.getDropDownView(position, null, parent);
                }

                parent.setVerticalScrollBarEnabled(false);
                return v;
            }
        });
    }

    private String getMaterialDesc(int position) {
        String ID = "";
        try {
            //Getting object of given index
            JSONObject json = result.getJSONObject(position - 1);

            //Fetching name from that object
            ID = json.getString("MaterialDescription");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //Returning the name
        return ID;
    }

    private String getExpectedQuantity(int position) {
        String ID = "";
        try {
            //Getting object of given index
            JSONObject json = result.getJSONObject(position - 1);

            //Fetching name from that object
            ID = json.getString("ExpectedQuantity");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //Returning the name
        return ID;
    }

    private String getProducedQuantity(int position) {
        String ID = "";
        try {
            //Getting object of given index
            JSONObject json = result.getJSONObject(position - 1);

            //Fetching name from that object
            ID = json.getString("ProducedQuantity");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //Returning the name
        return ID;
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        MaterialDescription = (getMaterialDesc(position));
        ExpectedQuantity = (getExpectedQuantity(position));
        ProducedQuantity = (getProducedQuantity(position));

        if (SP_ProductionNumber.getSelectedItem().toString().equals(initialstring)) {
            L_Details.setVisibility(View.GONE);
        } else {
            L_Details.setVisibility(View.VISIBLE);
            TVMaterialDescription.setText(MaterialDescription);
            TVProducedQuantity.setText(ProducedQuantity);
            TVExpectedQuantity.setText(ExpectedQuantity);
        }

    }


    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_sync) {

            if (merlinsBeard.isConnected()) {


                getData();

            } else {
                Toast.makeText(this, "no internet connection", Toast.LENGTH_SHORT).show();

            }

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
