package com.example.weather.ui.home;

import android.content.Context;
import android.content.SharedPreferences;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.weather.R;
import com.example.weather.databinding.FragmentHomeBinding;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

public class    HomeFragment extends Fragment {
    private static int Fragment_id;
    private static float Fragment_temp=0;
    private static float Fragment_temp_min=0;
    private static float Fragment_temp_max=0;
    private static float Fragment_humidity=0;
    private static String Fragment_description="description";
    private static String[] image_hour = new String[8];
    private static long[] date_hour = new long[8];
    private static String tomorrow_image_home = "01d";
    private static String twoday_image_home = "01d";

    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;

    private TextView date;
    static long mNow = System.currentTimeMillis();
    static Date mDate;
    static Date mTime;
    SimpleDateFormat mFormat = new SimpleDateFormat("yyyy-MM-dd");
    static SimpleDateFormat mday = new SimpleDateFormat("MM월 dd일");
    static SimpleDateFormat mhour = new SimpleDateFormat("HH:mm");
    static ImageView weather_image;
    static TextView description;
    static TextView temp;
    static TextView temp_min;
    static TextView temp_max;
    static TextView text_humidity;

    static GregorianCalendar calendar = new GregorianCalendar();

    static ImageView image0;
    static ImageView image1;
    static ImageView image2;
    static ImageView image3;
    static ImageView image4;
    static ImageView image5;
    static ImageView image6;
    static ImageView image7;

    static TextView textView_hour0;
    static TextView textView_hour1;
    static TextView textView_hour2;
    static TextView textView_hour3;
    static TextView textView_hour4;
    static TextView textView_hour5;
    static TextView textView_hour6;
    static TextView textView_hour7;
    SharedPreferences pref;
    boolean first;
    static String[] dateString = new String[8];

    static HashMap<String, Integer> images;
    static ImageView weather_tomorrow;
    static ImageView weather_twoday;
    static TextView textView_tomorrow;
    static TextView textView_twoday;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        date = (TextView) root.findViewById(R.id.text_date);
        date.setText(get_Time());


        weather_image = (ImageView) root.findViewById(R.id.weather_image);
        description = (TextView) root.findViewById(R.id.textview_description);
        temp = (TextView) root.findViewById(R.id.textView_temp);
        temp_min = (TextView) root.findViewById(R.id.textview_min);
        temp_max = (TextView) root.findViewById(R.id.textView_max);
        text_humidity = (TextView) root.findViewById(R.id.humidity);

        image0 = (ImageView) root.findViewById(R.id.weather00);
        image1 = (ImageView) root.findViewById(R.id.weather01);
        image2 = (ImageView) root.findViewById(R.id.weather02);
        image3 = (ImageView) root.findViewById(R.id.weather03);
        image4 = (ImageView) root.findViewById(R.id.weather04);
        image5 = (ImageView) root.findViewById(R.id.weather05);
        image6 = (ImageView) root.findViewById(R.id.weather06);
        image7 = (ImageView) root.findViewById(R.id.weather07);
        weather_tomorrow = (ImageView) root.findViewById(R.id.weather_tomorrow);
        weather_twoday = (ImageView) root.findViewById(R.id.weather_twoday);

        textView_hour0 = (TextView) root.findViewById(R.id.textView_hour);
        textView_hour1 = (TextView) root.findViewById(R.id.textView_hour2);
        textView_hour2 = (TextView) root.findViewById(R.id.textView_hour3);
        textView_hour3 = (TextView) root.findViewById(R.id.textView_hour4);
        textView_hour4 = (TextView) root.findViewById(R.id.textView_hour5);
        textView_hour5 = (TextView) root.findViewById(R.id.textView_hour6);
        textView_hour6 = (TextView) root.findViewById(R.id.textView_hour7);
        textView_hour7 = (TextView) root.findViewById(R.id.textView_hour8);
        textView_tomorrow = (TextView) root.findViewById(R.id.texview_tomorrow);
        textView_twoday = (TextView) root.findViewById(R.id.texview_twoday);

        images = new HashMap<String, Integer>();
        images.put( "01d", Integer.valueOf( R.drawable.d01 ) );
        images.put( "02d", Integer.valueOf( R.drawable.d02 ) );
        images.put( "03d", Integer.valueOf( R.drawable.d03 ) );
        images.put( "04d", Integer.valueOf( R.drawable.d04 ) );
        images.put( "09d", Integer.valueOf( R.drawable.d09 ) );
        images.put( "10d", Integer.valueOf( R.drawable.d10 ) );
        images.put( "11d", Integer.valueOf( R.drawable.d11 ) );
        images.put( "13d", Integer.valueOf( R.drawable.d13 ) );
        images.put( "50d", Integer.valueOf( R.drawable.d50 ) );

        images.put( "01n", Integer.valueOf( R.drawable.n01 ) );
        images.put( "02n", Integer.valueOf( R.drawable.n02 ) );
        images.put( "03n", Integer.valueOf( R.drawable.n03 ) );
        images.put( "04n", Integer.valueOf( R.drawable.n04 ) );
        images.put( "09n", Integer.valueOf( R.drawable.n09 ) );
        images.put( "10n", Integer.valueOf( R.drawable.n10 ) );
        images.put( "11n", Integer.valueOf( R.drawable.n11 ) );
        images.put( "13n", Integer.valueOf( R.drawable.n13 ) );
        images.put( "50n", Integer.valueOf( R.drawable.n50 ) );


