package com.autoe.autoecustomer.Model;

public class User {
    private String name;
    private String mobile;
    private String id;
    private Location homeAddress;
    private Location officeAddress;
    private Location otherAddress;
    private String profilePhoto;
    private String email;

    public User(String name, String mobile,String email, String id, Location homeAddress, Location officeAddress, Location otherAddress, String profilePhoto) {
        this.name = name;
        this.mobile = mobile;
        this.id = id;
        this.homeAddress = homeAddress;
        this.officeAddress = officeAddress;
        this.otherAddress = otherAddress;
        this.profilePhoto = profilePhoto;
        this.email= email;
    }

    public User() {
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Location getHomeAddress() {
        return homeAddress;
    }

    public void setHomeAddress(Location homeAddress) {
        this.homeAddress = homeAddress;
    }

    public Location getOfficeAddress() {
        return officeAddress;
    }

    public void setOfficeAddress(Location officeAddress) {
        this.officeAddress = officeAddress;
    }

    public Location getOtherAddress() {
        return otherAddress;
    }

    public void setOtherAddress(Location otherAddress) {
        this.otherAddress = otherAddress;
    }

    public String getProfilePhoto() {
        return profilePhoto;
    }

    public void setProfilePhoto(String profilePhoto) {
        this.profilePhoto = profilePhoto;
    }
}