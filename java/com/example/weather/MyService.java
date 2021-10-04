package com.example.weather;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.widget.Toast;

import com.example.weather.ui.notifications.NotificationsFragment;

public class MyService extends Service {
    NotificationManager Notifi_M;
    ServiceThread thread;
    Notification Notifi ;

    static Notification.Builder builder;
    String channelId = "channel";
    String channelName = "name";
    int importance = NotificationManager.IMPORTANCE_LOW;
    static float servicetemp = 0;
    static Bitmap clothes_4;
    static Bitmap clothes5_8;
    static Bitmap clothes9_11;
    static Bitmap clothes12_16;
    static Bitmap clothes17_19;
    static Bitmap clothes_20_22;
    static Bitmap clothes23_27;
    static Bitmap clothes28_;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Notifi_M = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        myServiceHandler handler = new myServiceHandler();
        thread = new ServiceThread(handler);
        thread.start();
        return START_STICKY;
    }

    //서비스가 종료될 때 할 작업

    public void onDestroy() {

        //builder.setOngoing(false);
        System.out.println("꺼짐");
        thread.stopForever();
        thread = null;//쓰레기 값을 만들어서 빠르게 회수하라고 null을 넣어줌.
    }

    class myServiceHandler extends Handler {
        @Override
        public void handleMessage(android.os.Message msg) {
            Intent intent = new Intent(MyService.this, NotificationsFragment.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(MyService.this, 0, intent,PendingIntent.FLAG_UPDATE_CURRENT);
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                NotificationChannel mChannel = new NotificationChannel(
                        channelId, channelName, importance
                );

                Notifi_M.createNotificationChannel(mChannel);
                builder = new Notification.Builder(getApplicationContext(), channelId).setOngoing(true);

                builder.setContentTitle("오늘은 " + servicetemp + "℃입니다!")
                        .setContentText("옷차림 불러오기")
                        .setSmallIcon(R.drawable.sunpng)
                        .setContentIntent(pendingIntent);

                setimage(servicetemp);
                //Notifi_M.notify( 777 , builder.build());
                startForeground(1, builder.build());
            }

            //토스트 띄우기
            Toast.makeText(MyService.this, "날씨 업데이트", Toast.LENGTH_LONG).show();
        }
    };

    public static void getTemp(float temp){
        servicetemp = temp;
    }

    public void setimage(float temp){

        clothes_4 = BitmapFactory.decodeResource(getResources(), R.drawable.head8);
        clothes5_8 = BitmapFactory.decodeResource(getResources(), R.drawable.hand7);
        clothes9_11 = BitmapFactory.decodeResource(getResources(), R.drawable.jacket6);
        clothes12_16 = BitmapFactory.decodeResource(getResources(), R.drawable.hoodt5);
        clothes17_19 = BitmapFactory.decodeResource(getResources(), R.drawable.jacket4);
        clothes_20_22 = BitmapFactory.decodeResource(getResources(), R.drawable.ban3);
        clothes23_27 = BitmapFactory.decodeResource(getResources(), R.drawable.ban2);
        clothes28_ = BitmapFactory.decodeResource(getResources(), R.drawable.min1);
        if(temp < 5){
            builder.setLargeIcon(clothes_4);
            builder.setContentText("패딩, 두꺼운코트, 목도리, 기모제품");
        }
        else if(5 <= temp && temp < 9){
            builder.setLargeIcon(clothes5_8);
            builder.setContentText("코트, 가죽자켓, 히트텍, 니트, 레깅스");
        }
        else if(9 <= temp && temp < 12){
            builder.setLargeIcon(clothes9_11);
            builder.setContentText("자켓, 트렌치코트, 야상, 니트, 청바지, 스타킹");
        }
        else if(12 <= temp && temp < 17){
            builder.setLargeIcon(clothes12_16);
            builder.setContentText("자켓, 가디건, 야상, 스타킹, 청바지, 면바지");
        }
        else if(17 <= temp && temp < 20){
            builder.setLargeIcon(clothes17_19);
            builder.setContentText("얇은니트, 맨투맨, 가디건, 청바지");
        }
        else if(20 <= temp && temp < 23){
            builder.setLargeIcon(clothes_20_22);
            builder.setContentText("얇은가디건, 긴팔, 면바지, 청바지");
        }
        else if(23 <= temp && temp < 28){
            builder.setLargeIcon(clothes23_27);
            builder.setContentText("반팔, 얇은셔츠, 반바지, 면바지");
        }
        else{
            builder.setLargeIcon(clothes28_);
            builder.setContentText("민소매, 반팔, 반바지, 원피스");
        }
    }
}