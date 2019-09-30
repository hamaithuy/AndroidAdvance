package com.example.jamlos.Model;

import java.util.ArrayList;

public class lstOrder {
    static ArrayList<Order> lstOrder;

    public lstOrder() {
    }

    public static ArrayList<Order> getLstOrder() {
        if(lstOrder == null){
            lstOrder = new ArrayList<Order>();
        }
        return lstOrder;
    }
}
