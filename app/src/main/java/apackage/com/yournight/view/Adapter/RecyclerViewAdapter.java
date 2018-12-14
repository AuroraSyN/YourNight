package apackage.com.yournight.view.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;
import apackage.com.yournight.R;


public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private List<Bitmap> bitmaps = Collections.emptyList();
    private List<String> titles = Collections.emptyList();
    private List<String> descriptions = Collections.emptyList();

    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    

    public RecyclerViewAdapter(Context context, List<Bitmap> bitmaps, List<String> titles, List<String> descriptions) {
        this.mInflater = LayoutInflater.from(context);
        this.bitmaps = bitmaps;
        this.titles = titles;
        this.descriptions = descriptions;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recyclerview_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.eventWallpaper.setImageBitmap(bitmaps.get(position));
        holder.clubTextView.setText(titles.get(position));
        holder.eventTextView.setText(descriptions.get(position));
        
    }

    @Override
    public int getItemCount() {
        return titles.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView clubTextView;
        public TextView eventTextView;
        public ImageView eventWallpaper;

        public ViewHolder(View itemView) {
            super(itemView);
            eventWallpaper  = itemView.findViewById(R.id.eventWallpaper);
            clubTextView = itemView.findViewById(R.id.clubTextView);
            eventTextView = itemView.findViewById(R.id.eventTextView);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    public Bitmap getItem(int id) {
        return bitmaps.get(id);
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

}




