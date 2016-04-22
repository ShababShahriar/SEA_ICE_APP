package com.example.wasif.seaiceapp;

import android.app.ActionBar;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.net.Uri;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.MenuItemCompat;
import android.util.Log;
import android.view.ActionMode;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MapsActivity extends FragmentActivity implements GoogleMap.OnMapClickListener, GoogleMap.OnMapLongClickListener,LocationListener,GoogleApiClient.ConnectionCallbacks,GoogleApiClient.OnConnectionFailedListener, GoogleMap.OnMarkerClickListener{

    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private ShakeDetector mShakeDetector;

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;
    ActionMode mActionMode;

    int zoom_level;
    private final int REQ_CODE_SPEECH_INPUT = 100;

    private ActionMode.Callback mActionModeCallback = new ActionMode.Callback(){
        @Override
        public void onDestroyActionMode(ActionMode mode) {
            mActionMode = null;
        }

        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            MenuInflater inflater = mode.getMenuInflater();
            inflater.inflate(R.menu.map_cab, menu);
            mode.setTitle("Select a placemarker type");
            mode.setSubtitle("It will be added to the selected location");
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            switch (item.getItemId()){
                case R.id.cab_add_memo:
                    //Toast.makeText(getApplicationContext(),"Add item here",Toast.LENGTH_SHORT).show();
                    Intent i1 = new Intent(getApplicationContext(), DataCollectionActivity.class);
                    startActivity(i1);
                    mode.finish();
                    return true;
                case R.id.cab_hunting:
                    Toast.makeText(getApplicationContext(),"Adding hunting place",Toast.LENGTH_SHORT).show();
                    mode.finish();
                    return true;
                case R.id.cab_ice_crack:
                    Toast.makeText(getApplicationContext(),"Adding Ice crack",Toast.LENGTH_SHORT).show();
                    mode.finish();
                    return true;
                case R.id.cab_risky:
                    Toast.makeText(getApplicationContext(),"Adding risky place",Toast.LENGTH_SHORT).show();
                    mode.finish();
                    return true;

                default:
                    return false;
            }
        }
    };


    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        int action = event.getAction();
        int keyCode = event.getKeyCode();
        switch (keyCode) {
            case KeyEvent.KEYCODE_VOLUME_UP:
                Log.d("keycode", "vol up");
                if (action == KeyEvent.ACTION_DOWN) {
                    //TODO
                    Log.d("action", "inside if");
                    if (zoom_level <20)
                    {
                        zoom_level++;
                        mMap.animateCamera(CameraUpdateFactory.zoomTo(zoom_level));
                    }

                    //Toast.makeText(getApplicationContext(), "Volume Up!", Toast.LENGTH_LONG).show();
                }
                return true;
            case KeyEvent.KEYCODE_VOLUME_DOWN:
                if (action == KeyEvent.ACTION_DOWN) {
                    //TODO
                    Log.d("action", "inside if");
                    if (zoom_level > 1)
                    {
                        zoom_level--;
                        mMap.animateCamera(CameraUpdateFactory.zoomTo(zoom_level));
                    }
                    //Toast.makeText(getApplicationContext(), "Volume Down!", Toast.LENGTH_LONG).show();
                }
                return true;
            default:
                return super.dispatchKeyEvent(event);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        zoom_level = 15;
        setup_actionbar();
        setUpMapIfNeeded();
        setup_checkbox();
        setup_buttons();
        setup_shake_sensor();
    }

    void setup_shake_sensor()
    {

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager
                .getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mShakeDetector = new ShakeDetector();
        mShakeDetector.setOnShakeListener(new ShakeDetector.OnShakeListener() {

            @Override
            public void onShake(int count) {
				/*
				 * The following method, "handleShakeEvent(count):" is a stub //
				 * method you would use to setup whatever you want done once the
				 * device has been shook.
				 */
                //CheckBox ck = (CheckBox) findViewById(R.id.chkShowMore);
                //ck.setChecked(!ck.isChecked());
                Log.d("Shake", "Device Shook!");

                promptSpeechInput();
                //setUpListeners();

                //handleShakeEvent(count);
            }
        });
    }


    /**
     * Showing google speech input dialog
     * */
    private void promptSpeechInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                getString(R.string.speech_prompt));
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(),
                    getString(R.string.speech_not_supported),
                    Toast.LENGTH_SHORT).show();
        }
    }


    /**
     * Receiving speech input
     * */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {

                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    //TextView t1 = (TextView) findViewById(R.id.lblRSpeech);
                    //t1.setText(result.get(0));
                    Toast.makeText(getApplicationContext(), "Received voice data: " + result.get(0) , Toast.LENGTH_LONG).show();
                    Log.d("returned answer:",result.toString());
                }
                break;
            }

        }
    }

    void setup_actionbar()
    {
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }
    void setup_checkbox()
    {
        CheckBox chkM = (CheckBox) findViewById(R.id.chkShowMore);
        chkM.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                LinearLayout l = (LinearLayout) findViewById(R.id.layoutMore);
                TextView s = (TextView) findViewById(R.id.lblInfoSummary);
                if (isChecked) {
                    l.setVisibility(View.VISIBLE);
                    s.setVisibility(View.INVISIBLE);
                } else {
                    l.setVisibility(View.GONE);
                    s.setVisibility(View.VISIBLE);
                }
            }
        });
    }
    void setup_buttons()
    {

        final Button btnDownload = (Button) findViewById(R.id.bDownload);
        btnDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(getApplicationContext(), btnDownload);
                //Inflating the Popup using xml file
                popup.getMenuInflater().inflate(R.menu.download_public_data, popup.getMenu());

                //registering popup with OnMenuItemClickListener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        Toast.makeText(getApplicationContext(), "You Clicked : " + item.getTitle(), Toast.LENGTH_SHORT).show();
                        return true;
                    }
                });
                popup.show();//showing popup menu
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_map, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.abtn_distress_call:
                Toast.makeText(this,"Distress call initiated!",Toast.LENGTH_LONG).show();
                // User chose the "Settings" item, show the app settings UI...
                return true;
            case R.id.abtn_memo:
                Intent i = new Intent(getApplicationContext(), MemoList.class);
                startActivity(i);
                return true;
            case R.id.abtn_radar:
                Intent j = new Intent(getApplicationContext(), RaderIce.class);
                startActivity(j);
                return true;

            //case R.id.more:
                // User chose the "Favorite" action, mark the current item
                // as a favorite...
              //  return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
        // Add the following line to register the Session Manager Listener onResume
        mSensorManager.registerListener(mShakeDetector, mAccelerometer, SensorManager.SENSOR_DELAY_UI);
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
                mMap.setOnMarkerClickListener(this);
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
        CameraUpdate yourLocation = CameraUpdateFactory.newLatLngZoom(coordinate, zoom_level);
        mMap.addMarker(new MarkerOptions().position(coordinate).title("Marker"));
        mMap.animateCamera(yourLocation);
    }

    private void giveDirectionThroughGoogleMap(double sourceLat,double sourceDest, double destLat, double destLon){
        String uri = String.format(Locale.ENGLISH, "http://maps.google.com/maps?saddr=%f,%f(%s)&daddr=%f,%f (%s)", sourceLat, sourceDest, "Home Sweet Home", destLat, destLon, "Where the party is at");
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
            //Toast.makeText(this, "Google client has returned not null", Toast.LENGTH_LONG).show();
            //Toast.makeText(this, mLastLocation.getLatitude() + " " + mLastLocation.getLongitude(), Toast.LENGTH_LONG).show();
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
    public void onPause() {
        // Add the following line to unregister the Sensor Manager onPause
        mSensorManager.unregisterListener(mShakeDetector);
        super.onPause();
    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    public void onMapLongClick(LatLng latLng) {
        //Toast.makeText(this,"the pressed location is"+latLng.longitude+" "+latLng.latitude,Toast.LENGTH_LONG).show();
        //if (mActionMode != null)

            mActionMode = startActionMode(mActionModeCallback);

        //Log.d("the long pressed location,",latLng.latitude +" " + latLng.longitude);
    }

    @Override
    public void onMapClick(LatLng latLng) {

    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        Toast.makeText(this,"the pressed location is" + marker.getPosition().toString(),Toast.LENGTH_SHORT).show();
        Intent i = new Intent(getApplicationContext(), MarkerDetails.class);
        startActivity(i);
        return true;
    }
}
