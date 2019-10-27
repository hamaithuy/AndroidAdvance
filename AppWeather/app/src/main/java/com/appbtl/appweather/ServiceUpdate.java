package com.appbtl.appweather;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;
import android.view.Display;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.appbtl.appweather.model.OpenWeatherJson;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.gson.Gson;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class ServiceUpdate extends Service {
    private LocationAPI locationAPI;
    private IOFile ioFile;
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


                locationAPI.fusedLocationClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {


                            //làm việc với location ở đây
                            String url = "http://api.openweathermap.org/data/2.5/weather?" + "lat=" + location.getLatitude() + "&lon=" + location.getLongitude() + "&appid=b87ce30a14229dd8e26f167dd2111f06";
                            //truyền tham số location để lấy file json
                            String dailys = "http://api.openweathermap.org/data/2.5/forecast/daily?lat=" + location.getLatitude() + "&lon=" + location.getLongitude() + "&units=metric&cnt=7&appid=be8d3e323de722ff78208a7dbb2dcd6f";
                            try {

                                String reSultMain = new WeatherAsynctask().execute(url).get();
                                String resultDailys = new WeatherDailysAsynctask().execute(dailys).get();
                                Log.d(TAG, "======================================================="+reSultMain);
                                ioFile.saveFile("mainWeather.bat", reSultMain, context);
                                ioFile.saveFile("dailys.bat", resultDailys, context);
                            } catch (ExecutionException e) {
                                e.printStackTrace();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
                String data= ioFile.readFile("mainWeather.bat",context);
                Log.d(TAG, data);
                OpenWeatherJson obj = new Gson().fromJson(data,OpenWeatherJson.class);
                builder.setContentText(obj.getMain().getTemp()+"");
                builder.setSmallIcon(R.mipmap.ic_launcher);
                builder.setContentTitle("test");
                    NotificationChannel channel = new NotificationChannel("test", "test", NotificationManager.IMPORTANCE_HIGH);
                    NotificationManager nfmanager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                    nfmanager.createNotificationChannel(channel);
                    builder.setChannelId(channel.getId());
                    Notification n = builder.build();
                    nfmanager.notify(0,n);
            }
        };
        Timer timer = new Timer("Timer");
        timer.schedule(timerTask,30*60*1000L,30*60*1000L);// 30p thực hiện 1 lần cập nhập thời tiết

        return START_STICKY;
    }
}
