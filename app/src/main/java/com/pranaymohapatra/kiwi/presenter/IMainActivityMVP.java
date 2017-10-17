package com.pranaymohapatra.kiwi.presenter;

import com.pranaymohapatra.kiwi.frameworks.basemvp.IActivityView;
import com.pranaymohapatra.kiwi.frameworks.basemvp.IPresenter;

import java.util.List;

public interface IMainActivityMVP {
    interface IMainActivityView extends IActivityView {

        void setTypingTextVisiblity(int visibility);

        void setRecyclerViewVisiblity(int visiblity);

        void setRestaurantList(List<Object> restaurentList);

    }

    interface IMainActivityPresenter extends IPresenter<IMainActivityView>{

        public void onFavoriteClick();
        public void onSortByRatingClick();
        public void onSortByPrice();
        public void onSearchChange(String text);

    }
}
