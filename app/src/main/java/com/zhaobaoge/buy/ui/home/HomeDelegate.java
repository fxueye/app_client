package com.zhaobaoge.buy.ui.home;

import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zhaobaoge.buy.R;
import com.zhaobaoge.mvp.view.AppDelegate;
import com.zhaobaoge.widget.HackyViewPager;
import com.zhaobaoge.widget.tab.TabScrollView;
import com.zhaobaoge.widget.tablayout.HorizontalTabLayout;
import com.zhaobaoge.widget.tablayout.adapter.TabAdapter;
import com.zhaobaoge.widget.tablayout.tabs.TabView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by shikewei on 2018/10/31.
 */

public class HomeDelegate extends AppDelegate {
    private HackyViewPager mViewPager;
    private HorizontalTabLayout mTabs;

    @Override
    public int getRootLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    public void initWidget() {
        mTabs = get(R.id.tsv_tabs);
        mViewPager = get(R.id.viewpager);
        mViewPager.setAdapter(new MyPagerAdapter());
        mTabs.setViewPager(mViewPager);


    }

    private class MyPagerAdapter extends PagerAdapter implements TabAdapter {
        List<String> titles;

        public MyPagerAdapter() {
            titles = new ArrayList<>();
            Collections.addAll(titles, "Android", "IOS", "Web", "JAVA", "C++", ".NET", "JavaScript", "Swift", "PHP", "Python", "C#", "Groovy", "SQL", "Ruby");
        }

        @Override
        public int getCount() {
            return titles.size();
        }

        @Override
        public TabView.TabIcon getIcon(int position) {
            return null;
        }

        @Override
        public TabView.TabTitle getTitle(int position) {

            return new TabView.TabTitle.Builder()
                    .setContent(titles.get(position))
                    .setTextColor(Color.RED, Color.BLACK)
                    .build();
        }
        @Override
        public int getBackground(int position) {
            return -1;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles.get(position);
        }
        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            TextView tv = new TextView(getActivity());
            tv.setTextColor(Color.BLACK);
            tv.setGravity(Gravity.CENTER);
            tv.setText(titles.get(position));
            tv.setTextSize(18);
            container.addView(tv);
            return tv;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
}
