package com.zhaobaoge.buy.ui.index;

import android.view.View;

import com.zhaobaoge.mvp.presenter.ActivityPresenter;

/**
 * Created by shikewei on 2018/10/31.
 */

public class IndexActivity extends ActivityPresenter<IndexDelegate> implements View.OnClickListener {
    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();

    }

    @Override
    public void onClick(View view) {

    }

    @Override
    protected Class<IndexDelegate> getDelegateClass() {
        return IndexDelegate.class;
    }
}
