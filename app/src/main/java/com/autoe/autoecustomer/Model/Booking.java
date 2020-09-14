package com.autoe.autoecustomer.Model;

import java.util.List;

public class Booking {

    private String nameOfUser;
    private String emailOfUser;
    private String mobileOfUser;
    private Location locationOfUser;
    private String carType;
    private List<String> optionalServices;
    private String mainPlan;
    private String executiveAssigned;
    private String dateAdded;
    private String dateOfService;
    private String mainTotal;
    private String optionalTotal;
    private String totalPrice;

    public Booking(String nameOfUser, String emailOfUser, String mobileOfUser, Location locationOfUser, String carType, List<String> optionalServices, String mainPlan, String executiveAssigned, String dateAdded, String dateOfService, String mainTotal, String optionalTotal, String totalPrice) {
        this.nameOfUser = nameOfUser;
        this.emailOfUser = emailOfUser;
        this.mobileOfUser = mobileOfUser;
        this.locationOfUser = locationOfUser;
        this.carType = carType;
        this.optionalServices = optionalServices;
        this.mainPlan = mainPlan;
        this.executiveAssigned = executiveAssigned;
        this.dateAdded = dateAdded;
        this.dateOfService = dateOfService;
        this.mainTotal = mainTotal;
        this.optionalTotal = optionalTotal;
        this.totalPrice = totalPrice;
    }

    public Booking() {
    }

    public String getNameOfUser() {
        return nameOfUser;
    }

    public void setNameOfUser(String nameOfUser) {
        this.nameOfUser = nameOfUser;
    }

    public String getEmailOfUser() {
        return emailOfUser;
    }

    public void setEmailOfUser(String emailOfUser) {
        this.emailOfUser = emailOfUser;
    }

    public String getMobileOfUser() {
        return mobileOfUser;
    }

    public void setMobileOfUser(String mobileOfUser) {
        this.mobileOfUser = mobileOfUser;
    }

    public Location getLocationOfUser() {
        return locationOfUser;
    }

    public void setLocationOfUser(Location locationOfUser) {
        this.locationOfUser = locationOfUser;
    }

    public String getCarType() {
        return carType;
    }

    public void setCarType(String carType) {
        this.carType = carType;
    }

    public List<String> getOptionalServices() {
        return optionalServices;
    }

    public void setOptionalServices(List<String> optionalServices) {
        this.optionalServices = optionalServices;
    }

    public String getMainPlan() {
        return mainPlan;
    }

    public void setMainPlan(String mainPlan) {
        this.mainPlan = mainPlan;
    }

    public String getExecutiveAssigned() {
        return executiveAssigned;
    }

    public void setExecutiveAssigned(String executiveAssigned) {
        this.executiveAssigned = executiveAssigned;
    }

    public String getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(String dateAdded) {
        this.dateAdded = dateAdded;
    }

    public String getDateOfService() {
        return dateOfService;
    }

    public void setDateOfService(String dateOfService) {
        this.dateOfService = dateOfService;
    }

    public String getMainTotal() {
        return mainTotal;
    }

    public void setMainTotal(String mainTotal) {
        this.mainTotal = mainTotal;
    }

    public String getOptionalTotal() {
        return optionalTotal;
    }

    public void setOptionalTotal(String optionalTotal) {
        this.optionalTotal = optionalTotal;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }
}
