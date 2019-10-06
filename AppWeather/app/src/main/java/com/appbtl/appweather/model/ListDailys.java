package com.appbtl.appweather.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ListDailys {
    @SerializedName("list")
    @Expose
    private List<OpenWeatherJsonDaily> list;

    public List<OpenWeatherJsonDaily> getList() {
        return list;
    }

    public void setList(List<OpenWeatherJsonDaily> list) {
        this.list = list;
    }
}
