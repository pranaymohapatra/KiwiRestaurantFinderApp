package com.pranaymohapatra.kiwi.frameworks.imageloader;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;


public class GlideImageLoaderImpl implements ImageLoader {
    Context mContext;

    public GlideImageLoaderImpl(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void loadImage(String url, ImageView imageView) {
        Glide.with(mContext)
                .load(url)
                .into(imageView);
    }

//    public void loadImage(String url, ImageView imageView, int placeHolderID) {
//        Glide.with(mContext)
//                .load(url)
//                .(placeHolderID)
//                .into(imageView);
//    }
}
