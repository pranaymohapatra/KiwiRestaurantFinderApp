package com.pranaymohapatra.kiwi.offlinedata;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class FavouriteDBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION =  1;
    private final static String CREATE = "CREATE TABLE " + FavouriteDBContract.Schema.TABLE_NAME + " (" +
            FavouriteDBContract.Schema.COLUMN_ID +" INTEGER PRIMARY KEY , " +
            FavouriteDBContract.Schema.NAME_TITLE +" TEXT , " +
            FavouriteDBContract.Schema.CUISINES +" TEXT , " +
            FavouriteDBContract.Schema.AVERAGE_COST +" TEXT , " +
            FavouriteDBContract.Schema.LOCALITY +" TEXT , " +
            FavouriteDBContract.Schema.USER_RATING +" TEXT , " +
            FavouriteDBContract.Schema.RATING_COLOR +" TEXT , " +
            FavouriteDBContract.Schema.URL +" TEXT , " +
            FavouriteDBContract.Schema.IMAGE_URL +" TEXT )";

    private final static String DROP_TABLE = "DROP TABLE IF EXISTS " + FavouriteDBContract.Schema.TABLE_NAME ;

    public FavouriteDBHelper(Context context) {
        super(context, FavouriteDBContract.Schema.DATABASE_NAME,null,DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_TABLE);
        onCreate(db);
    }

}
