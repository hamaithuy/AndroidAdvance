package com.example.quanlycuahang.Admin.Mon;

public class Mon {
    private String TenMon;
    private String LoaiMon;
    private Integer Gia;
    private Integer Loai;
    private Integer SoLuong;

    public Mon() {
    }

    public Mon(String tenMon, String loaiMon, Integer gia, Integer loai, Integer soLuong) {
        TenMon = tenMon;
        LoaiMon = loaiMon;
        Gia = gia;
        Loai = loai;
        SoLuong = soLuong;
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

    public Integer getGia() {
        return Gia;
    }

    public void setGia(Integer gia) {
        Gia = gia;
    }

    public Integer getLoai() {
        return Loai;
    }

    public void setLoai(Integer loai) {
        Loai = loai;
    }

    public Integer getSoLuong() {
        return SoLuong;
    }

    public void setSoLuong(Integer soLuong) {
        SoLuong = soLuong;
    }
}
