package com.example.jamlos.Model;

public class User {
    // PhoneNumber is Primary Key
    private String PhoneNumber;
    private String UserName;
    private String Email;
    private String Password;
    private String Address;

    public User() {
    }

    public User(String phoneNumber, String userName, String email, String password, String address) {
        PhoneNumber = phoneNumber;
        UserName = userName;
        Email = email;
        Password = password;
        Address = address;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }
}
