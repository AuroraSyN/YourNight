package apackage.com.yournight.view.EventSearch;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import apackage.com.yournight.R;


public class SearchHistoryListAdapter extends BaseAdapter {
    private ArrayList<SearchHistoryListModel> itemModels;
    private LayoutInflater layoutInflater;
    private Context context;

    public SearchHistoryListAdapter(Context context, ArrayList<SearchHistoryListModel> itemModels){
        this.context = context;
        this.itemModels = itemModels;
        layoutInflater = LayoutInflater.from(context);
    }

    public void addItem(SearchHistoryListModel itemModel){
        itemModels.add(0, itemModel);
    }

    @Override
    public int getCount() {
        return itemModels.size();
    }

    @Override
    public Object getItem(int position) {
        return itemModels.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {


            convertView = layoutInflater.inflate(R.layout.listviewsearchhistory_item, null);

            holder = new ViewHolder();

            holder.partyTextView =  convertView.findViewById(R.id.historyTextViewParty);
            holder.townTextView = convertView.findViewById(R.id.historyTextViewTown);
            holder.timeTextView = convertView.findViewById(R.id.historyTextViewTime);
            holder.dateTextView = convertView.findViewById(R.id.historyTextViewDate);


            convertView.setTag(holder);
        } else {
            holder = (SearchHistoryListAdapter.ViewHolder) convertView.getTag();
        }

        holder.partyTextView.setText(itemModels.get(position).getParty());
        holder.townTextView.setText(itemModels.get(position).getTown());
        holder.timeTextView.setText(itemModels.get(position).getTime());
        holder.dateTextView.setText("" + itemModels.get(position).getDate());


        return convertView;
    }


    private static class ViewHolder {

        TextView partyTextView;
        TextView townTextView;
        TextView dateTextView;
        TextView timeTextView;

    }

    public void clearItems(){
        itemModels.clear();
    }
}