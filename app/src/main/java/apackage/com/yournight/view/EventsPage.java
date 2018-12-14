package apackage.com.yournight.view;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;

import apackage.com.yournight.R;
import apackage.com.yournight.model.cache.DiskLruImageCacheObject;
import apackage.com.yournight.view.Adapter.RecyclerViewAdapter;
import apackage.com.yournight.view.helper.RecyclerItemClickListener;


import static apackage.com.yournight.model.network.EventNetwork.events;

public class EventsPage extends AppCompatActivity {
    private RecyclerViewAdapter adapter;
    private final static int MAXEVENTS = 20;
    ArrayList<Bitmap> bitmaps = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events_page);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar1);
        myToolbar.setTitle("Events");
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        Bitmap icon = BitmapFactory.decodeResource(getResources(),
                R.drawable.playatest);

        ArrayList<String> titles = new ArrayList<>();
        ArrayList<String> descriptions = new ArrayList<>();
        ArrayList<String> coverPictureURL = new ArrayList<>();



        if(events != null) {
            if(events.length >= MAXEVENTS){
                for (int i = 0; i <= MAXEVENTS; i++) {

                    Log.e("Fragment" ," Name " + events[i].getName());

                    titles.add(events[i].getName());
                    descriptions.add(events[i].getStartTime());
                    bitmaps.add(DiskLruImageCacheObject.getObject().diskLruImageCache.getBitmap(events[i].getId()));
                }
            }
            else{
                for (int i = 0; i <= events.length -1; i++) {

                    Log.e("Fragment" ," Name " + events[i].getName());

                    titles.add(events[i].getName());
                    descriptions.add(events[i].getStartTime());
                    bitmaps.add(DiskLruImageCacheObject.getObject().diskLruImageCache.getBitmap(events[i].getId()));
                }
            }
        }else{
            for (int i = 0; i <= MAXEVENTS; i++) {
                bitmaps.add(icon);
                titles.add("Network error");
                descriptions.add("Network error");
            }
        }

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        RecyclerView recyclerView1 = findViewById(R.id.recyclerView2);
        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getApplicationContext(), recyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        Intent intent = new Intent(getApplicationContext(), EventDetailPage.class);

                        intent.putExtra("id",position);
                        startActivity(intent);

                    }

                    @Override public void onLongItemClick(View view, int position) {
                        /*  Not doing anything to improve usability and prevent false clicks
                        Intent intent = new Intent(getApplicationContext(), EventDetailPage.class);

                        intent.putExtra("id",position);
                        startActivity(intent);*/
                    }
                })
        );
        recyclerView1.addOnItemTouchListener(
                new RecyclerItemClickListener(getApplicationContext(), recyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        Intent intent = new Intent(getApplicationContext(), EventDetailPage.class);

                        intent.putExtra("id",position);
                        startActivity(intent);

                    }

                    @Override public void onLongItemClick(View view, int position) {
                        /*  Not doing anything to improve usability and prevent false clicks
                        Intent intent = new Intent(getApplicationContext(), EventDetailPage.class);

                        intent.putExtra("id",position);
                        startActivity(intent);*/
                    }
                })
        );


        LinearLayoutManager linearLayoutManager
                = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager linearLayoutManager1
                = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView1.setLayoutManager(linearLayoutManager1);
        adapter = new RecyclerViewAdapter(getApplicationContext(), bitmaps, titles, descriptions);
        recyclerView.setAdapter(adapter);

        recyclerView1.setAdapter(adapter);
    }
}
