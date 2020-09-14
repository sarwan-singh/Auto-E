package com.autoe.autoecustomer.Model;

public class CarType {
    private String carType;
    private String carTypeId;
    private String priceForType;


    public CarType(String carType, String carTypeId, String priceForType) {
        this.carType = carType;
        this.carTypeId = carTypeId;
        this.priceForType = priceForType;
    }

    public CarType() {
    }


    public String getCarType() {
        return carType;
    }

    public void setCarType(String carType) {
        this.carType = carType;
    }

    public String getCarTypeId() {
        return carTypeId;
    }

    public void setCarTypeId(String carTypeId) {
        this.carTypeId = carTypeId;
    }

    public String getPriceForType() {
        return priceForType;
    }

    public void setPriceForType(String priceForType) {
        this.priceForType = priceForType;
    }
}
