package com.example.banhang.Model;

public class Admin {
    public Admin(String phone, String pass) {
        this.phone = phone;
        this.pass = pass;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    String phone,pass;

}
