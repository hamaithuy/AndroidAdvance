package com.example.quanlycuahang.Admin.HoaDon;

import com.example.quanlycuahang.Order.Oder;

import java.util.Date;
import java.util.List;

public class HoaDon {
    private Date thoigianGhinhan;
    private boolean isHoanThanh;
    private List<Oder> danhSachOder;
    private Integer tongTien;

    public HoaDon() {
    }

    public Date getThoigianGhinhan() {
        return thoigianGhinhan;
    }

    public void setThoigianGhinhan(Date thoigianGhinhan) {
        this.thoigianGhinhan = thoigianGhinhan;
    }

    public boolean isHoanThanh() {
        return isHoanThanh;
    }

    public void setHoanThanh(boolean hoanThanh) {
        isHoanThanh = hoanThanh;
    }

    public List<Oder> getDanhSachOder() {
        return danhSachOder;
    }

    public void setDanhSachOder(List<Oder> danhSachOder) {
        this.danhSachOder = danhSachOder;
    }

    public Integer getTongTien() {
        return tongTien;
    }

    public void setTongTien(Integer tongTien) {
        this.tongTien = tongTien;
    }

    public HoaDon(Date thoigianGhinhan, boolean isHoanThanh, List<Oder> danhSachOder, Integer tongTien) {
        this.thoigianGhinhan = thoigianGhinhan;
        this.isHoanThanh = isHoanThanh;
        this.danhSachOder = danhSachOder;
        this.tongTien = tongTien;
    }
}
