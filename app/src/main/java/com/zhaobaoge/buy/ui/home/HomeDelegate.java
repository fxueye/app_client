package com.zhaobaoge.buy.ui.home;

import android.support.v4.app.Fragment;

import com.zhaobaoge.buy.R;
import com.zhaobaoge.buy.TextFragment;
import com.zhaobaoge.mvp.view.AppDelegate;
import com.zhaobaoge.widget.HackyViewPager;
import com.zhaobaoge.widget.tab.PagerSlidingTabStrip;

import java.util.List;

/**
 * Created by shikewei on 2018/10/31.
 */

public class HomeDelegate extends AppDelegate {
    private HackyViewPager mViewPager;
    private PagerSlidingTabStrip mTabs;
    private List<Fragment> mFragments;
    @Override
    public int getRootLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    public void initWidget() {
        mTabs = get(R.id.psts_tabs);
        mViewPager = get(R.id.viewpager);

        mFragments.add(TextFragment.newInstance(""));

    }
}
