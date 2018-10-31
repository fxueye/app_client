package com.zhaobaoge.buy.ui.home;

import com.zhaobaoge.mvp.presenter.FragmentPresenter;

/**
 * Created by shikewei on 2018/10/31.
 */

public class HomeFragment extends FragmentPresenter<HomeDelegate> {
    @Override
    protected Class<HomeDelegate> getDelegateClass() {
        return HomeDelegate.class;
    }
}
