package com.pranaymohapatra.kiwi.model.restaurants;

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
import com.pranaymohapatra.kiwi.model.FavouriteRestaurantModel;
import com.pranaymohapatra.kiwi.offlinedata.FavouriteContentProvider;
import com.pranaymohapatra.kiwi.offlinedata.FavouriteDBContract;

import java.util.ArrayList;


public class FavouriteViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<FavouriteRestaurantModel> restaurantDataSet;
    private Context mContext;

    private OnLikeListener likeListener = new OnLikeListener() {
        @Override
        public void liked(LikeButton likeButton) {
        }

        @Override
        public void unLiked(LikeButton likeButton) {
            int position = Integer.parseInt(likeButton.getTag().toString());
            removeFavourite(position);
        }
    };
    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int id = v.getId();
            int position = Integer.parseInt(v.getTag().toString());
            FavouriteRestaurantModel restaurant = restaurantDataSet.get(position);
            switch (id) {
                case R.id.textholder:
                    Intent intent = new Intent();
                    intent.setClass(mContext, WebViewActivity.class);
                    intent.putExtra("URL", restaurant.getURL());
                    intent.putExtra("Name", restaurant.getName() + ", " + restaurant.getLocality());
                    mContext.startActivity(intent);
                    break;

                case R.id.sharebutton:
                    Intent sendIntent = new Intent();
                    sendIntent.setAction(Intent.ACTION_SEND);
                    sendIntent.putExtra(Intent.EXTRA_TITLE, restaurant.getName());
                    sendIntent.putExtra(Intent.EXTRA_TEXT, restaurant.getURL());
                    sendIntent.setType("text/plain");
                    mContext.startActivity(Intent.createChooser(sendIntent, "Share With..."));
            }

        }
    };

    public FavouriteViewAdapter(Context context, ArrayList<FavouriteRestaurantModel> dataList) {
        this.mContext = context;
        this.restaurantDataSet = dataList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;

        View cardView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_card_item, parent, false);
        vh = new ViewHolder(cardView);

        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder vh = (ViewHolder) holder;
        FavouriteRestaurantModel restaurantsItem = restaurantDataSet.get(position);

        vh.textViewHolder.setTag(position);      //setting onclick
        vh.textViewHolder.setOnClickListener(clickListener);


        vh.titleView.setText(restaurantsItem.getName());         //setting text and button drwbl
        vh.cuisineView.setText(restaurantsItem.getCuisines());
        vh.priceView.setText("₹ " + restaurantsItem.getAverageCost() + " for two(approx.)");
        vh.localityView.setText(restaurantsItem.getLocality());
        vh.ratingView.setText(restaurantsItem.getAverageRating());
        GradientDrawable drawable = (GradientDrawable) vh.ratingView.getBackground();
        drawable.setColor(Color.parseColor("#"
                + restaurantsItem.getRatingColor()));
        vh.ratingView.setBackground(drawable);
        new GlideImageLoaderImpl(mContext).loadImage(restaurantsItem.getImageURL(), vh.imageView);
        vh.likeButton.setLiked(true);

        vh.shareButton.setOnClickListener(clickListener);
        vh.shareButton.setTag(position);

        vh.likeButton.setOnLikeListener(likeListener);   //setting onclick for like
        vh.likeButton.setTag(position);
    }

    @Override
    public int getItemCount() {
        if (restaurantDataSet != null)
            return restaurantDataSet.size();
        else
            return 0;
    }

    public void setData(ArrayList<FavouriteRestaurantModel> newdata) {
        this.restaurantDataSet = newdata;
        notifyDataSetChanged();
    }

    private void removeFavourite(int position) {
        FavouriteRestaurantModel item = restaurantDataSet.remove(position);
        String selection = FavouriteDBContract.Schema.COLUMN_ID + "=?";
        String[] selectionargs = {String.valueOf(item.getID())};
        try {
            mContext.getContentResolver().delete(FavouriteContentProvider.CONTENT_URI, selection, selectionargs);
        } catch (Exception e) {
            Toast.makeText(mContext, "Cant make favorite", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
        notifyDataSetChanged();
    }

    private static class ViewHolder extends RecyclerView.ViewHolder {
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
}
