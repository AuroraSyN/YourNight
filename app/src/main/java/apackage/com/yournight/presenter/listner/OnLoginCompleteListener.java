package apackage.com.yournight.presenter.listner;

/**
 * Created by Aleksandr Soloninov on 17.11.2017.
 * Hochschule Worms, inf3032
 */

import apackage.com.yournight.model.network.SocialNetwork;

public interface OnLoginCompleteListener extends NetworkListener {
    /**
     * Called when login complete.
     * @param network id of social network where request was complete
     */
    void onLoginSuccess(SocialNetwork.Network network);
}
