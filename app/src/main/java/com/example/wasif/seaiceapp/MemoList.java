package com.example.wasif.seaiceapp;

import android.app.Activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MemoList extends Activity {

    ListView lvwM;
    //final String[] mlist = {"Alaska - 27 June 2015", "Kenai - 14 July 2016", "Alaska - 27 June 2015", "Alaska - 15 May 2015", "Alaska - 27 April 2007", "Alaska - 10 March 2006"};
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memo_list);
        lvwM = (ListView) findViewById(R.id.lvwMemoList1);
        String[] mlist = null;
        if (Utility.tempData != null)
        {
            mlist = new String[Utility.tempData.size()];
            for (int i = 0; i<Utility.tempData.size(); i++)
                mlist[i] = Utility.tempData.get(i).getTime();
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mlist);
            lvwM.setAdapter(adapter);
        }
        //lvwM.setItemChecked(2, true);
        //lvwM.setItemChecked(4, true);
        //lvwM.setItemChecked(5, true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_memo_list, menu);
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
