package com.zhaobaoge.buy.ui.user.login;

import android.view.View;

import com.zhaobaoge.buy.R;
import com.zhaobaoge.mvp.presenter.ActivityPresenter;

/**
 * Created by shikewei on 2018/11/1.
 */

public class LoginActivity extends ActivityPresenter<LoginDelegate> implements View.OnClickListener {

    @Override
    protected void bindEvenListener() {
        viewDelegate.setOnClickListener(this,
                R.id.linear_left_back,
                R.id.tv_login_type_switch,
                R.id.tv_login_forget,
                R.id.linear_login_link_to_register);
    }

    @Override
    protected Class<LoginDelegate> getDelegateClass() {
        return LoginDelegate.class;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.linear_left_back:
                finish();
                break;
            case R.id.tv_login_type_switch:
                viewDelegate.Switch();
                break;
            case R.id.tv_login_forget:

                break;
        }
    }

}
