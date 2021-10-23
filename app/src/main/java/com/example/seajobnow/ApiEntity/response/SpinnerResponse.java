package com.example.seajobnow.ApiEntity.response;

import com.example.seajobnow.ApiEntity.request.SpinnerDataRequest;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SpinnerResponse {
    @SerializedName("StatusCode")
    @Expose
    private Integer statusCode;
    @SerializedName("StatusMessage")
    @Expose
    private String statusMessage;
    @SerializedName("data")
    @Expose
    private SpinnerDataRequest data;

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    public SpinnerDataRequest getData() {
        return data;
    }

    public void setData(SpinnerDataRequest data) {
        this.data = data;
    }

}
