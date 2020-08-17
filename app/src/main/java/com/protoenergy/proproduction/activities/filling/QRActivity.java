package com.protoenergy.proproduction.activities.filling;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.google.zxing.Result;
import com.protoenergy.proproduction.R;
import com.protoenergy.proproduction.user.PreferenceHelper;


import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;




public class QRActivity extends AppCompatActivity {

    private CodeScanner mCodeScanner;
    EditText TVQR;
    TextView TVCount;
    Button BtnManualSave, BtnComplete, BtnRemove;
    String TripID, GetOutletID, GetQR, GetQuantity, GetSaleID, GetProduct, GetSkuAmount, GetTotalQuantity;

    //DatabaseHelper db;
    final int delayinsert = 1500; //milliseconds
    String alreadyscanned = "You Have Already Scanned This Cylinder!";
    final Handler handler = new Handler();
    ProgressDialog progressDialog;


    int id = 0;
    int total = 0;
    int totalquantity = 0;
    int totalsingle = 0;
    int results;
    int productpricesingle, totalquantitysingle, resultssingle;

    TextView totalamount;
    int  CylinderSize;

    String OutletName, MaterialCode;
    String qrcode,visiblecode;
    String[] separated;
    PreferenceHelper preferenceHelper;
    String tripday,transtime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrfilling);
        progressDialog = new ProgressDialog(this);
        preferenceHelper=new PreferenceHelper(this);
        Intent intent = getIntent();
        GetSaleID = intent.getStringExtra("SaleID");
        GetOutletID = intent.getStringExtra("OutletID");
        GetProduct = intent.getStringExtra("Product");
        MaterialCode = intent.getStringExtra("MaterialCode");
        CylinderSize = (intent.getIntExtra("CylinderSize", 0));

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat day = new SimpleDateFormat("yyyyMMdd");
        String tripday = day.format(cal.getTime());


        SimpleDateFormat full = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String transtime = full.format(cal.getTime());

        int houroftheday = cal.get(Calendar.HOUR_OF_DAY);
        if (houroftheday>6 && houroftheday<19){
            TripID = tripday+"0";
        }
        else {
            TripID = tripday+"1";
        }
        GetOutletID=tripday;




        Log.d("SHIFTID", GetSaleID + " TripDay " + GetOutletID + " Product " + GetProduct + " CylinderSize " + CylinderSize);


        TVQR = findViewById(R.id.et_qr);
        TVCount = findViewById(R.id.tv_count);
        BtnRemove = findViewById(R.id.btnManualRemove);
        BtnManualSave = findViewById(R.id.btnManualSave);
        BtnComplete = findViewById(R.id.btnComplete);


        handler.postDelayed(new Runnable() {
            public void run() {

               // TVCount.setText(String.valueOf(db.getSoldQRSum(GetOutletID,TripID, GetProduct)));
               // GetQuantity = (String.valueOf(db.getSoldQRSum(GetOutletID,TripID, GetProduct)));
                handler.postDelayed(this, delayinsert);

            }


        }, delayinsert);


        TVQR.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);

        BtnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              /*  Intent i = new Intent(QRActivity.this,RemoveQRActivity.class);
                i.putExtra("OutletID",GetOutletID);
                i.putExtra("Product",GetProduct);
                i.putExtra("SaleID",GetSaleID);
                i.putExtra("MaterialCode",MaterialCode);
                i.putExtra("CylinderSize",CylinderSize);
                startActivity(i);*/

            }


        });
        BtnManualSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TVQR.getText().length() == 6 ||TVQR.getText().length() == 10) {

                } else {
                    Toast.makeText(QRActivity.this, "Invalid QRCODE", Toast.LENGTH_LONG).show();
                }
            }




        });


        BtnComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                onBackPressed();
            }
        });


        handler.postDelayed(new Runnable() {
            public void run() {


                if ((TVQR.getText().length() == 17 ||(TVQR.getText().length() == 18))) {
                    String qr = TVQR.getText().toString();
                    String currentString = qr.trim();
                    if(TVQR.getText().length() == 17){
                        separated = currentString.split(" ");

                    }
                    if(TVQR.getText().length() == 18){
                         separated = currentString.split("  ");

                    }
                    Log.d("sepa", String.valueOf(separated));

                    qrcode=  separated[0];
                    visiblecode=  separated[1];

                  /*  boolean recordExists = db.QrExistSold(qrcode,visiblecode);
                    if (recordExists) {
                       Toast.makeText(QRActivity.this, "Cylinder Already Scanned ", Toast.LENGTH_SHORT).show();
                        TVCount.setText(String.valueOf(db.getSoldQRSum(GetOutletID,TripID, GetProduct)));
                        GetQuantity = (String.valueOf(db.getSoldQRSum(GetOutletID,TripID, GetProduct)));
                        TVQR.setText("");
                        TVQR.requestFocus();
                    } else {
                        savefillingqr(GetSaleID,TripID,GetOutletID,visiblecode,qrcode,"1",GetProduct,NOT_SYNCED_WITH_SERVEREDITABLE);

                    }*/

                }
            /*    if ((TVQR.getText().length() == 18)) {
                    String qr = TVQR.getText().toString();
                    String currentString = qr.trim();
                    String[] separated = currentString.split("  ");
                    qrcode=  separated[0];
                    visiblecode=  separated[1];

                    boolean recordExists = db.QrExistSold(qr, GetOutletID);
                    if (recordExists) {
                        Toast.makeText(QRActivity.this, "Cylinder Already Scanned ", Toast.LENGTH_SHORT).show();
                        TVCount.setText(String.valueOf(db.getSoldQRSum(GetOutletID,TripID, GetProduct)));
                        GetQuantity = (String.valueOf(db.getSoldQRSum(GetOutletID,TripID, GetProduct)));
                        TVQR.setText("");
                        TVQR.requestFocus();
                    } else {

                        savefillingqr(GetSaleID,TripID,GetOutletID,visiblecode,qrcode,"1",GetProduct,NOT_SYNCED_WITH_SERVEREDITABLE);

                    }
                }*/



                handler.postDelayed(this, delayinsert);

            }




        }, delayinsert);


        if (ContextCompat.checkSelfPermission(QRActivity.this, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(QRActivity.this, new String[]{Manifest.permission.CAMERA}, 123);
        } else {
            startScanning();
        }
    }

