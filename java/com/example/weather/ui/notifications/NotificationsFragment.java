package com.example.weather.ui.notifications;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.weather.MyService;
import com.example.weather.R;
import com.example.weather.databinding.FragmentNotificationsBinding;

import java.text.SimpleDateFormat;
import java.util.Date;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

public class NotificationsFragment extends Fragment {

    private NotificationsViewModel notificationsViewModel;
    private FragmentNotificationsBinding binding;


    private TextView date;
    long mNow = System.currentTimeMillis();
    Date mDate;
    SimpleDateFormat mFormat = new SimpleDateFormat("yyyy-MM-dd");
    String getTime;
    static String str_temp="";
    static String str_temp_min="";
    static String str_temp_max="";
    static TextView temp11;
    static TextView temp_min;
    static TextView temp_max;
    private static float frag_temp=0;
    private static float frag_temp_min=0;
    private static float frag_temp_max=0;

    static ImageView clothes_image;
    static TextView clothes_temp;
    static TextView clothes1;
    static TextView clothes1_min;
    static TextView clothes_max;

    private Button btnStart, btnEnd;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel =
                new ViewModelProvider(this).get(NotificationsViewModel.class);

        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        date = (TextView) root.findViewById(R.id.text_date);
        date.setText(getTime());

        temp11 = (TextView) root.findViewById(R.id.temparture);
        temp_min = (TextView) root.findViewById(R.id.temp_min);
        temp_max = (TextView) root.findViewById(R.id.temp_max);
        clothes_image = (ImageView) root.findViewById(R.id.clothes_image);
        clothes_temp = (TextView) root.findViewById(R.id.clothes_temp);
        clothes1 = (TextView) root.findViewById(R.id.clothes1);
        clothes1_min = (TextView) root.findViewById(R.id.clothes_min);
        clothes_max = (TextView) root.findViewById(R.id.clothes_max);
        btnStart = (Button)root.findViewById(R.id.btn_start);
        btnEnd = (Button)root.findViewById(R.id.btn_end);

