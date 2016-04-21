package com.example.wasif.seaiceapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;
import android.util.Pair;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wasif on 4/17/16.
 */


public class MyBroadcastReceiver extends BroadcastReceiver {

    Context context;
    DBHelper helper;
    @Override

    public void onReceive(Context context, Intent intent) {
        //Toast.makeText(context, "Intent Detected.", Toast.LENGTH_LONG).show();
        Log.d("braodcast receiver ", "called");

        if(isOnline(context)){
            helper = new DBHelper(context);
            ArrayList<CollectedData> allDatasToBeSent = helper.getDataForUser(Utility.getUserId());

            Log.d("device ", "online");
            Log.d("the data has come back",allDatasToBeSent.toString());
            for(int i = 0;i<allDatasToBeSent.size();i++){
                new SendDataTask().execute(allDatasToBeSent.get(i)).execute();
            }
        }
        else{
            Log.d("device ", "offline");
        }
    }




    public boolean isOnline(Context context) {

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        //should check null because in air plan mode it will be null
        return (netInfo != null && netInfo.isConnected());

    }
    class SendDataTask extends AsyncTask<Object, Void, String> {

        JSONObject responseJSON;
        CollectedData dataToBeSent;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }


        protected String doInBackground(Object... args) {

            JSONParser jParser = new JSONParser();
            // Building Parameters
            List<Pair> params = new ArrayList<Pair>();
            dataToBeSent = (CollectedData)(args[0]);
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

             if(responseJSON!=null){
                 helper.deleteRecord(dataToBeSent.getInternalTableId()+"");
             }
        }
    }

}
