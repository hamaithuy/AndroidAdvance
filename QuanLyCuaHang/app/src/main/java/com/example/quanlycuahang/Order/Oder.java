package com.example.quanlycuahang.Order;

public class Oder {
    private String MonID;
    private String TenMon;
    private String LoaiMon;
    private Integer Loai;
    private Integer Gia;
    private Integer Soluong;

    public String getMonID() {
        return MonID;
    }

    public void setMonID(String monID) {
        MonID = monID;
    }

    public String getTenMon() {
        return TenMon;
    }

    public void setTenMon(String tenMon) {
        TenMon = tenMon;
    }

    public String getLoaiMon() {
        return LoaiMon;
    }

    public void setLoaiMon(String loaiMon) {
        LoaiMon = loaiMon;
    }

    public Integer getLoai() {
        return Loai;
    }

    public void setLoai(Integer loai) {
        Loai = loai;
    }

    public Integer getGia() {
        return Gia;
    }

    public void setGia(Integer gia) {
        Gia = gia;
    }

    public Integer getSoluong() {
        return Soluong;
    }

    public void setSoluong(Integer soluong) {
        Soluong = soluong;
    }

    public Oder() {
    }

    public Oder(String monID, String tenMon, String loaiMon, Integer loai, Integer gia, Integer soluong) {
        MonID = monID;
        TenMon = tenMon;
        LoaiMon = loaiMon;
        Loai = loai;
        Gia = gia;
        Soluong = soluong;
    }
}
