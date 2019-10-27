package com.appbtl.appweather;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.appbtl.appweather.model.AddressInfo;
import com.appbtl.appweather.model.OpenWeatherJson;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {
    private RelativeLayout mainlayout;
    private Intent intent;
    private Context mContext;
    private PopupWindow mPopupWindow;
    private ImageView imgvisibility, imgpressure, imghumidity, imgwind;
    private TextView city, temp, tempMax, tempMin, mainWeather, visibility, humidity, speed, airpress, wtCity, address;
    private ConstraintLayout body;
    private LocationAPI locationAPI;
    private AnimationDrawable animBackgroundRain;
    private String resultDailys, reSultMain;
    private HashMap<String, String> Des = new HashMap<String, String>();
    private IOFile ioFile;
    private RequestPermission pms;
    AddressInfo add;
    private Intent it;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        controls();
        mContext = getApplicationContext();
        setIcon();
        pms = new RequestPermission();

        add = new AddressInfo();
        ioFile = new IOFile();
        locationAPI = new LocationAPI();
        locationAPI.connectLocationApi(this);//kết nối API
        locationAPI.fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        it = new Intent(this, ServiceUpdate.class);
        startService(it);
        mainlayout.setOnTouchListener(new OnSwipeTouchListener(MainActivity.this) {
            @Override
            public void onSwipeRight() {
                if (resultDailys == null) {
                    resultDailys = "";
                }
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }

            @Override
            public void onSwipeLeft() {

                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }

            @Override
            public void onSwipeBottom() {
                pms.pLocation(MainActivity.this);
                if (isOnline() == false) {
                    Toast.makeText(MainActivity.this, "Bạn chưa bật kết nối mạng!", Toast.LENGTH_SHORT).show();
                } else {
                    locationAPI.fusedLocationClient.getLastLocation().addOnSuccessListener(MainActivity.this, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            if (location != null) {
                                add = getAddress(location);

                                //làm việc với location ở đây
                                String url = "http://api.openweathermap.org/data/2.5/weather?" + "lat=" + location.getLatitude() + "&lon=" + location.getLongitude() + "&appid=b87ce30a14229dd8e26f167dd2111f06";
                                //truyền tham số location để lấy file json
                                String dailys = "http://api.openweathermap.org/data/2.5/forecast/daily?lat=" + location.getLatitude() + "&lon=" + location.getLongitude() + "&units=metric&cnt=7&appid=be8d3e323de722ff78208a7dbb2dcd6f";
                                try {
                                    reSultMain = new WeatherAsynctask().execute(url).get();
                                    resultDailys = new WeatherDailysAsynctask().execute(dailys).get();
                                    updateUI(add, reSultMain);
                                    ioFile.saveFile("mainWeather.bat", reSultMain, mContext);
                                    ioFile.saveFile("dailys.bat", resultDailys, mContext);
                                } catch (ExecutionException e) {
                                    e.printStackTrace();
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            } else {
                                Toast.makeText(MainActivity.this, "Chưa nhận được vị trí.Vui lòng kiểm tra lại GPS!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
        city.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(LAYOUT_INFLATER_SERVICE);
                View customview = inflater.inflate(R.layout.popup, null);
                mPopupWindow = new PopupWindow(
                        customview,
                        RelativeLayout.LayoutParams.WRAP_CONTENT,
                        RelativeLayout.LayoutParams.WRAP_CONTENT
                );
                Button btnFind = (Button) customview.findViewById(R.id.btnAccept);
                EditText editText = (EditText) customview.findViewById(R.id.editText);
                btnFind.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mPopupWindow.dismiss();
                    }
                });
                mPopupWindow.setFocusable(true);
                mPopupWindow.update();
                mPopupWindow.showAtLocation(body, Gravity.CENTER, 0, 0);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        pms.pLocation(MainActivity.this);
        locationAPI.fusedLocationClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                add = getAddress(location);
            }
        });
        String dt = ioFile.readFile("mainWeather.bat", mContext);
        File file = new File(this.getFilesDir(), "mainWeather.bat");
        if (file.exists())
//        if(dt!=null||dt.equals("")){
//
            updateUI(add, data());
//        }
        else {
            getWeather();
        }
    }

    private void getWeather() {
        if (isOnline() == false) {
            Toast.makeText(MainActivity.this, "Bạn chưa bật kết nối mạng!", Toast.LENGTH_SHORT).show();
        } else {
            locationAPI.fusedLocationClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if (location != null) {
                        add = getAddress(location);
                        //làm việc với location ở đây

                        // location.getLatitude()
                        // location.getLongitude()
                        String url = "http://api.openweathermap.org/data/2.5/weather?" + "lat=" + "21.028511" + "&lon=" + "105.804817" + "&appid=b87ce30a14229dd8e26f167dd2111f06";
                        //truyền tham số location để lấy file json
                        String dailys = "http://api.openweathermap.org/data/2.5/forecast/daily?lat=" + "21.028511" + "&lon=" + "105.804817" + "&units=metric&cnt=7&appid=be8d3e323de722ff78208a7dbb2dcd6f";
                        try {
                            reSultMain = new WeatherAsynctask().execute(url).get();
                            resultDailys = new WeatherDailysAsynctask().execute(dailys).get();
                            updateUI(add, reSultMain);
                            ioFile.saveFile("mainWeather.bat", reSultMain, mContext);
                            ioFile.saveFile("dailys.bat", resultDailys, mContext);

                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
            locationAPI.fusedLocationClient.getLastLocation().addOnFailureListener(this, new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(MainActivity.this, "Bạn chưa bật GPS.Vui lòng bật GPS và thử lại!", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void controls() {
        address = (TextView) findViewById(R.id.Address);
        wtCity = (TextView) findViewById(R.id.wtCity);
        city = (TextView) findViewById(R.id.City);
        body = (ConstraintLayout) findViewById(R.id.body);
        mainlayout = (RelativeLayout) findViewById(R.id.mainlayout);
        imgvisibility = (ImageView) findViewById(R.id.imgvisibility);
        imgpressure = (ImageView) findViewById(R.id.imgpressure);
        imghumidity = (ImageView) findViewById(R.id.imghumidity);
        imgwind = (ImageView) findViewById(R.id.imgwind);
        temp = (TextView) findViewById(R.id.txtTempm);
        tempMax = (TextView) findViewById(R.id.temp_max);
        tempMin = (TextView) findViewById(R.id.temp_min);
        mainWeather = (TextView) findViewById(R.id.main_weather);
        visibility = (TextView) findViewById(R.id.visibility);
        airpress = (TextView) findViewById(R.id.airpress);
        humidity = (TextView) findViewById(R.id.humidity);
        speed = (TextView) findViewById(R.id.speed);
        intent = new Intent(MainActivity.this, ActivityDetails.class);

        Des.put("scattered clouds", "Mây rải rác");
        Des.put("moderate rain", "Mưa vừa");
        Des.put("heavy intensity rain", "Mưa lớn");
        Des.put("broken clouds", "Mây rải rác");
        Des.put("sky is clear", "Trời quang");
        Des.put("light rain", "Mưa nhỏ");
    }

    public String data() {
        String dt = ioFile.readFile("mainWeather.bat", mContext);
        return dt;
    }

    private void updateUI(AddressInfo add, String rs) {
        OpenWeatherJson result = new Gson().fromJson(rs, OpenWeatherJson.class);
        int tempmain, tempmax, tempmin;
        double t = result.getMain().getTemp() - 273.15;
        tempmain = (int) t;
        double max = result.getMain().getTemp_max() - 273.15;
        tempmax = (int) max;
        double min = result.getMain().getTemp_min() - 273.15;
        tempmin = (int) min;
        //kết quả trả về json thành object
        wtCity.setText(add.getCity());
        city.setText(add.getCity());
        if (add.getVillage() == null || add.getVillage().equals("Unnamed Road")) {
            address.setText(add.getArea() + "-" + add.getCity());
        } else {
            address.setText(add.getVillage() + "-" + add.getArea() + "-" + add.getCity());
        }

        temp.setText(tempmain + "°C");
        tempMax.setText(tempmax + "°C");
        tempMin.setText(tempmin + "°C");
        String des = result.getWeather().get(0).getDescription();
        String status = Des.get(des);
        mainWeather.setText(status);
        visibility.setText(result.getVisibility() + "m");
        airpress.setText(result.getMain().getPressure() + " hpa");
        humidity.setText(result.getMain().getHumidity() + "%");
        speed.setText(result.getWind().getSpeed() + "km/h");
        // làm việc với giao diện ở đây;
    }


    private void setIcon() {
        imgvisibility.setImageResource(R.drawable.visibility);
        imgpressure.setImageResource(R.drawable.airpress);
        imghumidity.setImageResource(R.drawable.humidity);
        imgwind.setImageResource(R.drawable.speed);
        body.setBackgroundResource(R.drawable.background_rain2);
        animBackgroundRain = (AnimationDrawable) body.getBackground();
        animBackgroundRain.start();

    }

    private Boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        if (ni != null && ni.isConnected()) {
            return true;
        }
        return false;
    }

    private AddressInfo getAddress(Location location) {
        AddressInfo addressInfo = new AddressInfo();
        if (Geocoder.isPresent()) {
            Geocoder geocoder = new Geocoder(this);
            try {
                List<Address> ad = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                if (ad != null) {
                    for (Address name : ad) {
                        String ct = name.getAdminArea();
                        String subarea = name.getLocality();
                        String area = name.getSubAdminArea();
                        String village = name.getFeatureName();
                        String road = name.getThoroughfare();
                        addressInfo.setCity(ct);
                        addressInfo.setArea(area);
                        addressInfo.setVillage(village);
                        addressInfo.setRoad(road);
                        addressInfo.setPlace(subarea);
                        wtCity.setText(ct);
                        city.setText(ct);
                        if (village == "" || village.equals("Unnamed Road")) {
                            address.setText(area + "-" + ct);
                        } else {
                            address.setText(village + "-" + area + "-" + ct);
                        }

                        return addressInfo;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
