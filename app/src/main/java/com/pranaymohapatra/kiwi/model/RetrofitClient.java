package com.pranaymohapatra.kiwi.model;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    static Retrofit retrofit;
    static RestaurantFinderAPI restaurantFinderAPI;

    public static RestaurantFinderAPI getRetrofitClientAPI() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder().baseUrl(RestaurantFinderAPI.ZOMATO_ROOT_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.
                            create()).build();
        }
        if (restaurantFinderAPI == null) {
            restaurantFinderAPI = retrofit.create(RestaurantFinderAPI.class);
        }
        return restaurantFinderAPI;
    }
}
