package com.example.seajobnow.ApiEntity.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PostSpinnerDataRequest {

    @SerializedName("employementType")
    @Expose
    private EmployementTypeRequest employementType;
    @SerializedName("rank")
    @Expose
    private List<RankRequest> rank = null;
    @SerializedName("shipType")
    @Expose
    private List<ShipTypeRequest> shipType = null;
    @SerializedName("department")
    @Expose
    private List<DepartmentRequest> department = null;
    @SerializedName("designation")
    @Expose
    private List<DesignationRequest> designation = null;
    @SerializedName("salary")
    @Expose
    private List<SalaryRequest> salary = null;

    public EmployementTypeRequest getEmployementType() {
        return employementType;
    }

    public void setEmployementType(EmployementTypeRequest employementType) {
        this.employementType = employementType;
    }

    public List<RankRequest> getRank() {
        return rank;
    }

    public void setRank(List<RankRequest> rank) {
        this.rank = rank;
    }

    public List<ShipTypeRequest> getShipType() {
        return shipType;
    }

    public void setShipType(List<ShipTypeRequest> shipType) {
        this.shipType = shipType;
    }

    public List<DepartmentRequest> getDepartment() {
        return department;
    }

    public void setDepartment(List<DepartmentRequest> department) {
        this.department = department;
    }

    public List<DesignationRequest> getDesignation() {
        return designation;
    }

    public void setDesignation(List<DesignationRequest> designation) {
        this.designation = designation;
    }

    public List<SalaryRequest> getSalary() {
        return salary;
    }

    public void setSalary(List<SalaryRequest> salary) {
        this.salary = salary;
    }
}
