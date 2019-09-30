package com.example.jamlos.Model;

public class Category {
    String Name;
    String Image;
    String Type;

    public Category() {
    }

    public Category(String name, String image, String type) {
        Name = name;
        Image = image;
        Type = type;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getImage() { return Image; }

    public void setImage(String image) {
        Image = image;
    }

    public String getType() {return Type; }

    public void setType(String type) {Type = type; }
}
