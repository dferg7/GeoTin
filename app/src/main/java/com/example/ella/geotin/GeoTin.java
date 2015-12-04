package com.example.ella.geotin;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;


import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class GeoTin extends FragmentActivity {

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_geo_tin);
        setUpMapIfNeeded();
        onPopulate(21.200000,-157.810000, "test");
        onPopulate(21.300000,-157.710000, "testDos");

    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    public void onPost (View view) {
        EditText location_tf = (EditText) findViewById(R.id.TFaddress);
        String location = location_tf.getText().toString();

        // Get LocationManager object from system service LOCATION_SERVICE
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        // Get the current location
        Location mylocation = locationManager.getLastKnownLocation(locationManager.NETWORK_PROVIDER);

        // Get latitude of current location
        double latitude = mylocation.getLatitude();

        // Get longitude of current location
        double longitude = mylocation.getLongitude();

        // Create latlng object of current location
        LatLng latLng = new LatLng(latitude, longitude);

        // show current location in google map
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));

        // Zoom in google map
        mMap.animateCamera(CameraUpdateFactory.zoomIn());

        // Post marker only if user is within UH

        if(latitude > 21.291918 && latitude < 21.310791 && longitude > -157.821747 && longitude < -157.808540)  {
            // Add marker to current location
            Marker marker = mMap.addMarker(new MarkerOptions().position(new LatLng(latitude, longitude)).title(location));
            Log.d("Test", String.valueOf(new LatLng(latitude, longitude)));

        }
        else
            new AlertDialog.Builder(this).setTitle("Oh no!").setMessage("You're outside the bounds of UH Manoa :(").setNeutralButton("Okay", null).show();


    }

    public void onPopulate(double latitude, double longitude, String location) {
        Marker marker = mMap.addMarker(new MarkerOptions().position(new LatLng(latitude, longitude)).title(location));
    }



    public void onZoom(View view) {
        if(view.getId() == R.id.Bzoomin){
            mMap.animateCamera(CameraUpdateFactory.zoomIn());
        }
        if(view.getId() == R.id.Bzoomout){
            mMap.animateCamera(CameraUpdateFactory.zoomOut());
        }
    }

    // set up map type
    public void changeType(View view) {
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
    }

    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }


    private void setUpMap() {
        // Enable myLocation layer of google map
        mMap.setMyLocationEnabled(true);

        //mMap.addMarker(new MarkerOptions().position().title("I am here"));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));

    }

}
