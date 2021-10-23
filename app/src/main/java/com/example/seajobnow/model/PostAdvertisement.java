package com.example.seajobnow.model;

public class PostAdvertisement {

    String add_title;
    String ship_type;
    String start_date,end_date;

    public PostAdvertisement(String job_title, String ship_type, String start_date, String end_date) {
        this.add_title = job_title;
        this.ship_type = ship_type;
        this.start_date = start_date;
        this.end_date = end_date;
    }

    public String getAdd_title() {
        return add_title;
    }

    public void setAdd_title(String add_title) {
        this.add_title = add_title;
    }

    public String getShip_type() {
        return ship_type;
    }

    public void setShip_type(String ship_type) {
        this.ship_type = ship_type;
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
