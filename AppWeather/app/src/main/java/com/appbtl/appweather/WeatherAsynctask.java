package com.appbtl.appweather;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.appbtl.appweather.model.OpenWeatherJson;
import com.google.gson.Gson;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public abstract class WeatherAsynctask extends AsyncTask<String, Void, OpenWeatherJson> {
    OkHttpClient client = new OkHttpClient();
    OpenWeatherJson result;
    @Override
    protected void onPostExecute(OpenWeatherJson openWeatherJson) {
        doJson(openWeatherJson);
    }

    @Override
    protected OpenWeatherJson doInBackground(String... strings) {
        Request.Builder builder = new Request.Builder();
        builder.url(strings[0]);

        Request request = builder.build();

        try {

            Response response = client.newCall(request).execute();
            if (response!=null){
            String rs = response.body().string();
            result =  new Gson().fromJson(rs,OpenWeatherJson.class);
            return result;}

        } catch (Exception e) {

        }
        return null;
    }

    public abstract void doJson(OpenWeatherJson result);
}
