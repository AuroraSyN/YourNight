package apackage.com.yournight.view.EventSearch;


import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import apackage.com.yournight.R;
import apackage.com.yournight.model.event.Event;
import static apackage.com.yournight.model.network.EventNetwork.events;


public class FragmentSearch extends Fragment implements View.OnClickListener {

    final static String TAG = FragmentSearch.class.getCanonicalName();

    int
            year_x,
            month_x,
            day_x,
            hour_x,
            min_x;

    private Calendar
            currentCalender;

    private SearchHistoryListAdapter
            searchAdapter;

    ArrayList<Event>
            mEvents;

    EditText
            editTextStartDate,
            editTextEndDate,
            editTextStartTime,
            editTextEndTime;

    public static FragmentSearch newInstance() {
        FragmentSearch fragment = new FragmentSearch();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // inflate the layout and return the view
        final View view = inflater.inflate(R.layout.fragmentsearch, container, false);

        // the actionbar of the fragment
        Toolbar myToolbar = view.findViewById(R.id.toolbarSearch);
        myToolbar.setTitle("Suche");

        // setup all time/date editTexts with clicklistener
        editTextStartDate = view.findViewById(R.id.editStartDate);
        editTextStartDate.setOnClickListener(this);
        editTextEndDate = view.findViewById(R.id.editEndDate);
        editTextEndDate.setOnClickListener(this);
        editTextStartTime = view.findViewById(R.id.editStartTime);
        editTextStartTime.setOnClickListener(this);
        editTextEndTime = view.findViewById(R.id.editEndTime);
        editTextEndTime.setOnClickListener(this);

        // get events
        getEvents();
        // the search button and history
        setupSearch(view);
        // the calendar to choose time/data
        initCalendar();
        // the seekbar to search for events in distance
        setupSeekbar(view);
        // the checkbox that toggles the advanced search box
        setupCheckbox(view);

        return view;
    }

    // get fake events (as test whilst facebook-api is down)
    private void getEvents(){
        // get events-array from global EventNetwork
        Event[] badApi = events;
        mEvents = new ArrayList<Event>();
        // copy events to list
        for (int i = 0; i < 29 ; i++) {
            mEvents.add(badApi[i]);
            Log.i(TAG, badApi[i].getEventGeneralInfo());
        }
        Toast.makeText(getContext(), "Events downloaded", Toast.LENGTH_SHORT).show();
    }

