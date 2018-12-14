package apackage.com.yournight.model.Location;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;


public class GeoOperations implements LocationListener {
    
    private Location location;
    private LocationManager locationManager;

    private double latitude;
    private double longitude;

    private final long MIN_DISTANCE_UP_ = 1;
    private final long MIN_TIME__UP =   10 * 300;

    private Handler delayhandler;
    private Runnable delayrunnable;

    private Activity activity;

    private static final int PERMISSION_ACCESS_COARSE_LOCATION = 11;

    public GeoOperations(Activity activity, Context context, boolean splash){
        this.activity = activity;


        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, new String[] { Manifest.permission.ACCESS_COARSE_LOCATION },
                    PERMISSION_ACCESS_COARSE_LOCATION);
        }

        if(!isLocationEnabled(context)){
              if(!splash){
            showGPSDisabledAlertToUser();
               }
        }
        else{
            discoverLocation();
        }
    }

    public LocationModel getLocation() {


        try {
            locationManager = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);


            if (ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME__UP, MIN_DISTANCE_UP_, this);
            }

            else{
                Log.e("Geo Network", "No Permission");
            }

            if(locationManager != null) {
                location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

                if (location != null) {
                    latitude = location.getLatitude();
                    longitude = location.getLongitude();

                    LocationModel locationModel = new LocationModel();
                    locationModel.setLatitude(latitude);
                    locationModel.setLongitute(longitude);

                    Log.e("Geo Network", "lat " + latitude);
                    Log.e("Geo Network", "long " + longitude);

                    return locationModel;
                } else {
                    Log.e("Geo Network", "location is null");
                }

            }
            else{
                Log.e("Geo Network", "locationManager is null");
            }

        } catch (Exception e) {
            e.printStackTrace();
            Log.e("Exception ", " " + e.toString());
        }

        return null;
    }

    private void discoverLocation() {

        delayhandler= new Handler();
        delayrunnable = new Runnable() {
            @Override
            public void run() {
                if(location == null) {
                    getLocation();
                    Log.e("geo" , "searching...  " );
                    delayhandler.postDelayed(this, 500);
                }
                else{
                    Log.e("geo" , "found...  " );
                    delayhandler.removeCallbacks(delayrunnable);
                }
            }
        };
        delayhandler.postDelayed(delayrunnable, 500);
    }


    public static boolean isLocationEnabled(Context context) {
        int locationMode = 0;
        String locationProviders;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            try {
                locationMode = Settings.Secure.getInt(context.getContentResolver(), Settings.Secure.LOCATION_MODE);

            } catch (Settings.SettingNotFoundException e) {
                e.printStackTrace();
                return false;
            }

            return locationMode != Settings.Secure.LOCATION_MODE_OFF;

        }else{
            locationProviders = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
            return !TextUtils.isEmpty(locationProviders);
        }
    }

    public void showGPSDisabledAlertToUser(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(activity);
        alertDialogBuilder.setMessage("GPS is disabled in your device. Please enable it")
                .setCancelable(false)
                .setPositiveButton("Goto Settings",
                        new DialogInterface.OnClickListener(){
                            public void onClick(DialogInterface dialog, int id){
                                Intent callGPSSettingIntent = new Intent(
                                        android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                activity.startActivity(callGPSSettingIntent);


                            }
                        });
        alertDialogBuilder.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int id){
                        dialog.cancel();
                    }
                });
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }


    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {
        Log.e("GEO", "Provider enabled");
        discoverLocation();
    }

    @Override
    public void onProviderDisabled(String s) {
        Log.e("GEO", "Provider disabled");
    }
}
