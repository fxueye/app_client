package com.zhaobaoge.widget.verticaltablayout.adapter;


import com.zhaobaoge.widget.verticaltablayout.widget.TabView;

public interface TabAdapter {
    int getCount();

    TabView.TabIcon getIcon(int position);

    TabView.TabTitle getTitle(int position);

    int getBackground(int position);
}
