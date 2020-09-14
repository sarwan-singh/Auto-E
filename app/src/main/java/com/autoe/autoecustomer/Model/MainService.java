package com.autoe.autoecustomer.Model;

public class MainService {
    private String serviceName;
    private String serviceDescription;
    private String serviceId;
    private String servicePriceCarSedan;
    private String servicePriceCarXUV;
    private String servicePriceCarHatchBack;
    private String serviceType;

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getServiceDescription() {
        return serviceDescription;
    }

    public void setServiceDescription(String serviceDescription) {
        this.serviceDescription = serviceDescription;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getServicePriceCarSedan() {
        return servicePriceCarSedan;
    }

    public void setServicePriceCarSedan(String servicePriceCarSedan) {
        this.servicePriceCarSedan = servicePriceCarSedan;
    }

    public String getServicePriceCarXUV() {
        return servicePriceCarXUV;
    }

    public void setServicePriceCarXUV(String servicePriceCarXUV) {
        this.servicePriceCarXUV = servicePriceCarXUV;
    }

    public String getServicePriceCarHatchBack() {
        return servicePriceCarHatchBack;
    }

    public void setServicePriceCarHatchBack(String servicePriceCarHatchBack) {
        this.servicePriceCarHatchBack = servicePriceCarHatchBack;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public MainService(String serviceName, String serviceDescription, String serviceId, String servicePriceCarSedan, String servicePriceCarXUV, String servicePriceCarHatchBack, String serviceType) {
        this.serviceName = serviceName;
        this.serviceDescription = serviceDescription;
        this.serviceId = serviceId;
        this.servicePriceCarSedan = servicePriceCarSedan;
        this.servicePriceCarXUV = servicePriceCarXUV;
        this.servicePriceCarHatchBack = servicePriceCarHatchBack;
        this.serviceType = serviceType;
    }

    public MainService() {
    }
}
