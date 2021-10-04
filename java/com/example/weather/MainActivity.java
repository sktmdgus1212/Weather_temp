package com.example.weather;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.example.weather.databinding.ActivityMainBinding;
import com.example.weather.ui.home.HomeFragment;
import com.example.weather.ui.notifications.NotificationsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    private static String BASE_URL = "https://api.openweathermap.org/data/2.5/weather";
    private static String apiKey = "0d3bc45d4f2a570d9d625cb05b823a63"; // 발급받은 API ke
    static OpenWeather response;
    static ValueHandler handler = new ValueHandler();
    ValueHandler2 handler2 = new ValueHandler2();
    static List<OpenWeather.Weather> weather;
    static OpenWeather.Main main;

    Retrofit retrofit;
    WeatherApi weatherApi;
    private final static String appKey = "0d3bc45d4f2a570d9d625cb05b823a63";
    String[] hour_image;
    String tomorrow_image;
    String twoday_image;
    long[] hour_date;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);


        getWeatherData();

        retrofit = new Retrofit.Builder().baseUrl("https://api.openweathermap.org/").addConverterFactory(GsonConverterFactory.create()).build();
        weatherApi = retrofit.create(WeatherApi.class);
        hour_image = new String[8];
        hour_date = new long[8];
        newapi();

        }




    public static void getWeatherData() {
        StringBuilder urlBuilder = new StringBuilder(BASE_URL);

        try {
            urlBuilder.append("?" + URLEncoder.encode("q", "UTF-8") + "=Seoul");
            urlBuilder.append("&" + URLEncoder.encode("appid", "UTF-8") + "=" + apiKey);
            urlBuilder.append("&" + URLEncoder.encode("lang", "UTF-8") + "=kr");
            urlBuilder.append("&" + URLEncoder.encode("units", "UTF-8") + "=metric");

            Thread thread = new Thread() {
                public void run(){
                    RestTemplate restTemplate = new RestTemplate();
                    response = restTemplate.getForObject(urlBuilder.toString(), OpenWeather.class);
                    System.out.println("response >>> " + response);
                    //weather object
                    weather = response.weather;
                    main = response.main;
                    int thread_id = weather.get(0).id;
                    float thread_temp = main.temp;
                    float thread_temp_min = main.temp_min;
                    float thread_temp_max = main.temp_max;
                    float thread_humidity = main.humidity;
                    String thread_description = weather.get(0).description;

                    Message message = handler.obtainMessage();
                    Bundle bundle = new Bundle();
                    bundle.putInt("id", thread_id);
                    bundle.putFloat("temp", thread_temp);
                    bundle.putFloat("temp_min", thread_temp_min);
                    bundle.putFloat("temp_max", thread_temp_max);
                    bundle.putFloat("humidity", thread_humidity);
                    bundle.putString("description", thread_description);
                    message.setData(bundle);
                    //sendMessage가 되면 이 handler가 해당되는 핸들러객체가(ValueHandler) 자동으로 호출된다.
                    handler.sendMessage(message);
                }
            };

            thread.start();

            try {
                thread.join();
            }
            catch (InterruptedException e){
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("exception >>> ("+urlBuilder+")");
        }

    }

     static class ValueHandler extends Handler {
            public int handler_id;
            public float handler_temp;
            public float handler_temp_min;
            public float handler_temp_max;
            public float handler_humidity;
            public String handler_description;
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle bundle = msg.getData();
            handler_id = bundle.getInt("id");
            handler_temp = bundle.getFloat("temp");
            handler_temp_min = bundle.getFloat("temp_min");
            handler_temp_max = bundle.getFloat("temp_max");
            handler_humidity = bundle.getFloat("humidity");
            handler_description = bundle.getString("description");
            //homefragment 설정
            HomeFragment.setWeather_image(handler_id);
            HomeFragment.setTempDescription(handler_temp, handler_description, handler_temp_min, handler_temp_max, handler_humidity);
            NotificationsFragment.getTemp(handler_temp, handler_temp_min, handler_temp_max);
            MyService.getTemp(handler_temp);
            System.out.println("handler_temp >>> "+handler_temp);
        }
    }

    public void newapi(){
        Thread newthread =  new Thread() {
            public void run() {
                Call<WeatherResult> getWeather = weatherApi.getWeather("37.56", "127.00", "current,minutely,alerts", "kr", appKey); //나라는 런던 으로 걍 했어용


                try {

                    WeatherResult weatherResult = getWeather.execute().body();
                    List<WeatherResult.Hourly> hourly = weatherResult.hourly;
                    List<WeatherResult.Daily> daily = weatherResult.daily;

                    Message message = handler2.obtainMessage();
                    Bundle bundle = new Bundle();

                    List<WeatherResult.Daily.Weather> day_weather = daily.get(1).weather;
                    tomorrow_image = day_weather.get(0).day_icon;
                    day_weather = daily.get(2).weather;
                    twoday_image = day_weather.get(0).day_icon;

                    bundle.putString("tomorrow_image", tomorrow_image);
                    bundle.putString("twoday_image", twoday_image);

                    for(int i = 0; i < 8 ; i++){
                        hour_date[i] = hourly.get(3 * i+3).hour_dt;
                        List<WeatherResult.Hourly.Weather> weathers = hourly.get(3 * i+3).weather;
                        hour_image[i] = weathers.get(0).hour_icon;
                        System.out.println("ererer"+hour_image[i]);
                        bundle.putLong("hour_date"+i, hour_date[i]);
                        bundle.putString("home_image"+i, hour_image[i]);
                    }

                    message.setData(bundle);
                    //sendMessage가 되면 이 handler가 해당되는 핸들러객체가(ValueHandler) 자동으로 호출된다.
                    handler2.sendMessage(message);

                } catch (IOException e) {
                    System.out.println("errorerror");
                    e.printStackTrace();
                }
            }
        };

        newthread.start();
    }

    class ValueHandler2 extends Handler {
        public String[] home_image = new String[8];
        public long[] home_date = new long[8];
        public String tomorrow_image;
        public String twoday_image;
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle bundle = msg.getData();

            tomorrow_image = bundle.getString("tomorrow_image");
            twoday_image = bundle.getString("twoday_image");
            for(int i = 0 ;i < 8 ; i++){
                home_image[i] = bundle.getString("home_image"+i);
                home_date[i] = bundle.getLong("hour_date"+i);
            }
            HomeFragment.sethourimage(home_image, home_date);
            HomeFragment.setdayimage(tomorrow_image, twoday_image);
        }
    }
}

