package apackage.com.yournight.view;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

import apackage.com.yournight.R;
import apackage.com.yournight.model.access.AccessLogin;
import apackage.com.yournight.view.fragment.FragmentHome;
import apackage.com.yournight.view.fragment.FragmentMap;
import apackage.com.yournight.view.fragment.FragmentProfile;
import apackage.com.yournight.view.EventSearch.FragmentSearch;
import apackage.com.yournight.view.helper.BottomNavigationViewHelper;

public class MainActivity extends AppCompatActivity {

    final static String ID_KEY = "ID";
    final static String NAME_KEY = "NAME";
    final static String EMAIL_KEY = "EMAIL";

    private AccessLogin asses;
    //Save
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onPause() {
        super.onPause();
        editor = sharedPreferences.edit();
        editor.putString(ID_KEY, asses.getUserID());
        editor.putString(NAME_KEY, asses.getUserName());
        editor.putString(EMAIL_KEY, asses.getUserEmail());
        editor.commit();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AccessLogin.initialize();
        asses = AccessLogin.getInstance();
        //  init_layout();
        init();
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setOnNavigationItemSelectedListener
                (new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        Fragment selectedFragment = null;
                        switch (item.getItemId()) {
                            case R.id.action_item1:
                                selectedFragment = FragmentHome.newInstance();
                                break;
                            case R.id.action_item2:
                                selectedFragment = FragmentMap.newInstance();
                                break;
                            case R.id.action_item3:
                                selectedFragment = FragmentSearch.newInstance();
                                break;
                            case R.id.action_item4:
                                selectedFragment = FragmentProfile.newInstance();
                                break;
                        }

                        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.frame_layout, selectedFragment);
                        transaction.commit();
                        return true;
                    }
                });

        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout, FragmentHome.newInstance());
        transaction.commit();
        //.Remove
        //showUserDataInLog();
    }

    private void loadDataBase(){
        asses.setUserID(sharedPreferences.getString(ID_KEY, "Network error, try reconnect"));
        asses.setUserEmail(sharedPreferences.getString(NAME_KEY, "Network error, try reconnect"));
        asses.setUserEmail(sharedPreferences.getString(EMAIL_KEY, "Network error, try reconnect"));
    }

    private void init(){
        sharedPreferences = getPreferences(MODE_PRIVATE);

        if (asses.getToken() != null) {
            asses.setUserEmail(asses.getToken().getEmail());
            asses.setUserName(asses.getToken().getUserName());
            asses.setUserID(asses.getToken().getUserId());
            //        userPictureView.setProfileId(userID);
        } else {
            asses.setUserEmail("email@offline.login");
            asses.setUserName("Offline Login");
            asses.setUserID("0ffline");
        }
        if (asses.getUserEmail() == null || asses.getUserName() == null) {
            loadDataBase();
        }
    }

    private void showUserDataInLog() {
        Log.e("Main:", asses.getUserEmail());
        Log.e("Main:", asses.getUserName());
        Log.e("Main:", asses.getUserID());
//        Log.e("MainToken:", "" + asses.getToken().getToken());
    }


}
