package com.example.wasif.seaiceapp;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;
import java.util.Locale;

public class MapsActivity extends  FragmentActivity implements GoogleMap.OnMapClickListener, GoogleMap.OnMapLongClickListener,LocationListener,GoogleApiClient.ConnectionCallbacks,GoogleApiClient.OnConnectionFailedListener{

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);


        setUpMapIfNeeded();

    }




    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap(double,double)} once when {@link #mMap} is not null.
     * <p/>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p/>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                try {
                    mMap.setMyLocationEnabled(true);
                }catch(SecurityException e){

                }

                // mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                // myMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                // myMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                // myMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);

                mMap.setOnMapClickListener(this);
                mMap.setOnMapLongClickListener(this);

                //setUpMap();
                //map is built, so we are seraching for the current location
                buildGoogleApiClient();
                //String address = new String("BUET New Academic Building Road,Azimpur,Lalbagh,Dhaka");
                //String address = new String("humhum water fall,Sylhet");
                //markMapOnAGivenAddress(address);

               // markADistrict("cox's bazar");
            }
        }
    }

    private void markADistrict(String placeName) {
        LatLng location = null;
        if(location!=null){

            CameraUpdate yourLocation = CameraUpdateFactory.newLatLngZoom(location, 10);
            mMap.addMarker(new MarkerOptions().position(location).title("Marker"));
            mMap.animateCamera(yourLocation);
        }
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */
    private void setUpMap(double lat, double lon) {
        //String address = new String("BUET New Academic Building Road,Azimpur,Lalbagh,Dhaka");
        //markMapOnAGivenAddress(address);

        //LatLng coordinate = getLocationFromAddress(this,address);
         LatLng coordinate = new LatLng(lat, lon);
        CameraUpdate yourLocation = CameraUpdateFactory.newLatLngZoom(coordinate, 15);
        mMap.addMarker(new MarkerOptions().position(new LatLng(lat, lon)).title("Marker"));
        mMap.animateCamera(yourLocation);

        /*Polyline line = mMap.addPolyline(new PolylineOptions()
                .add(coordinate, new LatLng(23.81037, 90.4125))
                .width(5)
                .color(Color.RED));

                */
        // Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
        //Uri.parse("http://maps.google.com/maps?daddr=23.81037,90.4125"));
        //startActivity(intent);
        // giveDirectionThroughGoogleMap(lat,lon,23.81037,90.4125);
        //takeMeToalocation();
        //mMap.addMarker(new MarkerOptions().position(new LatLng(lat, lon)).title("Marker"));
    }

    private void markMapOnAGivenAddress(String address){

        LatLng coordinate = getLocationFromAddress(this,address);
        // LatLng coordinate = new LatLng(lat, lon);
        CameraUpdate yourLocation = CameraUpdateFactory.newLatLngZoom(coordinate, 15);
        mMap.addMarker(new MarkerOptions().position(coordinate).title("Marker"));
        mMap.animateCamera(yourLocation);
    }

    private void giveDirectionThroughGoogleMap(double sourceLat,double sourceDest, double destLat, double destLon){
        String uri = String.format(Locale.ENGLISH, "http://maps.google.com/maps?saddr=%f,%f(%s)&daddr=%f,%f (%s)",sourceLat,sourceDest, "Home Sweet Home",destLat,destLon, "Where the party is at");
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
        //intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
        startActivity(intent);
    }

    private void takeMeToalocation(){
        Uri gmmIntentUri = Uri.parse("geo:0,0?q=1600 Amphitheatre Parkway, Mountain+View, California");
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        startActivity(mapIntent);

    }

    public LatLng getLocationFromAddress(Context context,String strAddress) {

        Geocoder coder = new Geocoder(context);
        List<Address> address;
        LatLng p1 = null;

        try {
            address = coder.getFromLocationName(strAddress, 5);
            if (address == null) {
                return null;
            }
            Address location = address.get(0);
            location.getLatitude();
            location.getLongitude();

            p1 = new LatLng(location.getLatitude(), location.getLongitude() );

        } catch (Exception ex) {

            ex.printStackTrace();
        }

        return p1;
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        //Toast.makeText(this,"google map client",Toast.LENGTH_LONG).show();
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnected(Bundle bundle) {

        try {
            mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        }catch(SecurityException e){

        }

        if (mLastLocation == null) {
            Toast.makeText(this, "Google client has returned null", Toast.LENGTH_LONG).show();
            //buildGoogleApiClient();
        } else if (mLastLocation != null) {
            // Toast.makeText(this,"Google client has returned",Toast.LENGTH_LONG).show();
            // mLatitudeText.setText(String.valueOf(mLastLocation.getLatitude()));
            //mLongitudeText.setText(String.valueOf(mLastLocation.getLongitude()));
            Toast.makeText(this, "Google client has returned not null", Toast.LENGTH_LONG).show();
            Toast.makeText(this, mLastLocation.getLatitude() + " " + mLastLocation.getLongitude(), Toast.LENGTH_LONG).show();
            setUpMap(mLastLocation.getLatitude(),mLastLocation.getLongitude());
        }

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    public void onMapLongClick(LatLng latLng) {
        Toast.makeText(this,"the pressed location is"+latLng.longitude+" "+latLng.latitude,Toast.LENGTH_LONG).show();
        Log.d("the long pressed location,",latLng.latitude +" " + latLng.longitude);
    }

    @Override
    public void onMapClick(LatLng latLng) {

    }
}
