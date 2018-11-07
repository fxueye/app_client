package com.zhaobaoge.buy.ui.sort;

import android.content.Intent;
import android.view.View;

import com.zhaobaoge.buy.R;
import com.zhaobaoge.buy.ui.search.SearchActivity;
import com.zhaobaoge.mvp.presenter.FragmentPresenter;

/**
 * Created by shikewei on 2018/10/31.
 */

public class SortFragment extends FragmentPresenter<SortDelegate> implements View.OnClickListener {

    @Override
    protected void bindEvenListener() {
        viewDelegate.setOnClickListener(this, R.id.linear_title_search_bac);
    }

    @Override
    protected Class<SortDelegate> getDelegateClass() {
        return SortDelegate.class;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.linear_title_search_bac:
                startActivity(new Intent(getActivity(), SearchActivity.class));
                break;
        }
    }
}
