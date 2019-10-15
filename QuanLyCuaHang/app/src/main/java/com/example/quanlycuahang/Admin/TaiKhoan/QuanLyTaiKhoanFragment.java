package com.example.quanlycuahang.Admin.TaiKhoan;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.quanlycuahang.R;

import java.util.List;

public class QuanLyTaiKhoanFragment extends Fragment {
    private RecyclerView recyclerView;
    View vRoot;

    public QuanLyTaiKhoanFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        vRoot = inflater.inflate(R.layout.fragment_quan_ly_tai_khoan, container, false);
        init();
        getData();
        return vRoot;
    }

    private void getData() {
        new TaiKhoanFireBaseDataBase().DanhSachTaiKhoan(new TaiKhoanFireBaseDataBase.TaiKhoanDataStatuts() {
            @Override
            public void DataIsLoaded(List<TaiKhoan> taiKhoans, List<String> keys) {
                new RecyclerViewTaiKhoan().setConfig(recyclerView, getContext(), taiKhoans, keys);
            }

            @Override
            public void DataIsInserted() {

            }

            @Override
            public void DataIsUpdated() {

            }

            @Override
            public void DataIsDeleted() {

            }
        });

    }

    private void init() {
        recyclerView = vRoot.findViewById(R.id.rv_tai_khoan);
    }


}
