package com.example.login;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Api {
    @GET("weather")
    Call<weatherresponse> getcurrentweatherdata(@Query("q") String name, @Query("APPID") String app_id);
}
