package com.zhaobaoge.buy;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.zhangebaoge.viewpagerindicator.IconPagerAdapter;
import com.zhangebaoge.viewpagerindicator.TabPageIndicator;
import com.zhaobaoge.mvp.view.AppDelegate;

/**
 * Created by shikewei on 2018/10/29.
 */

public class MainDelegate extends AppDelegate {
    private static final String[] CONTENT = new String[] { "Calendar", "Camera", "Alarms", "Location" };
    private static final int[] ICONS = new int[] {
            R.drawable.perm_group_calendar,
            R.drawable.perm_group_camera,
            R.drawable.perm_group_device_alarms,
            R.drawable.perm_group_location,
    };

    @Override
    public int getRootLayoutId() {
        return R.layout.activity_main;
    }
    @Override
    public void initWidget(){
        super.initWidget();
        TextView textView = get(R.id.tv);
        textView.setText("测试");
        AppCompatActivity activity =  this.getActivity();
        FragmentPagerAdapter adapter = new GoogleMusicAdapter(activity.getSupportFragmentManager());
        ViewPager pager = get(R.id.pager);
        pager.setAdapter(adapter);
        TabPageIndicator indicator = get(R.id.indicator);
        indicator.setViewPager(pager);
    }

    public  void setText( String text){
        TextView textView = get(R.id.tv);
        textView.setText(text);
    }
    class GoogleMusicAdapter extends FragmentPagerAdapter implements IconPagerAdapter {
        public GoogleMusicAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return TestFragment.newInstance(CONTENT[position % CONTENT.length]);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return CONTENT[position % CONTENT.length].toUpperCase();
        }

        @Override public int getIconResId(int index) {
            return ICONS[index];
        }

        @Override
        public int getCount() {
            return CONTENT.length;
        }
    }
}
