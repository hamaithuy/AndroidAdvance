package com.appbtl.appweather;

import android.os.AsyncTask;

import com.appbtl.appweather.model.ListDailys;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class WeatherDailysAsynctask extends AsyncTask<String,Void,String> {
    OkHttpClient client = new OkHttpClient();
    @Override
    protected String doInBackground(String... strings) {
        Request.Builder builder = new Request.Builder();
        builder.url(strings[0]);
        Request request = builder.build();
        try {
            Response response = client.newCall(request).execute();
            String rs = response.body().string();
            return rs;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
