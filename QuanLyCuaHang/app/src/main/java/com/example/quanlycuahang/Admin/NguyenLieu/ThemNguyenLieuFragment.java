package com.example.quanlycuahang.Admin.NguyenLieu;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.quanlycuahang.Admin.Mon.Mon;
import com.example.quanlycuahang.Admin.Mon.RecyclerViewMon;
import com.example.quanlycuahang.R;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ThemNguyenLieuFragment extends Fragment {
    View vRoot;
    private Spinner spNLLocTheo;
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
        LocData();
        return vRoot;
    }

    private void LocData() {
        spNLLocTheo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i==0){
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
                if (i==1){
                    new NguyenLieuFireBaseDatabaseHelper().DanhSachNguyenLieuSinhTo(new NguyenLieuFireBaseDatabaseHelper.NguyenLieuDataStatuts() {
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
                if (i==2){
                    new NguyenLieuFireBaseDatabaseHelper().DanhSachNguyenLieuNuocUong(new NguyenLieuFireBaseDatabaseHelper.NguyenLieuDataStatuts() {
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
                if (i==3){
                    new NguyenLieuFireBaseDatabaseHelper().DanhSachNguyenLieuThucAn(new NguyenLieuFireBaseDatabaseHelper.NguyenLieuDataStatuts() {
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
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
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
        recyclerView = vRoot.findViewById(R.id.rv_them_nguyen_lieu);
        spNLLocTheo = vRoot.findViewById(R.id.sp_loc_theo_nguyen_lieu);

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
        spNLLocTheo.setAdapter(adapterLocTheo);

    }

}
