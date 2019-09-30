package com.example.jamlos.Model;

import java.util.ArrayList;

public class Product {
    private String Name;
    private String Price;
    private ArrayList<String> lstImage;
    private String Discount;
    private String Type;

    public Product() {
    }

    public Product(String name, String price, ArrayList<String> lstImage, String discount, String type) {
        Name = name;
        Price = price;
        this.lstImage = lstImage;
        Discount = discount;
        Type = type;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public ArrayList<String> getLstImage() {
        return lstImage;
    }

    public void setLstImage(ArrayList<String> lstImage) {
        this.lstImage = lstImage;
    }

    public String getDiscount() {
        return Discount;
    }

    public void setDiscount(String discount) {
        Discount = discount;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }
}
