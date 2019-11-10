package vn.edu.hou.appchat.models;

import java.util.ArrayList;


public class Conversation {
    private String roomID;
    private ArrayList<Message> listMessageData;

    public Conversation(){
        listMessageData = new ArrayList<>();
    }

    public ArrayList<Message> getListMessageData() {
        return listMessageData;
    }

    public String getRoomID() {
        return roomID;
    }

    public void setRoomID(String roomID) {
        this.roomID = roomID;
    }

    public void setListMessageData(ArrayList<Message> listMessageData) {
        this.listMessageData = listMessageData;
    }
}
