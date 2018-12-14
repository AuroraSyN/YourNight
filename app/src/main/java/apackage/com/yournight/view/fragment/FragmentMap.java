package apackage.com.yournight.view.fragment;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import apackage.com.yournight.R;
import apackage.com.yournight.model.cache.DiskLruImageCacheObject;
import apackage.com.yournight.view.EventDetailPage;
import apackage.com.yournight.view.helper.CustomInfoWindowAdapter;

import static apackage.com.yournight.model.network.EventNetwork.events;

public class FragmentMap extends Fragment implements OnMapReadyCallback, GoogleMap.OnInfoWindowClickListener {

    MapView mapView;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragmentmap, container, false);

        mapView = (MapView) v.findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);


        return v;
    }

    public static FragmentMap newInstance() {
        FragmentMap fragment = new FragmentMap();
        return fragment;
    }

    @Override
    public void onResume() {
        mapView.onResume();
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mapView != null) {
            mapView.onSaveInstanceState(outState);
        }
    }


    @Override
    public void onMapReady(GoogleMap map) {

        boolean success = map.setMapStyle(new MapStyleOptions(getResources()
                .getString(R.string.style_json)));

        if (!success) {

            Log.e("Map", "Style parsing failed.");
        }

        if (ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        } else {
            if (!map.isMyLocationEnabled())
                map.setMyLocationEnabled(true);

            LocationManager lm = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
            Location myLocation = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);

            if (myLocation == null) {
                Criteria criteria = new Criteria();
                criteria.setAccuracy(Criteria.ACCURACY_COARSE);
                String provider = lm.getBestProvider(criteria, true);
                myLocation = lm.getLastKnownLocation(provider);
            }

            if (myLocation != null) {
                LatLng userLocation = new LatLng(myLocation.getLatitude(), myLocation.getLongitude());
                MapsInitializer.initialize(this.getActivity());
                map.animateCamera(CameraUpdateFactory.newLatLngZoom(userLocation, 14), 1500, null);

            }
        }
        for (int i = 0; i <= events.length - 1; i++) {

            Log.e("Fragment", " Name " + events[i].getName());

            double latitude = Double.parseDouble(events[i].getLatitude());
            double longitude = Double.parseDouble(events[i].getLongitude());
            LatLng location = new LatLng(latitude, longitude);
            Marker marker = map.addMarker(new MarkerOptions()
                    .title(events[i].getName())
                    .snippet(events[i].getAttending() + " Zusagen")
                    .position(location)
                    .icon(BitmapDescriptorFactory.defaultMarker(151)).alpha(0.9f));
            marker.setTag(i);
            map.setOnInfoWindowClickListener(this);
            map.setInfoWindowAdapter(new CustomInfoWindowAdapter(getActivity()));


        }
    }


    @Override
    public void onInfoWindowClick(Marker marker) {

        // Check if a click count was set, then display the click count.
        Intent intent = new Intent(getActivity(), EventDetailPage.class);

        intent.putExtra("id", (Integer) marker.getTag());
        startActivity(intent);
        // Return false to indicate that we have not consumed the event and that we wish
        // for the default behavior to occur (which is for the camera to move such that the
        // marker is centered and for the marker's info window to open, if it has one).

    }


}
