package com.zhaobaoge.buy.ui.home;

import android.view.View;

import com.zhaobaoge.buy.R;
import com.zhaobaoge.mvp.presenter.FragmentPresenter;

/**
 * Created by shikewei on 2018/10/31.
 */

public class HomeFragment extends FragmentPresenter<HomeDelegate> implements View.OnClickListener {

    @Override
    protected void bindEvenListener() {
        viewDelegate.setOnClickListener(this, R.id.linear_today_deploy);
    }
    @Override
    protected Class<HomeDelegate> getDelegateClass() {
        return HomeDelegate.class;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.linear_today_deploy:

                break;
        }
    }
}
