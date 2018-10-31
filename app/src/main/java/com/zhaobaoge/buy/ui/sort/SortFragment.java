package com.zhaobaoge.buy.ui.sort;

import com.zhaobaoge.mvp.presenter.FragmentPresenter;

/**
 * Created by shikewei on 2018/10/31.
 */

public class SortFragment extends FragmentPresenter<SortDelegate> {
    @Override
    protected Class<SortDelegate> getDelegateClass() {
        return SortDelegate.class;
    }
}
