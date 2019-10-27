package com.appbtl.appweather.model;

public class AddressInfo {
    private String city;
    private String area;
    private String village;
    private String place;
    private String country;
    private String road;

    public String getRoad() {
        return road;
    }

    public void setRoad(String road) {
        this.road = road;
    }

    public AddressInfo() {
        this.city = "";
        this.area = "";
        this.village = "";
        this.place = "";
        this.country = "";
        this.road="";
    }

    public AddressInfo(String city, String area, String village, String place, String country, String road) {
        this.city = city;
        this.area = area;
        this.village = village;
        this.place = place;
        this.country = country;
        this.road = road;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getVillage() {
        return village;
    }

    public void setVillage(String village) {
        this.village = village;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
