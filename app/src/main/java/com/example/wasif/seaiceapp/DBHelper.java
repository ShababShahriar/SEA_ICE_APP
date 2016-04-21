package com.example.wasif.seaiceapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by wasif on 4/17/16.
 */
public class DBHelper extends SQLiteOpenHelper {

    //Give names to each column of the table. Helps in calling and remembering

    public static final String DATABASE_NAME = "tempReport.db";
    public static final String REPORT_TABLE_NAME = "tempTable";
    public static final String REPORT_COLUMN_ID = "id";
    public static final String REPORT_COLUMN_USERID = "userID";

    public static final String REPORT_COLUMN_IMAGE = "image";
    public static final String REPORT_COLUMN_TIME = "time";
    public static final String REPORT_COLUMN_AUDIO="audio";
    public static final String REPORT_COLUMN_LATITUDE="latitude";
    public static final String REPORT_COLUMN_LONGITUDE="longitude";
    //private HashMap hp;

    public DBHelper(Context context)
    {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Create new table.
        db.execSQL(
                "create table tempTable " +
                        "(id integer primary key,userID text,image blob,audio blob,time text,latitude text,longitude text)"
        );
        Log.d("Database created", "yes we can");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // If table exists, drop it and create a new one. Otherwise just create a new one.
        // TODO Don't drop the table right away. Check how long it has been stagnant. Drop if only it has been sitting for a (given) long time. Otherwise add new entries.
        db.execSQL("DROP TABLE IF EXISTS tempTable");
        onCreate(db);
    }

    public boolean insertRecord(CollectedData dataToBeInserted)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("image",dataToBeInserted.getImage());
        contentValues.put("userID",dataToBeInserted.getUserId());
        contentValues.put("audio",dataToBeInserted.getAudio());
        contentValues.put("time",dataToBeInserted.getTimeOfRecording());
        contentValues.put("latitude",dataToBeInserted.getLatitude());
        contentValues.put("longitude",dataToBeInserted.getLongitude());

        db.insert("tempTable", null, contentValues);
        Log.d("after_insert",getDataForUser(dataToBeInserted.getUserId()).toString());
        return true;
    }

    //get all data about a single user
    public ArrayList<CollectedData> getDataForUser(String userId) {
        // handle using ID NOT user name
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from tempTable where userID=\"" + userId + "\"", null);

        return convertCursorIntoArrayList(res);
    }

    private ArrayList<CollectedData> convertCursorIntoArrayList(Cursor res){

        //SQLiteDatabase db = this.getReadableDatabase();
        //Cursor res =  db.rawQuery( "select * from tempTable", null );
        res.moveToFirst();
        ArrayList<CollectedData> allDataByAUser = new ArrayList<CollectedData>();

        while(res.isAfterLast() == false){

            String userID = res.getString(res.getColumnIndex(REPORT_COLUMN_USERID));
            String time = res.getString(res.getColumnIndex(REPORT_COLUMN_TIME));
            String latitude = res.getString(res.getColumnIndex(REPORT_COLUMN_LATITUDE));
            String longitude = res.getString(res.getColumnIndex(REPORT_COLUMN_LONGITUDE));
            byte[] image = res.getBlob(res.getColumnIndex(REPORT_COLUMN_IMAGE));
            byte[] audio = res.getBlob(res.getColumnIndex(REPORT_COLUMN_AUDIO));
            int internalId = res.getInt(res.getColumnIndex(REPORT_COLUMN_ID));
            CollectedData data = new CollectedData(image,audio,latitude,longitude,time,userID);
            data.setInternalTableId(internalId);
            Log.d("data from sqlite:","" + data.toString());
            allDataByAUser.add(data);
            //array_list.add(res.getString(res.getColumnIndex(REPORT_COLUMN_ID)) +" " +res.getString(res.getColumnIndex(REPORT_COLUMN_USERID)) + " " + res.getString(res.getColumnIndex(REPORT_COLUMN_TIME)) + " " + res.getString(res.getColumnIndex(REPORT_COLUMN_LATITUDE))+" "+res.getString(res.getColumnIndex(REPORT_COLUMN_LONGITUDE)));
            res.moveToNext();
        }
        return allDataByAUser;

    }

    public int numberOfRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, REPORT_TABLE_NAME);
        return numRows;
    }



    /*public boolean updateContact (Integer id, String name, String phone, String email, String street,String place)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("phone", phone);
        contentValues.put("email", email);
        contentValues.put("street", street);
        contentValues.put("place", place);
        db.update("contacts", contentValues, "id = ? ", new String[] { Integer.toString(id) } );
        return true;
    }
    */

    public Integer deleteRecord (String id)
    {
        Log.d("before deleting: ",getAllRecords().toString());
        SQLiteDatabase db = this.getWritableDatabase();
        int returnId = db.delete("tempTable",
                "id = ? ",
                new String[]{id});
        Log.d("after deleting: ",getAllRecords().toString());
        return returnId;
    }

    public ArrayList<String> getAllRecords()
    {
        ArrayList<String> array_list = new ArrayList<String>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from tempTable", null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            //Log.d("User ID: ",""+ res.getString(res.getColumnIndex(REPORT_COLUMN_USERID)));
            //Log.d("Time: ","" + res.getString(res.getColumnIndex(REPORT_COLUMN_TIME)));
            //Log.d("Latitude: ",""+res.getString(res.getColumnIndex(REPORT_COLUMN_LATITUDE)));
            //Log.d("Longitude: ",""+res.getString(res.getColumnIndex(REPORT_COLUMN_LONGITUDE)));
            array_list.add(res.getString(res.getColumnIndex(REPORT_COLUMN_ID)) +" " +res.getString(res.getColumnIndex(REPORT_COLUMN_USERID)) + " " + res.getString(res.getColumnIndex(REPORT_COLUMN_TIME)) + " " + res.getString(res.getColumnIndex(REPORT_COLUMN_LATITUDE))+" "+res.getString(res.getColumnIndex(REPORT_COLUMN_LONGITUDE)));
            res.moveToNext();
        }
        return array_list;
    }
}
