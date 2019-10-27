package com.appbtl.appweather;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.appbtl.appweather.model.OpenWeatherJson;
import com.google.android.gms.location.LocationServices;
import com.google.gson.Gson;

import java.util.Timer;
import java.util.TimerTask;

public class ServiceUpdate extends Service {
    private LocationAPI locationAPI;
    private IOFile ioFile;
    private String reSultMain, data;
    private Context context;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        locationAPI = new LocationAPI();
        ioFile = new IOFile();
        final Notification.Builder builder = new Notification.Builder(this);
        context = ServiceUpdate.this;
        locationAPI.connectLocationApi(this);//kết nối API
        locationAPI.fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        TimerTask timerTask = new TimerTask() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void run() {
                OpenWeatherJson obj = new Gson().fromJson(data, OpenWeatherJson.class);
                builder.setContentText("Your Weather is Updated, Have a nice day!!");
                builder.setSmallIcon(R.mipmap.ic_launcher);
                builder.setContentTitle("Weather Updated");
                NotificationChannel channel = new NotificationChannel("Weather Updated", "Weather Updated", NotificationManager.IMPORTANCE_HIGH);
                NotificationManager nfmanager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                nfmanager.createNotificationChannel(channel);
                builder.setChannelId(channel.getId());
                Notification n = builder.build();
                nfmanager.notify(0, n);
            }
        };
        Timer timer = new Timer("Timer");
        timer.schedule(timerTask, 30 * 60 * 1000L, 30 * 60 * 1000L);// 30p thực hiện 1 lần cập nhập thời tiết

        return START_STICKY;
    }
}
