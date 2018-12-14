package apackage.com.yournight.view.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v7.widget.Toolbar;
import android.widget.LinearLayout;

import java.util.ArrayList;

import apackage.com.yournight.R;
import apackage.com.yournight.model.cache.DiskLruImageCacheObject;
import apackage.com.yournight.view.Adapter.RecyclerViewAdapter;
import apackage.com.yournight.view.EventDetailPage;
import apackage.com.yournight.view.EventSearch.FragmentSearch;
import apackage.com.yournight.view.EventsPage;
import apackage.com.yournight.view.NightListActivity;
import apackage.com.yournight.view.helper.RecyclerItemClickListener;


import static apackage.com.yournight.model.network.EventNetwork.events;

public class FragmentHome extends Fragment implements RecyclerViewAdapter.ItemClickListener {

    private Menu menu;

    public static FragmentHome newInstance() {
        return new FragmentHome();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private RecyclerViewAdapter adapter;

    private final static int MAXEVENTS = 20;

    public void setRecyclerView(View view){

        Bitmap icon = BitmapFactory.decodeResource(getResources(),
                R.drawable.playatest);
        Bitmap kfcicon = BitmapFactory.decodeResource(getResources(),
                R.drawable.kfc);

        ArrayList<String> titles = new ArrayList<>();
        ArrayList<String> descriptions = new ArrayList<>();

        ArrayList<Bitmap> bitmaps = new ArrayList<>();

        if(events != null) {
            if(events.length >= MAXEVENTS){
                /*
                for (int i = 0; i <= MAXEVENTS; i++) {
                    bitmaps.add(icon);
                    titles.add("Network error");
                    descriptions.add("Network error");
                } */


                for (int i = 0; i <= MAXEVENTS; i++) {

                    //Log.e("Fragment" ," Name " + events[i].getName());

                    titles.add(events[i].getName());
                    descriptions.add(events[i].getStartTime());
                    bitmaps.add(icon);
                }
                bitmaps.set(2, kfcicon);

            }
            else{
                for (int i = 0; i <= events.length -1; i++) {

                    //Log.e("Fragment" ," Name " + events[i].getName());

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

        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager
                = new LinearLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new RecyclerViewAdapter(view.getContext(), bitmaps, titles, descriptions);
        adapter.setClickListener(this);
        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getActivity(), recyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        Intent intent = new Intent(getActivity(), EventDetailPage.class);

                        intent.putExtra("id",position);
                        startActivity(intent);

                    }

                    @Override public void onLongItemClick(View view, int position) {
                        /*  Not doing anything to improve usability and prevent false clicks
                        Intent intent = new Intent(getActivity(), EventDetailPage.class);

                        intent.putExtra("id",position);
                        startActivity(intent);*/
                    }
                })
        );
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(View view, int position) {
        //Log.e("FragmentHome"," Clicked " + adapter.getItem(position) + " on item position " + position, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //int id = item.getItemId();

/*        if (id == R.id.action_info) {


            return true;
        }*/
        Fragment selectedFragment = null;

        switch (item.getItemId()) {
            case R.id.action_info:
                // Do onlick on menu action here
                selectedFragment = FragmentSearch.newInstance();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.add(selectedFragment, "Suche");
                transaction.addToBackStack("myscreen");
                transaction.replace(R.id.frame_layout, selectedFragment);
                transaction.commit();
                return true;
        }
        return false;

       // return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_fragmenthome, menu);

        this.menu = menu;
        showOption(R.id.action_info);

    }

    private void hideOption(int id) {
        MenuItem item = menu.findItem(id);
        item.setVisible(true);
    }

    private void showOption(int id) {
        MenuItem item = menu.findItem(id);
        item.setVisible(true);
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        setHasOptionsMenu(true);
        final View view = inflater.inflate(R.layout.fragmenthome, container, false);

        final Toolbar mToolbar = view.findViewById(R.id.toolbar);
        mToolbar.setTitle("");
        LinearLayout eventPage = (LinearLayout) view.findViewById(R.id.event1Linear);
        LinearLayout nightlist1 = (LinearLayout) view.findViewById(R.id.nightlist1Linear);
        LinearLayout nightlist2 = (LinearLayout) view.findViewById(R.id.nightlist2linear);

        eventPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent event_intent = new Intent(getActivity(), EventsPage.class);
                startActivity(event_intent );
            }
        });

        nightlist1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view = inflater.inflate(R.layout.activity_night_list, container, false);
                Intent picture_intent = new Intent(getActivity(), NightListActivity.class);
                startActivity(picture_intent);
            }
        });

        nightlist2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view = inflater.inflate(R.layout.activity_night_list, container, false);
                Intent picture_intent = new Intent(getActivity(), NightListActivity.class);
                startActivity(picture_intent);
            }
        });

        ((AppCompatActivity)getActivity()).setSupportActionBar(mToolbar);

        AppBarLayout mAppBarLayout =  view.findViewById(R.id.app_bar);
        mAppBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();

                }
                if (scrollRange + verticalOffset == 0) {
                    isShow = true;

                    showOption(R.id.action_info);

                } else if (isShow) {
                    isShow = false;

                    hideOption(R.id.action_info);
                }
            }
        });

        setRecyclerView(view);

        return view;
    }
}

