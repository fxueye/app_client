package com.zhaobaoge.buy.ui.center;

import android.util.Log;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.zhaobaoge.buy.MainDelegate;
import com.zhaobaoge.buy.R;
import com.zhaobaoge.mvp.view.AppDelegate;
import com.zhaobaoge.widget.NotifyingScrollView;

/**
 * Created by shikewei on 2018/10/31.
 */

public class CenterDelegate extends AppDelegate {
    private static final String TAG = "CenterDelegate";
    private NotifyingScrollView notifyingScrollView;
    private LinearLayout linearLayout;

    @Override
    public int getRootLayoutId() {
        return R.layout.fragment_center;
    }

    @Override
    public void initWidget() {
        notifyingScrollView = get(R.id.scroll_personal);
        linearLayout = get(R.id.linear_personal_title_base);
        notifyingScrollView.setOnScrollChangedListener(new NotifyingScrollView.OnScrollChangedListener() {
            @Override
            public void OnScrollChanged(ScrollView who, int l, int t, int oldl, int oldt) {
                float ratio = (float) Math.min(Math.max(t, 0), linearLayout.getHeight()) / (float) linearLayout.getHeight();
                linearLayout.setAlpha(ratio);
            }
        });
    }
}
