package ua.rogdan.trag.data;


import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

public class TaskPoint {
    private int id;
    private String latitude;
    private String longitude;
    private String description;
    private String deadlineTime;
    private ArrayList<Goods> goodsList;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDeadlineTime() {
        return deadlineTime;
    }

    public void setDeadlineTime(String deadlineTime) {
        this.deadlineTime = deadlineTime;
    }

    public ArrayList<Goods> getGoodsList() {
        return goodsList;
    }

    public void setGoodsList(ArrayList<Goods> goodsList) {
        this.goodsList = goodsList;
    }

    public LatLng getCoordinates() {
        return new LatLng(Double.valueOf(latitude), Double.valueOf(longitude));
    }
}
