package com.example.wasif.seaiceapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

/**
 * Created by wasif on 4/17/16.
 */
public class MyReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        //Toast.makeText(context, "Intent Detected.", Toast.LENGTH_LONG).show();
        Log.d("braodcast receiver ", "called");

        if(isOnline(context)){
            Log.d("device ", "online");
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
    /*

    public static boolean isNetworkAvailable(Context context) {
        boolean isMobile = false, isWifi = false;

        NetworkInfo[] infoAvailableNetworks = getConnectivityManagerInstance(
                context).getAllNetworkInfo();

        if (infoAvailableNetworks != null) {
            for (NetworkInfo network : infoAvailableNetworks) {

                if (network.getType() == ConnectivityManager.TYPE_WIFI) {
                    if (network.isConnected() && network.isAvailable())
                        isWifi = true;
                }
                if (network.getType() == ConnectivityManager.TYPE_MOBILE) {
                    if (network.isConnected() && network.isAvailable())
                        isMobile = true;
                }
            }
        }

        return isMobile || isWifi;
    }
    */

}
