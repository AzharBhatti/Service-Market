package com.example.servicemarket.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.example.servicemarket.R;
import com.example.servicemarket.model.LoginRequest;
import com.example.servicemarket.model.LoginResponse;
import com.example.servicemarket.utils.Constants;
import com.example.servicemarket.utils.CoreService;
import com.example.servicemarket.utils.GPSTracker;
import com.example.servicemarket.utils.Services;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;
    ProgressDialog progressDialog;
    String latitude = "";
    String longitude = "";
    EditText username_et, password_et;
    Button login_btn;

    @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
        setContentView(R.layout.activity_login);

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please wait..");

//        GPSTracker gpsTracker = new GPSTracker(this);
//
//        if (gpsTracker.getIsGPSTrackingEnabled()) {
//            latitude = String.valueOf(gpsTracker.getLatitude());
//            longitude = String.valueOf(gpsTracker.getLongitude());
//        }

        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if(checkAndRequestPermissions()) {
            // carry on the normal flow, as the case of  permissions  granted.
            @SuppressLint("MissingPermission") Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            longitude = String.valueOf(location.getLongitude());
            latitude = String.valueOf(location.getLatitude());
            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 10, locationListener);
        }



        username_et = findViewById(R.id.username_et);
        password_et = findViewById(R.id.password_et);
        login_btn = findViewById(R.id.login_btn);

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(username_et.getText().toString().equalsIgnoreCase(""))
                {
                    username_et.setError("Please enter a valid username");
                }
                else if(password_et.getText().toString().equalsIgnoreCase(""))
                {
                    username_et.setError("Please enter a password");
                }
                else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            loginTask();
                        }
                    });

                }
            }
        });

    }

    private void loginTask()
    {
        if(!LoginActivity.this.isFinishing())
        {
            progressDialog.show();
        }

        Services coreApi = CoreService.getInstance(Constants.URL);

        Call<LoginResponse<Object>> callback = coreApi.login(new LoginRequest(username_et.getText().toString(), password_et.getText().toString(), latitude, longitude));
        callback.enqueue(new Callback<LoginResponse<Object>>() {
            @Override
            public void onResponse(Call<LoginResponse<Object>> call, Response<LoginResponse<Object>> response) {


                LoginResponse<Object> body = response.body();
//                LoginResponse<Object> body = response.body();
                if(body != null && body.getResponseCode() != null) {
                    if (body.getResponseCode().equalsIgnoreCase("00100")) {

                        progressDialog.dismiss();

                        AlertDialog alertDialog = new AlertDialog.Builder(LoginActivity.this).create();
                        alertDialog.setTitle("Alert!");
                        alertDialog.setMessage("Authentication Successful!");
                        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        alertDialog.show();

                        progressDialog.dismiss();

                    }
                    else if (body.getResponseCode().equalsIgnoreCase("00300")) {

                        progressDialog.dismiss();

                        AlertDialog alertDialog = new AlertDialog.Builder(LoginActivity.this).create();
                        alertDialog.setTitle("Alert!");
                        alertDialog.setMessage("Please enter a valid user!");
                        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        alertDialog.show();

                        progressDialog.dismiss();

                    }
                    else if (body.getResponseCode().equalsIgnoreCase("00400")) {

                        progressDialog.dismiss();

                        AlertDialog alertDialog = new AlertDialog.Builder(LoginActivity.this).create();
                        alertDialog.setTitle("Alert!");
                        alertDialog.setMessage("User has been suspended, contact administration for further support!");
                        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        alertDialog.show();

                        progressDialog.dismiss();

                    } else {
                        AlertDialog alertDialog = new AlertDialog.Builder(LoginActivity.this).create();
                        alertDialog.setTitle("Alert!");
                        alertDialog.setMessage(body.getResponseMessage());
                        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        alertDialog.show();

                        progressDialog.dismiss();
                    }
                }
                else {
                    progressDialog.dismiss();
                    AlertDialog alertDialog = new AlertDialog.Builder(LoginActivity.this).create();
                    alertDialog.setTitle("Alert!");
                    alertDialog.setMessage("Something went wrong, Please try again!");
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    alertDialog.show();
                }

//                AccountsCustomListAdapter customAdapter = new AccountsCustomListAdapter(CnicValidate.this, R.layout.account_listview_items, accountList);
//
//                accountListView.setAdapter(customAdapter);



            }

            @Override
            public void onFailure(Call<LoginResponse<Object>> call, Throwable t) {

                t.printStackTrace();
                progressDialog.dismiss();
                AlertDialog alertDialog = new AlertDialog.Builder(LoginActivity.this).create();
                alertDialog.setTitle("Alert!");
                alertDialog.setMessage("Something went wrong, Please try again!");
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                alertDialog.show();
            }
        });



    }

    private final LocationListener locationListener = new LocationListener() {
        public void onLocationChanged(Location location) {
            longitude = String.valueOf(location.getLongitude());
            latitude = String.valueOf(location.getLatitude());
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };

    private  boolean checkAndRequestPermissions() {
        int permissionCoarse = ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION);
        int locationPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        List<String> listPermissionsNeeded = new ArrayList<>();
        if (locationPermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (permissionCoarse != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.ACCESS_COARSE_LOCATION);
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]),REQUEST_ID_MULTIPLE_PERMISSIONS);
            return false;
        }
        return true;
    }
}
