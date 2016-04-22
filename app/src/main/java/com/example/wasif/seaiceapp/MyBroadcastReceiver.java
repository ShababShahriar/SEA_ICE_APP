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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wasif on 4/17/16.
 */


public class MyBroadcastReceiver extends BroadcastReceiver {

    Context context;
    DBHelper helper;
    ArrayList<CollectedData> allDatasToBeSent;
    @Override

    public void onReceive(Context context, Intent intent) {
        //Toast.makeText(context, "Intent Detected.", Toast.LENGTH_LONG).show();
        Log.d("braodcast receiver ", "called");

        if(isOnline(context)){
            helper = new DBHelper(context);
            if(allDatasToBeSent != null){
                allDatasToBeSent.clear();
            }
            allDatasToBeSent = helper.getDataForUser(Utility.getUserId());

            if(allDatasToBeSent.size()==0){
                return;
            }
            Log.d("device ", "online");
            Log.d("the number of data that has come back", ""+allDatasToBeSent.size());
            callNextData(0);


        }
        else{
            Log.d("device ", "offline");
        }
    }

    private void callNextData(int index){
        if(index>= allDatasToBeSent.size()){
            return;
        }
        new SendDataTask().execute(index);
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
        int indexOfTheDataToSend = 0;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }


        protected String doInBackground(Object... args) {

            JSONParser jParser = new JSONParser();
            // Building Parameters
            List<Pair> params = new ArrayList<Pair>();
            indexOfTheDataToSend = (Integer)(args[0]);
            dataToBeSent=allDatasToBeSent.get(indexOfTheDataToSend);

            params.add(new Pair("latitude", dataToBeSent.getLatitude()));
            params.add(new Pair("longitude", dataToBeSent.getLongitude()));
            params.add(new Pair("image", Base64.encodeToString(dataToBeSent.getImage(), 0)));
            params.add(new Pair("audio", Base64.encodeToString(dataToBeSent.getAudio(), 0)));
            params.add(new Pair("time", dataToBeSent.getTimeOfRecording()));
            params.add(new Pair("userId", dataToBeSent.getUserId()));


            Log.d("just before sending ", dataToBeSent.toString());
            // getting JSON string from URL
            responseJSON = jParser.makeHttpRequest("/add_post", "POST", params);
            //Log.d("returned data", responseJson.toString());
            return null;


        }


        protected void onPostExecute(String a) {

             if(responseJSON!=null){

                 try {
                     if(responseJSON.getString("query").equals("OK")) {

                         helper.deleteRecord(dataToBeSent.getInternalTableId() + "");
                         Log.d("the entry has been cleared from ","database");
                         callNextData(indexOfTheDataToSend+1);
                     }
                 } catch (JSONException e) {
                     e.printStackTrace();
                 }
             }
        }
    }

}
