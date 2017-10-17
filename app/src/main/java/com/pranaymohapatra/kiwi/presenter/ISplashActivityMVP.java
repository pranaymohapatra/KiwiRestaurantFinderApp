package com.pranaymohapatra.kiwi.presenter;

import android.content.Intent;
import android.os.Bundle;

import com.pranaymohapatra.kiwi.frameworks.basemvp.IActivityView;
import com.pranaymohapatra.kiwi.frameworks.basemvp.IPresenter;

public interface ISplashActivityMVP {

    interface ISplashActivityPresenter extends IPresenter<ISplashActivityView> {

        Intent getSettingsIntent();

        void updateValuesFromBundle(Bundle savedState);

        Bundle saveInstanceState(Bundle outState);

        void viewResumed();

        void locationPermissionGranted();

        void locationPermissionDenied();


    }

    interface ISplashActivityView extends IActivityView {

        void setResultAndFinish(int id, int result);


    }

}
