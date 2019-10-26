package com.example.quanlycuahang.Kitchen;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.quanlycuahang.Admin.HoaDon.HoaDon;
import com.example.quanlycuahang.Admin.HoaDon.HoaDonFirebaseHelper;
import com.example.quanlycuahang.R;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;


/**
 * A simple {@link Fragment} subclass.
 */
public class KitchenFragment extends Fragment {
    View vRoot;
    List<HoaDon> glstHoadon = new ArrayList<>();
    private RecyclerView m_recyclerViewKitchenParent;
    public KitchenFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        vRoot = inflater.inflate(R.layout.fragment_kitchen, container, false);
        m_recyclerViewKitchenParent = (RecyclerView) vRoot.findViewById(R.id.rv_kitchen_parent);
        hienThidulieu();
        return vRoot;
    }

    private void hienThidulieu() {
        new HoaDonFirebaseHelper().DanhSachHoa(new HoaDonFirebaseHelper.HoaDonDataStatuts() {
            @Override
            public void DataIsLoaded(List<HoaDon> hoaDons, List<String> keys) {
                glstHoadon.clear();
                for(HoaDon hd : hoaDons)
                {
                if(!hd.isHoanThanh())
                glstHoadon.add(hd);
                }
                new RecyclerViewKitchenParent().setConfig(m_recyclerViewKitchenParent, getContext(), glstHoadon, keys);
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
