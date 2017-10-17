package com.pranaymohapatra.kiwi.offlinedata;

import android.provider.BaseColumns;


public class FavouriteDBContract {
    private FavouriteDBContract(){}

    public static class Schema implements BaseColumns {
        public static final String DATABASE_NAME = "FavouriteRestaurants.db";
        public static final String TABLE_NAME = "favouriterestaurants";
        public static final String COLUMN_ID = "ID";
        public static final String NAME_TITLE = "NAME";
        public static final String CUISINES ="CUISINES";
        public static final String AVERAGE_COST = "AVERAGECOST";
        public static final String LOCALITY ="LOCALITY";
        public static final String USER_RATING = "RATING";
        public static final String RATING_COLOR = "COLOR";
        public static final String URL = "URL";
        public static final String IMAGE_URL = "IMAGEURL";
    }
}
