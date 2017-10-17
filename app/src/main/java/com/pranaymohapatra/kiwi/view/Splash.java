package com.pranaymohapatra.kiwi.view;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.pranaymohapatra.kiwi.R;
import com.pranaymohapatra.kiwi.frameworks.basemvp.AppBaseActivity;
import com.pranaymohapatra.kiwi.presenter.ISplashActivityMVP;
import com.pranaymohapatra.kiwi.presenter.SplashActivityPresenterImpl;

import butterknife.BindView;

public class Splash
        extends AppBaseActivity<ISplashActivityMVP.ISplashActivityPresenter>
        implements ISplashActivityMVP.ISplashActivityView {

    private static final String TAG = Splash.class.getSimpleName();

    private static final int REQUEST_PERMISSIONS_REQUEST_CODE = 34;

    @BindView(R.id.splashprogbar)
    ProgressBar progressBar;
    ISplashActivityMVP.ISplashActivityPresenter splashPresenter;
    private Location mCurrentLocation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        splashPresenter = new SplashActivityPresenterImpl();
        super.onCreate(savedInstanceState);

        getPresenter().updateValuesFromBundle(savedInstanceState);

        // Kick off the process of building the LocationCallback, LocationRequest, and
        // LocationSettingsRequest objects.
    }

    @Override
    public int getViewToCreate() {
        return R.layout.activity_splash;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.i(TAG, "onRequestPermissionResult");
        if (requestCode == REQUEST_PERMISSIONS_REQUEST_CODE) {
            if (grantResults.length <= 0) {
                Log.i(TAG, "User interaction was cancelled.");
            } else if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getPresenter().locationPermissionGranted();
            } else {
                // Permission denied.
                getPresenter().locationPermissionDenied();
            }
        }
    }


    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public void setResultAndFinish(int id, int result) {
        Intent resultIntent = new Intent();
        resultIntent.putExtra("CITY_ID", id);
        switch (result) {
            case Activity.RESULT_OK:
                setResult(RESULT_OK, resultIntent);
                break;
            case Activity.RESULT_CANCELED:
                setResult(Activity.RESULT_CANCELED, resultIntent);
        }
        this.finish();
    }

    @Override
    protected void onResume() {
        getPresenter().viewResumed();
        super.onResume();
    }

    @Override
    public ISplashActivityMVP.ISplashActivityPresenter getPresenter() {
        return splashPresenter;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState = getPresenter().saveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }


    @Override
    public void hideProgressBar() {
        progressBar.setVisibility(View.INVISIBLE);
        super.hideProgressBar();
    }
}
