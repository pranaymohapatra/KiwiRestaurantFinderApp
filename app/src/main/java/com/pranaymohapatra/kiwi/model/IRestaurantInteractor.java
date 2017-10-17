package com.pranaymohapatra.kiwi.model;

import com.pranaymohapatra.kiwi.model.restaurants.RestaurantData;

public interface IRestaurantInteractor {
    void getRestaurantList(int city, String entitytype, String search, OnRestaurantSync onRestaurantSync);
    void disposeData();

    interface OnRestaurantSync {
        void onRestaurantSyncSuccess(RestaurantData data);
        void onRestaruantSyncStarted();

        void onRestaurantSyncFail();
    }
}
