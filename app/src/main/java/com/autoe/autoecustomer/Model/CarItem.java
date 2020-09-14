package com.autoe.autoecustomer.Model;

public class CarItem {
    private String Label_of_car;
    private String Description_of_car;
    private String price_of_car;

    public CarItem() {
    }

    public CarItem(String label_of_car, String description_of_car, String price_of_car) {
        Label_of_car = label_of_car;
        Description_of_car = description_of_car;
        this.price_of_car = price_of_car;
    }

    public String getLabel_of_car() {
        return Label_of_car;
    }

    public void setLabel_of_car(String label_of_car) {
        Label_of_car = label_of_car;
    }

    public String getDescription_of_car() {
        return Description_of_car;
    }

    public void setDescription_of_car(String description_of_car) {
        Description_of_car = description_of_car;
    }

    public String getPrice_of_car() {
        return price_of_car;
    }

    public void setPrice_of_car(String price_of_car) {
        this.price_of_car = price_of_car;
    }
}
