package com.pranaymohapatra.kiwi.model;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.like.LikeButton;
import com.like.OnLikeListener;
import com.pranaymohapatra.kiwi.R;
import com.pranaymohapatra.kiwi.view.WebViewActivity;
import com.pranaymohapatra.kiwi.frameworks.imageloader.GlideImageLoaderImpl;
import com.pranaymohapatra.kiwi.model.restaurants.Restaurant;
import com.pranaymohapatra.kiwi.model.restaurants.RestaurantsItem;
import com.pranaymohapatra.kiwi.offlinedata.FavouriteContentProvider;
import com.pranaymohapatra.kiwi.offlinedata.FavouriteDBContract;

import java.util.ArrayList;


public class RestaurantViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    final int TYPE_HEADER = 101;
    final int TYPE_CARD = 102;
    ArrayList<Object> restaurantDataSet;
    Context mContext;

    public OnLikeListener likeListener = new OnLikeListener() {
        @Override
        public void liked(LikeButton likeButton) {
            int position = Integer.parseInt(likeButton.getTag().toString());
            setFavouriteRestaurant(position, 1);
        }

        @Override
        public void unLiked(LikeButton likeButton) {
            int position = Integer.parseInt(likeButton.getTag().toString());
            setFavouriteRestaurant(position, 0);
        }
    };
    public View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int id = v.getId();
            int position = Integer.parseInt(v.getTag().toString());
            Restaurant restaurant = ((RestaurantsItem) restaurantDataSet.get(position)).getRestaurant();
            switch (id) {
                case R.id.textholder:
                    Intent intent = new Intent();
                    intent.setClass(mContext, WebViewActivity.class);
                    intent.putExtra("URL", restaurant.getUrl());
                    intent.putExtra("Name", restaurant.getName() + ", " + restaurant.getLocation().getLocality());
                    mContext.startActivity(intent);
                    break;

                case R.id.sharebutton:
                    Intent sendIntent = new Intent();
                    sendIntent.setAction(Intent.ACTION_SEND);
                    sendIntent.putExtra(Intent.EXTRA_TITLE, restaurant.getName());
                    sendIntent.putExtra(Intent.EXTRA_TEXT, restaurant.getUrl());
                    sendIntent.setType("text/plain");
                    mContext.startActivity(Intent.createChooser(sendIntent, "Share With..."));
            }

        }
    };

    public RestaurantViewAdapter(Context context, ArrayList<Object> dataList) {
        this.mContext = context;
        this.restaurantDataSet = dataList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh = null;
        switch (viewType) {
            case TYPE_CARD:
                View cardView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_card_item, parent, false);
                vh = new ViewHolder(cardView);
                break;

            case TYPE_HEADER:
                View header = LayoutInflater.from(parent.getContext()).inflate(R.layout.headerview, parent, false);
                vh = new HeaderView(header);
                break;
        }
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case TYPE_CARD:
                ViewHolder vh = (ViewHolder) holder;

                vh.textViewHolder.setTag(position);      //setting onclick
                vh.textViewHolder.setOnClickListener(clickListener);
                RestaurantsItem restaurantsItem = (RestaurantsItem) restaurantDataSet.get(position);

                vh.titleView.setText(restaurantsItem.getRestaurant().getName());         //setting text and button drwbl
                vh.cuisineView.setText(restaurantsItem.getRestaurant().getCuisines());
                vh.priceView.setText("₹ " + restaurantsItem.getRestaurant().getAverageCostForTwo());
                vh.localityView.setText(restaurantsItem.getRestaurant().getLocation().getLocalityVerbose());
                vh.ratingView.setText(restaurantsItem.getRestaurant().getUserRating().getAggregateRating());
                GradientDrawable drawable = (GradientDrawable) vh.ratingView.getBackground();
                drawable.setColor(Color.parseColor("#"
                        + restaurantsItem.getRestaurant().getUserRating().getRatingColor()));
                vh.ratingView.setBackground(drawable);
                new GlideImageLoaderImpl(mContext).loadImage(restaurantsItem.getRestaurant().getThumb(), vh.imageView);
                if (restaurantsItem.getIsFavourite() == 1) {
                    vh.likeButton.setLiked(true);
                } else
                    vh.likeButton.setLiked(false);

                vh.shareButton.setOnClickListener(clickListener);
                vh.shareButton.setTag(position);

                vh.likeButton.setOnLikeListener(likeListener);   //setting onclick for like
                vh.likeButton.setTag(position);
                break;
            case TYPE_HEADER:
                HeaderView hh = (HeaderView) holder;
                String name = (String) restaurantDataSet.get(position);
                hh.cuisineName.setText(name);
        }

    }

    @Override
    public int getItemCount() {
        if (restaurantDataSet != null)
            return restaurantDataSet.size();
        else
            return 0;
    }

    @Override
    public int getItemViewType(int position) {
        Object o = restaurantDataSet.get(position);
        if (o instanceof String)
            return TYPE_HEADER;
        else
            return TYPE_CARD;
    }

    public void setData(ArrayList<Object> newdata) {
        this.restaurantDataSet = newdata;
        notifyDataSetChanged();
    }

    private ContentValues getValues(Restaurant restaurant) {
        ContentValues value = new ContentValues();
        value.put(FavouriteDBContract.Schema.COLUMN_ID, restaurant.getId());
        value.put(FavouriteDBContract.Schema.NAME_TITLE, restaurant.getName());
        value.put(FavouriteDBContract.Schema.CUISINES, restaurant.getCuisines());
        value.put(FavouriteDBContract.Schema.AVERAGE_COST, restaurant.getAverageCostForTwo());
        value.put(FavouriteDBContract.Schema.LOCALITY, restaurant.getLocation().getLocalityVerbose());
        value.put(FavouriteDBContract.Schema.USER_RATING, restaurant.getUserRating().getAggregateRating());
        value.put(FavouriteDBContract.Schema.RATING_COLOR, restaurant.getUserRating().getRatingColor());
        value.put(FavouriteDBContract.Schema.URL, restaurant.getUrl());
        value.put(FavouriteDBContract.Schema.IMAGE_URL, restaurant.getThumb());
        return value;
    }

    private void setFavouriteRestaurant(int position, int fav) {
        RestaurantsItem item = (RestaurantsItem) restaurantDataSet.get(position);
        Restaurant restaurant = item.getRestaurant();

        if (fav == 0) {
            try {
                String selection = FavouriteDBContract.Schema.COLUMN_ID + "=?";
                String[] selectionargs = {String.valueOf(restaurant.getId())};
                mContext.getContentResolver().delete(FavouriteContentProvider.CONTENT_URI, selection, selectionargs);
                item.setIsFavourite(fav);
            } catch (Exception e) {
                Toast.makeText(mContext, "Cant make favorite", Toast.LENGTH_SHORT);
                e.printStackTrace();
            }

        }
        ContentValues value = getValues(restaurant);
        try {
            mContext.getContentResolver().insert(FavouriteContentProvider.CONTENT_URI, value);
            item.setIsFavourite(fav);
        } catch (Exception e) {
            Toast.makeText(mContext, "Cant make favorite", Toast.LENGTH_SHORT);
            e.printStackTrace();
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView titleView;
        TextView cuisineView;
        TextView priceView;
        TextView localityView;
        TextView ratingView;
        ImageView imageView;
        LikeButton likeButton;
        LinearLayout textViewHolder;
        Button shareButton;

        public ViewHolder(View v) {
            super(v);
            titleView = (TextView) v.findViewById(R.id.textview1);
            cuisineView = (TextView) v.findViewById(R.id.cuisines);
            priceView = (TextView) v.findViewById(R.id.price);
            localityView = (TextView) v.findViewById(R.id.locality);
            ratingView = (TextView) v.findViewById(R.id.rating);
            imageView = (ImageView) v.findViewById(R.id.imageView);
            likeButton = (LikeButton) v.findViewById(R.id.favbutton);
            likeButton.setUnlikeDrawableRes(R.drawable.starbuttonnotlike);
            textViewHolder = (LinearLayout) v.findViewById(R.id.textholder);
            shareButton = (Button) v.findViewById(R.id.sharebutton);
        }
    }

    public static class HeaderView extends RecyclerView.ViewHolder {
        TextView cuisineName;

        public HeaderView(View v) {
            super(v);
            cuisineName = (TextView) v.findViewById(R.id.cuisinelabel);
        }
    }

}
