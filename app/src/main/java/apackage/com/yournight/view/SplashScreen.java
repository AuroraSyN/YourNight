package apackage.com.yournight.view;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import apackage.com.yournight.R;
import apackage.com.yournight.model.Location.GoogleFusedLocationProvider;
import apackage.com.yournight.model.network.EventNetwork;

public class SplashScreen extends AppCompatActivity implements Animation.AnimationListener{

    /**
     * Duration of wait
     **/
    private final int SPLASH_DISPLAY_LENGTH = 8000;
    private RequestQueue requestQueue;
    private EventNetwork server;
    private TextView tv;
    private ImageView iv;
    private Animation transition, upsideDown, downsideUp;
    private ProgressBar circle;
    private static final int NUMBER_OF_ANIMATIONS = 2;
    private int animationFinishedCount = 0;

    /**
     * Called when the activity is first created.
     */

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.activity_splash_screen);

        GoogleFusedLocationProvider googleFusedLocationProvider = new GoogleFusedLocationProvider(this);

        Log.e("SplashScreen", "Splash screen started");

        requestQueue = Volley.newRequestQueue(this);
        new EventNetwork(this, requestQueue, googleFusedLocationProvider);

        startAnimation();



        
        /* New Handler to start the Menu-Activity
         * and close this Splash-Screen after some seconds.*/
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
//                Intent mainIntent = new Intent(SplashScreen.this,MainActivity.class);
//                SplashScreen.this.startActivity(mainIntent);
//                SplashScreen.this.finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }

    public void startAnimation() {
        tv = (TextView) findViewById(R.id.splashtext);
        iv = (ImageView) findViewById(R.id.splashscreen);
        circle = (ProgressBar) findViewById(R.id.progressBarCircle);
        //Loading the animation files and set the AnimationListener
        //transition = AnimationUtils.loadAnimation(this,R.anim.transition);
        upsideDown = AnimationUtils.loadAnimation(this, R.anim.upsidedown);
        downsideUp = AnimationUtils.loadAnimation(this, R.anim.downtoup);
        upsideDown.setAnimationListener(this);
        downsideUp.setAnimationListener(this);
        tv.startAnimation(downsideUp);
        iv.startAnimation(upsideDown);
    }

    @Override
    public void onAnimationStart(Animation animation) {
    }

    @Override
    public void onAnimationEnd(Animation animation) {
        //If the two animation has ended then the progessbar should be visible
        if (++animationFinishedCount == NUMBER_OF_ANIMATIONS) {
            circle.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onAnimationRepeat(Animation animation) {
    }

}
