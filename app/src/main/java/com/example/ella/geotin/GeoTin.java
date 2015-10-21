package com.example.ella.geotin;

import android.location.Address;
import android.location.Geocoder;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class GeoTin extends FragmentActivity {

    private GoogleMap map; // Might be null if Google Play services APK is not available.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_geo_tin);
        setUpMapIfNeeded();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    public void onSearch(View view) throws IOException {
        EditText location_tf = (EditText)findViewById(R.id.TFaddress);
        String location = location_tf.getText().toString();
        List<Address> addressList = null;

        if (location != null || !location.equals(" ")) {
            Geocoder geocoder = new Geocoder(this);
            try {
                addressList = geocoder.getFromLocationName(location, 1);

            } catch (IOException e) {
                e.printStackTrace();
            }

            Address address = addressList.get(0);
            LatLng latLng = new LatLng(address.getLatitude() , address.getLongitude());
            map.addMarker(new MarkerOptions().position(latLng).title("Marker"));
            map.animateCamera(CameraUpdateFactory.newLatLng(latLng));

        }
    }

    public void onZoom(View view) {
        if(view.getId() == R.id.Bzoomin){
            map.animateCamera(CameraUpdateFactory.zoomIn());
        }
        if(view.getId() == R.id.Bzoomout){
            map.animateCamera(CameraUpdateFactory.zoomOut());
        }
    }

    // set up map type
    public void changeType(View view) {
        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
/*        if(mMap.getMapType() == GoogleMap.MAP_TYPE_NORMAL) {
            mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        }
        else
            mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);*/
    }

    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (map == null) {
            // Try to obtain the map from the SupportMapFragment.
            map = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (map != null) {
                setUpMap();
            }
        }
    }


    private void setUpMap() {
        map.setMyLocationEnabled(true);

        //map.addMarker(new MarkerOptions().position().title("I am here"));
        //map.moveCamera(CameraUpdateFactory.newLatLng(latLng));

    }
}
