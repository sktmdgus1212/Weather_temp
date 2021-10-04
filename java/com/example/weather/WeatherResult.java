package com.example.weather;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class WeatherResult {

    public List<Hourly> hourly;
    public List<Daily> daily;


    public static class Hourly {
        public List<Hourly.Weather> weather;

        @SerializedName("dt")
        public int hour_dt;

        public static class Weather {
            @SerializedName("icon")
            public String hour_icon;

        }
    }

    public static class Daily {
        public List<Daily.Weather> weather;

        public static class Weather {
            @SerializedName("icon")
            public String day_icon;

        }
    }


}
