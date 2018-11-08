package com.zhaobaoge.widget.tablayout.iter;

/**
 * Created by shikewei on 2018/11/8.
 */

public interface ITabLayout {
    public void addOnTabSelectedListener(OnTabSelectedListener listener);

    public void removeOnTabSelectedListener(OnTabSelectedListener listener);

    public int getSelectedTabPosition();

}
