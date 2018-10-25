package com.zhaobaoge.buy;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by shikewei on 2018/10/25.
 */

public interface WeatherService {
    @GET("weather_mini")
    Call<String> getMessage(@Query("city") String city);
}
