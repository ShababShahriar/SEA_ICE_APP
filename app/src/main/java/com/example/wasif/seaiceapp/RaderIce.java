package com.example.wasif.seaiceapp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RaderIce extends Activity {

    SeekBar sk;
    ArrayList<Bitmap> allImageBitmaps;
    ImageView imgIceRadar;

    private Context context;
    private ProgressDialog pd;
    int max;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rader_ice);

        sk = (SeekBar) findViewById(R.id.seekbarIceAnim);
        imgIceRadar = (ImageView) findViewById(R.id.imgRaderIceAnim);
        context = this;

        sk.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                imgIceRadar.setImageBitmap(allImageBitmaps.get(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        pd = new ProgressDialog(context);
        pd.setTitle("Fetching Data...");
        pd.setMessage("Please wait");
        pd.setCancelable(false);
        pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        pd.setIndeterminate(false);
        pd.setMax(1);
        pd.show();
        max = 0;

        new getPuffinResponse().execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_rader_ice, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement


        return super.onOptionsItemSelected(item);
    }

    class getPuffinResponse extends AsyncTask<String, Void, String> {

        JSONObject jsonGetRes;
        Drawable theIceRadar;
        Bitmap bmpSEaIceRadar;


        @Override
        protected void onPreExecute() {
            //TextView t = (TextView) findViewById(R.id.lblIceFractLastUpdated);
            //t.setText("Starting AsyncTask");
            super.onPreExecute();

        }


        protected String doInBackground(String... args) {

            JSONParser jParser = new JSONParser();
            // Building Parameters
            List<Pair> params = new ArrayList<Pair>();

            // getting JSON string from URL
            String responseFromPuffinServer= jParser.makeHttpRequestToGetString("http://feeder.gina.alaska.edu/radar-uaf-barrow-seaice-images.json", "GET", params);

            try {
                JSONObject imageJSON = new JSONObject(responseFromPuffinServer);
                Log.d("image json", imageJSON.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }

            ArrayList<String> allImageUrls = extractUrlOfTheLargeImage(responseFromPuffinServer);
            Collections.reverse(allImageUrls);

            allImageBitmaps = new ArrayList<Bitmap>();
            //pd.dismiss();
            //pd = new ProgressDialog(context);
            runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    pd.setTitle("Downloading Image...");
                    pd.setMessage("Please wait");
                    pd.setCancelable(false);
                    pd.setIndeterminate(false);
                    pd.setMax(max-1);
                }
            });

            //pd.show();

            for(int i=0;i<allImageUrls.size();i++) {
                try {

                    pd.setProgress(i+1);
                    //imageUrl = imageUrl.replaceAll(" ", "%20");
                    Bitmap bmpSEaIceRadar = LoadImageFromWebOperations(new URL(allImageUrls.get(i)));
                    allImageBitmaps.add(bmpSEaIceRadar);

                } catch (MalformedURLException e) {
                    Log.d("url not formed", " correctly");
                    e.printStackTrace();
                }
            }


            return null;


        }

        private Bitmap LoadImageFromWebOperations(URL url) {
            try {

                InputStream in = url.openStream();
                Log.d("opended stream","yes");
                bmpSEaIceRadar = BitmapFactory.decodeStream(in);
                return  bmpSEaIceRadar;
            } catch (Exception e) {
                // log error
            }
            return null;
        }

        private ArrayList<String> extractUrlOfTheLargeImage(String responseFromPuffinServer) {
            // try parse the string to a JSON object
            int beginSearchFrom = 0;
            ArrayList<String> allTheURLS = new ArrayList<String>();

            while(true) {
                max++;
                int findLargeOccIndex = responseFromPuffinServer.indexOf("small",beginSearchFrom);
                //Log.d("found large at",findPrev+"");
                int picAddBegin = findLargeOccIndex + new String("small").length() + 2;
                int lastAdd = responseFromPuffinServer.indexOf(",", picAddBegin);

                if(findLargeOccIndex==-1 || lastAdd==-1){
                    break;
                }
                beginSearchFrom = lastAdd;
                String pichttp = responseFromPuffinServer.substring(picAddBegin, lastAdd);
                pichttp = pichttp.substring(1, pichttp.length() - 1);
                Log.d("extracted http id", pichttp);
                allTheURLS.add(pichttp);
            }

            return allTheURLS;
        }

        protected void onPostExecute(String a){
            sk.setMax(allImageBitmaps.size() - 1);
            sk.setProgress(0);
            pd.dismiss();
            TextView t = (TextView) findViewById(R.id.lblIceFractLastUpdated);
            t.setText("FrameCount: " + allImageBitmaps.size());
            Log.d("image", "On Post EX, size : " + allImageBitmaps.size());
            /*
            for(int i=0;i<allImageBitmaps.size();i++) {
                Log.d("setting image",i+"");
                imageIceRadar.setImageBitmap(allImageBitmaps.get(i));
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }*/
    }
}





}
