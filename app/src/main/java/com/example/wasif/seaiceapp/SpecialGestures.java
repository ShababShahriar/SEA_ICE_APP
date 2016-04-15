package com.example.wasif.seaiceapp;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

/**
 * Created by Shabab on 4/15/2016.
 */
public class SpecialGestures extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // for power button
//        IntentFilter filter = new IntentFilter( Intent.ACTION_SCREEN_ON );
//        filter.addAction( Intent.ACTION_SCREEN_OFF );
//        registerReceiver(new ScreenReceiver(), filter);
    }


    /**
     * Control Volume Buttons
     */

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
                    Toast.makeText(getApplicationContext(), "Volume Up!", Toast.LENGTH_LONG).show();
                }
                return true;
            case KeyEvent.KEYCODE_VOLUME_DOWN:
                if (action == KeyEvent.ACTION_DOWN) {
                    //TODO
                    Log.d("action", "inside if");
                    Toast.makeText(getApplicationContext(), "Volume Down!", Toast.LENGTH_LONG).show();
                }
                return true;
            default:
                return super.dispatchKeyEvent(event);
        }
    }


    /**
     * Control Power Button - Not Possible according to our requirements.
     * Have to use voice commands after selecting option by volume buttons.
     */


//    static class Constants {
//        public static final String TAG = "";
//    }
//
//    public static abstract class BaseActivity extends Activity {
//        public static void unlockScreen () {
//            if (current == null) return;
//
//            Log.i(Constants.TAG, "Turning on screen ... ");
//
//            Window window = current.getWindow();
//            window.addFlags( WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD );
//            window.addFlags( WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED );
//            window.addFlags( WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON   );
//        }
//
//        public static void clearScreen () {
//            if (current == null) return;
//
//            Log.i( Constants.TAG, "Clearing screen flag when on ... " );
//
//            Window window = current.getWindow();
//            window.clearFlags( WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD );
//            window.clearFlags( WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED );
//            window.clearFlags( WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON   );
//        }
//
//        private static BaseActivity current;
//
//        @Override
//        protected void onResume () {
//            current = this;
//            super.onResume();
//        }
//    }
//
//
//    public class ScreenReceiver extends BroadcastReceiver {
//
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            String action = intent.getAction();
//            switch (action) {
//                case Intent.ACTION_SCREEN_OFF:
//                    BaseActivity.unlockScreen();
//                    Toast.makeText(getApplicationContext(), "Home button pressed", Toast.LENGTH_LONG).show();
//                    break;
//
//                case Intent.ACTION_SCREEN_ON:
//                    // and do whatever you need to do here
//                    BaseActivity.clearScreen();
////                    Toast.makeText(getApplicationContext(), "Home button pressed", Toast.LENGTH_LONG).show();
//            }
//        }
//    }
}
