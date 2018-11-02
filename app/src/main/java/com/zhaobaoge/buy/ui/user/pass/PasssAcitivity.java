package com.zhaobaoge.buy.ui.user.pass;

import android.view.View;
import android.view.View.OnClickListener;

import com.zhaobaoge.buy.R;
import com.zhaobaoge.mvp.presenter.ActivityPresenter;

/**
 * Created by shikewei on 2018/11/2.
 */

public class PasssAcitivity extends ActivityPresenter<PassDelegate> implements OnClickListener {
    protected void bindEvenListener() {
        viewDelegate.setOnClickListener(this, R.id.linear_left_back);
    }

    @Override
    protected Class<PassDelegate> getDelegateClass() {
        return PassDelegate.class;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.linear_left_back:
                finish();
                break;
        }
    }
}
