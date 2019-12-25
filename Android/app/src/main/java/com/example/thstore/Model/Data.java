package com.example.thstore.Model;


import java.io.Serializable;

public class Data implements Serializable {
    private String Image;
    private String Name;
    private String Price;
    private String ID;

    public Data() {}


    public Data( String image, String name, String price, String id ){
        Image = image;
        Name = name;
        Price = price;
        ID = id;
    }

    public String getID() {
        return ID;
    }

    public void setID(String id) { ID = id; }

    public String getImage(){
        return Image;
    }

    public void setPicture(String picture){
        Image = picture;
    }


    public String getName(){
        return Name;
    }

    public void setName(String name)
    {
        Name = name;
    }


    public String getPrice(){return Price;}

    public void setPrice(String price) {
        Price = price;
    }


}
