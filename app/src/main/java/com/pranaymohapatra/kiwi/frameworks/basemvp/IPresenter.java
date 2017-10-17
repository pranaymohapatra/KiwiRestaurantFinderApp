package com.pranaymohapatra.kiwi.frameworks.basemvp;

import android.content.Intent;


public interface IPresenter<T extends IView> {
    void onViewStarted();

    void attachView(T view);

    void onViewCreated();

    boolean onActivityResult(int requestCode, int resultCode, Intent data);

    void detachView();
}