        pref = getActivity().getSharedPreferences("isFirst", Context.MODE_PRIVATE);
        first = pref.getBoolean("isFirst", false);
        if(first==false){
            Log.d("Is first Time?", "first");
            SharedPreferences.Editor editor = pref.edit();
            editor.putBoolean("isFirst",true);
            editor.commit();
            //앱 최초 실행시 하고 싶은 작업
            for(int i = 0; i < 8 ; i++){
                image_hour[i] = "01d";
                date_hour[i] = 0;
            }
        }else{
            Log.d("Is first Time?", "not first");
        }


        sethourimage(image_hour, date_hour);
        setWeather_image(Fragment_id);
        setTempDescription(Fragment_temp, Fragment_description, Fragment_temp_min, Fragment_temp_max, Fragment_humidity);
        setdayimage(tomorrow_image_home, twoday_image_home);
        return root;
    }

    public static void setdayimage(String tomorrow, String twoday){
        tomorrow_image_home = tomorrow;
        twoday_image_home = twoday;

        calendar.add(Calendar.DATE, +1);
        String tomorrowdate = mday.format(calendar.getTime());
        calendar.add(Calendar.DATE, +1);
        String twodaydate = mday.format(calendar.getTime());

        textView_tomorrow.setText(tomorrowdate);
        textView_twoday.setText(twodaydate);

        calendar.add(Calendar.DATE, -2);
        weather_tomorrow.setImageResource(images.get(tomorrow_image_home).intValue());
        weather_twoday.setImageResource(images.get(twoday_image_home).intValue());
    }

    public static void sethourimage(String[] hour_image, long[] home_date){
        for(int i = 0 ; i < 8 ; i++){
            image_hour[i] = hour_image[i];
            date_hour[i] = home_date[i];
            mDate = new Date();
            mDate.setTime(home_date[i] * 1000);
            dateString[i] = mhour.format(mDate);
            System.out.println("dataString >>> "+home_date[i]);
        }

        try{
            image0.setImageResource(images.get(image_hour[0]).intValue() );
            image1.setImageResource(images.get(image_hour[1]).intValue() );
            image2.setImageResource(images.get(image_hour[2]).intValue() );
            image3.setImageResource(images.get(image_hour[3]).intValue() );
            image4.setImageResource(images.get(image_hour[4]).intValue() );
            image5.setImageResource(images.get(image_hour[5]).intValue() );
            image6.setImageResource(images.get(image_hour[6]).intValue() );
            image7.setImageResource(images.get(image_hour[7]).intValue() );
            System.out.println("success!!");
        }
        catch (NullPointerException e){
            e.printStackTrace();
        }



        textView_hour0.setText(dateString[0]);
        textView_hour1.setText(dateString[1]);
        textView_hour2.setText(dateString[2]);
        textView_hour3.setText(dateString[3]);
        textView_hour4.setText(dateString[4]);
        textView_hour5.setText(dateString[5]);
        textView_hour6.setText(dateString[6]);
        textView_hour7.setText(dateString[7]);
    }
    public static void setTempDescription(float value_temp, String value_description, float min, float max, float humidity){
        Fragment_temp = value_temp;
        Fragment_description = value_description;
        Fragment_temp_min = min;
        Fragment_temp_max = max;
        Fragment_humidity = humidity;
        String str_temp = Float.toString(value_temp);
        String str_temp_min = Float.toString(min);
        String str_temp_max = Float.toString(max);
        String str_humidity = Float.toString(humidity);

        description.setText(value_description);
        temp.setText("Now. "+str_temp+"℃");
        temp_min.setText("Low. "+str_temp_min+"℃");
        temp_max.setText("High. "+str_temp_max+"℃");
        text_humidity.setText("Humidity. "+str_humidity+"%");
    }
    public static void setWeather_image(int id){
        Fragment_id = id;
        SimpleDateFormat time = new SimpleDateFormat("k");
        mTime = new Date(mNow);
        String str = time.format(mTime);
        int hour = Integer.parseInt(str);
        System.out.println(hour+"hour");
        if(200 <= id && id <= 299){
            weather_image.setImageResource(R.drawable.d11);
        }
        else if((300 <= id && id <= 399) || (520 <= id && id <= 531)){
            weather_image.setImageResource(R.drawable.d09);
        }
        else if(500 <= id && id <= 504){
            /*if((19 <= hour && hour <= 24) || (00 <= hour && hour <= 06)){
                weather_image.setImageResource(R.drawable.n10);
            }
            else {*/
                weather_image.setImageResource(R.drawable.d10);

        }
        else if((600 <= id && id <= 622) || id == 511){
            weather_image.setImageResource(R.drawable.d13);
        }
        else if(700 <= id && id <= 781){
            weather_image.setImageResource(R.drawable.d50);
        }
        else if(id == 800){
                weather_image.setImageResource(R.drawable.d01);

        }
        else if(id == 801){
                weather_image.setImageResource(R.drawable.d02);

        }
        else if(id == 802){
                weather_image.setImageResource(R.drawable.d03);

        }
        else{
                weather_image.setImageResource(R.drawable.d04);
        }

    }


    private String get_Time(){
        mDate = new Date(mNow);
        return mFormat.format(mDate);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}