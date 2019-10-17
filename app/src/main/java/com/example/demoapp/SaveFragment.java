package com.example.demoapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class SaveFragment extends Fragment {
    ListView listSaveRoom;
    SimpleDateFormat dtf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    //Date tP = dtf.format(dt);

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_save,container,false);
        try {
            ArrayList<Room> listRoom= Room.makeListRoom();
            ListAdapter listAdapter = new ListAdapter(getActivity(),listRoom);
            // Danh sách phòng đã lưu
            listSaveRoom = (ListView) view.findViewById(R.id.ls_saveRoom);
            listSaveRoom.setAdapter(listAdapter);
            listSaveRoom.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Intent intent = new Intent(getActivity(),com.example.demoapp.DetailActivity.class);
                    startActivity(intent);
                }
            });
        }catch (Exception ex){
            String error = ex.getMessage();
        }
        return view;
    }
}
