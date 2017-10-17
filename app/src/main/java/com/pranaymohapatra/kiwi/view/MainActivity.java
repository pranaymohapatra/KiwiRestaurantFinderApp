package com.pranaymohapatra.kiwi.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.jakewharton.rxbinding2.widget.RxTextView;
import com.jakewharton.rxbinding2.widget.TextViewTextChangeEvent;
import com.pranaymohapatra.kiwi.R;
import com.pranaymohapatra.kiwi.frameworks.basemvp.AppBaseActivity;
import com.pranaymohapatra.kiwi.model.RestaurantViewAdapter;
import com.pranaymohapatra.kiwi.presenter.IMainActivityMVP;
import com.pranaymohapatra.kiwi.presenter.MainActivityPresenterImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;


public class MainActivity extends
        AppBaseActivity<IMainActivityMVP.IMainActivityPresenter>
        implements IMainActivityMVP.IMainActivityView {

    private static String TAG = MainActivity.class.getSimpleName();


    Disposable searchDisposable;
    RestaurantViewAdapter viewAdapter;
    RecyclerView.LayoutManager mLayoutManager;

    @BindView(R.id.searchbox)
    EditText searchbox;

    IMainActivityMVP.IMainActivityPresenter presenter;
    @BindView(R.id.textview)
    TextView textView;
    @BindView(R.id.restaurantview)
    RecyclerView restaurantRecycler;
    @BindView(R.id.loadicator)
    ProgressBar loadicator;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        presenter = new MainActivityPresenterImpl();
        super.onCreate(savedInstanceState);
        setmToolbar(R.drawable.ic_sort_down);
        startActivityForResult(new Intent(this, Splash.class), 1001, null);
        mLayoutManager = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.VERTICAL, false);
        viewAdapter = new RestaurantViewAdapter(MainActivity.this, null);
        restaurantRecycler.setAdapter(viewAdapter);
        restaurantRecycler.setLayoutManager(mLayoutManager);


        searchDisposable =
                RxTextView.textChangeEvents(searchbox)
                        .debounce(500, TimeUnit.MILLISECONDS) // default Scheduler is Computation
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(getSearchObserver());
    }

    @Override
    public int getViewToCreate() {
        return R.layout.activity_main;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.favorites:
                getPresenter().onFavoriteClick();

                break;
            case R.id.sortbyrating:
                getPresenter().onSortByRatingClick();

                break;
            case R.id.sortbyprice:
                getPresenter().onSortByPrice();
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public IMainActivityMVP.IMainActivityPresenter getPresenter() {
        return presenter;
    }

    private DisposableObserver<TextViewTextChangeEvent> getSearchObserver() {
        return new DisposableObserver<TextViewTextChangeEvent>() {
            @Override
            public void onComplete() {

            }

            @Override
            public void onError(Throwable e) {
                Toast.makeText(MainActivity.this, "Error in searching", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNext(TextViewTextChangeEvent onTextChangeEvent) {
                getPresenter().onSearchChange(onTextChangeEvent.text().toString());
            }
        };
    }


    @Override
    public void setTypingTextVisiblity(int visibility) {
        textView.setVisibility(visibility);
    }

    @Override
    public void setRecyclerViewVisiblity(int visiblity) {
        restaurantRecycler.setVisibility(visiblity);
    }

    @Override
    public void setRestaurantList(List<Object> restaurentList) {
        viewAdapter.setData((ArrayList<Object>) restaurentList);
    }

    @Override
    public void showProgressBar() {
        loadicator.setVisibility(View.VISIBLE);
        super.showProgressBar();
    }

    @Override
    public void hideProgressBar() {
        loadicator.setVisibility(View.INVISIBLE);
        super.hideProgressBar();
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(this,message,Toast.LENGTH_SHORT);
        super.showToast(message);
    }
}
