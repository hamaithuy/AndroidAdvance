package com.example.quanlycuahang.Order;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quanlycuahang.Admin.HoaDon.HoaDon;
import com.example.quanlycuahang.Admin.HoaDon.HoaDonFirebaseHelper;
import com.example.quanlycuahang.Admin.Mon.Mon;
import com.example.quanlycuahang.Admin.Mon.MonFireBaseDatabaseHelper;
import com.example.quanlycuahang.R;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

public class OrderFragment extends Fragment {
    View vRoot;
    private RecyclerView m_recyclerView;
    private RecyclerView m_recyclerViewGiohang;
    private Button m_btnThanhtoan;
    private Spinner spLocTheo;
    private boolean load = false;
    private List<Oder> glstOder;
    private TextView txtTongtien;

    public OrderFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        vRoot = inflater.inflate(R.layout.fragment_order, container, false);
        m_recyclerView = vRoot.findViewById(R.id.rv_oder);
        m_btnThanhtoan = vRoot.findViewById(R.id.btnThanhtoan);
        m_recyclerViewGiohang = vRoot.findViewById(R.id.rv_Giohang);
        spLocTheo = vRoot.findViewById(R.id.sp_oder_loc_theo);
        init();
        hienthiDulieu();
        thanhtoanHoaDon();
        return vRoot;
    }

    private void init() {
        txtTongtien = vRoot.findViewById(R.id.txtTongtien_Giohang);
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
        spLocTheo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0) {
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
                if (i == 1) {
                    new MonFireBaseDatabaseHelper().DanhSachMonSinhTo(new MonFireBaseDatabaseHelper.DataStatuts() {
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
                if (i == 2) {
                    new MonFireBaseDatabaseHelper().DanhSachMonNuocUong(new MonFireBaseDatabaseHelper.DataStatuts() {
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
                if (i == 3) {
                    new MonFireBaseDatabaseHelper().DanhSachMonThucAn(new MonFireBaseDatabaseHelper.DataStatuts() {
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
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void hienthiDulieu() {
        new OderFirebaseHelper().DanhSachHoaDonTam(new OderFirebaseHelper.OderDataStatuts() {
            @Override
            public void DataIsLoaded(List<Oder> oders, List<String> keys) {
                new RecyclerViewGiohang().setConfig(m_recyclerViewGiohang, getContext(), oders, keys);
                Integer tongtien=0;
                for(Oder oder : oders)
                {
                    tongtien+= oder.getSoluong()*oder.getGia();
                }
                Locale loc = Locale.getDefault();
                NumberFormat nf = NumberFormat.getCurrencyInstance(loc);

                txtTongtien.setText(nf.format(tongtien)+" VNĐ");
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
                {
                    if(glstOder.size()>0)
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
            }
        });
    }
}
