package apackage.com.yournight.view.EventSearch;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import apackage.com.yournight.R;
import apackage.com.yournight.model.event.Event;

public class EventResultsActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_results);

        ArrayList<Event> events;
        try {
            events = (ArrayList<Event>) getIntent().getSerializableExtra("events");
        } catch (Exception e){
            Log.d("SearchResults", "Failed getting events list: " + e);
            Toast.makeText(getApplicationContext(), "Fehler beim Anzeigen der Suchergebnisse!", Toast.LENGTH_SHORT);
            return;
        }

        TextView txt_nr_results = findViewById(R.id.txt_number_results);
        txt_nr_results.setText(events.size() + " Suchergebnisse");

        mRecyclerView = findViewById(R.id.list_results);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);
        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        // specify an adapter (see also next example)
        mAdapter = new EventResultsListAdapter(events);
        mRecyclerView.setAdapter(mAdapter);
    }
}
