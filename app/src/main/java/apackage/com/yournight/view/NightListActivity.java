package apackage.com.yournight.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;


import java.util.ArrayList;

import apackage.com.yournight.R;
import apackage.com.yournight.view.Adapter.EventListAdapter;
import apackage.com.yournight.view.Adapter.EventListModel;
import static apackage.com.yournight.model.network.EventNetwork.events;

public class NightListActivity extends AppCompatActivity {
    private ListView mlist;
    private EventListAdapter eventListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_night_list);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        myToolbar.setTitle("Deine Events");
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initAdapter();
        addItems();


    }

    private void initAdapter(){

        mlist = (ListView) findViewById(R.id.listView);

        ArrayList<EventListModel> items = new ArrayList<>();
        eventListAdapter = new EventListAdapter(getBaseContext(), items);
        mlist.setAdapter(eventListAdapter);

    }

    private void addItems(){

       // EventListModel item = new EventListModel();

        for(int i = 0; i < events.length; i++) {
            EventListModel item = new EventListModel();
           item.setEventName(events[i].getName());
           item.setEventPlace(events[i].getCity());
           item.setEventMemberAmount(Integer.parseInt(events[i].getAttending()));
           item.setEventDate(events[i].getStartTime());

           eventListAdapter.addItem(item);
           eventListAdapter.notifyDataSetChanged();
        }


    }
}
