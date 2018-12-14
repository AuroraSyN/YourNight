package apackage.com.yournight.view.EventSearch;

import android.util.Log;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

import apackage.com.yournight.model.event.Event;

class SearchHelper {

    private static final String TAG = "SEARCH";

    /*
        3 cases:
        event (+ date)              | "Kneipentour"
        event + location (+ date)   | "Worms" + "Kneipentour"
        location (+ date)           | "6.7." + "Worms"
     */

    ArrayList<Event> matches;

    // TODO: 19.04.2018 also return suggestions?
    ArrayList<Event> search(SearchHistoryListModel searchTerm, ArrayList<Event> events){

        matches = new ArrayList<>();

        String searchEvent = searchTerm.getParty();
        String searchDate = searchTerm.getDate();
        String searchLocation = searchTerm.getTown();

        Log.i(TAG, "Finding match for " + searchEvent + " + " + searchDate + " + " + searchLocation);

        // TODO: 19.04.2018 fuzzy search -> levensthein distance
        // TODO: 25.04.2018 all to lower case
        // user searches for a specific event
        if (searchEvent != null){
            for (Event event : events){
                // user searched for a specific event + date or location
                if (searchLocation != null || searchDate != null){
                    // find matches for specifice event + location
                    if (searchDate == null){
                        if (event.getName().contains(searchEvent) && event.getCity().contains(searchLocation))
                            addMatch(event);
                    } else if (searchLocation == null){
                        // find matches for specifice event + date
                        if (event.getName().contains(searchEvent) && event.getStartTime().contains(searchDate))
                            addMatch(event);
                    } else {
                        // find matches for specific event + date + location
                        if (event.getName().contains(searchEvent) && event.getStartTime().contains(searchDate) && event.getCity().contains(searchLocation))
                            addMatch(event);
                    }
                } else {
                    // find matches for specifice event
                    if (event.getName().contains(searchEvent))
                        addMatch(event);
                }
            }
        } else {
            // user wants event suggestions
            // if user didnt select a date he wants suggestions for todays date
            if (searchDate == null)
                searchDate = DateFormat.getDateTimeInstance().format(new Date());

            for (Event event : events){
                if (event.getStartTime().contains(searchDate) && event.getCity().contains(searchLocation))
                    addMatch(event);
            }
        }

        // return found and parsed events back to searchFragment where they will be displayed
        return matches;
    }

    private void addMatch(Event e){
        Log.i(TAG,"Found match:\n"+e);
        matches.add(e);
    }
}
