package com.pranaymohapatra.kiwi.presenter;

import com.pranaymohapatra.kiwi.frameworks.basemvp.IActivityView;
import com.pranaymohapatra.kiwi.frameworks.basemvp.IPresenter;
import com.pranaymohapatra.kiwi.model.FavouriteRestaurantModel;

import java.util.ArrayList;

public interface IFavouritesActivityMVP {

    interface IFavouritesPresenter extends IPresenter<IFavouritesView> {

    }

    interface IFavouritesView extends IActivityView{

        void setTextViewVisibility(int visibility);
        void setRecyclerViewVisibiltiy(int visibiltiy);
        void setRecyclerData(ArrayList<FavouriteRestaurantModel> data);

    }
}
