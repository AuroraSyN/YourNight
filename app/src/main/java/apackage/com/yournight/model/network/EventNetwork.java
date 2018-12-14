package apackage.com.yournight.model.network;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import apackage.com.yournight.model.Database.BadApiTestEvents;
import apackage.com.yournight.model.Location.GoogleFusedLocationProvider;
import apackage.com.yournight.model.Location.LocationModel;
import apackage.com.yournight.model.event.Event;
import apackage.com.yournight.model.event.EventParser;

/**
 * Created by Aleksandr Soloninov on 21.11.2017.
 * Hochschule Worms, inf3032
 */

public class EventNetwork {
    final static private String jsonURL = "https://eventaccesswebservice.azurewebsites.net/values";
    public static Event[] events;
    public static List<Event> eventsList;
    public static String azureAccessToken;
    /* init */
    private RequestQueue requestQueue;
    private JsonObjectRequest jsonObjectRequest;
    private JSONArray jsonArray;
    private Gson gson;
    private int errorCounter = 0;
    private GoogleFusedLocationProvider googleFusedLocationProvider;
    private JSONObject sendJSONObject;
    private Activity activity;

    private static final int REQUEST_PERMISSIONS_REQUEST_CODE = 34;

    private Handler delayhandler;
    private Runnable delayrunnable;
    private int counterDisover = 0;

    private BadApiTestEvents badApiTestEvents = new BadApiTestEvents();


    /* Constructor */
    public EventNetwork(Activity activity, RequestQueue requestQueue, GoogleFusedLocationProvider googleFusedLocationProvider) {
        this.activity = activity;
        this.requestQueue = requestQueue;
        this.googleFusedLocationProvider = googleFusedLocationProvider;
        sendJSONObject = new JSONObject();

        if (!checkPermissions()) {
            getEventsFromServer(null);
            requestPermissions();
        }
        else{
            if(isLocationEnabled(activity)){
                discoverLocation();
            }
            else{
                showGPSDisabledAlertToUser();
                getEventsFromServer(null);
            }
        }
    }

    private void discoverLocation() {

        delayhandler= new Handler();
        delayrunnable = new Runnable() {
            @Override
            public void run() {

                if(counterDisover <= 5){
                    LocationModel locationModel = googleFusedLocationProvider.getLocation();

                    if(locationModel != null){
                        Log.e("geo" , "found...  " );
                        delayhandler.removeCallbacks(delayrunnable);

                        getEventsFromServer(locationModel);
                    }
                    else{
                        Log.e("geo" , "searching...  " + counterDisover );
                        delayhandler.postDelayed(this, 1000);
                        counterDisover++;
                    }
                }
                else {
                    getEventsFromServer(null);
                }
            }
        };
        delayhandler.postDelayed(delayrunnable, 500);
    }

