package com.zhaobaoge.buy;


import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by shikewei on 2018/10/25.
 */

public interface RxWeatherService {
    @GET("weather_mini")
    Observable<String> getMessage(@Query("city") String city);
}
