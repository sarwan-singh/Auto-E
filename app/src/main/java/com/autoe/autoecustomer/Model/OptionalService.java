package com.autoe.autoecustomer.Model;

public class OptionalService {
    private String serviceName;
    private String serviceId;
    private String servicePrice;
    private String serviceType;
    private String serviceDescription;

    public OptionalService() {
    }

    public OptionalService(String serviceName, String serviceId, String servicePrice, String serviceType, String serviceDescription) {
        this.serviceName = serviceName;
        this.serviceId = serviceId;
        this.servicePrice = servicePrice;
        this.serviceType = serviceType;
        this.serviceDescription = serviceDescription;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getServicePrice() {
        return servicePrice;
    }

    public void setServicePrice(String servicePrice) {
        this.servicePrice = servicePrice;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public String getServiceDescription() {
        return serviceDescription;
    }

    public void setServiceDescription(String serviceDescription) {
        this.serviceDescription = serviceDescription;
    }
}
