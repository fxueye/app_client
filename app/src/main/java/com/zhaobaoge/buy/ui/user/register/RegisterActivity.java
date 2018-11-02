package com.zhaobaoge.buy.ui.user.register;

import android.content.Intent;
import android.view.View;

import com.zhaobaoge.buy.R;
import com.zhaobaoge.buy.ui.user.login.LoginActivity;
import com.zhaobaoge.mvp.presenter.ActivityPresenter;

/**
 * Created by shikewei on 2018/11/2.
 */

public class RegisterActivity extends ActivityPresenter<RegisterDelegate> implements View.OnClickListener {
    @Override
    protected void bindEvenListener() {
        viewDelegate.setOnClickListener(this, R.id.linear_left_back,R.id.linear_register_link_to_login);
    }

    @Override
    protected Class<RegisterDelegate> getDelegateClass() {
        return RegisterDelegate.class;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.linear_left_back:
                finish();
                break;
            case R.id.linear_register_link_to_login:
                finish();
                startActivity(new Intent(this, LoginActivity.class));
                break;
        }
    }
}
