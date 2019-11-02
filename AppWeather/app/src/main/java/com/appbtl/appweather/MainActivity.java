package com.appbtl.appweather;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.RemoteViews;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.appbtl.appweather.model.AddressInfo;
import com.appbtl.appweather.model.ListDailys;
import com.appbtl.appweather.model.OpenWeatherJson;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {
    private RelativeLayout mainlayout;
    private Intent intent, intent1;
    private Context mContext;
    private PopupWindow mPopupWindow;
    private ImageView imgvisibility, imgpressure, imghumidity, imgwind, imgTT;
    private TextView city, temp, tempMax, tempMin, mainWeather, visibility, humidity, speed, airpress, wtCity, address;
    private ConstraintLayout body;
    private LocationAPI locationAPI;
    private AnimationDrawable animBackgroundRain, animHumidity, animWind, animVisibility, animPressure, animTT;
    public static String resultDailys, reSultMain, resultHours;
    public static HashMap<String, String> Des = new HashMap<String, String>();
    private IOFile ioFile;
    private RequestPermission pms;
    AddressInfo add;
    private Intent it;
    private static final String TAG = "MyActivity";
    private ArrayList<String> ls = new ArrayList<String>();

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
                if (resultHours == null) {
                    resultHours = "";
                }
                startActivity(intent1);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }

            @Override
            public void onSwipeBottom() {
                pms.pLocation(MainActivity.this);
                if (isOnline() == false) {
                    String dt;
                    dt = ioFile.readFile("mainWeather.bat", mContext);
                    OpenWeatherJson result = new Gson().fromJson(dt, OpenWeatherJson.class);
                    updateUI(getAddress(result.getCoord().getLat(), result.getCoord().getLon()), data());
                    Toast.makeText(MainActivity.this, "Bạn chưa bật kết nối mạng!", Toast.LENGTH_SHORT).show();
                } else {
                    locationAPI.fusedLocationClient.getLastLocation().addOnSuccessListener(MainActivity.this, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            if (location != null) {
                                add = getAddress(location.getLatitude(), location.getLongitude());

                                //làm việc với location ở đây
                                String url = "http://api.openweathermap.org/data/2.5/weather?" + "lat=" + location.getLatitude() + "&lon=" + location.getLongitude() + "&appid=b87ce30a14229dd8e26f167dd2111f06";
                                //truyền tham số location để lấy file json
                                String dailys = "http://api.openweathermap.org/data/2.5/forecast/daily?lat=" + location.getLatitude() + "&lon=" + location.getLongitude() + "&units=metric&cnt=7&appid=be8d3e323de722ff78208a7dbb2dcd6f";
                                try {
                                    reSultMain = new WeatherAsynctask().execute(url).get();
                                    resultDailys = new WeatherDailysAsynctask().execute(dailys).get();
                                    OpenWeatherJson js = new Gson().fromJson(reSultMain, OpenWeatherJson.class);
                                    String hours = "https://openweathermap.org/data/2.5/forecast/?appid=b6907d289e10d714a6e88b30761fae22&id=" + js.getId() + "&units=metric";
                                    resultHours = new WeatherDailysAsynctask().execute(hours).get();
                                    Log.i(TAG, "onSuccess: " + resultHours);
                                    updateUI(add, reSultMain);
                                    ioFile.saveFile("mainWeather.bat", reSultMain, mContext);
                                    ioFile.saveFile("dailys.bat", resultDailys, mContext);
                                    ioFile.saveFile("hours.bat", resultHours, mContext);

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
                        RelativeLayout.LayoutParams.MATCH_PARENT,
                        RelativeLayout.LayoutParams.MATCH_PARENT
                );
                Button btnFind = (Button) customview.findViewById(R.id.btnAccept);
                final EditText editText = (EditText) customview.findViewById(R.id.editText);
                Button btnLocale = (Button) customview.findViewById(R.id.getCurrentLocale);
                final ListView lvls = (ListView) customview.findViewById(R.id.lv);
                ArrayList<String> sub = new ArrayList<String>();
                sub = ls;
                Collections.reverse(sub);
                ArrayAdapter<String> arrayAdapter
                        = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, sub);
                lvls.setAdapter(arrayAdapter);
                lvls.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        editText.setText(parent.getItemAtPosition(position).toString());
                    }
                });
                final ArrayList<String> finalSub = sub;
                btnFind.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Log.i(TAG, "onClick: " + editText.getText().toString());
                        String normalized_string = Normalizer.normalize(editText.getText(), Normalizer.Form.NFD).replaceAll("đ", "d").replaceAll("Đ", "D").replaceAll("[^\\p{ASCII}]", "");
                        normalized_string = normalized_string.replaceAll("\\s+", "+");
                        String url = "http://api.openweathermap.org/data/2.5/weather?q=" + normalized_string + "&appid=b87ce30a14229dd8e26f167dd2111f06";
                        try {
                            reSultMain = new WeatherAsynctask().execute(url).get();
                            OpenWeatherJson result = new Gson().fromJson(reSultMain, OpenWeatherJson.class);
                            String hours = "https://openweathermap.org/data/2.5/forecast/?appid=b6907d289e10d714a6e88b30761fae22&id=" + result.getId() + "&units=metric";
                            Log.i(TAG, "onClick: " + hours);
                            resultHours = new WeatherDailysAsynctask().execute(hours).get();
                            String dailys = "http://api.openweathermap.org/data/2.5/forecast/daily?lat=" + result.getCoord().getLat() + "&lon=" + result.getCoord().getLat() + "&units=metric&cnt=7&appid=be8d3e323de722ff78208a7dbb2dcd6f";
                            resultDailys = new WeatherDailysAsynctask().execute(dailys).get();
                            Log.i(TAG, "onClick: " + mContext.toString());
                            ioFile.saveFile("mainWeather.bat", reSultMain, mContext);
                            ioFile.saveFile("dailys.bat", resultDailys, mContext);
                            ioFile.saveFile("hours.bat", resultHours, mContext);
                            AddressInfo address = getAddress(result.getCoord().getLat(), result.getCoord().getLon());
                            updateUI(address, reSultMain);
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        ioFile.saveFile("mainWeather.bat", reSultMain, mContext);
                        ls.remove(editText.getText().toString());
                        ls.add(editText.getText().toString());
                        mPopupWindow.dismiss();

                    }
                });
                btnLocale.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        getWeather1();
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
        File file = new File(this.getFilesDir(), "mainWeather.bat");
        if (file.exists()) {
            String dt = ioFile.readFile("mainWeather.bat", mContext);
            final OpenWeatherJson result = new Gson().fromJson(dt, OpenWeatherJson.class);
            locationAPI.fusedLocationClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
//                add = getAddress(location.getLatitude(),location.getLongitude());
                    add = getAddress(result.getCoord().getLat(), result.getCoord().getLon());
                }
            });
            Log.i(TAG, dt);
            updateUI(getAddress(result.getCoord().getLat(), result.getCoord().getLon()), data());
        } else {
            getWeather1();
        }
    }

    private void getWeather1() {
        if (isOnline() == false) {
            File file = new File(this.getFilesDir(), "mainWeather.bat");
            String dt;
            if (file.exists()) {
                dt = ioFile.readFile("mainWeather.bat", mContext);
                OpenWeatherJson result = new Gson().fromJson(dt, OpenWeatherJson.class);
                updateUI(getAddress(result.getCoord().getLat(), result.getCoord().getLon()), data());
                Toast.makeText(MainActivity.this, "Bạn chưa bật kết nối mạng!", Toast.LENGTH_SHORT).show();
            }
        } else {
            locationAPI.fusedLocationClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if (location != null) {
                        add = getAddress(location.getLatitude(), location.getLongitude());
                        //làm việc với location ở đây

                        // location.getLatitude()
                        // location.getLongitude()
                        String url = "http://api.openweathermap.org/data/2.5/weather?" + "lat=" + location.getLatitude() + "&lon=" + location.getLongitude() + "&appid=b87ce30a14229dd8e26f167dd2111f06";
                        //truyền tham số location để lấy file json
                        String dailys = "http://api.openweathermap.org/data/2.5/forecast/daily?lat=" + location.getLatitude() + "&lon=" + location.getLongitude() + "&units=metric&cnt=7&appid=be8d3e323de722ff78208a7dbb2dcd6f";
                        //get daily hours
                        try {
                            reSultMain = new WeatherAsynctask().execute(url).get();
                            OpenWeatherJson js = new Gson().fromJson(reSultMain, OpenWeatherJson.class);
                            resultDailys = new WeatherDailysAsynctask().execute(dailys).get();
                            String hours = "https://openweathermap.org/data/2.5/forecast/?appid=b6907d289e10d714a6e88b30761fae22&id=" + js.getId() + "&units=metric";
                            resultHours = new WeatherDailysAsynctask().execute(hours).get();
                            updateUI(add, reSultMain);
                            ioFile.saveFile("mainWeather.bat", reSultMain, mContext);
                            ioFile.saveFile("dailys.bat", resultDailys, mContext);
                            ioFile.saveFile("hours.bat", resultHours, mContext);
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
        imgTT = (ImageView) findViewById(R.id.imgTT);
        intent = new Intent(MainActivity.this, ActivityDetails.class);
        intent1 = new Intent(MainActivity.this, ActivityInfo.class);
        Des.put("scattered clouds", "Mây rải rác");
        Des.put("moderate rain", "Mưa vừa");
        Des.put("heavy intensity rain", "Mưa lớn");
        Des.put("broken clouds", "Mây rải rác");
        Des.put("sky is clear", "Trời quang");
        Des.put("light rain", "Mưa nhỏ");
        Des.put("few clouds", "Ít mây");
        Des.put("mist", "Sương mù");
        Des.put("clear sky", "Trời quang");

        ls.add("hà nội");
        ls.add("hà đông");
        ls.add("hà giang");
        ls.add("bắc giang");
        ls.add("london");
        ls.add("ottawa");
        ls.add("tokyo");
    }

    public String data() {
        String dt = ioFile.readFile("mainWeather.bat", mContext);
        return dt;
    }

    private void updateUI(AddressInfo add, String rs) {
        if (rs == null || rs == "") {
            rs = data();
        }
        OpenWeatherJson result = new Gson().fromJson(rs, OpenWeatherJson.class);
        int tempmain, tempmax, tempmin;
        double t = result.getMain().getTemp() - 273.15;
        tempmain = (int) t;
        String daily = ioFile.readFile("dailys.bat", MainActivity.this);
        ListDailys dl = new Gson().fromJson(daily, ListDailys.class);
        double max = dl.getList().get(0).getTemp().getMax();
        tempmax = (int) max;
        double min = dl.getList().get(0).getTemp().getMin();
        tempmin = (int) min;
        //kết quả trả về json thành object
        wtCity.setText(add.getCity());
        city.setText(add.getCity());
        if (!result.getWeather().get(0).getMain().equals("rain")) {
            imgTT.setImageResource(R.drawable.anim_sun);
            animTT = (AnimationDrawable) imgTT.getDrawable();
            animTT.start();
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

        Context context = MainActivity.this;
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget);
        ComponentName thisWidget = new ComponentName(context, widget.class);
        remoteViews.setTextViewText(R.id.textView2, result.getVisibility() + "m");
        remoteViews.setTextViewText(R.id.textView3, result.getMain().getPressure() + " hpa");
        remoteViews.setTextViewText(R.id.textView4, result.getMain().getHumidity() + "%");
        remoteViews.setTextViewText(R.id.textView5, result.getWind().getSpeed() + "km/h");
        remoteViews.setTextViewText(R.id.tvTemp, tempmain + "°C");
        remoteViews.setTextViewText(R.id.textView13, status);
        remoteViews.setTextViewText(R.id.cityWidget, result.getName());
        appWidgetManager.updateAppWidget(thisWidget, remoteViews);
        // làm việc với giao diện ở đây;
    }


    private void setIcon() {
        imgvisibility.setImageResource(R.drawable.anim_visibility);
        animVisibility = (AnimationDrawable) imgvisibility.getDrawable();
        animVisibility.start();
        imgpressure.setImageResource(R.drawable.anim_air);
        animPressure = (AnimationDrawable) imgpressure.getDrawable();
        animPressure.start();
        imghumidity.setImageResource(R.drawable.humidity_anim);
        animHumidity = (AnimationDrawable) imghumidity.getDrawable();
        animHumidity.start();
        imgwind.setImageResource(R.drawable.anim_wind);
        animWind = (AnimationDrawable) imgwind.getDrawable();
        animWind.start();
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

    private AddressInfo getAddress(double lat, double lon) {
        AddressInfo addressInfo = new AddressInfo();
        if (Geocoder.isPresent()) {
            Geocoder geocoder = new Geocoder(this);
            try {
                List<Address> ad = geocoder.getFromLocation(lat, lon, 1);
                Log.i(TAG, ad.toString());
                if (ad != null) {
                    for (Address name : ad) {
                        Log.i(TAG, name.toString());
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
                        if (road != "" && road != "Unnamed Road" && road != null) {
                            address.setText(road + "-" + subarea + "-" + ct);
                        } else {
                            if (subarea != "" && subarea != null) {
                                address.setText(subarea + "-" + ct);
                            } else {
                                address.setText(ct);
                            }
                        }

                        return addressInfo;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return addressInfo;
    }
}
