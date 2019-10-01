package com.example.demoapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class HomeFragment extends Fragment {
    GridView gridNewRoom;
    GridView gridCoupleRoom;
    SimpleDateFormat dtf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    //Date tP = dtf.format(dt);

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home,container,false);
        try {
            ArrayList<Room> listRoom= Room.makeListRoom();
            GridAdapter roomAdapter = new GridAdapter(getActivity(),listRoom);
            // Phòng mới
            gridNewRoom = (GridView) view.findViewById(R.id.gridNewRoom);
            gridNewRoom.setAdapter(roomAdapter);
            // Phòng ở ghép
            gridCoupleRoom = (GridView) view.findViewById(R.id.gridCoupleRoom);
            gridCoupleRoom.setAdapter((roomAdapter));
        }catch (Exception ex){
            String erorr = ex.getMessage();
        }
        return view;
    }
}
