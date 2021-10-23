package com.example.seajobnow.ApiEntity.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ShipTypeRequest {
    @SerializedName("vt_id")
    @Expose
    private String vtId;
    @SerializedName("vt_name")
    @Expose
    private String vtName;

    public String getVtId() {
        return vtId;
    }

    public void setVtId(String vtId) {
        this.vtId = vtId;
    }

    public String getVtName() {
        return vtName;
    }

    public void setVtName(String vtName) {
        this.vtName = vtName;
    }

}
