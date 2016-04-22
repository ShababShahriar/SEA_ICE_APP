package com.example.wasif.seaiceapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Location;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DataCollectionActivity extends AppCompatActivity implements  GoogleApiClient.ConnectionCallbacks,GoogleApiClient.OnConnectionFailedListener{

    private int REQUEST_IMAGE_CAPTURE = 1;
    private Bitmap imageBitmap;
    private ImageView mImageView;
    private byte[] audioByteArray;
    private byte[] imageByteArray;
    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;


    private static final String LOG_TAG = "AudioRecordTest";
    private static String mFileName = null;

    Button play,stop,record,send;
    private MediaRecorder myAudioRecorder;
    private String outputFile = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_collection);
        mImageView = (ImageView)(findViewById(R.id.imgCaptured));

        collectData();
    }

    private void collectData() {
        dispatchTakePictureIntent();
        arrangeAudioRecording();
    }

    private void arrangeAudioRecording() {
        play=(Button)findViewById(R.id.btnPlay);
        stop=(Button)findViewById(R.id.btnStop);
        record=(Button)findViewById(R.id.btnRecord);
        send=(Button)findViewById(R.id.btnSend);

        stop.setEnabled(false);
        play.setEnabled(false);
        send.setEnabled(false);

        outputFile = Environment.getExternalStorageDirectory().getAbsolutePath() + "/recording.3gp";;

        myAudioRecorder=new MediaRecorder();
        myAudioRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        myAudioRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        myAudioRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
        myAudioRecorder.setOutputFile(outputFile);

        record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    myAudioRecorder.prepare();
                    myAudioRecorder.start();
                } catch (IllegalStateException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                record.setEnabled(false);
                stop.setEnabled(true);

                Toast.makeText(getApplicationContext(), "Recording started", Toast.LENGTH_LONG).show();
            }
        });


        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myAudioRecorder.stop();
                myAudioRecorder.release();
                myAudioRecorder  = null;

                stop.setEnabled(false);
                play.setEnabled(true);
                send.setEnabled(true);

                Toast.makeText(getApplicationContext(), "Audio recorded successfully",Toast.LENGTH_LONG).show();
            }
        });

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                buildGoogleApiClient();



            }
        });

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) throws IllegalArgumentException, SecurityException, IllegalStateException {
                MediaPlayer m = new MediaPlayer();

                try {
                    m.setDataSource(outputFile);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                try {
                    m.prepare();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                m.start();
                Toast.makeText(getApplicationContext(), "Playing audio", Toast.LENGTH_LONG).show();
            }
        });
    }

    private byte[] convertBitmapIntoByteArray(Bitmap imageBitmap){

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, bos);
        imageByteArray = bos.toByteArray();
        return imageByteArray;

    }

    private byte[] convertAudioFileIntoByteArrayAndRemoveTheFile(String outputFile){
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(outputFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        byte[] buffer =new byte[1024];
        int read;
        try {
            while ((read = fis.read(buffer)) != -1) {
                baos.write(buffer, 0, read);
            }
            baos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

        audioByteArray = baos.toByteArray();
       // Log.d(LOG_TAG, new String(audioByteArray));

        deleteTheAudioFile(outputFile);

        return  audioByteArray;
    }

    private void deleteTheAudioFile(String outputFile) {


        File f0 = new File(outputFile);
        boolean d0 = f0.delete();
        Log.d("Delete Check", "File deleted: " + outputFile + " " + d0);
    }

    private void takeImage() {

    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {

            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);

        }
    }

    private boolean checkInternetConnection() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        // test for connection
        if (cm.getActiveNetworkInfo() != null
                && cm.getActiveNetworkInfo().isAvailable()
                && cm.getActiveNetworkInfo().isConnected()) {
            Log.v(LOG_TAG, "Internet Connection Present");
            return true;
        } else {
            Log.v(LOG_TAG, "Internet Connection Not Present");
            return false;
        }
    }

    private String getCurrentTimestamp(){
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSZ");
        //DateFormat dateFormat = new SimpleDateFormat("YYYY-MM-DD HH:mm:ss");
        Date date = new Date();
//            dateFormat.format(date);

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        //cal.add(Calendar.MINUTE, -1 * timeToSubtract);
        Date newDate = cal.getTime();
        //Log.d("timestamp", dateFormat.format(newDate));

        String timestamp = dateFormat.format(newDate);
        //Log.d("timestamp: ", "" + timestamp);
        return timestamp;
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            imageBitmap = (Bitmap) extras.get("data");
            mImageView.setImageBitmap(imageBitmap);


        }
    }











    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_data_collection, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onPause() {
        super.onPause();

    }



    @Override
    public void onConnected(Bundle bundle) {

        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);
        if (mLastLocation == null) {
            Toast.makeText(this, "Google client has returned null", Toast.LENGTH_LONG).show();
            //buildGoogleApiClient();
        } else if (mLastLocation != null) {
            // Toast.makeText(this,"Google client has returned",Toast.LENGTH_LONG).show();
            // mLatitudeText.setText(String.valueOf(mLastLocation.getLatitude()));
            //mLongitudeText.setText(String.valueOf(mLastLocation.getLongitude()));
            Toast.makeText(this, "Google client has returned not null", Toast.LENGTH_LONG).show();
            Toast.makeText(this, mLastLocation.getLatitude() + " " + mLastLocation.getLongitude(), Toast.LENGTH_LONG).show();
            convertAudioFileIntoByteArrayAndRemoveTheFile(outputFile);
            convertBitmapIntoByteArray(imageBitmap);
            String timeStamp = getCurrentTimestamp();
            //Log.d(LOG_TAG,mLastLocation.toString());
            //Log.d(LOG_TAG, new String(imageByteArray));
            //Log.d(LOG_TAG, new String(audioByteArray));
            CollectedData dataToBeSEnt =new CollectedData(imageByteArray,audioByteArray,mLastLocation.getLatitude()+"",mLastLocation.getLongitude()+"",timeStamp,Utility.getUserId());
            sendDataToApproriateDatabase(dataToBeSEnt);
        }

    }

    private void sendDataToApproriateDatabase(CollectedData dataToBeSent) {
        //if internet is present, try to send data to server
        if(checkInternetConnection()){
             new SendDataTask().execute(dataToBeSent);
        }

      else{
            storeDataInSQLite(dataToBeSent);
        }
    }

    private void storeDataInSQLite(CollectedData dataToBeSent) {

        DBHelper helper = new DBHelper(this);
        helper.insertRecord(dataToBeSent);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    class SendDataTask extends AsyncTask<Object, Void, String> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }


        protected String doInBackground(Object... args) {

            JSONParser jParser = new JSONParser();
            // Building Parameters
            List<Pair> params = new ArrayList<Pair>();
            CollectedData dataToBeSent = (CollectedData)(args[0]);
            params.add(new Pair("latitude", dataToBeSent.getLatitude()));
            params.add(new Pair("longitude", dataToBeSent.getLongitude()));
            params.add(new Pair("image", Base64.encodeToString(dataToBeSent.getImage(), 0)));
            params.add(new Pair("audio", Base64.encodeToString(dataToBeSent.getAudio(), 0)));
            params.add(new Pair("time", dataToBeSent.getTimeOfRecording()));
            params.add(new Pair("userId", dataToBeSent.getUserId()));


            Log.d("just before sending ", dataToBeSent.toString());
            // getting JSON string from URL
            //JSONObject responseJson = jParser.makeHttpRequest("/insertData", "GET", params);
            //Log.d("returned data", responseJson.toString());
            return null;


        }


        protected void onPostExecute(String a) {


        }
    }
}
