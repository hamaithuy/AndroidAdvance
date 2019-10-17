package com.example.demoapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class HomeFragment extends Fragment {
    GridView gridNewRoom;
    GridView gridCoupleRoom;
    ImageView iv_Post;
    EditText editText;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home,container,false);
        try {
            ArrayList<Room> listRoom= new ArrayList<Room>();
            GridAdapter roomAdapter = new GridAdapter(getActivity(),listRoom);

            // Phòng mới
            gridNewRoom = (GridView) view.findViewById(R.id.gridNewRoom);
            gridNewRoom.setAdapter(roomAdapter);
            gridNewRoom.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Intent intent = new Intent(getActivity(),com.example.demoapp.DetailActivity.class);
                    startActivity(intent);
                }
            });

            // Phòng ở ghép
            gridCoupleRoom = (GridView) view.findViewById(R.id.gridCoupleRoom);
            gridCoupleRoom.setAdapter((roomAdapter));
            gridCoupleRoom.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Intent intent = new Intent(getActivity(),com.example.demoapp.DetailActivity.class);
                    startActivity(intent);
                }
            });
            // Đăng phòng
            iv_Post = (ImageView) view.findViewById(R.id.iv_Post);
            iv_Post.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getActivity(),com.example.demoapp.PostActivity.class);
                    startActivity(intent);
                }
            });
        }catch (Exception ex){
            String erorr = ex.getMessage();
        }
        return view;

    }


    @Override
    public void onResume() {
        super.onResume();
        //gridNewRoom.setAdapter();
    }
}
