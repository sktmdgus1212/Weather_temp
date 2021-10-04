package com.example.weather;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherApi {
    public static final String API_URL = "https://api.openweathermap.org/"; //저랑 같은 날씨 API를
    //쓰면 같아요. 이게 BASE_URL 이 될 수 도 있어요.

    @GET("data/2.5/onecall") //BASE_URL과 get괄호 안에를 합치면 설명서에 나와있는 주소가 돼요.
    Call<WeatherResult> getWeather(@Query("lat") String lat, @Query("lon") String lon, @Query("exclude") String exclude, @Query("lang") String lang, @Query("appid") String appid);
// q 는 설명서에 ?q 부분이고 appid 도 설명서에 appid 부분이에요. 즉, 설명서 API주소를 이런식으로 맟추는거에요.
}