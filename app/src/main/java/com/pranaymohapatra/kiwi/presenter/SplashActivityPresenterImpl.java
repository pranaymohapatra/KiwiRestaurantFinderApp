package com.pranaymohapatra.kiwi.presenter;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.SettingsClient;
import com.pranaymohapatra.kiwi.BuildConfig;
import com.pranaymohapatra.kiwi.R;
import com.pranaymohapatra.kiwi.frameworks.basemvp.AppBasePresenter;
import com.pranaymohapatra.kiwi.model.RestaurantFinderAPI;
import com.pranaymohapatra.kiwi.model.RetrofitClient;
import com.pranaymohapatra.kiwi.model.location.LocationModel;
import com.pranaymohapatra.kiwi.view.Splash;

import java.text.DateFormat;
import java.util.Date;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class SplashActivityPresenterImpl
        extends AppBasePresenter<ISplashActivityMVP.ISplashActivityView>
        implements ISplashActivityMVP.ISplashActivityPresenter {

    private static final String TAG = Splash.class.getSimpleName();
    private static final int REQUEST_PERMISSIONS_REQUEST_CODE = 34;
    private static final int REQUEST_CHECK_SETTINGS = 0x1;
    private static final long UPDATE_INTERVAL_IN_MILLISECONDS = 10000;
    private static final long FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS =
            UPDATE_INTERVAL_IN_MILLISECONDS / 2;
    // Keys for storing activity state in the Bundle.
    private final static String KEY_REQUESTING_LOCATION_UPDATES = "requesting-location-updates";
    private final static String KEY_LOCATION = "location";
    private final static String KEY_LAST_UPDATED_TIME_STRING = "last-updated-time-string";
    Observable<LocationModel> locationModelObservable;
    RestaurantFinderAPI locationAPI;
    Disposable locationDisposable;
    ISplashActivityMVP.ISplashActivityView splashActivityView;
    private int CITY_ID;
    private FusedLocationProviderClient mFusedLocationClient;
    private SettingsClient mSettingsClient;
    private LocationRequest mLocationRequest;
    private LocationSettingsRequest mLocationSettingsRequest;
    private LocationCallback mLocationCallback;
    private Location mCurrentLocation;
    private boolean mRequestingLocationUpdates;
    private String mLastUpdateTime;

    private void createLocationCallback() {
        mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
                mCurrentLocation = locationResult.getLastLocation();
                mLastUpdateTime = DateFormat.getTimeInstance().format(new Date());
                getLocation(mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude());
            }
        };
    }


    private void createLocationRequest() {

        mLocationRequest = new LocationRequest();

        mLocationRequest.setInterval(UPDATE_INTERVAL_IN_MILLISECONDS);

        mLocationRequest.setFastestInterval(FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS);

        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
    }

    private void buildLocationSettingsRequest() {
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(mLocationRequest);
        mLocationSettingsRequest = builder.build();
    }

    @Override
    public Intent getSettingsIntent() {
        return null;
    }

    @Override
    public void updateValuesFromBundle(Bundle savedInstanceState) {
        if (savedInstanceState != null) {

            if (savedInstanceState.keySet().contains(KEY_REQUESTING_LOCATION_UPDATES)) {
                mRequestingLocationUpdates = savedInstanceState.getBoolean(
                        KEY_REQUESTING_LOCATION_UPDATES);
            }

            if (savedInstanceState.keySet().contains(KEY_LOCATION)) {
                mCurrentLocation = savedInstanceState.getParcelable(KEY_LOCATION);
            }

            if (savedInstanceState.keySet().contains(KEY_LAST_UPDATED_TIME_STRING)) {
                mLastUpdateTime = savedInstanceState.getString(KEY_LAST_UPDATED_TIME_STRING);
            }
        }
    }

    private void startLocationUpdates() {
        // Begin by checking if the device has the necessary location settings.
        mSettingsClient.checkLocationSettings(mLocationSettingsRequest)
                .addOnSuccessListener((Splash) splashActivityView.getContext(), locationSettingsResponse -> {
                    Log.i(TAG, "All location settings are satisfied.");

                    //noinspection MissingPermission
                    mFusedLocationClient.requestLocationUpdates(mLocationRequest,
                            mLocationCallback, Looper.myLooper());
                })
                .addOnFailureListener((Splash) splashActivityView.getContext(), e -> {
                    int statusCode = ((ApiException) e).getStatusCode();
                    switch (statusCode) {
                        case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                            Log.i(TAG, "Location settings are not satisfied. Attempting to upgrade " +
                                    "location settings ");
                            try {
                                // Show the dialog by calling startResolutionForResult(), and check the
                                // result in onActivityResult().
                                ResolvableApiException rae = (ResolvableApiException) e;
                                rae.startResolutionForResult((Splash) splashActivityView.getContext(), REQUEST_CHECK_SETTINGS);
                            } catch (IntentSender.SendIntentException sie) {
                                Log.i(TAG, "PendingIntent unable to execute request.");
                            }
                            break;
                        case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                            String errorMessage = "Location settings are inadequate, and cannot be " +
                                    "fixed here. Fix in Settings.";
                            Log.e(TAG, errorMessage);
                            Toast.makeText(splashActivityView.getContext(), errorMessage, Toast.LENGTH_LONG).show();
                            mRequestingLocationUpdates = false;
                    }

                });
    }

    private boolean checkPermissions() {
        int permissionState = ActivityCompat.checkSelfPermission(splashActivityView.getContext(),
                Manifest.permission.ACCESS_FINE_LOCATION);
        return permissionState == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermissions() {
        boolean shouldProvideRationale =
                ActivityCompat.shouldShowRequestPermissionRationale((Splash) splashActivityView.getContext(),
                        Manifest.permission.ACCESS_FINE_LOCATION);

        if (shouldProvideRationale) {
            Log.i(TAG, "Displaying permission rationale to provide additional context.");
            splashActivityView.showSnackbar(R.string.permission_rationale,
                    android.R.string.ok, view -> {
                        // Request permission
                        ActivityCompat.requestPermissions((Splash) splashActivityView.getContext(),
                                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                REQUEST_PERMISSIONS_REQUEST_CODE);
                    });
        } else {
            Log.i(TAG, "Requesting permission");
            ActivityCompat.requestPermissions((Splash) splashActivityView.getContext(),
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_PERMISSIONS_REQUEST_CODE);
        }
    }

    public void getLocation(double lat, double lon) {
        Log.d(TAG, "getLocationCalled");
        locationModelObservable = locationAPI.getLocation(lat, lon);
        locationModelObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<LocationModel>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        locationDisposable = d;
                    }

                    @Override
                    public void onNext(LocationModel value) {
                        CITY_ID = value.getLocationSuggestions().get(0).getId();
                        splashActivityView.hideProgressBar();
                    }

                    @Override
                    public void onError(Throwable e) {
                        splashActivityView.showToast("Error in getting location. Check your Internet");
                        locationDisposable.dispose();
                        splashActivityView.setResultAndFinish(CITY_ID, Activity.RESULT_CANCELED);
                    }

                    @Override
                    public void onComplete() {
                        splashActivityView.setResultAndFinish(CITY_ID, Activity.RESULT_OK);
                    }
                });
    }

    @Override
    public Bundle saveInstanceState(Bundle outState) {
        outState.putBoolean(KEY_REQUESTING_LOCATION_UPDATES, mRequestingLocationUpdates);
        outState.putParcelable(KEY_LOCATION, mCurrentLocation);
        outState.putString(KEY_LAST_UPDATED_TIME_STRING, mLastUpdateTime);
        return outState;
    }

    @Override
    public void viewResumed() {
        if (mRequestingLocationUpdates && checkPermissions()) {
            startLocationUpdates();
        } else if (!checkPermissions()) {
            requestPermissions();
        }
    }

    @Override
    public void locationPermissionGranted() {
        if (mRequestingLocationUpdates) {
            Log.i(TAG, "Permission granted, updates requested, starting location updates");
            startLocationUpdates();
        }
    }

    @Override
    public void locationPermissionDenied() {
        splashActivityView.showSnackbar(R.string.permission_denied_explanation,
                R.string.settings, view -> {
                    // Build intent that displays the App settings screen.
                    Intent intent = new Intent();
                    intent.setAction(
                            Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    Uri uri = Uri.fromParts("package",
                            BuildConfig.APPLICATION_ID, null);
                    intent.setData(uri);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    splashActivityView.startActivity(intent);
                });
    }

    @Override
    public void attachView(ISplashActivityMVP.ISplashActivityView view) {
        super.attachView(view);
        this.splashActivityView = view;
        mRequestingLocationUpdates = true;
        locationAPI = RetrofitClient.getRetrofitClientAPI();
        mLastUpdateTime = "";
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(splashActivityView.getContext());
        mSettingsClient = LocationServices.getSettingsClient(splashActivityView.getContext());
        createLocationCallback();
        createLocationRequest();
        buildLocationSettingsRequest();
    }

    @Override
    public void detachView() {
        if (!locationDisposable.isDisposed())
            locationDisposable.dispose();
        mFusedLocationClient.removeLocationUpdates(mLocationCallback)
                .addOnCompleteListener((Splash) splashActivityView.getContext(), task -> mRequestingLocationUpdates = false);
        mFusedLocationClient = null;
        mSettingsClient = null;
        super.detachView();
    }
}
