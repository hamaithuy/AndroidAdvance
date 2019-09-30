package com.example.jamlos.Model;

import java.util.ArrayList;

public class Request {
    private String UserName;
    private String PhoneNumber;
    private String Address;
    private ArrayList<Order> lstOrder;
    private String TotalValue;

    public Request() {
    }

    public Request(String userName, String phoneNumber, String address, ArrayList<Order> lstOrder, String totalValue) {
        UserName = userName;
        PhoneNumber = phoneNumber;
        Address = address;
        this.lstOrder = lstOrder;
        TotalValue = totalValue;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public ArrayList<Order> getLstOrder() {
        return lstOrder;
    }

    public void setLstOrder(ArrayList<Order> lstOrder) {
        this.lstOrder = lstOrder;
    }

    public String getTotalValue() {
        return TotalValue;
    }

    public void setTotalValue(String totalValue) {
        TotalValue = totalValue;
    }
}


