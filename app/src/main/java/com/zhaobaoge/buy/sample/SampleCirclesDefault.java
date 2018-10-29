package com.zhaobaoge.buy.sample;

import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.zhaobaoge.viewpagerindicator.CirclePageIndicator;
import com.zhaobaoge.buy.R;


public class SampleCirclesDefault extends BaseSampleActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.simple_circles);

        mAdapter = new TestFragmentAdapter(getSupportFragmentManager());

        mPager = (ViewPager)findViewById(R.id.pager);
        mPager.setAdapter(mAdapter);

        mIndicator = (CirclePageIndicator)findViewById(R.id.indicator);
        mIndicator.setViewPager(mPager);
    }
}