    public static boolean isLocationEnabled(Activity activity) {
        int locationMode = 0;
        String locationProviders;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            try {
                locationMode = Settings.Secure.getInt(activity.getContentResolver(), Settings.Secure.LOCATION_MODE);

            } catch (Settings.SettingNotFoundException e) {
                e.printStackTrace();
                return false;
            }

            return locationMode != Settings.Secure.LOCATION_MODE_OFF;

        }else{
            locationProviders = Settings.Secure.getString(activity.getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
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

    private boolean checkPermissions() {
        int permissionState = ActivityCompat.checkSelfPermission(activity,
                Manifest.permission.ACCESS_COARSE_LOCATION);
        return permissionState == PackageManager.PERMISSION_GRANTED;
    }

    private void startLocationPermissionRequest() {
        ActivityCompat.requestPermissions(activity,
                new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                REQUEST_PERMISSIONS_REQUEST_CODE);
    }

    private void requestPermissions() {
        boolean shouldProvideRationale =
                ActivityCompat.shouldShowRequestPermissionRationale(activity,
                        Manifest.permission.ACCESS_COARSE_LOCATION);

        // Provide an additional rationale to the user. This would happen if the user denied the
        // request previously, but didn't check the "Don't ask again" checkbox.
        if (shouldProvideRationale) {
            Log.i("FusedProvider", "Displaying permission rationale to provide additional context.");
            startLocationPermissionRequest();
        } else {
            Log.i("FusedProvider", "Requesting permission");
            // Request permission. It's possible this can be auto answered if device policy
            // sets the permission in a given state or the user denied the permission
            // previously and checked "Never ask again".
            startLocationPermissionRequest();
        }
    }

    final synchronized public void getEventsFromServer(LocationModel locationModel) {
        //default berlin 52.515894, 13.377155
        //frankfurt 50.112231, 8.626535
        //frankenthal 49.526058,8.363838
        double latPos = 52.5158948;
        double longPos = 13.377155;

        if(locationModel == null){
            Log.e("EventNetwork", "locationModel is null");
        }
        else {
            Log.e("EventNetwork", "locationModel is not null !!!");
            Log.e("EventNetwork", "Lat " + locationModel.getLatitude());
            Log.e("EventNetwork", "Long " + locationModel.getLongitute());
            latPos = locationModel.getLatitude();
            longPos = locationModel.getLongitute();
        }
        try {
            /* lat, long, distance */
            sendJSONObject.put("Lat", latPos);
            sendJSONObject.put("Long", longPos);
            sendJSONObject.put("Distance", 5000);
            sendJSONObject.put("Limit", 10);
        } catch (Exception e) {
            Log.e("EventNetwork", "Error " + e.toString());
            e.printStackTrace();
        }
        String tempJsonURL="http://yournightserver.azurewebsites.net/events?" +
                "lat=" + String.valueOf(latPos) + "&" +
                "lng=" + String.valueOf(longPos) + "&" +
                "limit=" + String.valueOf(10) + "&" +
                "distance=" + String.valueOf(5000) + "&" +
                "sort=venue" +
                "&accessToken=EAACEdEose0cBAC5qaZBdpJhzQoZCXGgOV5xqY3JqvhkMToARV84o" +
                "lG4MZAIBMl0dRoJED2xRFQxzZBEZC2YTbJhDYUSw2aCBaZA0KfsiUMA5VOhji6VL9owdSZCeDMGZBFqiBBbDI9NddP91LHNlAfIAVZBovZBk3UcfIuDQejGz48TGvTG3tyeZBhy07MNyHFObJqGPToTDdt3iwZDZD";

        Log.e("sendJSONObject", sendJSONObject.toString());
        Log.e("Parse URL: ", tempJsonURL);


        // Facebook Api is down
        Event [] badApi = badApiTestEvents.getDownEvents();
        eventsList = new ArrayList<Event>();
        badApi[3].setName("PSE Präsi");
        badApi[2].setName("KFC Fanclub");
        badApi[5].setName("Daily Scrum");
        badApi[4].setName("Champions League");
        badApi[2].setCity("Worms");
        for (int i = 0; i < 29 ; i++) {
            Log.e("badApiTestEvents", "eventList size is : " + eventsList.size());

            eventsList.add(badApi[i]);
            Log.e("badApiTestEvents", badApi[i].getName());
            Log.e("badApiTestEvents", badApi[i].getId());
            Log.e("badApiTestEvents", badApi[i].getStartTime());
        }


        (new EventParser(activity)).execute();

        /*
        // Creating the JsonObjectRequest class called obreq, passing required parameters:
        // GET is used to fetch data from the server, JsonURL is the URL to be fetched from.
        // sendJSONObject,
        jsonObjectRequest = new JsonObjectRequest(Request.Method.DEPRECATED_GET_OR_POST, tempJsonURL,
                // The third parameter Listener overrides the method onResponse() and passes
                // JSONObject as a parameter
                new Response.Listener<JSONObject>() {
                    // Takes the response from the JSON request
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            jsonArray = response.getJSONArray("events");
                            Log.e("EventNetwork", "true responded");
                            eventsList = jsonService();

                            (new EventParser(activity)).execute();
                        } catch (JSONException e) {
                            Log.e("EventNetwork", "JSONException " + e.toString());
                            e.printStackTrace();
                        }
                    }
                },

                new Response.ErrorListener() {
                    @Override

                    public void onErrorResponse(VolleyError error) {
                        Log.e("Volley has error", "Error # " + errorCounter + " > " +error.toString());
                        getEventsFromServer(null);
                        try {
                            Thread.sleep(3000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        errorCounter++;
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                String auth = "bearer " + azureAccessToken;
                headers.put("Authorization", auth);
                Log.e("HEADERS",headers.get("Authorization").toString());
                return headers;
            }
        };
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        requestQueue.add(jsonObjectRequest);
        */
    }

    private List<Event> jsonService() {
        gson = new Gson();
        events = gson.fromJson(jsonArray.toString(), Event[].class);
        return Arrays.asList(events);
    }
}