package apackage.com.yournight.view;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import apackage.com.yournight.R;
import apackage.com.yournight.model.cache.DiskLruImageCacheObject;
import apackage.com.yournight.model.event.Event;

import static apackage.com.yournight.model.network.EventNetwork.events;

public class EventDetailPage extends AppCompatActivity {

Event event=new Event();

    // fixed app bar back button not working when no parent activity in manifest declared
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        event= events[(getIntent().getExtras().getInt("id",0))];

        toolbar.setTitle(event.getName());
        ImageView eventImage= findViewById(R.id.eventWallpaper);
        eventImage.setImageBitmap(DiskLruImageCacheObject.getObject().diskLruImageCache.getBitmap(event.getId()));

        String[] month1 = event.getStartTime().split("\\.");

        Log.e("Fragment" ," Monat " +event.getStartTime());
       // Log.e("Fragment" ," Monat " +month1[0]);
       // Log.e("Fragment" ," Monat " +month1[1]);



        TextView month= findViewById(R.id.month);
        TextView date= findViewById(R.id.date);
        date.setText(month1[0]);
        date.setText("disabled");
        month.setText("disabled | Januar" );

        /*
        switch (month1[1]){
            case("01"): month.setText("JAN");  break;
            case("02"): month.setText("FEB");  break;
            case("03"): month.setText("MÄR");  break;
            case("04"): month.setText("APR");  break;
            case("05"): month.setText("MAI");  break;
            case("06"): month.setText("JUN");  break;
            case("07"): month.setText("JUL");  break;
            case("08"): month.setText("AUG");  break;
            case("09"): month.setText("SEP");  break;
            case("10"): month.setText("OKT");  break;
            case("11"): month.setText("NOV");  break;
            case("12"): month.setText("DEZ");  break;
        } */


        TextView name= findViewById(R.id.eventName);
        name.setText(event.getName());

        TextView time1=findViewById(R.id.time1);
        time1.setText(event.getStartTime());
        TextView time2=findViewById(R.id.time2);
        time2.setText(event.getEndTime());

        TextView placename=findViewById(R.id.placeName);
        placename.setText(event.getPlaceName());
        TextView location=findViewById(R.id.location);
        if (event.getStreet() != "null") {
            location.setText(event.getStreet() + ", " + event.getZip() + " " + event.getCity());
        }
        else{
            location.setText(event.getZip() + " " + event.getCity());
        }
        TextView attending=findViewById(R.id.attending);
        attending.setText(event.getAttending());
        TextView maybe=findViewById(R.id.maybe);
        maybe.setText(event.getMaybe());


        TextView description= findViewById(R.id.textViewDescription);
        description.setText(event.getDescription());


        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


    }


}
