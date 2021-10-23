package com.example.seajobnow.ApiEntity.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SalaryRequest {

    @SerializedName("csm_id")
    @Expose
    private String csmId;
    @SerializedName("salary")
    @Expose
    private String salary;

    public String getCsmId() {
        return csmId;
    }

    public void setCsmId(String csmId) {
        this.csmId = csmId;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }
}
