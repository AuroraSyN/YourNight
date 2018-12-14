package apackage.com.yournight.view.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;

import apackage.com.yournight.R;


public class EventListAdapter extends BaseAdapter  {

    private ArrayList<EventListModel> itemModels;
    private LayoutInflater layoutInflater;
    private Context context;
    public EventListAdapter(Context context, ArrayList<EventListModel> itemModels){
        this.context = context;
        this.itemModels = itemModels;
        layoutInflater = LayoutInflater.from(context);
    }

    public void addItem(EventListModel itemModel){
        itemModels.add(itemModel);
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


            convertView = layoutInflater.inflate(R.layout.cardview_item, null);

            holder = new ViewHolder();


            holder.eventNameTxtView =  convertView.findViewById(R.id.cardEventName);
            holder.eventPlaceTxtView = convertView.findViewById(R.id.cardLocation);
            holder.eventDateTxtView = convertView.findViewById(R.id.cardDate);
            //holder.eventStartimeTxtView = convertView.findViewById(R.id.eventTextView);
            holder.eventMemberAmountTxtView = convertView.findViewById(R.id.cardMembers);
            holder.eventCoverPicture = convertView.findViewById(R.id.cardImg);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.eventNameTxtView.setText(itemModels.get(position).getEventName());
        holder.eventPlaceTxtView.setText(itemModels.get(position).getEventPlace());
        holder.eventDateTxtView.setText(itemModels.get(position).getEventDate());
        //holder.eventStartimeTxtView.setText(itemModels.get(position).getEventStartime());
        holder.eventMemberAmountTxtView.setText("" + itemModels.get(position).getEventMemberAmount());

       // File file = new File(itemModels.get(position).getEventPicturePath());
       // Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
        Bitmap icon = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.playatest);
        holder.eventCoverPicture.setImageBitmap(icon);

        return convertView;
    }

    
    private static class ViewHolder {

        TextView eventNameTxtView;
        TextView eventPlaceTxtView;
        TextView eventDateTxtView;
        //TextView eventStartimeTxtView;
        TextView eventMemberAmountTxtView;
        ImageView eventCoverPicture;

    }

    public void clearItems(){
        itemModels.clear();
    }
}