        btnStart.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),"상단바 알림 켜기",Toast.LENGTH_SHORT).show();
                //Intent intent = new Intent(getActivity(), MyService.class);
                //startService(intent);
                getActivity().startForegroundService(new Intent(getActivity(), MyService.class));
            }
        });

        btnEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),"상단바 알림 끄기",Toast.LENGTH_SHORT).show();
                //Intent intent = new Intent(getActivity(), MyService.class);
                //stopService(intent);
                getActivity().stopService(new Intent(getActivity(), MyService.class));
            }
        });

        //getTemp(frag_temp);
        setTemp();
        setimage(frag_temp);
        setimage_min(frag_temp_min);
        setimage_max(frag_temp_max);
        return root;
    }

    public static void getTemp(float temp, float min, float max){
        frag_temp = temp;
        frag_temp_min = min;
        frag_temp_max = max;

        str_temp = Float.toString(temp);
        str_temp_min = Float.toString(min);
        str_temp_max = Float.toString(max);
    }

    public static void setTemp(){
        temp11.setText("현재온도: "+ str_temp+"℃");
        temp_min.setText("High. "+str_temp_min+"℃");
        temp_max.setText("Low. "+str_temp_max+"℃");
    }

    public static void setimage(float temp){
        if(temp < 5){
            clothes_image.setImageResource(R.drawable.clothes_4);
            clothes_temp.setText("4℃ 이하일 때 옷차림");
            clothes1.setText("패딩, 두꺼운코트, 목도리, 기모제품");
        }
        else if(5 <= temp && temp < 9){
            clothes_image.setImageResource(R.drawable.clothes5_8);
            clothes_temp.setText("5℃~8℃일 때 옷차림");
            clothes1.setText("코트, 가죽자켓, 히트텍, 니트, 레깅스");
        }
        else if(9 <= temp && temp < 12){
            clothes_image.setImageResource(R.drawable.clothes9_11);
            clothes_temp.setText("9℃~11℃일 때 옷차림");
            clothes1.setText("자켓, 트렌치코트, 야상, 니트, 청바지, 스타킹");
        }
        else if(12 <= temp && temp < 17){
            clothes_image.setImageResource(R.drawable.clothes12_16);
            clothes_temp.setText("12℃~16℃일 때 옷차림");
            clothes1.setText("자켓, 가디건, 야상, 스타킹, 청바지, 면바지");
        }
        else if(17 <= temp && temp < 20){
            clothes_image.setImageResource(R.drawable.clothes17_19);
            clothes_temp.setText("17℃~19℃일 때 옷차림");
            clothes1.setText("얇은 니트, 맨투맨, 가디건, 청바지");
        }
        else if(20 <= temp && temp < 23){
            clothes_image.setImageResource(R.drawable.clothes_20_22);
            clothes_temp.setText("20℃~22℃일 때 옷차림");
            clothes1.setText("얇은 가디건, 긴팔, 면바지, 청바지");
        }
        else if(23 <= temp && temp < 28){
            clothes_image.setImageResource(R.drawable.clothes23_27);
            clothes_temp.setText("23℃~27℃일 때 옷차림");
            clothes1.setText("반팔, 얇은 셔츠, 반바지, 면바지");
        }
        else{
            clothes_image.setImageResource(R.drawable.clothes28_);
            clothes_temp.setText("28℃ 이상일 때 옷차림");
            clothes1.setText("민소매, 반팔, 반바지, 원피스");
        }
    }

    public static void setimage_min(float temp){
        if(temp < 5){

            clothes1_min.setText("패딩, 두꺼운코트, 목도리, 기모제품");
        }
        else if(5 <= temp && temp < 9){
            clothes1_min.setText("코트, 가죽자켓, 히트텍, 니트, 레깅스");
        }
        else if(9 <= temp && temp < 12){
            clothes1_min.setText("자켓, 트렌치코트, 야상, 니트, 청바지, 스타킹");
        }
        else if(12 <= temp && temp < 17){
            clothes1_min.setText("자켓, 가디건, 야상, 스타킹, 청바지, 면바지");
        }
        else if(17 <= temp && temp < 20){
            clothes1_min.setText("얇은 니트, 맨투맨, 가디건, 청바지");
        }
        else if(20 <= temp && temp < 23){
            clothes1_min.setText("얇은 가디건, 긴팔, 면바지, 청바지");
        }
        else if(23 <= temp && temp < 28){
            clothes1_min.setText("반팔, 얇은 셔츠, 반바지, 면바지");
        }
        else{
            clothes1_min.setText("민소매, 반팔, 반바지, 원피스");
        }
    }

    public static void setimage_max(float temp){
        if(temp < 5){
            clothes_max.setText("패딩, 두꺼운코트, 목도리, 기모제품");
        }
        else if(5 <= temp && temp < 9){
            clothes_max.setText("코트, 가죽자켓, 히트텍, 니트, 레깅스");
        }
        else if(9 <= temp && temp < 12){
            clothes_max.setText("자켓, 트렌치코트, 야상, 니트, 청바지, 스타킹");
        }
        else if(12 <= temp && temp < 17){
            clothes_max.setText("자켓, 가디건, 야상, 스타킹, 청바지, 면바지");
        }
        else if(17 <= temp && temp < 20){
            clothes_max.setText("얇은 니트, 맨투맨, 가디건, 청바지");
        }
        else if(20 <= temp && temp < 23){
            clothes_max.setText("얇은 가디건, 긴팔, 면바지, 청바지");
        }
        else if(23 <= temp && temp < 28){
            clothes_max.setText("반팔, 얇은 셔츠, 반바지, 면바지");
        }
        else{
            clothes_max.setText("민소매, 반팔, 반바지, 원피스");
        }
    }

    private String getTime(){
        mNow = System.currentTimeMillis();
        mDate = new Date(mNow);
        return mFormat.format(mDate);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}