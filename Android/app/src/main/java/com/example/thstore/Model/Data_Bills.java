package com.example.thstore.Model;

public class Data_Bills {

    private String Name;
    private String Price;
    private String Color;
    private String Size;
    private String Image_Cart;
    private String Account;
    private String ID;
    private String Total;
    //String color,

    public Data_Bills(){}

    public Data_Bills(String name, String price,  String account, String image_cart, String size,  String id, String total)
    {
        Name = name;
        Price = price;
        //Color = color;
        Size = size;
        Account = account;
        Image_Cart = image_cart;
        ID = id;
        Total = total;
    }

    public String getID() {
        return ID;
    }

    public void setID(String id) {
        ID = id;
    }

    public String getImage_Cart() {
        return Image_Cart;
    }

    public void setImage(String image_cart) {
        Image_Cart = image_cart;
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


    /*public String getColor() {
        return Color;
    }

    public void setColor(String color) {
        Color = color;
    }*/

    public String getSize() {
        return Size;
    }

    public void setSize(String size) {
        Size = size;
    }


    public String getAccount() {
        return Account;
    }

    public void setAcount(String acount) {
        Account = acount;
    }

    public String getTotal() {
        return Total;
    }

    public void setTotal(String total) {
        Total = total;
    }
}
