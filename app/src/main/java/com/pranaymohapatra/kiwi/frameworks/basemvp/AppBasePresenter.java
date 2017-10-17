package com.pranaymohapatra.kiwi.frameworks.basemvp;

import android.content.Intent;

public abstract class AppBasePresenter<T extends IView> implements IPresenter<T> {

    @Override
    public void attachView(T view) {
    }

    public void onViewStarted() {

    }

    @Override
    public void onViewCreated() {

    }

    public void detachView() {

    }

    @Override
    public boolean onActivityResult(int requestCode, int resultCode, Intent data) {
        return false;
    }






}
