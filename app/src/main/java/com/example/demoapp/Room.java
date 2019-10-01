package com.example.demoapp;
import java.util.ArrayList;
import java.util.Date;
public class Room {
    public int image;
    public String title;
    public float price;
    public String address;
    public String phoneNumber;
    public String acreage;
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



    public Room(int image, String title, float price, String address, String phoneNumber,
                String acreage, Date timePost, String description,
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


    public static ArrayList<Room> makeListRoom(){
        ArrayList<Room> listRoom = new ArrayList<Room>();
        Date dt = new Date();
        Room room1 = new Room(R.drawable.phongtro,"Còn nhiều phòng trọ cho thuê ở Định Công",3000000,"Ngõ 245 Phố Định Công",
                "0364968346 - anh Sơn","30 m2", dt,"Có nhiều phong cho thuê, mại zô",
                true,true,true,true,true,true,true,true);
        Room room2 = new Room(R.drawable.phongtro2,"Còn nhiều phòng trọ cho thuê ở Định Công",3000000,"Ngõ 245 Phố Định Công",
                "0364968346 - anh Sơn","30 m2", dt,"Có nhiều phong cho thuê, mại zô",
                true,true,true,true,true,true,true,true);
        Room room3 = new Room(R.drawable.phongtro,"Còn nhiều phòng trọ cho thuê ở Định Công",3000000,"Ngõ 245 Phố Định Công",
                "0364968346 - anh Sơn","30 m2", dt,"Có nhiều phong cho thuê, mại zô",
                true,true,true,true,true,true,true,true);
        Room room4 = new Room(R.drawable.phongtro2,"Còn nhiều phòng trọ cho thuê ở Định Công",3000000,"Ngõ 245 Phố Định Công",
                "0364968346 - anh Sơn","30 m2", dt,"Có nhiều phong cho thuê, mại zô",
                true,true,true,true,true,true,true,true);

        listRoom.add(room1);
        listRoom.add(room2);
        listRoom.add(room3);
        listRoom.add(room4);
        return listRoom;
    }

}
