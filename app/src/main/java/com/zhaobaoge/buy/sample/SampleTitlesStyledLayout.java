package com.zhaobaoge.buy.sample;

import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.zhaobaoge.viewpagerindicator.TitlePageIndicator;
import com.zhaobaoge.buy.R;


public class SampleTitlesStyledLayout extends BaseSampleActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.themed_titles);

        mAdapter = new TestFragmentAdapter(getSupportFragmentManager());

        mPager = (ViewPager)findViewById(R.id.pager);
        mPager.setAdapter(mAdapter);

        mIndicator = (TitlePageIndicator)findViewById(R.id.indicator);
        mIndicator.setViewPager(mPager);
    }
}