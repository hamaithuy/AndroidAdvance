package com.example.demoapp;
import java.util.ArrayList;
import java.util.Date;
public class Room {
    public int image;
    public String title;
    public double price;
    public String address;
    public String phoneNumber;
    public float acreage;
    public Date timePost;
    public String description;

    public Boolean wifi;
    public Boolean ownWc;
    public Boolean keepCar;
    public Boolean freedom;
    public Boolean kitchen;
    public Boolean airMachine;
    public Boolean fridge;
    public Boolean washMachine;

    public int kindPost; // 1-Cho thuê , 2- Ở ghép
    public int kindRoom; // 1-Phòng, 2- Căn hộ, 3- Căn hộ mini, 4- Nguyên căn
    public String boss;  // Chủ nhà


    public Room(int image, String title, double price, String address, String phoneNumber,
                float acreage, Date timePost, String description,
                Boolean wifi, Boolean ownWc, Boolean keepCar, Boolean freedom, Boolean kitchen,
                Boolean airMachine, Boolean fridge, Boolean washMachine) {

                this.image = image;
                this.title = title;
                this.price = price;
                this.address = address;
                this.phoneNumber = phoneNumber;
                this.acreage = acreage;
                this.timePost =timePost;
                this.description = description;

                this.wifi = wifi;
                this.ownWc = ownWc;
                this.keepCar = keepCar;
                this.freedom = freedom;
                this.kitchen = kitchen;
                this.airMachine = airMachine;
                this.fridge = fridge;
                this.washMachine = washMachine;


    }

    public Room() {
    }



    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public float getAcreage() {
        return acreage;
    }

    public void setAcreage(float acreage) {
        this.acreage = acreage;
    }

    public Date getTimePost() {
        return timePost;
    }

    public void setTimePost(Date timePost) {
        this.timePost = timePost;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getWifi() {
        return wifi;
    }

    public void setWifi(Boolean wifi) {
        this.wifi = wifi;
    }

    public Boolean getOwnWc() {
        return ownWc;
    }

    public void setOwnWc(Boolean ownWc) {
        this.ownWc = ownWc;
    }

    public Boolean getKeepCar() {
        return keepCar;
    }

    public void setKeepCar(Boolean keepCar) {
        this.keepCar = keepCar;
    }

    public Boolean getFreedom() {
        return freedom;
    }

    public void setFreedom(Boolean freedom) {
        this.freedom = freedom;
    }

    public Boolean getKitchen() {
        return kitchen;
    }

    public void setKitchen(Boolean kitchen) {
        this.kitchen = kitchen;
    }

    public Boolean getAirMachine() {
        return airMachine;
    }

    public void setAirMachine(Boolean airMachine) {
        this.airMachine = airMachine;
    }

    public Boolean getFridge() {
        return fridge;
    }

    public void setFridge(Boolean fridge) {
        this.fridge = fridge;
    }

    public Boolean getWashMachine() {
        return washMachine;
    }

    public void setWashMachine(Boolean washMachine) {
        this.washMachine = washMachine;
    }

    public int getKindPost() {
        return kindPost;
    }

    public void setKindPost(int kindPost) {
        this.kindPost = kindPost;
    }

    public int getKindRoom() {
        return kindRoom;
    }

    public void setKindRoom(int kindRoom) {
        this.kindRoom = kindRoom;
    }

    public String getBoss() {
        return boss;
    }

    public void setBoss(String boss) {
        this.boss = boss;
    }
}
