package com.pranaymohapatra.kiwi.model;

import com.pranaymohapatra.kiwi.model.restaurants.RestaurantData;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class RestaurantInteractorImpl implements IRestaurantInteractor {

    private OnRestaurantSync onRestaurantSync;
    private RestaurantData restaurantData;
    private RestaurantFinderAPI restaurantFinderAPI;
    private Disposable restaurantDisposable;

    @Override
    public void getRestaurantList(int city, String entitytype, String searchString, OnRestaurantSync restaurantSync) {

        this.onRestaurantSync = restaurantSync;
        onRestaurantSync.onRestaruantSyncStarted();
        restaurantFinderAPI = RetrofitClient.getRetrofitClientAPI();
        restaurantData = new RestaurantData();

        Integer[] ids = {0, 20, 40, 60, 80};
        Observable.fromArray(ids)
                .concatMap(count -> restaurantFinderAPI.getRestaraunts(city, entitytype, searchString, count, 20))
                .concatMap(restaurantModel -> restaurantData.addRestaurantByCuisine(restaurantModel))
                .concatMap(integer -> restaurantData.makeCompositeList())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        restaurantDisposable = d;
                    }

                    @Override
                    public void onNext(Integer value) {

                    }

                    @Override
                    public void onError(Throwable e) {
                        onRestaurantSync.onRestaurantSyncFail();
                    }

                    @Override
                    public void onComplete() {
                        onRestaurantSync.onRestaurantSyncSuccess(restaurantData);
                    }
                });
    }

    @Override
    public void disposeData() {
        if (restaurantDisposable != null && !restaurantDisposable.isDisposed())
            restaurantDisposable.dispose();
    }

//    class ModifyTask extends AsyncTask<List<RestaurantsItem>, Integer, Void> {
//        @Override
//        protected void onPreExecute() {
//            if (restaurantData == null)
//                restaurantData = new RestaurantData();
//            else
//                restaurantData.purge();
//            super.onPreExecute();
//        }
//
//        @Override
//        protected Void doInBackground(List<RestaurantsItem>... params) {
//            copyList = new ArrayList<>();
//            copyList.addAll(params[0]);
//            for (RestaurantsItem item : copyList) {
//                for (String cuisine : item.getRestaurant().getCuisines().split(",\\s", 0)) {
//                    restaurantData.addRestaurantsByCuisine(cuisine, item);
//                }
//            }
//            restaurantData.makeCompositeList();
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(Void aVoid) {
//            restaurantList.clear();
//            onRestaurantSync.onRestaurantSyncSuccess(restaurantData);
//            super.onPostExecute(aVoid);
//        }
//    }
}
