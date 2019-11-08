package com.example.demoapp1.Model;

public class User {
    private String id;
    private String name;
    private Boolean perAdmin;
    private String email;
    private String RoomPosted;
    private String RoomSaved;


    public User() {
    }

    public User(String id, String name, Boolean perAdmin, String email, String roomPosted, String roomSaved) {
        this.id = id;
        this.name = name;
        this.perAdmin = perAdmin;
        this.email = email;
        RoomPosted = roomPosted;
        RoomSaved = roomSaved;
    }

    public User(String name, Boolean perAdmin, String email, String roomPosted, String roomSaved) {
        this.name = name;
        this.perAdmin = perAdmin;
        this.email = email;
        RoomPosted = roomPosted;
        RoomSaved = roomSaved;
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

    public String getRoomPosted() {
        return RoomPosted;
    }

    public void setRoomPosted(String roomPosted) {
        RoomPosted = roomPosted;
    }

    public String getRoomSaved() {
        return RoomSaved;
    }

    public void setRoomSaved(String roomSaved) {
        RoomSaved = roomSaved;
    }
}


