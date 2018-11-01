package com.zhaobaoge.buy.ui.center;

import android.content.Intent;
import android.view.View;

import com.zhaobaoge.buy.R;
import com.zhaobaoge.buy.ui.user.login.LoginActivity;
import com.zhaobaoge.mvp.presenter.FragmentPresenter;

/**
 * Created by shikewei on 2018/10/31.
 */

public class CenterFragment extends FragmentPresenter<CenterDelegate> implements View.OnClickListener {
    @Override
    protected void bindEvenListener() {
        viewDelegate.setOnClickListener(this, R.id.button);
    }

    @Override
    protected Class<CenterDelegate> getDelegateClass() {
        return CenterDelegate.class;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button:
                startActivity(new Intent(getActivity(), LoginActivity.class));
                break;
        }
    }
}
