package com.pranaymohapatra.kiwi.frameworks.basemvp;

import android.content.Context;
import android.content.Intent;
import android.view.View;

public interface IView extends IBaseView {

    public void showProgressBar();

    public void showProgressBar(String message);

    public void hideProgressBar();

    public void showToast(String message);

    void showSnackbar(final int mainTextStringId, final int actionStringId,
                              View.OnClickListener listener);

    public void startActivity(Intent intent);

    public void startActivityForResult(Intent intent, int requestcode);

    public void setResult(int result);

    public void setResult(int result, Intent data);

    Context getContext();

    void finish();

}
