package com.nitjsr.musafir;

public class BusStop {

    private int BusStopId;
    private String location;
    private String busList;

    public int getBusStopId() { return BusStopId; }

    public void setBusStopId(int busStopId) { BusStopId = busStopId; }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getBusList() {
        return busList;
    }

    public void setBusList(String busList) {
        this.busList = busList;
    }

}