    // the search button and history
    private void setupSearch(View v){
        // TODO: 11.04.2018 reverse list so that it shows the last search first
        // TODO: 11.04.2018 fix list height
        // TODO: 11.04.2018 list only shows start time for search
        // TODO: 11.04.2018 list events clickable to search that term again?
        ListView mList = v.findViewById(R.id.listViewSearch);

        final Button search = v.findViewById(R.id.btnSearch);

        final AutoCompleteTextView editTextParty = v.findViewById(R.id.editTextParty);
        setupAutoCompleteView(editTextParty, setupAutocompleteEventNamesData());

        final AutoCompleteTextView editTextTown = v.findViewById(R.id.editTextTown);
        setupAutoCompleteView(editTextTown, setupAutocompleteTownData());

        final SearchHelper searchHelper = new SearchHelper();

        ArrayList<SearchHistoryListModel> items = new ArrayList<>();
        searchAdapter = new SearchHistoryListAdapter(getActivity().getBaseContext(),items);
        mList.setAdapter(searchAdapter);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SearchHistoryListModel item = new SearchHistoryListModel();
                if (editTextParty.getText() != null) {
                    item.setParty(editTextParty.getText().toString());
                }
                if (editTextTown.getText() != null) {
                    item.setTown(editTextTown.getText().toString());
                }
                if (editTextStartDate.getText() != null) {
                    item.setDate(editTextStartDate.getText().toString());
                }
                if (editTextStartTime.getText() != null) {
                    item.setTime(editTextStartTime.getText().toString());
                }
                searchAdapter.addItem(item);
                searchAdapter.notifyDataSetChanged();

                // TODO: 18.04.2018 make sure that all required fields are filled
                // search the events based on user input search terms
                displaySearchResults(searchHelper.search(item, mEvents));
            }
        });
    }

    // TODO: 26.04.2018 error inflating list with only one element
    private void setupAutoCompleteView(AutoCompleteTextView v, String[] data){
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), R.layout.autocomplete_list_item, data);
        v.setDropDownBackgroundResource(R.drawable.autocomplete_style);
        v.setThreshold(1);
        v.setAdapter(adapter);
    }

    // TODO: 25.04.2018 improve this to only be called once during app startup?
    // TODO: 25.04.2018 it doesnt show all names from the text file
    // NOTE: "SPAN_EXCLUSIVE_EXCLUSIVE spans cannot have a zero length" error might be from android keyboard, not code related
    // current data from datendieter/osm, alternative data - http://www.nxplorer.net/files/liste-deutscher-stadte.txt
    // from https://stackoverflow.com/questions/18583986/show-autocompletetextview-using-txt-file-data-in-android#18584027
    private String[] setupAutocompleteTownData(){

        String[] arr= null;
        List<String> items= new ArrayList<String>();

        try {
            BufferedReader reader = null;
            reader = new BufferedReader(
                    new InputStreamReader(getActivity().getAssets().open("staedte_osm.txt"), "UTF-8"));

            String str_line;

            while ((str_line = reader.readLine()) != null){
                str_line = str_line.trim();
                if ((str_line.length()!=0)){
                    items.add(str_line);
                }
            }

            arr = (String[])items.toArray(new String[items.size()]);
        } catch (Exception e){
            Log.e(TAG, "Failed reading staedte.txt: " + e);
            return arr = new String[] {
                    "Worms", "Mannheim", "Berlin"
            };
        }
        return arr;
    }

    private String[] setupAutocompleteEventNamesData(){
        return new String[] {
                "TestEvent",
                "PSE",
                "Daily Scrum",
                "Champions League",
                "KFC Fanclub"
        };
    }

    // display all returned events in the app
    private void displaySearchResults(ArrayList<Event> events){
        Intent intent = new Intent(getActivity(), EventResultsActivity.class);
        intent.putExtra("events", events);
        startActivity(intent);
    }

    // the calendar to choose time/date
    private void initCalendar(){
        currentCalender = Calendar.getInstance();
        day_x = currentCalender.get(Calendar.DAY_OF_MONTH);
        month_x = currentCalender.get(Calendar.MONTH);
        year_x = currentCalender.get(Calendar.YEAR);
        month_x = month_x + 1;      // todo: warum?!
        hour_x = currentCalender.get(Calendar.HOUR_OF_DAY);
        min_x = currentCalender.get(Calendar.MINUTE);
    }

    // the checkbox that toggles the advanced search box
    private void setupCheckbox(View v){
        CheckBox checkBox = v.findViewById(R.id.checkBox);
        checkBox.setClickable(true);
        final LinearLayout linearBox = v.findViewById(R.id.linearBox);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (compoundButton.isChecked()) {
                    linearBox.setVisibility(View.VISIBLE);
                } else  {
                    linearBox.setVisibility(View.GONE);
                }
            }
        });
    }

    // the seekbar to search for events in distance
    private void setupSeekbar(View v){

        final TextView distanceValue = v.findViewById(R.id.textKM);

        //Seekbar settings
        SeekBar seekBar = v.findViewById(R.id.seekBar);
        seekBar.setMax(5);
        seekBar.setProgress(1);
        distanceValue.setText("" + 1);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progressChanged = 0;
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                progressChanged = i;
                distanceValue.setText("" + progressChanged);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    // create a time picker dialog
    private void pickTime(final TextView txt_time) {
        new TimePickerDialog(getActivity(), R.style.DialogTheme,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int i, int i1) {
                        txt_time.setText("" + i + ":" + i1 + " Uhr");
                    }
                }, hour_x, min_x, true).show();
    }

    // create a date picker dialog
    private void pickDate(final TextView txt_date){
        new DatePickerDialog(getActivity(), R.style.DialogTheme,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        month = month +1;
                        txt_date.setText("" + day + "/" + month + "/" + year);
                    }
                }, year_x, month_x, day_x).show();
    }

    // anonymous onClick to simplify date/time selection
    // TODO: 11.04.2018 improve usability and handling of date/time selection(similar to time/date picker in DB-APP?)
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.editStartDate:
                pickDate(editTextStartDate);
                break;
            case R.id.editEndDate:
                pickDate(editTextEndDate);
                break;
            case R.id.editStartTime:
                pickTime(editTextStartTime);
                break;
            case R.id.editEndTime:
                pickTime(editTextEndTime);
                break;
        }
    }
}
