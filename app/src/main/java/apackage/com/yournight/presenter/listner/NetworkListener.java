package apackage.com.yournight.presenter.listner;

/**
 * Created by Aleksandr Soloninov on 17.11.2017.
 * Hochschule Worms, inf3032
 */

import apackage.com.yournight.model.network.SocialNetwork;

interface NetworkListener {
    void onError(SocialNetwork.Network socialNetwork, String errorMessage);
}
