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
 * Created by wasif on 4/23/16.
 */
public class DBHelperForUserPosts extends SQLiteOpenHelper {

    //Give names to each column of the table. Helps in calling and remembering
    private int markerIdInSqlite;
    private double latitude;
    private double longitude;
    private String markerType;

    public static final String DATABASE_NAME = "userPosts.db";
    public static final String REPORT_TABLE_NAME = "postTable";
    public static final String REPORT_COLUMN_ID = "id";
    public static final String REPORT_COLUMN_USERID = "userID";
    public static final String REPORT_COLUMN_LATITUDE="latitude";
    public static final String REPORT_COLUMN_LONGITUDE="longitude";
    public static final String REPORT_MARKER_TYPE="markerType";

    public DBHelperForUserPosts(Context context)
    {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Create new table.
        db.execSQL(
                "create table postTable " +
                        "(id integer primary key,userID text,latitude text,longitude text,markerType text)"
        );
        Log.d("Database created", "yes we can");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // If table exists, drop it and create a new one. Otherwise just create a new one.
        // TODO Don't drop the table right away. Check how long it has been stagnant. Drop if only it has been sitting for a (given) long time. Otherwise add new entries.
        db.execSQL("DROP TABLE IF EXISTS postTable");
        onCreate(db);
    }


    public boolean insertUserPost(CustomMarker dataToBeInserted)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("userID",Utility.getUserId());
        contentValues.put("latitude",dataToBeInserted.getLatitude());
        contentValues.put("longitude",dataToBeInserted.getLongitude());
        contentValues.put("markerType",dataToBeInserted.getMarkerType());
        Log.d("before_insert", getDataForUser(Utility.getUserId()).toString());
        db.insert("postTable", null, contentValues);
        Log.d("after_insert",getDataForUser(Utility.getUserId()).toString());
        return true;
    }

    public int numberOfRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, REPORT_TABLE_NAME);
        return numRows;
    }




    //get all data about a single user
    public ArrayList<CustomMarker> getDataForUser(String userId) {
        // handle using ID NOT user name
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from postTable where userID=\"" + userId + "\"", null);

        return convertCursorIntoArrayList(res);
    }

    private ArrayList<CustomMarker> convertCursorIntoArrayList(Cursor res){

        //SQLiteDatabase db = this.getReadableDatabase();
        //Cursor res =  db.rawQuery( "select * from tempTable", null );
        res.moveToFirst();
        ArrayList<CustomMarker> allDataByAUser = new ArrayList<CustomMarker>();

        while(res.isAfterLast() == false){

            String userID = res.getString(res.getColumnIndex(REPORT_COLUMN_USERID));
            String latitude = res.getString(res.getColumnIndex(REPORT_COLUMN_LATITUDE));
            String longitude = res.getString(res.getColumnIndex(REPORT_COLUMN_LONGITUDE));
            String markerType = res.getString(res.getColumnIndex(REPORT_MARKER_TYPE));
            int internalId = res.getInt(res.getColumnIndex(REPORT_COLUMN_ID));
            CustomMarker marker = new CustomMarker(Double.parseDouble(latitude),Double.parseDouble(longitude),markerType);
            marker.setMarkerIdInSqlite(internalId);
            Log.d("data from sqlite:", "" + marker.toString());
            allDataByAUser.add(marker);
            //array_list.add(res.getString(res.getColumnIndex(REPORT_COLUMN_ID)) +" " +res.getString(res.getColumnIndex(REPORT_COLUMN_USERID)) + " " + res.getString(res.getColumnIndex(REPORT_COLUMN_TIME)) + " " + res.getString(res.getColumnIndex(REPORT_COLUMN_LATITUDE))+" "+res.getString(res.getColumnIndex(REPORT_COLUMN_LONGITUDE)));
            res.moveToNext();
        }
        return allDataByAUser;

    }

}
