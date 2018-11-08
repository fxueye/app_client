package com.zhaobaoge.widget.tablayout.adapter;


import com.zhaobaoge.widget.tablayout.tabs.TabView;

public interface TabAdapter {
    int getCount();

    TabView.TabIcon getIcon(int position);

    TabView.TabTitle getTitle(int position);

    int getBackground(int position);
}
