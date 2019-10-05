package com.example.quanlycuahang.Admin.Mon;

public class Mon {
    private String TenMon;
    private Integer Gia;
    private Integer SoLuong;

    public Mon() {
    }

    public Mon(String tenMon, Integer gia, Integer soLuong) {
        TenMon = tenMon;
        Gia = gia;
        SoLuong = soLuong;
    }

    public String getTenMon() {
        return TenMon;
    }

    public void setTenMon(String tenMon) {
        TenMon = tenMon;
    }

    public Integer getGia() {
        return Gia;
    }

    public void setGia(Integer gia) {
        Gia = gia;
    }

    public Integer getSoLuong() {
        return SoLuong;
    }

    public void setSoLuong(Integer soLuong) {
        SoLuong = soLuong;
    }
}
