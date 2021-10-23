package com.example.seajobnow.ApiEntity.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RankRequest {
    @SerializedName("actual_rank_id")
    @Expose
    private String actualRankId;
    @SerializedName("actual_rank_name")
    @Expose
    private String actualRankName;

    public String getActualRankId() {
        return actualRankId;
    }

    public void setActualRankId(String actualRankId) {
        this.actualRankId = actualRankId;
    }

    public String getActualRankName() {
        return actualRankName;
    }

    public void setActualRankName(String actualRankName) {
        this.actualRankName = actualRankName;
    }


}
