package com.example.quanlycuahang.Admin.Mon;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.quanlycuahang.R;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ThemMonFragment extends Fragment {
    private RecyclerView recyclerView;
    View vRoot;
    private EditText edtTenMon;
    private EditText edtGia;
    private Button btnThem;


    public ThemMonFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        vRoot = inflater.inflate(R.layout.fragment_them_mon, container, false);
        init();
        getData();
        saveData();
        return vRoot;

    }

    private void saveData() {
        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Mon mon = new Mon();
                mon.setTenMon(edtTenMon.getText().toString());
                String strGia = edtGia.getText().toString();
                int gia = Integer.parseInt(strGia);
                mon.setGia(gia);
                mon.setSoLuong(0);
                new MonFireBaseDatabaseHelper().ThemMon(mon, new MonFireBaseDatabaseHelper.DataStatuts() {
                    @Override
                    public void DataIsLoaded(List<Mon> mons, List<String> keys) {

                    }

                    @Override
                    public void DataIsInserted() {
                        Toast.makeText(getContext(), "da them thanh cong", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void DataIsUpdated() {

                    }

                    @Override
                    public void DataIsDeleted() {

                    }
                });
            }
        });
    }

    private void init() {
        recyclerView = vRoot.findViewById(R.id.rv_mon);
        edtTenMon = vRoot.findViewById(R.id.edt_ten_mon);
        edtGia = vRoot.findViewById(R.id.edt_gia);
        btnThem = vRoot.findViewById(R.id.btn_them);
    }

    private void getData() {


        new MonFireBaseDatabaseHelper().DanhSachMon(new MonFireBaseDatabaseHelper.DataStatuts() {
            @Override
            public void DataIsLoaded(List<Mon> mons, List<String> keys) {
                new RecyclerViewMon().setConfig(recyclerView, getContext(), mons, keys);
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


}
