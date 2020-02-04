package com.protoenergy.proproduction.activities.mapping;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.LayoutInflater;
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
import com.protoenergy.proproduction.R;
import com.protoenergy.proproduction.user.PreferenceHelper;
import com.protoenergy.proproduction.user.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.protoenergy.proproduction.common.Constants.URLs.BASE_URL;
import static com.protoenergy.proproduction.common.Constants.URLs.GET_ORDERNUMBERS;
import static com.protoenergy.proproduction.common.Constants.URLs.URL_UPDATECHECKER;

public class CheckerActivity extends AppCompatActivity implements Spinner.OnItemSelectedListener {

    private Spinner SP_ProductionNumber, SP_Year, SP_Month;
    private ArrayList<String> outlets;
    private JSONArray result;
    ProgressDialog pd, pd2;
    Button btnSale, btnUpdate;


    String initialstring = "Choose an Order Number";
    LinearLayout L_Details;
    MerlinsBeard merlinsBeard;

    String MaterialDescription, ExpectedQuantity, ProducedQuantity;
    String OrderNumber, Year, Month, SerialNumber, QRCode;
    EditText ETSerialNumber, ETQRCode, ETTare;
    TextView TVMaterialDescription, TVExpectedQuantity, TVProducedQuantity;

    double tareweight;
    PreferenceHelper preferenceHelper;
    LinearLayout LADD, LUPDATE;
    Button btnClear1, btnClear2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checker);
        outlets = new ArrayList<>();
        pd = new ProgressDialog(this);
        pd2 = new ProgressDialog(this);
        preferenceHelper = new PreferenceHelper(this);
        SP_ProductionNumber = findViewById(R.id.sp_productionnumber);
        SP_ProductionNumber.setOnItemSelectedListener(this);

        TVMaterialDescription = findViewById(R.id.tv_material);
        TVExpectedQuantity = findViewById(R.id.tv_expected);
        TVProducedQuantity = findViewById(R.id.tv_produced);
        LADD = findViewById(R.id.l_add);
        LUPDATE = findViewById(R.id.l_update);

        ETSerialNumber = findViewById(R.id.et_serialnumber);
        ETQRCode = findViewById(R.id.et_qr);
        ETTare = findViewById(R.id.et_tare);
        L_Details = findViewById(R.id.l_detail);
        SP_Year = findViewById(R.id.sp_year);
        SP_Month = findViewById(R.id.sp_month);
        btnSale = findViewById(R.id.btn_Save);
        btnUpdate = findViewById(R.id.btn_Update);


        btnClear1 = findViewById(R.id.btn_Clear);
        btnClear2 = findViewById(R.id.btn_clear2);

        btnClear1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ETSerialNumber.setText("");
                ETQRCode.setText("");
                ETTare.setText("");
            }
        });
        btnClear2.setOnClickListener(new View.OnClickListener() {
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
                    Toast.makeText(CheckerActivity.this, "Please choose order number", Toast.LENGTH_SHORT).show();
                } else {
                    OrderNumber = SP_ProductionNumber.getSelectedItem().toString();

                    if (SP_Year.getSelectedItem().toString().equals("The Year")) {
                        Toast.makeText(CheckerActivity.this, "choose the year", Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(CheckerActivity.this, "choose the Month", Toast.LENGTH_SHORT).show();
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
                                Toast.makeText(CheckerActivity.this, "Serial Number is 6", Toast.LENGTH_SHORT).show();
                            } else {
                                SerialNumber = ETSerialNumber.getText().toString();
                                if ((ETQRCode.getText().toString().length() != 10) && (ETQRCode.getText().toString().length() != 6)) {
                                    Toast.makeText(CheckerActivity.this, "Check the QR", Toast.LENGTH_SHORT).show();
                                } else {
                                    QRCode = ETQRCode.getText().toString();
                                    if (ETTare.getText().toString().isEmpty()) {
                                        Toast.makeText(CheckerActivity.this, "Add Tare weight", Toast.LENGTH_SHORT).show();

                                    } else {
                                        tareweight = Double.parseDouble(ETTare.getText().toString());
                                        if (MaterialDescription.contains("6KG")) {

                                            if (tareweight >= 7.7 && tareweight <= 9.5) {
                                                saveQR(OrderNumber, Year, Month, SerialNumber, QRCode, tareweight);
                                            } else {
                                                Toast.makeText(CheckerActivity.this, "wrong tare of 6 kg", Toast.LENGTH_SHORT).show();
                                            }
                                        }

                                        if (MaterialDescription.contains("13KG")) {

                                            if (tareweight >= 12.0 && tareweight <= 14.0) {
                                                saveQR(OrderNumber, Year, Month, SerialNumber, QRCode, tareweight);
                                            } else {
                                                Toast.makeText(CheckerActivity.this, "wrong tare of 13 kg", Toast.LENGTH_SHORT).show();
                                            }
                                        }

                                        if (MaterialDescription.contains("50KG")) {

                                            if (tareweight >= 36.0 && tareweight <= 40.0) {
                                                saveQR(OrderNumber, Year, Month, SerialNumber, QRCode, tareweight);
                                            } else {
                                                Toast.makeText(CheckerActivity.this, "wrong tare of 50 KG", Toast.LENGTH_SHORT).show();
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


        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SP_ProductionNumber.getSelectedItem().toString().equals(initialstring)) {
                    L_Details.setVisibility(View.GONE);
                    Toast.makeText(CheckerActivity.this, "Please choose order number", Toast.LENGTH_SHORT).show();
                } else {
                    OrderNumber = SP_ProductionNumber.getSelectedItem().toString();

                    if (SP_Year.getSelectedItem().toString().equals("The Year")) {
                        Toast.makeText(CheckerActivity.this, "choose the year", Toast.LENGTH_SHORT).show();
                    } else {
                        if (SP_Year.getSelectedItem().toString().equals("2018")) {
                            Year = "A";
                        }
                        if (SP_Year.getSelectedItem().toString().equals("2019")) {
                            Year = "B";
                        }

                        if (SP_Month.getSelectedItem().toString().equals("The Month")) {
                            Toast.makeText(CheckerActivity.this, "choose the Month", Toast.LENGTH_SHORT).show();
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
                                Toast.makeText(CheckerActivity.this, "Serial Number is 6 digit", Toast.LENGTH_SHORT).show();
                            } else {
                                SerialNumber = ETSerialNumber.getText().toString();
                                if ((ETQRCode.getText().toString().length() != 10) && (ETQRCode.getText().toString().length() != 6)) {
                                    Toast.makeText(CheckerActivity.this, "Check the QR", Toast.LENGTH_SHORT).show();
                                } else {
                                    QRCode = ETQRCode.getText().toString();
                                    if (ETTare.getText().toString().isEmpty()) {
                                        Toast.makeText(CheckerActivity.this, "Add Tare weight", Toast.LENGTH_SHORT).show();

                                    } else {
                                        tareweight = Double.parseDouble(ETTare.getText().toString());
                                        if (MaterialDescription.contains("6KG")) {

                                            if (tareweight >= 7.7 && tareweight <= 9.5) {
                                                UpdateQR(OrderNumber, Year, Month, SerialNumber, QRCode, tareweight);
                                            } else {
                                                Toast.makeText(CheckerActivity.this, "wrong tare of 6KG", Toast.LENGTH_SHORT).show();
                                            }
                                        }

                                        if (MaterialDescription.contains("13KG")) {

                                            if (tareweight >= 12.0 && tareweight <= 14.0) {
                                                UpdateQR(OrderNumber, Year, Month, SerialNumber, QRCode, tareweight);
                                            } else {
                                                Toast.makeText(CheckerActivity.this, "wrong tare of 13KG", Toast.LENGTH_SHORT).show();
                                            }
                                        }

                                        if (MaterialDescription.contains("50KG")) {


                                            if (tareweight >= 36.0 && tareweight <= 40.0) {
                                                UpdateQR(OrderNumber, Year, Month, SerialNumber, QRCode, tareweight);
                                            } else {
                                                Toast.makeText(CheckerActivity.this, "wrong tare of 50 KG", Toast.LENGTH_SHORT).show();
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
        pd2.setTitle("Checker ...");
        pd2.setMessage("Saving Info !...");
        pd2.show();
        JsonObjectRequest jsonArrayRequest = new JsonObjectRequest(Request.Method.GET, BASE_URL + "production/validateqr?ProductionOrder=" + orderNumber + "&SerialNumber=" + serialNumber + "&Year=" + year + "&Month=" + month + "&EmptyWeight=" + tareweight + "&UpdatedBy=" + preferenceHelper.getUserID() + "&CylinderCode=" + QRCode
                , null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject obj) {
                        Log.d("Resr", String.valueOf(obj));

                        try {
                            // Loop through the array elements
                            for (int i = 0; i < obj.length(); i++) {

                                pd2.dismiss();
                                boolean error = obj.getBoolean("Error");
                                String message = obj.getString("Message");

                                if (message.contains("Successfully")) {
                                    pd2.dismiss();
                                    Toast.makeText(CheckerActivity.this, message, Toast.LENGTH_SHORT).show();//updating the status in sqlite
                                    LUPDATE.setVisibility(View.GONE);
                                    ETSerialNumber.setText("");
                                    ETQRCode.setText("");
                                    ETTare.setText("");
                                    SP_Year.getItemAtPosition(-1);
                                    SP_Month.getItemAtPosition(-1);

                                } else if (message.contains("No  Cylinder with")) {
                                    new AlertDialog.Builder(CheckerActivity.this)
                                            .setIcon(R.mipmap.ic_launcher)
                                            .setTitle("No such cylinder Code")
                                            .setMessage("")
                                            .setCancelable(true)
                                            .setPositiveButton("CHECK", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    dialog.dismiss();
                                                }
                                            })
                                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    dialog.cancel();
                                                }
                                            }).show();
                                } else {


                                    String SerialNumber = obj.getString("SerialNumber");
                                    String TagQRCode = obj.getString("TagQRCode");
                                    String TagVisible = obj.getString("TagVisible");
                                    String EmptyWeight = obj.getString("EmptyWeight");
                                    String Message = obj.getString("Message");
                                    btnUpdate.setVisibility(View.VISIBLE);
                                    LADD.setVisibility(View.GONE);
                                    LUPDATE.setVisibility(View.VISIBLE);
                                    ETSerialNumber.setText(SerialNumber);
                                    ETQRCode.setText(TagQRCode);
                                    ETTare.setText(EmptyWeight);
                                    // dsg();
                                    // AlertDialogCreate(SerialNumber, TagQRCode, EmptyWeight);
                                    pd2.dismiss();


                                }


                            }
                        } catch (JSONException e) {
                            pd2.dismiss();
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();

                        }
                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Do something when error occurred
                    }
                }
        );
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }






    public void AlertDialogCreate(String serialNumber, String tagQRCode, String emptyWeight) {
        final AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Wrong Info")
                .setMessage("")
                .setPositiveButton("UPDATE", null)
                .setNegativeButton("Cancel", null)
                .setCancelable(true)
                .show();

        Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
        positiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnUpdate.setVisibility(View.VISIBLE);
                LADD.setVisibility(View.GONE);
                LUPDATE.setVisibility(View.VISIBLE);
                ETSerialNumber.setText(serialNumber);
                ETQRCode.setText(tagQRCode);
                ETTare.setText(emptyWeight);
                dialog.dismiss();
            }
        });


    }


    private void UpdateQR(String orderNumber, String year, String month, String serialNumber, String QRCode, double tareweight) {
        pd2.setMessage("Saving Maker Data");
        pd2.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_UPDATECHECKER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        pd2.dismiss();
                        try {
                            JSONObject obj = new JSONObject(response);
                            boolean error = obj.getBoolean("Error");
                            String message = obj.getString("Message");
                            if (!error) {
                                Toast.makeText(CheckerActivity.this, "" + message, Toast.LENGTH_SHORT).show();//updating the status in sqlite
                                LADD.setVisibility(View.VISIBLE);
                                LUPDATE.setVisibility(View.GONE);

                                ETSerialNumber.setText("");
                                ETQRCode.setText("");
                                ETTare.setText("");
                                SP_Year.getItemAtPosition(-1);
                                SP_Month.getItemAtPosition(-1);

                            } else {
                                Toast.makeText(CheckerActivity.this, "" + message, Toast.LENGTH_SHORT).show();//updating the status in sqlite

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
                params.put("UpdatedBy", preferenceHelper.getUserID());


                return params;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                0,
                0,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        VolleySingleton.getInstance(CheckerActivity.this).addToRequestQueue(stringRequest);
    }


    private void getData() {
        //Creating a string request
        pd = new ProgressDialog(CheckerActivity.this);
        pd.setMessage("Loading ...");
        pd.show();
        StringRequest stringRequest = new StringRequest(GET_ORDERNUMBERS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            result = new JSONArray(response);
                            getOrderNumbers(result);
                            pd.dismiss();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        pd.dismiss();
                        Toast.makeText(CheckerActivity.this, "Cant reach to server", Toast.LENGTH_SHORT).show();

                    }
                });

        //Creating a request queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        //Adding request to the queue
        requestQueue.add(stringRequest);
    }

    private void getOrderNumbers(JSONArray j) {
        //Traversing through all the items in the json array
        //  outlets.add(initialstring);
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

        SP_ProductionNumber.setAdapter(new ArrayAdapter<String>(CheckerActivity.this, android.R.layout.simple_spinner_dropdown_item, outlets) {
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
