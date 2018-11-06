package com.zhaobaoge.buy.ui.sort;


import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.zhaobaoge.buy.R;
import com.zhaobaoge.buy.TextFragment;
import com.zhaobaoge.mvp.view.AppDelegate;
import com.zhaobaoge.widget.tab.PagerSlidingTabStrip;
import com.zhaobaoge.widget.tab.VerticalTabLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shikewei on 2018/10/31.
 */

public class SortDelegate extends AppDelegate {
    List<Fragment> fragments;

    @Override
    public int getRootLayoutId() {
        return R.layout.fragment_sort;
    }

    @Override
    public void initWidget() {
        fragments = new ArrayList<>();
        fragments.add(TextFragment.newInstance("1"));
        fragments.add(TextFragment.newInstance("2"));
        fragments.add(TextFragment.newInstance("3"));
        fragments.add(TextFragment.newInstance("4"));
        fragments.add(TextFragment.newInstance("5"));
        VerticalTabLayout tabs =  get(R.id.psts_tabs);
        ViewPager viewPager =  get(R.id.vp_center);
        AppCompatActivity activity = getActivity();
        viewPager.setAdapter(new ContentAdapter(activity.getSupportFragmentManager()));
        tabs.setViewPager(viewPager);



    }

    private class ContentAdapter extends FragmentPagerAdapter {

        public ContentAdapter(FragmentManager fm) {
            super(fm);
        }

        public String[] titles = new String[]{"1", "2", "3", "4", "5"};

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }
    }

}
