package com.pranaymohapatra.kiwi.offlinedata;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

public class FavouriteContentProvider extends ContentProvider {

    public static final Uri CONTENT_URI = Uri.parse("content://favouriterestaurants");

    FavouriteDBHelper dbHelper;

    public FavouriteContentProvider() {
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        return db.delete(FavouriteDBContract.Schema.TABLE_NAME, selection, selectionArgs);
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        // TODO: Implement this to handle requests to insert a new row.
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        long id;
        id = db.insert(FavouriteDBContract.Schema.TABLE_NAME, null, values);
        getContext().getContentResolver().notifyChange(uri, null);
        Log.d("KIWIAPP","Inserted RowID " + id);
        return Uri.parse(CONTENT_URI + "/" + id);
    }

    @Override
    public int bulkInsert(@NonNull Uri uri, @NonNull ContentValues[] values) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.beginTransaction();
        int cont = 0;
        for (int i = 0; i < values.length; i++) {
            long id = db.insert(FavouriteDBContract.Schema.TABLE_NAME, null, values[i]);
            cont++;
        }
        db.setTransactionSuccessful();
        db.endTransaction();
        getContext().getContentResolver().notifyChange(uri, null);
        return cont;
    }

    @Override
    public boolean onCreate() {
        // TODO: Implement this to initialize your content provider on startup.
        dbHelper = new FavouriteDBHelper(getContext());
        return false;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        // TODO: Implement this to handle query requests from clients.
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        queryBuilder.setTables(FavouriteDBContract.Schema.TABLE_NAME);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = queryBuilder.query(db, projection, selection, selectionArgs, null, null, sortOrder);
        cursor.setNotificationUri(getContext().getContentResolver(),CONTENT_URI);
        return cursor;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int rowsupdated = db.update(FavouriteDBContract.Schema.TABLE_NAME, values, selection, selectionArgs);
        getContext().getContentResolver().notifyChange(CONTENT_URI,null);
        Log.d("KIWIAPP", "Updated " + rowsupdated + " Rows");
        return rowsupdated;
    }

}
