package com.pranaymohapatra.kiwi.model.restaurants;

import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import io.reactivex.Observable;


public class RestaurantData {

    private static Map<String, List<RestaurantsItem>> restaurantsCuisine;
    private static ArrayList<Object> restaurantList = new ArrayList<>();
    private static String searchOrder = "Rating";
    private RatingComparator ratingComparator;
    private PriceComparator priceComparator;

    public RestaurantData() {
        Log.d("KIWIAPP", "Restaurant DAta Contructor");
        restaurantsCuisine = new TreeMap<>();
        ratingComparator = new RatingComparator();
        priceComparator = new PriceComparator();
    }

    private void addRestaurantsByCuisine(String cuisine, RestaurantsItem restaurant) {
        List<RestaurantsItem> temp = restaurantsCuisine.get(cuisine);
        if (temp != null)
            temp.add(restaurant);
        else {
            temp = new ArrayList<>();
            temp.add(restaurant);
        }
        restaurantsCuisine.put(cuisine, temp);
    }

    public Observable<Integer> addRestaurantByCuisine(RestaurantModel model) {
        Log.d("KIWIAPP", "addrestunrt by cuisne called");
        List<RestaurantsItem> temp = model.getRestaurants();
        for (RestaurantsItem item : temp) {
            for (String cuisine : item.getRestaurant().getCuisines().split(",\\s", 0)) {
                addRestaurantsByCuisine(cuisine, item);
            }
        }
        return Observable.just(temp.size());
    }

    public void purge() {
        restaurantsCuisine.clear();
        restaurantList.clear();
    }

    public void printAll() {
        Set<String> keySet = restaurantsCuisine.keySet();
        for (String key : keySet) {
            List<RestaurantsItem> items = restaurantsCuisine.get(key);
            for (RestaurantsItem item : items) {
                Log.d("KIWIAPP", item.getRestaurant().getName());
            }
        }
    }

    public Observable<Integer> makeCompositeList() {
        Log.d("KIWIAPP", "makeCompositeList");
        restaurantList.clear();
        Set<String> keySet = restaurantsCuisine.keySet();
        for (String key : keySet) {
            restaurantList.add(key);
            List<RestaurantsItem> items = restaurantsCuisine.get(key);
            for (RestaurantsItem item : items) {
                restaurantList.add(item);
            }
        }
        return Observable.just(restaurantList.size());

    }

    public void sortRestaurantsBy(String criteria) {
        searchOrder = criteria;
        if (restaurantsCuisine.isEmpty())
            return;
        Set<String> keySet = restaurantsCuisine.keySet();
        if ("Rating".equals(searchOrder)) {
            for (String key : keySet) {
                restaurantList.add(key);
                List<RestaurantsItem> items = restaurantsCuisine.get(key);
                Collections.sort(items, ratingComparator);
            }
        } else if ("Price".equals(searchOrder)) {
            for (String key : keySet) {
                restaurantList.add(key);
                List<RestaurantsItem> items = restaurantsCuisine.get(key);
                Collections.sort(items, priceComparator);
            }
        }
        makeCompositeList();
    }

    public ArrayList<Object> getCompositeList() {
        return restaurantList;
    }

    private static class RatingComparator implements Comparator<RestaurantsItem> {
        @Override
        public int compare(RestaurantsItem o1, RestaurantsItem o2) {
            String r1, r2;
            r1 = o1.getRestaurant().getUserRating().getAggregateRating();
            r2 = o2.getRestaurant().getUserRating().getAggregateRating();
            return r2.compareTo(r1);
        }
    }

    private static class PriceComparator implements Comparator<RestaurantsItem> {
        @Override
        public int compare(RestaurantsItem o1, RestaurantsItem o2) {
            Integer r1, r2;
            r1 = o1.getRestaurant().getAverageCostForTwo();
            r2 = o2.getRestaurant().getAverageCostForTwo();
            return r1.compareTo(r2);
        }
    }
}
