package com.autoe.autoecustomer.Model;

public class LocationItem {
    private String label;
    private String address;
    private int mapResource;

    public LocationItem() {
    }

    public LocationItem(String label, String address, int mapResource) {
        this.label = label;
        this.address = address;
        this.mapResource = mapResource;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getMapResource() {
        return mapResource;
    }

    public void setMapResource(int mapResource) {
        this.mapResource = mapResource;
    }
}
