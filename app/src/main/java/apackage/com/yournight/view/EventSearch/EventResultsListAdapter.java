package apackage.com.yournight.view.EventSearch;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import apackage.com.yournight.R;
import apackage.com.yournight.model.event.Event;

public class EventResultsListAdapter extends RecyclerView.Adapter<EventResultsListAdapter.ViewHolder> {
    private ArrayList<Event> mDataset;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView name, location;

        public ViewHolder(View v) {
            super(v);

            name = v.findViewById(R.id.search_result_name);
            location = v.findViewById(R.id.search_result_location);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public EventResultsListAdapter(ArrayList<Event> myDataset) {
        mDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public EventResultsListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.event_search_result, parent, false);

        return new ViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        // TODO: 02.05.2018 try-catch?
        holder.name.setText(mDataset.get(position).getName());
        holder.location.setText(mDataset.get(position).getCity());

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
