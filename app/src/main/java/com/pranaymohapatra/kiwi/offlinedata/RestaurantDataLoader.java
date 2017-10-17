package com.pranaymohapatra.kiwi.offlinedata;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.CancellationSignal;
import android.os.OperationCanceledException;

import com.pranaymohapatra.kiwi.model.FavouriteRestaurantModel;

import java.util.ArrayList;


public class RestaurantDataLoader extends AsyncTaskLoader<ArrayList<FavouriteRestaurantModel>> {

    final ForceLoadContentObserver mObserver;
    ArrayList<FavouriteRestaurantModel> mList;
    CancellationSignal mCancellationSignal;
    private Uri mUri;
    private String[] mProjection;
    private String mSelection;
    private String[] mSelectionArgs;
    private String mSortOrder;

    public RestaurantDataLoader(Context context, Uri uri, String[] projection, String selection,
                                String[] selectionArgs, String sortOrder) {
        super(context);
        mObserver = new ForceLoadContentObserver();
        mUri = uri;
        mProjection = projection;
        mSelection = selection;
        mSelectionArgs = selectionArgs;
        mSortOrder = sortOrder;

    }

    @Override
    public ArrayList<FavouriteRestaurantModel> loadInBackground() {
        synchronized (this) {
            if (isLoadInBackgroundCanceled()) {
                throw new OperationCanceledException();
            }
            mCancellationSignal = new CancellationSignal();
        }
        try {
            Cursor cursor = getContext().getContentResolver().query(mUri, mProjection, mSelection,
                    mSelectionArgs, mSortOrder, mCancellationSignal);
            ArrayList list = new ArrayList<>();
            if (cursor != null) {
                try {
                    while (cursor.moveToNext()) {
                        FavouriteRestaurantModel restaurant = new FavouriteRestaurantModel();
                        restaurant.setID(cursor.getInt(cursor.getColumnIndex(FavouriteDBContract.Schema.COLUMN_ID)));
                        restaurant.setName(cursor.getString(cursor.getColumnIndex((FavouriteDBContract.Schema.NAME_TITLE))));
                        restaurant.setCuisines(cursor.getString(cursor.getColumnIndex((FavouriteDBContract.Schema.CUISINES))));
                        restaurant.setAverageCost(cursor.getString(cursor.getColumnIndex((FavouriteDBContract.Schema.AVERAGE_COST))));
                        restaurant.setLocality(cursor.getString(cursor.getColumnIndex((FavouriteDBContract.Schema.LOCALITY))));
                        restaurant.setAverageRating(cursor.getString(cursor.getColumnIndex(FavouriteDBContract.Schema.USER_RATING)));
                        restaurant.setRatingColor(cursor.getString(cursor.getColumnIndex(FavouriteDBContract.Schema.RATING_COLOR)));
                        restaurant.setURL(cursor.getString(cursor.getColumnIndex((FavouriteDBContract.Schema.URL))));
                        restaurant.setImageURL(cursor.getString(cursor.getColumnIndex(FavouriteDBContract.Schema.IMAGE_URL)));
                        list.add(restaurant);
                    }

                } catch (RuntimeException ex) {
                    cursor.close();
                    throw ex;
                }
            }
            return list;
        } finally {
            synchronized (this) {
                mCancellationSignal = null;
            }
        }
    }

    @Override
    public void deliverResult(ArrayList<FavouriteRestaurantModel> list) {
        if (isReset()) {
            // An async query came in while the loader is stopped
            return;
        }
        mList = list;

        if (isStarted()) {
            super.deliverResult(list);
        }
    }

    @Override
    public void cancelLoadInBackground() {
        super.cancelLoadInBackground();

        synchronized (this) {
            if (mCancellationSignal != null) {
                mCancellationSignal.cancel();
            }
        }
    }

    @Override
    protected void onStartLoading() {

        getContext().getContentResolver().registerContentObserver(mUri, false, mObserver);

        if (mList != null) {
            deliverResult(mList);
        }
        if (takeContentChanged() || mList == null) {
            forceLoad();
        }
    }

    @Override
    protected void onStopLoading() {
        // Attempt to cancel the current load task if possible.
        cancelLoad();
    }

    @Override
    protected void onReset() {
        super.onReset();

        // Ensure the loader is stopped
        onStopLoading();
    }
}
