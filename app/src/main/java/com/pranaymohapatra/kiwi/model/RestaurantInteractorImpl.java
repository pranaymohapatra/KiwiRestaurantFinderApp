package com.pranaymohapatra.kiwi.model;

import android.os.AsyncTask;

import com.pranaymohapatra.kiwi.model.restaurants.RestaurantData;
import com.pranaymohapatra.kiwi.model.restaurants.RestaurantModel;
import com.pranaymohapatra.kiwi.model.restaurants.RestaurantsItem;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class RestaurantInteractorImpl implements IRestaurantInteractor {

    public static int COUNT;
    Scheduler restaurantScheduler;
    RestaurantFinderAPI restaurantFinderAPI;
    Observable<RestaurantModel> restaurantModelObservable;
    Disposable restaurantDisposable;
    ArrayList<RestaurantsItem> restaurantList;
    ArrayList<RestaurantsItem> copyList;

    OnRestaurantSync onRestaurantSync;
    RestaurantData restaurantData;

    @Override
    public void getRestaurantList(int city, String entitytype, String searchString, OnRestaurantSync onRestaurantSync) {
        onRestaurantSync.onRestaruantSyncStarted();

        this.onRestaurantSync = onRestaurantSync;
        restaurantList = new ArrayList<>();
        restaurantFinderAPI = RetrofitClient.getRetrofitClientAPI();
        restaurantScheduler = Schedulers.newThread();

        for (int i = 0; i <= 80; i += 20) {
            restaurantModelObservable = restaurantFinderAPI.getRestaraunts(city, entitytype, searchString, i, 20);

            restaurantModelObservable
                    .subscribeOn(restaurantScheduler)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<RestaurantModel>() {
                        @Override
                        public void onSubscribe(Disposable d) {
                            COUNT++;
                            restaurantDisposable = d;
                        }

                        @Override
                        public void onNext(RestaurantModel value) {
                            restaurantList.addAll(value.getRestaurants());
                        }

                        @Override
                        public void onError(Throwable e) {
                            restaurantDisposable.dispose();
                            onRestaurantSync.onRestaurantSyncFail();
                        }

                        @Override
                        public void onComplete() {
                            if (COUNT == 5) {
                                new ModifyTask().execute(restaurantList);
                                COUNT = 0;
                            }
                        }
                    });
        }


    }

    @Override
    public void disposeData() {
        if (restaurantDisposable != null && !restaurantDisposable.isDisposed())
            restaurantDisposable.dispose();
    }

    class ModifyTask extends AsyncTask<List<RestaurantsItem>, Integer, Void> {
        @Override
        protected void onPreExecute() {
            if (restaurantData == null)
                restaurantData = new RestaurantData();
            else
                restaurantData.purge();
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(List<RestaurantsItem>... params) {
            copyList = new ArrayList<>();
            copyList.addAll(params[0]);
            for (RestaurantsItem item : copyList) {
                for (String cuisine : item.getRestaurant().getCuisines().split(",\\s", 0)) {
                    restaurantData.addRestaurantsByCuisine(cuisine, item);
                }
            }
            restaurantData.makeCompositeList();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            restaurantList.clear();
            onRestaurantSync.onRestaurantSyncSuccess(restaurantData);
            super.onPostExecute(aVoid);
        }
    }
}
