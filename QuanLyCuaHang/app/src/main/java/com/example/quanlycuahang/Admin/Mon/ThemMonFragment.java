package com.example.quanlycuahang.Admin.Mon;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.example.quanlycuahang.R;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ThemMonFragment extends Fragment {
    private RecyclerView recyclerView;
    private Button btnActivityThem;
    private Spinner spXepTheo;
    private Spinner spLocTheo;
    View vRoot;


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
        return vRoot;

    }


    private void init() {
        recyclerView = vRoot.findViewById(R.id.rv_mon);
        btnActivityThem = vRoot.findViewById(R.id.btn_activity_them);
        btnActivityThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(),ThemMonActivity.class);
                startActivity(intent);
            }
        });
        spXepTheo=vRoot.findViewById(R.id.sp_xep_theo);
        spLocTheo =vRoot.findViewById(R.id.sp_loc_theo);
        String arr[] = {
                "Sắp xếp theo",
                "Từ thấp đến cao",
                "từ cao đến thấp",
                "từ a -> z",
                "từ z -> a"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (
                        getContext(),
                        R.layout.color_spinner_layout,
                        arr
                );
        adapter.setDropDownViewResource
                (android.R.layout.simple_list_item_single_choice);

        spXepTheo.setAdapter(adapter);
        String arrLocTheo[] = {
                "Lọc theo",
                "Sinh tố",
                "Nước uống",
                "Đồ ăn"};
        ArrayAdapter<String> adapterLocTheo = new ArrayAdapter<String>
                (
                        getContext(),
                        R.layout.color_spinner_layout,
                        arrLocTheo
                );
        adapterLocTheo.setDropDownViewResource
                (android.R.layout.simple_list_item_single_choice);
        spLocTheo.setAdapter(adapterLocTheo);
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
