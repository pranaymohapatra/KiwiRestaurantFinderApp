package com.pranaymohapatra.kiwi.frameworks.basemvp;

import android.content.Context;

public interface IBaseView {
    void hideProgressBar();

    void showProgressBar();

    void showProgressBar(String message);

    void showToast(String message);

    Context getContext();
}
