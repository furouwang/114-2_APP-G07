package com.example.myapplication;

import retrofit2.Call;
import retrofit2.http.GET;

public interface WeatherService {
    // 🌊 這是氣象署免註冊、公開的小琉球浮標即時海象資料網址
    @GET("api/v1/rest/datastore/O-A0017-001?Authorization=rdec-key-123-45678-query")
    Call<String> getRawWeatherData();
}
