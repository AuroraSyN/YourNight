package apackage.com.yournight.view;

/**
 * Created by Aleksandr Soloninov on 17.11.2017.
 * Hochschule Worms, inf3032
 */

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.login.widget.LoginButton;

import java.util.Arrays;
import java.util.List;

import apackage.com.yournight.R;
import apackage.com.yournight.model.access.AccessLogin;
import apackage.com.yournight.model.access.AccessToken;
import apackage.com.yournight.model.access.AzureAccessToken;
import apackage.com.yournight.model.network.EventNetwork;
import apackage.com.yournight.model.network.FacebookNetwork;
import apackage.com.yournight.model.network.SocialNetwork;
import apackage.com.yournight.presenter.listner.OnLoginCompleteListener;

public class LoginPage extends AppCompatActivity implements OnLoginCompleteListener {

    private AccessLogin assesLogin;
    private LoginButton loginButton;
    private FacebookNetwork facebook;

    //TODO some bug with azure acesses token
    //TODO get server time
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String azureAccessToken = preferences.getString("azureAccessToken", "");
        long expiredTime = preferences.getLong("expires_in", 0);


        (new AzureAccessToken(this)).execute();

//        if(!azureAccessToken.equalsIgnoreCase("")) {
//            if (expiredTime >= System.currentTimeMillis()) {
//
//                Log.e("LoginPage", "Token expired... Get Azure Access Token");
//
//                (new AzureAccessToken(this)).execute();
//            } else {
//                Log.e("LoginPage", "Get token from SharedPreference... Get Azure Access Token");
//                EventNetwork.azureAccessToken = azureAccessToken;
//            }
//        }
//        else{
//            Log.e("LoginPage", "Get Azure Access Token, no token in SharedPreference");
//            (new AzureAccessToken(this)).execute();
//        }
        
        AccessLogin.initialize();
        assesLogin = AccessLogin.getInstance();
        setContentView(R.layout.activity_login_page);
        loginButton = findViewById(R.id.facebook_loginButton);
        List<String> fbScope = Arrays.asList("public_profile", "email");
        assesLogin.addSocialNetwork(new FacebookNetwork(this, fbScope));
        facebook = (FacebookNetwork) assesLogin.getSocialNetwork(SocialNetwork.Network.FACEBOOK);
        //facebook.logout();

        // start splash screen (and main activity)
        final Intent intent = new Intent(LoginPage.this, SplashScreen.class);

        if (facebook.isConnected()){

            AccessToken token = facebook.getAccessToken();
            assesLogin.setToken(token);
            Log.e("LoginPage", "Facebook AccessLogin(auto) successful : ");
            Log.e("LoginPage","with token : " + token.getToken());
            Log.e("LoginPage"," -> email : " + token.getEmail());
            Log.e("LoginPage"," -> secret: " + token.getSecret());
            startActivity(intent);
        } else {
            facebook.requestLogin(loginButton, this);
        }

        TextView login_no_facebook = findViewById(R.id.btn_login_no_facebook);
        login_no_facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        updateStatuses();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        assesLogin.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onLoginSuccess(SocialNetwork.Network network) {
        if (network == SocialNetwork.Network.FACEBOOK) {
            AccessToken token = assesLogin.getSocialNetwork(SocialNetwork.Network.FACEBOOK).getAccessToken();
            assesLogin.setToken(token);
            Log.e("LoginPage", "Facebook AccessLogin successful with token : " + token.getToken() + "\n" + "and Email : " + token.getEmail());
            Intent intent = new Intent(LoginPage.this, SplashScreen.class);
            startActivity(intent);
        }
        updateStatuses();
    }

    @Override
    public void onError(SocialNetwork.Network socialNetwork, String errorMessage) {
        Log.e("LoginPage", "ERROR!" + socialNetwork + "|||" + errorMessage);
        Toast.makeText(getApplicationContext(), socialNetwork.name() + ": " + errorMessage,
                Toast.LENGTH_SHORT).show();
    }

    private void updateStatuses() {
        StringBuilder content = new StringBuilder();
        for (SocialNetwork socialNetwork : assesLogin.getInitializedSocialNetworks()) {
            content.append(socialNetwork.getNetwork())
                    .append(": ")
                    .append(socialNetwork.isConnected())
                    .append("\n");
        }
    }

    public void logoutAllNetworks(View view) {
        for (SocialNetwork socialNetwork : assesLogin.getInitializedSocialNetworks()) {
            socialNetwork.logout();
        }
        updateStatuses();
    }
}
