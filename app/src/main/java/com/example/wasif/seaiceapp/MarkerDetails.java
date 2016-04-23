package com.example.wasif.seaiceapp;

import android.app.ActionBar;
import android.app.Activity;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class MarkerDetails extends Activity {
    boolean is_memo = false;
    boolean is_hunting = false;
    int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marker_details);
        Bundle bundle = getIntent().getExtras();
        ImageView img = (ImageView) findViewById(R.id.imgMarkerDetails);
        Button btnPlay = (Button) findViewById(R.id.btnMarkerPlayPause);
        String title = bundle.getString("title");
        if (title.substring(0,4).equals("Memo"))
        {
            is_memo = true;
            id = Integer.valueOf(title.substring(4));
            btnPlay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //String outputFile = Environment.getExternalStorageDirectory().getAbsolutePath() + "/temp_rec.3gp";
                    FileOutputStream fos = null;
                    File tempMp3 = null;
                    try {
                        tempMp3 = File.createTempFile("kurchina", "mp3", getCacheDir());
                        tempMp3.deleteOnExit();
                        fos = new FileOutputStream(tempMp3);
                        fos.write(Utility.tempData.get(id).getAudio());
                        fos.close();
                    } catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                    MediaPlayer m = new MediaPlayer();

                    try {
                        FileInputStream fis = new FileInputStream(tempMp3);
                        m.setDataSource(fis.getFD());
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
            img.setImageBitmap(BitmapFactory.decodeByteArray(Utility.tempData.get(id).getImage(), 0, Utility.tempData.get(id).getImage().length));
            TextView txtTitle = (TextView) findViewById(R.id.lblMarkerType);
            txtTitle.setText(title.substring(0,4) + " " + id);
        }
        else
        {
            img.setVisibility(View.INVISIBLE);
            btnPlay.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_marker_detaills, menu);
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
}
