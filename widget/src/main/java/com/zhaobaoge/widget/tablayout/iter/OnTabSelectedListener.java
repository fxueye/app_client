package com.zhaobaoge.widget.tablayout.iter;

import com.zhaobaoge.widget.tablayout.tabs.TabView;

/**
 * Created by shikewei on 2018/11/8.
 */

public interface OnTabSelectedListener {

    void onTabSelected(TabView tab, int position);

    void onTabReselected(TabView tab, int position);
}