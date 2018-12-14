package apackage.com.yournight.model.network;

/**
 * Created by Aleksandr Soloninov on 17.11.2017.
 * Hochschule Worms, inf3032
 */

import android.content.Intent;

import apackage.com.yournight.model.access.AccessToken;
import apackage.com.yournight.presenter.listner.OnLoginCompleteListener;


public abstract class SocialNetwork {

    OnLoginCompleteListener listener;

    /**
     * Check if selected social network connected: true or false
     * @return true if connected, else false
     */
    public abstract boolean isConnected();

    public abstract void requestLogin(OnLoginCompleteListener listener);

    public void setListener(OnLoginCompleteListener listener) {
        this.listener = listener;
    }

    /**
     * Logout from social network
     */
    public abstract void logout();

    public abstract Network getNetwork();

    public abstract AccessToken getAccessToken();

    public abstract void onActivityResult(int requestCode, int resultCode, Intent data);

    public enum Network {
        FACEBOOK
    }

}
