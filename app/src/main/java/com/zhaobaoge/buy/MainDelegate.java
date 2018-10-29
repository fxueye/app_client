package com.zhaobaoge.buy;

import android.widget.TextView;

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

    }

    public  void setText( String text){
        TextView textView = get(R.id.tv);
        textView.setText(text);
    }

}
