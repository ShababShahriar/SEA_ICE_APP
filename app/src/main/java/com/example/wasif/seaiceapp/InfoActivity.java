package com.example.wasif.seaiceapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class InfoActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnShowImage;
    ImageView imageIceRadar;
    TextView txtTempData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        btnShowImage = (Button) (findViewById(R.id.btnShowImage));
        btnShowImage.setOnClickListener(this);

        imageIceRadar = (ImageView) (findViewById(R.id.imgIceRadar));

        txtTempData = (TextView) (findViewById(R.id.txtTemp));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_info, menu);
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
    public void onClick(View v) {

        if (v.getId() == R.id.btnShowImage) {
            Log.d("clicked: ", "yes");
            //dispathPuffinFeeder();
            //new getPuffinResponse().execute();
            new bringTempDataTask().execute();
        }
    }

    class bringTempDataTask extends AsyncTask<String, Void, String> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }


        protected String doInBackground(String... args) {

            JSONParser jParser = new JSONParser();
            // Building Parameters
            List<Pair> params = new ArrayList<Pair>();

            // getting JSON string from URL
            String tempData = jParser.makeHttpRequestToGetString("http://coastwatch.pfeg.noaa.gov/erddap/tabledap/pmelTaoDySst.json?longitude,latitude,time,station,wmo_platform_code,T_25&time>=2015-05-23T12:00:00Z&time<=2015-05-31T12:00:00Z", "GET", params);

            JSONObject tempJSON = null;
            try {
                tempJSON =new JSONObject(tempData);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Log.d("returned data",tempJSON.toString());
            return null;


        }




        protected void onPostExecute(String a) {




        }
    }




    class getPuffinResponse extends AsyncTask<String, Void, String> {

      JSONObject jsonGetRes;
      Drawable theIceRadar;
      Bitmap  bmpSEaIceRadar;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }


        protected String doInBackground(String... args) {

            JSONParser jParser = new JSONParser();
            // Building Parameters
            List<Pair> params = new ArrayList<Pair>();

            // getting JSON string from URL
            String responseFromPuffinServer= jParser.makeHttpRequestToGetString("http://feeder.gina.alaska.edu/radar-uaf-barrow-seaice-images.json", "GET", params);
            String imageUrl = extractUrlOfTheLargeImage(responseFromPuffinServer);
            Log.d("imageURL",imageUrl);
            //removing the  " " things.
            imageUrl=imageUrl.substring(1,imageUrl.length()-1);
            Log.d("after imageURL",imageUrl);

            try {
                //imageUrl = imageUrl.replaceAll(" ", "%20");
                bmpSEaIceRadar = LoadImageFromWebOperations(new URL(imageUrl));
            } catch (MalformedURLException e) {
                Log.d("url not formed"," correctly");
                e.printStackTrace();
            }


            return null;


        }

        private Bitmap LoadImageFromWebOperations(URL url) {
            /*
            try {
                InputStream is = (InputStream) new URL(url).getContent();
                Drawable d = Drawable.createFromStream(is, "src name");
                return d;
            } catch (Exception e) {
                return null;
            }
            */

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

        private String extractUrlOfTheLargeImage(String responseFromPuffinServer) {
            // try parse the string to a JSON object


                int findLargeOccIndex= responseFromPuffinServer.indexOf("large");
                //Log.d("found large at",findPrev+"");
                int picAddBegin = findLargeOccIndex + new String("large").length()+2;
                int lastAdd = responseFromPuffinServer.indexOf("}",picAddBegin);
                String pichttp = responseFromPuffinServer.substring(picAddBegin,lastAdd);
                Log.d("extracted http id",pichttp);

                return pichttp;
        }

        protected void onPostExecute (String a){


            imageIceRadar.setImageBitmap(bmpSEaIceRadar);

        }
    }
}




