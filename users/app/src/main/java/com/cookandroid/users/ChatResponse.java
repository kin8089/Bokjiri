package com.cookandroid.users;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ChatResponse implements Serializable {
    @SerializedName("message")
    public String message;
    @SerializedName("url")
    public String url;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
