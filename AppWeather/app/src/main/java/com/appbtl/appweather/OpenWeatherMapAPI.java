package com.appbtl.appweather;

import com.appbtl.appweather.model.OpenWeatherJson;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class OpenWeatherMapAPI {
    static final String KEY = "b87ce30a14229dd8e26f167dd2111f06";
public static OpenWeatherJson prediction(String Cityname){
    try {
        String location = URLEncoder.encode(Cityname,"UTF-8");
        URL url = new URL("http://api.openweathermap.org/data/2.5/weather?q="+location+"&appid"+KEY);
        InputStreamReader inputStreamReader = new InputStreamReader(url.openStream(),"UTF-8");
        OpenWeatherJson result = new Gson().fromJson(inputStreamReader,OpenWeatherJson.class);
        return result;
    } catch (UnsupportedEncodingException e) {
        e.printStackTrace();
    } catch (MalformedURLException e) {
        e.printStackTrace();
    } catch (IOException e) {
        e.printStackTrace();
    }
    return null;
    }
    public static OpenWeatherJson prediction(double lat,double lon){
        try {
            String key = "b87ce30a14229dd8e26f167dd2111f06";
            URL url = new URL("http://api.openweathermap.org/data/2.5/weather?lat="+lat+"&lon="+lon+"&appid="+KEY);
            InputStreamReader inputStreamReader = new InputStreamReader(url.openStream(),"UTF-8");
            OpenWeatherJson result = new Gson().fromJson(inputStreamReader,OpenWeatherJson.class);
            return result;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
