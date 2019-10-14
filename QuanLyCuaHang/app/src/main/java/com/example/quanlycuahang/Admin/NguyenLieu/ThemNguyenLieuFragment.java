package com.example.quanlycuahang.Admin.NguyenLieu;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.quanlycuahang.Admin.Mon.Mon;
import com.example.quanlycuahang.Admin.Mon.RecyclerViewMon;
import com.example.quanlycuahang.R;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ThemNguyenLieuFragment extends Fragment {
    View vRoot;
    private RecyclerView recyclerView;

    public ThemNguyenLieuFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        vRoot = inflater.inflate(R.layout.fragment_them_nguyen_lieu, container, false);
        init();
        getData();
        return vRoot;
    }

    private void getData() {
        new NguyenLieuFireBaseDatabaseHelper().DanhSachNguyenLieu(new NguyenLieuFireBaseDatabaseHelper.NguyenLieuDataStatuts() {
            @Override
            public void DataIsLoaded(List<Mon> mons, List<String> keys) {
                new NguyenLieuRecyclerView().setConfig(recyclerView, getContext(), mons, keys);
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
        recyclerView=vRoot.findViewById(R.id.rv_them_nguyen_lieu);
        
    }

}
