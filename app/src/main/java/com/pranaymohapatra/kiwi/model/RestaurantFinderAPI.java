package com.pranaymohapatra.kiwi.model;

import com.pranaymohapatra.kiwi.model.location.LocationModel;
import com.pranaymohapatra.kiwi.model.restaurants.RestaurantModel;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;


public interface RestaurantFinderAPI {

    String ZOMATO_ROOT_URL = "https://developers.zomato.com/api/v2.1/";

    @GET("search")
    @Headers({"Accept: application/json","user-key: 258f3e496748d15cb792bc554000d6b6"})
    Observable<RestaurantModel> getRestaraunts(@Query("entity_id") int entityID,
                                               @Query("entity_type") String entity_type,
                                               @Query("q")String searchFor,
                                               @Query("start") int start,
                                               @Query("count") int count);
    @GET("cities")
    @Headers({"Accept: application/json","user-key: 258f3e496748d15cb792bc554000d6b6"})
    Observable<LocationModel> getLocation(@Query("lat") double lat,
                                          @Query("lon") double lon);
}
