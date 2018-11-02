package com.zhaobaoge.buy.ui.index;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.zhaobaoge.buy.R;
import com.zhaobaoge.buy.ui.center.CenterFragment;
import com.zhaobaoge.buy.ui.home.HomeFragment;
import com.zhaobaoge.buy.ui.sort.SortFragment;
import com.zhaobaoge.mvp.view.AppDelegate;
import com.zhaobaoge.widget.tab.TabsIndicator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shikewei on 2018/10/31.
 */

public class IndexDelegate extends AppDelegate {
    private TabsIndicator mTabsIndicator;
    private ViewPager mViewPager;

    @Override
    public int getRootLayoutId() {
        return R.layout.activity_index;
    }

    @Override
    public void initWidget() {
        super.initWidget();
        mTabsIndicator = get(R.id.index_tabsIndicator);
        AppCompatActivity activity = getActivity();
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new HomeFragment());
        fragments.add(new SortFragment());
        fragments.add(new CenterFragment());
        HomeAdapter homeAdapter = new HomeAdapter(activity.getSupportFragmentManager(), fragments);
        mViewPager = get(R.id.index_viewPager);
        mViewPager.setAdapter(homeAdapter);
        mViewPager.setOffscreenPageLimit(fragments.size());
        mTabsIndicator.setViewPager(mViewPager);
//        mTabsIndicator.setOnTabChangedListner(new TabsIndicator.OnTabChangedListner() {
//            @Override
//            public void onTabSelected(int tabNum) {
//                if (tabNum == 2) {
//                    getActivity().startActivity(new Intent(getActivity(), LoginActivity.class));
//                }
//            }
//        });
    }

    private class HomeAdapter extends FragmentPagerAdapter {
        private List<Fragment> mFragments = new ArrayList<>();

        public HomeAdapter(FragmentManager fm, List<Fragment> fragments) {
            super(fm);
            this.mFragments = fragments;
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }
    }


}
