package com.pranaymohapatra.kiwi.presenter;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import com.pranaymohapatra.kiwi.frameworks.basemvp.AppBasePresenter;
import com.pranaymohapatra.kiwi.model.IRestaurantInteractor;
import com.pranaymohapatra.kiwi.model.RestaurantInteractorImpl;
import com.pranaymohapatra.kiwi.model.restaurants.RestaurantData;
import com.pranaymohapatra.kiwi.presenter.IMainActivityMVP.IMainActivityPresenter;
import com.pranaymohapatra.kiwi.view.FavoritesActivity;

public class MainActivityPresenterImpl extends AppBasePresenter<IMainActivityMVP.IMainActivityView>
        implements IMainActivityPresenter, IRestaurantInteractor.OnRestaurantSync {

    IMainActivityMVP.IMainActivityView mainActivityView;
    IRestaurantInteractor interactor;
    RestaurantData restaurantData;
    private int CITY_ID;
    private String ENTITY_TYPE = "city";

    @Override
    public void attachView(IMainActivityMVP.IMainActivityView view) {
        super.attachView(view);
        mainActivityView = view;
        interactor = new RestaurantInteractorImpl();

    }

    @Override
    public void onFavoriteClick() {
        Intent intent = new Intent(mainActivityView.getContext(), FavoritesActivity.class);
        mainActivityView.startActivity(intent);
    }

    @Override
    public void onSortByRatingClick() {
        if (restaurantData != null) {
            restaurantData.sortRestaurantsBy("Rating");
            mainActivityView.setRestaurantList(restaurantData.getCompositeList());
        }
    }

    @Override
    public void onSortByPrice() {
        if (restaurantData != null) {
            restaurantData.sortRestaurantsBy("Price");
            mainActivityView.setRestaurantList(restaurantData.getCompositeList());
        }
    }

    @Override
    public void onSearchChange(String text) {
        if (text.length() < 2) {
            mainActivityView.setTypingTextVisiblity(View.VISIBLE);
            mainActivityView.setRecyclerViewVisiblity(View.INVISIBLE);
            return;
        }
        interactor.getRestaurantList(CITY_ID, ENTITY_TYPE, text, this);
    }

    @Override
    public void onRestaurantSyncSuccess(RestaurantData data) {
        this.restaurantData = data;
        mainActivityView.setRestaurantList(data.getCompositeList());
        if (data.getCompositeList() != null && data.getCompositeList().size() > 0) {
            mainActivityView.setRecyclerViewVisiblity(View.VISIBLE);
            mainActivityView.setTypingTextVisiblity(View.GONE);
            mainActivityView.hideProgressBar();
        }
    }

    @Override
    public void onRestaruantSyncStarted() {
        mainActivityView.showProgressBar();
    }

    @Override
    public void onRestaurantSyncFail() {
        mainActivityView.hideProgressBar();
        mainActivityView.showToast("Network issues, please search again");
        mainActivityView.setRecyclerViewVisiblity(View.INVISIBLE);
        mainActivityView.setTypingTextVisiblity(View.VISIBLE);
    }

    @Override
    public boolean onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1001) {
            if (resultCode == Activity.RESULT_OK)
                CITY_ID = data.getIntExtra("CITY_ID", 1);
            else
                CITY_ID = 1;
        }
        return false;
    }

    @Override
    public void detachView() {
        interactor.disposeData();
        super.detachView();
    }
}
