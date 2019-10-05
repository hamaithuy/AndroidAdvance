package com.example.banhang.Model;

public class Products {
    private String ten,gia,mota,time,date,pid,image;
    public Products(){

    }
    public Products(String ten, String gia, String mota, String time, String date, String pid, String image) {
        this.ten = ten;
        this.gia = gia;
        this.mota = mota;
        this.time = time;
        this.date = date;
        this.pid = pid;
        this.image = image;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getGia() {
        return gia;
    }

    public void setGia(String gia) {
        this.gia = gia;
    }

    public String getMota() {
        return mota;
    }

    public void setMota(String mota) {
        this.mota = mota;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
