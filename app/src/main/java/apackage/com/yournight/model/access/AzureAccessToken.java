package apackage.com.yournight.model.access;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import apackage.com.yournight.model.network.EventNetwork;

/**
 * Created by Aleksandr Soloninov on 03.12.2017.
 * Hochschule Worms, inf3032
 */

public class AzureAccessToken extends android.os.AsyncTask {

    final private static String QUERY = "http://eventwebservice20180502044802.azurewebsites.net/token";
    final private static String BODY = "username=user&password=yournightpass&grant_type=password";
    final private static String TYPE = "application/json; charset=UTF-8";

    private Activity activity;

    public AzureAccessToken(Activity activity){
        this.activity = activity;
    }
    
    @Override
    protected Object doInBackground(Object[] objects) {
        try {
            StringBuilder result = new StringBuilder();
            URL url = new URL(QUERY);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setConnectTimeout(5000);
            connection.setRequestProperty("Content-Type", TYPE);
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestMethod("POST");

            OutputStream stream = connection.getOutputStream();
            stream.write(BODY.getBytes("UTF-8"));
            stream.close();
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }
            reader.close();
            connection.disconnect();

            Log.e("AzureToken", "Result " + result.toString());

            try {
                JSONObject jsonObj = new JSONObject(result.toString());

                String expire = jsonObj.getString("expires_in");
                String access_token = jsonObj.getString("access_token");

                long expiredTime = (Integer.valueOf(expire) * 1000) + System.currentTimeMillis();

                EventNetwork.azureAccessToken = access_token;

                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(activity);
                SharedPreferences.Editor editor = preferences.edit();
                
                editor.putString("azureAccessToken", access_token);
                editor.putLong("expiredTime", expiredTime);
                editor.apply();

                Log.e("AzureToken", "AccessToken " + access_token);
                Log.e("AzureToken", "expire " + expire);

            } catch (JSONException e) {
                Log.e("AzureToken", "Could not get Access token from Azure Webservice " + e.toString());
                e.printStackTrace();
            }

            return null;

        } catch (UnsupportedEncodingException e) {
            Log.e("AzureToken", "UnsupportedEncodingException.... Could not get Access token from Azure Webservice " + e.toString());
            e.printStackTrace();
        } catch (ProtocolException e) {
            Log.e("AzureToken", "ProtocolException ... Could not get Access token from Azure Webservice " + e.toString());
            e.printStackTrace();
        } catch (MalformedURLException e) {
            Log.e("AzureToken", "MalformedURLException... Could not get Access token from Azure Webservice " + e.toString());
            e.printStackTrace();
        } catch (IOException e) {
            Log.e("AzureToken", "IOException... Could not get Access token from Azure Webservice " + e.toString());
            e.printStackTrace();
        }
        return null;
    }
}
