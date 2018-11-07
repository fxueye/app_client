package com.zhaobaoge.buy.ui.search;

import com.zhaobaoge.mvp.presenter.ActivityPresenter;

/**
 * Created by shikewei on 2018/11/7.
 */

public class SearchActivity extends ActivityPresenter<SearchDelegate> {
    @Override
    protected Class<SearchDelegate> getDelegateClass() {
        return SearchDelegate.class;
    }
}
