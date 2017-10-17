package com.pranaymohapatra.kiwi.presenter;

import android.app.LoaderManager;
import android.content.Loader;
import android.os.Bundle;
import android.view.View;

import com.pranaymohapatra.kiwi.frameworks.basemvp.AppBasePresenter;
import com.pranaymohapatra.kiwi.model.FavouriteRestaurantModel;
import com.pranaymohapatra.kiwi.offlinedata.FavouriteContentProvider;
import com.pranaymohapatra.kiwi.offlinedata.FavouriteDBContract;
import com.pranaymohapatra.kiwi.offlinedata.RestaurantDataLoader;
import com.pranaymohapatra.kiwi.view.FavoritesActivity;

import java.util.ArrayList;

public class FavouritesActivityPresenterImpl
        extends AppBasePresenter<IFavouritesActivityMVP.IFavouritesView>
        implements IFavouritesActivityMVP.IFavouritesPresenter,
        LoaderManager.LoaderCallbacks<ArrayList<FavouriteRestaurantModel>> {


    int LOADER_ID = 10010;

    String[] projection = {FavouriteDBContract.Schema.COLUMN_ID,
            FavouriteDBContract.Schema.NAME_TITLE,
            FavouriteDBContract.Schema.CUISINES,
            FavouriteDBContract.Schema.AVERAGE_COST,
            FavouriteDBContract.Schema.LOCALITY,
            FavouriteDBContract.Schema.USER_RATING,
            FavouriteDBContract.Schema.RATING_COLOR,
            FavouriteDBContract.Schema.URL,
            FavouriteDBContract.Schema.IMAGE_URL};

    IFavouritesActivityMVP.IFavouritesView favouritesView;

    private void initLoader() {
        ((FavoritesActivity) favouritesView.getContext()).getLoaderManager().initLoader(LOADER_ID, null, this);
    }

    @Override
    public Loader<ArrayList<FavouriteRestaurantModel>> onCreateLoader(int id, Bundle args) {

        if (id == LOADER_ID) {
            RestaurantDataLoader restaurantDataLoader = new RestaurantDataLoader(favouritesView.getContext(),
                    FavouriteContentProvider.CONTENT_URI, projection, null, null, null);
            return restaurantDataLoader;
        }
        return null;
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<FavouriteRestaurantModel>> loader, ArrayList<FavouriteRestaurantModel> data) {
        favouritesView.hideProgressBar();
        if (data.size() > 0) {
            favouritesView.setRecyclerData(data);
            favouritesView.setRecyclerViewVisibiltiy(View.VISIBLE);
            favouritesView.setTextViewVisibility(View.INVISIBLE);
        } else {
            favouritesView.setRecyclerViewVisibiltiy(View.INVISIBLE);
            favouritesView.setTextViewVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<FavouriteRestaurantModel>> loader) {

    }


    @Override
    public void attachView(IFavouritesActivityMVP.IFavouritesView view) {
        favouritesView = view;
        initLoader();
        super.attachView(view);
    }

    @Override
    public void detachView() {
        super.detachView();
    }
}
