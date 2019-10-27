package com.appbtl.appweather.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SysHours {
    @SerializedName("pod")
    @Expose
    private String pod;

    public String getPod() {
        return pod;
    }

    public void setPod(String pod) {
        this.pod = pod;
    }
}
