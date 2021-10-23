package com.example.seajobnow.model;

public class PostJobs {

    String job_title;
    String department,rank,salary,ship_type,location;
    String start_date,end_date;

    public PostJobs(String job_title, String department, String rank, String salary, String ship_type, String location, String start_date, String end_date) {
        this.job_title = job_title;
        this.department = department;
        this.rank = rank;
        this.salary = salary;
        this.ship_type = ship_type;
        this.location = location;
        this.start_date = start_date;
        this.end_date = end_date;
    }

    public String getJob_title() {
        return job_title;
    }

    public void setJob_title(String job_title) {
        this.job_title = job_title;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public String getShip_type() {
        return ship_type;
    }

    public void setShip_type(String ship_type) {
        this.ship_type = ship_type;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }
}
