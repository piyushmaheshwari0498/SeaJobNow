package com.example.seajobnow.ApiEntity.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginRequest {
    @SerializedName("comp_code")
    @Expose
    private String compCode;
    @SerializedName("comp_name")
    @Expose
    private String compName;
    @SerializedName("comp_position")
    @Expose
    private String compPosition;
    @SerializedName("comp_status")
    @Expose
    private String compStatus;
    @SerializedName("comp_id")
    @Expose
    private String compId;
    @SerializedName("acceess_key")
    @Expose
    private String acceessKey;

    public String getCompCode() {
        return compCode;
    }

    public void setCompCode(String compCode) {
        this.compCode = compCode;
    }

    public String getCompName() {
        return compName;
    }

    public void setCompName(String compName) {
        this.compName = compName;
    }

    public String getCompPosition() {
        return compPosition;
    }

    public void setCompPosition(String compPosition) {
        this.compPosition = compPosition;
    }

    public String getCompStatus() {
        return compStatus;
    }

    public void setCompStatus(String compStatus) {
        this.compStatus = compStatus;
    }

    public String getCompId() {
        return compId;
    }

    public void setCompId(String compId) {
        this.compId = compId;
    }

    public String getAcceessKey() {
        return acceessKey;
    }

    public void setAcceessKey(String acceessKey) {
        this.acceessKey = acceessKey;
    }
}
