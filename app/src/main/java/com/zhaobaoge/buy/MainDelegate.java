package com.zhaobaoge.buy;

import android.widget.TextView;

import com.zhaobaoge.mvp.view.AppDelegate;

/**
 * Created by shikewei on 2018/10/29.
 */

public class MainDelegate extends AppDelegate {

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
