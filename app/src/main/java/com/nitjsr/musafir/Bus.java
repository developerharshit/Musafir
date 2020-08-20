package com.nitjsr.musafir;

import java.io.Serializable;

public class Bus {
    private String BUS_NAME,SRC,DST,CURRENT_LOCATION;
    private int BUS_ID,TOTAL_SEATS,SEATS_OCCUPIED,SEATS_AVAILABLE;

    public String getBUS_NAME() {
        return BUS_NAME;
    }

    public void setBUS_NAME(String BUS_NAME) {
        this.BUS_NAME = BUS_NAME;
    }

    public String getSRC() {
        return SRC;
    }

    public void setSRC(String SRC) {
        this.SRC = SRC;
    }

    public String getDST() {
        return DST;
    }

    public void setDST(String DST) {
        this.DST = DST;
    }

    public String getCURRENT_LOCATION() {
        return CURRENT_LOCATION;
    }

    public void setCURRENT_LOCATION(String CURRENT_LOCATION) {
        this.CURRENT_LOCATION = CURRENT_LOCATION;
    }

    public int getBUS_ID() {
        return BUS_ID;
    }

    public void setBUS_ID(int BUS_ID) {
        this.BUS_ID = BUS_ID;
    }

    public int getTOTAL_SEATS() {
        return TOTAL_SEATS;
    }

    public void setTOTAL_SEATS(int TOTAL_SEATS) {
        this.TOTAL_SEATS = TOTAL_SEATS;
    }

    public int getSEATS_OCCUPIED() {
        return SEATS_OCCUPIED;
    }

    public void setSEATS_OCCUPIED(int SEATS_OCCUPIED) {
        this.SEATS_OCCUPIED = SEATS_OCCUPIED;
    }

    public int getSEATS_AVAILABLE() {
        return SEATS_AVAILABLE;
    }

    public void setSEATS_AVAILABLE(int SEATS_AVAILABLE) {
        this.SEATS_AVAILABLE = SEATS_AVAILABLE;
    }
}

