package com.pranaymohapatra.kiwi.frameworks.imageloader;

import android.widget.ImageView;


public interface ImageLoader {
    void loadImage(String url, ImageView imageView);

    //void loadImage(String url, ImageView imageView, int placeHolderID);
}