/*
    private void savefillingqr(String getSaleID, String tripID, String getOutletID, String visiblecode, String qrcode, String s, String getProduct, int notSyncedWithServereditable) {
        JSONObject request = new JSONObject();
        try {

            request.put("AssociatedActivityID", tripID);
            request.put("QRCode", qrcode);
            request.put("VisibleCode", visiblecode);
            request.put("MovedBy", preferenceHelper.getUserID());
           // request.put("TerminalID", preferenceHelper.getContainerID());



        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, URL_SAVE_FILLINGQR, request, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                Log.d("FillingQR", String.valueOf(response));
                try {
                    //Check if user got logged in successfully
                    if (!response.getBoolean(KEY_ERROR)) {
                        Toast.makeText(QRActivity.this, ""+response.getString(KEY_MESSAGE), Toast.LENGTH_SHORT).show();
                        db.addSoldQR(getSaleID, tripID, getOutletID, visiblecode, qrcode, s, getProduct, notSyncedWithServereditable);
                        TVCount.setText(String.valueOf(db.getSoldQRSum(getOutletID,tripID, getProduct)));
                        GetQuantity = (String.valueOf(db.getSoldQRSum(getOutletID,tripID, getProduct)));
                        TVQR.setText("");
                        TVQR.requestFocus();
                    }
                    else {
                        TVQR.setText("");
                        TVQR.requestFocus();
                        AlertDialog.Builder builder;
                        builder = new AlertDialog.Builder(QRActivity.this);
                        builder.setTitle("wrong cylinder info")
                                .setMessage(""+response.getString(KEY_MESSAGE))
                                .setPositiveButton("OKAY", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }



                                })
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {


            }
        });
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                0,
                0,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getInstance(QRActivity.this).addToRequestQueue(jsonObjectRequest);
    }
*/

    private void startScanning() {
        CodeScannerView scannerView = findViewById(R.id.scanner_view);
        mCodeScanner = new CodeScanner(this, scannerView);
        mCodeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull final Result result) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        TVQR.setText(result.getText());
                        //Toast.makeText(QRActivity.this, result.getText(), Toast.LENGTH_SHORT).show();
                        mCodeScanner.startPreview();
                    }
                });
            }
        });
        scannerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCodeScanner.startPreview();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 123) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Camera permission granted", Toast.LENGTH_LONG).show();
                startScanning();
            } else {
                Toast.makeText(this, "Camera permission denied", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mCodeScanner != null) {
            mCodeScanner.startPreview();
        }
    }

    @Override
    protected void onPause() {
        if (mCodeScanner != null) {
            mCodeScanner.releaseResources();
        }
        super.onPause();
    }

    @Override
    public void onBackPressed() {
        GetQuantity = TVCount.getText().toString();

/*
        GetSkuAmount = String.valueOf(resultssingle);
        GetTotalQuantity = String.valueOf(totalquantitysingle);
        db.deleteProduct(GetOutletID, GetProduct);
        if (totalquantitysingle != 0) {

            db.addProduct(TripID, GetOutletID, GetSaleID, GetProduct, MaterialCode, GetQuantity, GetSkuAmount, NOT_SYNCED_WITH_SERVEREDITABLE);

        }*/
        super.onBackPressed();
    }
}