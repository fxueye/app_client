package com.zhaobaoge.buy.ui.center;

import com.zhaobaoge.mvp.presenter.FragmentPresenter;

/**
 * Created by shikewei on 2018/10/31.
 */

public class CenterFragment extends FragmentPresenter<CenterDelegate> {
    @Override
    protected Class<CenterDelegate> getDelegateClass() {
        return CenterDelegate.class;
    }
}
