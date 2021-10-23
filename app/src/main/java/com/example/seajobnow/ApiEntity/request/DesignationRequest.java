package com.example.seajobnow.ApiEntity.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DesignationRequest {

    @SerializedName("cdgm_id")
    @Expose
    private String cdgmId;
    @SerializedName("cdgm_designation")
    @Expose
    private String cdgmDesignation;

    public String getCdgmId() {
        return cdgmId;
    }

    public void setCdgmId(String cdgmId) {
        this.cdgmId = cdgmId;
    }

    public String getCdgmDesignation() {
        return cdgmDesignation;
    }

    public void setCdgmDesignation(String cdgmDesignation) {
        this.cdgmDesignation = cdgmDesignation;
    }
}
