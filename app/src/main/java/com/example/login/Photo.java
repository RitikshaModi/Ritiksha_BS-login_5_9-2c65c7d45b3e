package com.example.login;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Photo {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("width")
    @Expose
    private Integer width;
    @SerializedName("height")
    @Expose
    private Integer height;
    @SerializedName("urls")
    @Expose
    private Urls urls;
    public Urls getUrls() {
        return urls;
    }
    public void setUrls(Urls urls) {
        this.urls = urls;
    }
}
