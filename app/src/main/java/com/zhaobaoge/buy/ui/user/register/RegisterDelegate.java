package com.zhaobaoge.buy.ui.user.register;

import android.widget.TextView;

import com.zhaobaoge.buy.R;
import com.zhaobaoge.mvp.view.AppDelegate;

/**
 * Created by shikewei on 2018/11/2.
 */

public class RegisterDelegate extends AppDelegate {

    @Override
    public void initWidget() {

        TextView textView = get(R.id.tv_top_title);
        textView.setText(R.string.register);
    }
    @Override
    public int getRootLayoutId() {
        return R.layout.activity_register;
    }
}
