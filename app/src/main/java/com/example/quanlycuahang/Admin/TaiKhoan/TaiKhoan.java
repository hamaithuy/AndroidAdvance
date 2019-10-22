package com.example.quanlycuahang.Admin.TaiKhoan;

public class TaiKhoan {
    private String TenTaiKhoan;
    private String MatKhau;
    private String Ten;
    private Integer ViTri;

    public TaiKhoan() {
    }

    public TaiKhoan(String tenTaiKhoan, String matKhau, String ten, Integer viTri) {
        TenTaiKhoan = tenTaiKhoan;
        MatKhau = matKhau;
        Ten = ten;
        ViTri = viTri;
    }

    public String getTenTaiKhoan() {
        return TenTaiKhoan;
    }

    public void setTenTaiKhoan(String tenTaiKhoan) {
        TenTaiKhoan = tenTaiKhoan;
    }

    public String getMatKhau() {
        return MatKhau;
    }

    public void setMatKhau(String matKhau) {
        MatKhau = matKhau;
    }

    public String getTen() {
        return Ten;
    }

    public void setTen(String ten) {
        Ten = ten;
    }

    public Integer getViTri() {
        return ViTri;
    }

    public void setViTri(Integer viTri) {
        ViTri = viTri;
    }
}
