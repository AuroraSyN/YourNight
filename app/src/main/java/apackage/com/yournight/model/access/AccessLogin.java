package apackage.com.yournight.model.access;

/**
 * Created by Aleksandr Soloninov on 17.11.2017.
 * Hochschule Worms, inf3032
 */


import android.content.Intent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import apackage.com.yournight.model.network.SocialNetwork;

public class AccessLogin {

    private String userEmail;
    private String userName;
    private String userID;

    private static AccessLogin instance = null;

    private Map<SocialNetwork.Network, SocialNetwork> socialNetworksMap = new HashMap<>();

    private AccessToken token;


    private AccessLogin() {
    }

    public static void initialize() {
        if (instance == null) {
            instance = new AccessLogin();
        }
    }

    public static AccessLogin getInstance() {
        return instance;
    }


    public void addSocialNetwork(SocialNetwork socialNetwork) {
        if (socialNetworksMap.get(socialNetwork.getNetwork()) != null) {
            throw new RuntimeException("Social network with id = " + socialNetwork.getNetwork() + " already exists");
        }

        socialNetworksMap.put(socialNetwork.getNetwork(), socialNetwork);
    }

    public SocialNetwork getSocialNetwork(SocialNetwork.Network network) throws RuntimeException {
        if (!socialNetworksMap.containsKey(network)) {
            throw new RuntimeException("Social network " + network + " not found");
        }
        return socialNetworksMap.get(network);
    }

    /**
     * Get list of initialized social networks
     * @return list of initialized social networks
     */
    public List<SocialNetwork> getInitializedSocialNetworks() {
        return Collections.unmodifiableList(new ArrayList<>(socialNetworksMap.values()));
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        for (SocialNetwork network : socialNetworksMap.values()) {
            network.onActivityResult(requestCode, resultCode, data);
        }
    }

    public AccessToken getToken() {
        return token;
    }

    public void setToken(AccessToken token) {
        this.token = token;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
}
