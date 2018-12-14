package apackage.com.yournight.view.helper;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

import apackage.com.yournight.R;
import apackage.com.yournight.model.cache.DiskLruImageCacheObject;
import apackage.com.yournight.model.event.Event;

import static apackage.com.yournight.model.network.EventNetwork.events;

public class CustomInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {

    private Activity context;

    public CustomInfoWindowAdapter(Activity context) {
        this.context = context;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        Event event = events[(Integer) marker.getTag()];
        View view = context.getLayoutInflater().inflate(R.layout.custom_info_contents, null);
        ImageView image = view.findViewById(R.id.icon_customWindow);
        image.setImageBitmap(DiskLruImageCacheObject.getObject().diskLruImageCache.getBitmap(event.getId()));
        TextView title = view.findViewById(R.id.title_customWindow);
        TextView attending = view.findViewById(R.id.attending_customWindow);
        TextView location = view.findViewById(R.id.location_customWindow);
        TextView address = view.findViewById(R.id.address_customWindow);
        TextView starting = view.findViewById(R.id.starting_customWindow);

        title.setText(event.getName());
        attending.setText(event.getAttending() + " Teilnehmer");
        location.setText(event.getPlaceName());
        if (event.getStreet() != "null") {
            address.setText(event.getStreet() + ", " + event.getZip() + " " + event.getCity());
        } else {
            address.setText(event.getZip() + " " + event.getCity());
        }
        starting.setText(event.getStartTime());

        return view;
    }
}
