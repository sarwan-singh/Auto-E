package com.autoe.autoecustomer.Model;

public class Location {

    private String latitude;
    private String longitude;
    private String labelOfLocation;
    private String descOfLocation;

    public Location(String latitude, String longitude, String labelOfLocation, String descOfLocation) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.labelOfLocation = labelOfLocation;
        this.descOfLocation = descOfLocation;
    }

    public Location() {
    }

    public String getLatitude() {
        return latitude;
    }

    public String getDescOfLocation() {
        return descOfLocation;
    }

    public void setDescOfLocation(String descOfLocation) {
        this.descOfLocation = descOfLocation;
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

    public String getLabelOfLocation() {
        return labelOfLocation;
    }

    public void setLabelOfLocation(String labelOfLocation) {
        this.labelOfLocation = labelOfLocation;
    }
}
