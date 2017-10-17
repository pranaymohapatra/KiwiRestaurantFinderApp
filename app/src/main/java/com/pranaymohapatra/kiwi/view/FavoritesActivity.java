package com.pranaymohapatra.kiwi.view;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.pranaymohapatra.kiwi.R;
import com.pranaymohapatra.kiwi.frameworks.basemvp.AppBaseActivity;
import com.pranaymohapatra.kiwi.model.FavouriteRestaurantModel;
import com.pranaymohapatra.kiwi.model.restaurants.FavouriteViewAdapter;
import com.pranaymohapatra.kiwi.presenter.FavouritesActivityPresenterImpl;
import com.pranaymohapatra.kiwi.presenter.IFavouritesActivityMVP;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class FavoritesActivity extends AppBaseActivity<IFavouritesActivityMVP.IFavouritesPresenter>
        implements IFavouritesActivityMVP.IFavouritesView {

    ArrayList<FavouriteRestaurantModel> favoriteList = new ArrayList<>();
    @BindView(R.id.favprogbar)
    ProgressBar progressBar;
    @BindView(R.id.favouriterecycler)
    RecyclerView favoriteRecyclerView;
    @BindView(R.id.nofavourites)
    TextView nofavourites;
    private FavouriteViewAdapter favoriteAdapter;
    IFavouritesActivityMVP.IFavouritesPresenter favouritesPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        favouritesPresenter = new FavouritesActivityPresenterImpl();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites_actvity);
        ButterKnife.bind(this);
        setmToolbar(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(FavoritesActivity.this, LinearLayoutManager.VERTICAL, false);
        favoriteRecyclerView.setLayoutManager(layoutManager);
        favoriteAdapter = new FavouriteViewAdapter(FavoritesActivity.this, null);
        favoriteRecyclerView.setAdapter(favoriteAdapter);
    }

    @Override
    public int getViewToCreate() {
        return R.layout.activity_favorites_actvity;
    }

    @Override
    public IFavouritesActivityMVP.IFavouritesPresenter getPresenter() {
        return favouritesPresenter;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void hideProgressBar() {
        progressBar.setVisibility(View.INVISIBLE);
        super.hideProgressBar();
    }

    @Override
    public void setTextViewVisibility(int visibility) {
        nofavourites.setVisibility(visibility);
    }

    @Override
    public void setRecyclerViewVisibiltiy(int visibiltiy) {
        favoriteRecyclerView.setVisibility(visibiltiy);
    }

    @Override
    public void setRecyclerData(ArrayList<FavouriteRestaurantModel> data) {
        favoriteAdapter.setData(data);
    }
}
