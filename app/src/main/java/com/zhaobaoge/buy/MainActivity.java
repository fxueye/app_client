package com.zhaobaoge.buy;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

import com.zhaobaoge.buy.sample.ListSamples;
import com.zhaobaoge.mvp.presenter.ActivityPresenter;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;


public class MainActivity extends ActivityPresenter<MainDelegate> implements OnClickListener {

    private static final String TAG = "MainActivity";
    private static String BASE_URL = "http://wthrcdn.etouch.cn";

    @Override
    protected Class<MainDelegate> getDelegateClass() {
        return MainDelegate.class;
    }

    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        viewDelegate.setOnClickListener(this, R.id.tv);
        viewDelegate.setOnClickListener(this, R.id.button);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == viewDelegate.get(R.id.tv).getId()) {
            Retrofit retrofit2 = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .build();
            RxWeatherService rxWeatherService = retrofit2.create(RxWeatherService.class);
            rxWeatherService.getMessage("上海")
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<String>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(String s) {
                            Log.d(TAG, s);
                            viewDelegate.setText(s);
                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onComplete() {

                        }
                    });
        }else if(view.getId() == viewDelegate.get(R.id.button).getId()){
            startActivity(new Intent(this, ListSamples.class));
        }

    }
}
