package com.pranaymohapatra.kiwi.frameworks.basemvp;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.pranaymohapatra.kiwi.R;

import butterknife.ButterKnife;

public abstract class AppBaseActivity<T extends IPresenter> extends AppCompatActivity implements IActivityView {


    public static final String TAG = "KIWIAPP";
    public Toolbar mToolbar;
    T mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getViewToCreate());
        mPresenter = getPresenter();
        if (mPresenter != null) {
            mPresenter.attachView(this);
        }
    }

    public abstract int getViewToCreate();


    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        //Activity don't want to setContent View;
        if (layoutResID == -1) {
            return;
        }
        super.setContentView(layoutResID);
        ButterKnife.bind(this);
        if (mPresenter != null) {
            mPresenter.onViewCreated();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mPresenter != null) {
            mPresenter.onViewStarted();
        }
    }


    protected void onStop() {
        super.onStop();
        if (mPresenter != null) {
            mPresenter.detachView();
        }
    }

    @Override
    public void showProgressBar() {

    }

    @Override
    public void showProgressBar(String message) {

    }

    @Override
    public void hideProgressBar() {

    }

    @Override
    public void showSnackbar(final int mainTextStringId, final int actionStringId,
                              View.OnClickListener listener) {
        Snackbar.make(
                findViewById(android.R.id.content),
                getString(mainTextStringId),
                Snackbar.LENGTH_INDEFINITE)
                .setAction(getString(actionStringId), listener).show();
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public abstract T getPresenter();

    public void setmToolbar(int overflowIconID) {
        Toolbar toolbar = getmToolbar();
        toolbar.setOverflowIcon(ContextCompat.getDrawable(this,overflowIconID));
        setSupportActionBar(toolbar);
    }

    public void setmToolbar(boolean needBackButton) {
        Toolbar toolbar = getmToolbar();
        if (needBackButton && toolbar!=null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                mToolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_go_back_left_arrow, null));
            } else {
                mToolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_go_back_left_arrow));
            }
            mToolbar.setNavigationOnClickListener(v -> onBackPressed());
    }
            setSupportActionBar(toolbar);
    }

    public Toolbar getmToolbar(){
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        if (mToolbar != null) {
            return  mToolbar;
        }
        else return null;
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void setTitle(CharSequence title) {
        getSupportActionBar().setTitle(title);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        getPresenter().onActivityResult(requestCode,resultCode,data);
    }
}
