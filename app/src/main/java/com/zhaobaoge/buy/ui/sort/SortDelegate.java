package com.zhaobaoge.buy.ui.sort;


import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zhaobaoge.buy.R;
import com.zhaobaoge.mvp.view.AppDelegate;
import com.zhaobaoge.widget.verticaltablayout.VerticalTabLayout;
import com.zhaobaoge.widget.verticaltablayout.adapter.TabAdapter;
import com.zhaobaoge.widget.verticaltablayout.widget.TabView;
import com.zhaobaoge.widget.verticalviewpager.DefaultTransformer;
import com.zhaobaoge.widget.verticalviewpager.VerticalViewPager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by shikewei on 2018/10/31.
 */

public class SortDelegate extends AppDelegate {



    @Override
    public int getRootLayoutId() {
        return R.layout.fragment_sort;
    }

    @Override
    public void initWidget() {

        VerticalTabLayout tabs = get(R.id.tab_layout_category_level1);
        VerticalViewPager viewPager = get(R.id.vertical_viewpager_category_level1);
        viewPager.setPageTransformer(true, new DefaultTransformer());
        viewPager.setOverScrollMode(View.OVER_SCROLL_NEVER);
        viewPager.setAdapter(new MyPagerAdapter());
        tabs.setViewPager(viewPager);


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
