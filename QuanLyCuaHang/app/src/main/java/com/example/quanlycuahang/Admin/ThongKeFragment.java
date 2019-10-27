package com.example.quanlycuahang.Admin;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.quanlycuahang.Admin.HoaDon.HoaDon;
import com.example.quanlycuahang.Admin.HoaDon.HoaDonFirebaseHelper;
import com.example.quanlycuahang.R;

import java.util.Date;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ThongKeFragment extends Fragment {
    View vRoot;
    private Button btnTheongay;

    public ThongKeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        vRoot = inflater.inflate(R.layout.fragment_thong_ke, container, false);
        // Inflate the layout for this fragment
        init();
        return vRoot;
    }

    private void init() {
        btnTheongay = vRoot.findViewById(R.id.btnTheongay);


    }

}
