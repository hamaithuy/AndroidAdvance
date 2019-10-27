package com.example.demoapp.Model;

public class User {
    private String id;
    private String name;
    private Boolean perAdmin;
    private String email;
    private String postedRoom;
    private String savedRoom;

    public User() {
    }

    public User(String id, String name, Boolean perAdmin, String photo, String postedRoom, String savedRoom) {
        this.id = id;
        this.name = name;
        this.perAdmin = perAdmin;
        this.email = photo;
        this.postedRoom = postedRoom;
        this.savedRoom = savedRoom;
    }

    public User(String id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public User(String id, String name, Boolean perAdmin, String postedRoom, String savedRoom) {
        this.id = id;
        this.name = name;
        this.perAdmin = perAdmin;
        this.postedRoom = postedRoom;
        this.savedRoom = savedRoom;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getPerAdmin() {
        return perAdmin;
    }

    public void setPerAdmin(Boolean perAdmin) {
        this.perAdmin = perAdmin;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPostedRoom() {
        return postedRoom;
    }

    public void setPostedRoom(String postedRoom) {
        this.postedRoom = postedRoom;
    }

    public String getSavedRoom() {
        return savedRoom;
    }

    public void setSavedRoom(String savedRoom) {
        this.savedRoom = savedRoom;
    }
}


