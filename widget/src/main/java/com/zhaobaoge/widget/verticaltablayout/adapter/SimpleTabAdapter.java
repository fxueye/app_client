package com.zhaobaoge.widget.verticaltablayout.adapter;


import com.zhaobaoge.widget.verticaltablayout.widget.TabView;


public abstract class SimpleTabAdapter implements TabAdapter {
    @Override
    public abstract int getCount();

    @Override
    public TabView.TabIcon getIcon(int position) {
        return null;
    }

    @Override
    public TabView.TabTitle getTitle(int position) {
        return null;
    }

    @Override
    public int getBackground(int position) {
        return 0;
    }
}
