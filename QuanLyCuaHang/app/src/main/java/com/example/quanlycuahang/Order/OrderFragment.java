package com.example.quanlycuahang.Order;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.quanlycuahang.Admin.HoaDon.HoaDon;
import com.example.quanlycuahang.Admin.HoaDon.HoaDonFirebaseHelper;
import com.example.quanlycuahang.Admin.Mon.Mon;
import com.example.quanlycuahang.Admin.Mon.MonFireBaseDatabaseHelper;
import com.example.quanlycuahang.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

public class OrderFragment extends Fragment {
    View vRoot;
    private RecyclerView m_recyclerView;
    private RecyclerView m_recyclerViewGiohang;
    private Button m_btnThanhtoan;
    private boolean load = false;
    private List<Oder> glstOder;

    public OrderFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        vRoot = inflater.inflate(R.layout.fragment_order, container, false);
        m_recyclerView = vRoot.findViewById(R.id.rv_oder);
        m_btnThanhtoan = vRoot.findViewById(R.id.btnThanhtoan);
        m_recyclerViewGiohang = vRoot.findViewById(R.id.rv_Giohang);
        hienthiDulieu();
        thanhtoanHoaDon();
        return vRoot;
    }

    private void hienthiDulieu() {
         new   OderFirebaseHelper().DanhSachHoaDonTam(new OderFirebaseHelper.OderDataStatuts() {
             @Override
             public void DataIsLoaded(List<Oder> oders, List<String> keys) {
                 new RecyclerViewGiohang().setConfig(m_recyclerViewGiohang, getContext(), oders, keys);
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
        new MonFireBaseDatabaseHelper().DanhSachMon(new MonFireBaseDatabaseHelper.DataStatuts() {
            @Override
            public void DataIsLoaded(List<Mon> mons, List<String> keys) {
                new RecyclerViewOder().setConfig(m_recyclerView, getContext(), mons, keys);
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
    private void thanhtoanHoaDon() {
        m_btnThanhtoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new OderFirebaseHelper().DanhSachHoaDonTam(new OderFirebaseHelper.OderDataStatuts() {
                    @Override
                    public void DataIsLoaded(List<Oder> oders, List<String> keys) {
                        glstOder = oders;
                        load = true;
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
                if (load)
                    new HoaDonFirebaseHelper().ThemHoaDon(glstOder, new HoaDonFirebaseHelper.HoaDonDataStatuts() {
                        @Override
                        public void DataIsLoaded(List<HoaDon> hoaDons, List<String> keys) {

                        }

                        @Override
                        public void DataIsInserted() {
                            Toast.makeText(getContext(), "Thanh toán thành công", Toast.LENGTH_SHORT).show();
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
}
