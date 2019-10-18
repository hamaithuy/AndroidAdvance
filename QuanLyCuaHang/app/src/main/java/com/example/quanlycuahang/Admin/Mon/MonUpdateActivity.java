package com.example.quanlycuahang.Admin.Mon;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.quanlycuahang.R;

import java.util.List;

public class MonUpdateActivity extends AppCompatActivity {
    private EditText edtTenMon, edtGia;
    private Button btnCapNhap, btnXoa;
    private Spinner snLoaiMon;
    private String key;
    private String TenMon;
    private String Gia;
    private String LoaiMon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mon_update);
        init();
        updateMon();
        XoaMon();
    }

    private void XoaMon() {
        btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new MonFireBaseDatabaseHelper().XoaMon(key, new MonFireBaseDatabaseHelper.DataStatuts() {
                    @Override
                    public void DataIsLoaded(List<Mon> mons, List<String> keys) {

                    }

                    @Override
                    public void DataIsInserted() {

                    }

                    @Override
                    public void DataIsUpdated() {

                    }

                    @Override
                    public void DataIsDeleted() {
                        Toast.makeText(MonUpdateActivity.this, "Xoa Thanh cong", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private void updateMon() {
        btnCapNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Mon mon = new Mon();
                mon.setTenMon(edtTenMon.getText().toString());
                String strGia = edtGia.getText().toString();
                int gia = Integer.parseInt(strGia);
                String LoaiMon = snLoaiMon.getSelectedItem().toString();
                mon.setGia(gia);
                mon.setSoLuong(0);
                mon.setLoaiMon(LoaiMon);
                new MonFireBaseDatabaseHelper().SuaMon(key, mon, new MonFireBaseDatabaseHelper.DataStatuts() {
                    @Override
                    public void DataIsLoaded(List<Mon> mons, List<String> keys) {

                    }

                    @Override
                    public void DataIsInserted() {

                    }

                    @Override
                    public void DataIsUpdated() {
                        Toast.makeText(MonUpdateActivity.this, "Cap nhap thanh cong", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void DataIsDeleted() {

                    }
                });
            }
        });
    }

    private void init() {
        edtTenMon = findViewById(R.id.edt_update_ten_mon);
        edtGia = findViewById(R.id.edt_update_gia);
        btnCapNhap = findViewById(R.id.btn_tao_tai_khoan);
        btnXoa = findViewById(R.id.btn_xoa);
        snLoaiMon=findViewById(R.id.sn_update_loai_mon);
        key = getIntent().getStringExtra("key");
        TenMon = getIntent().getStringExtra("TenMon");
        Gia = getIntent().getStringExtra("Gia");
        LoaiMon = getIntent().getStringExtra("LoaiMon");
        edtTenMon.setText(TenMon);
        edtGia.setText(Gia);
        String arr[] = {
                "Sinh tố",
                "Nước uống",
                "Đồ ăn"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (
                        this,
                        android.R.layout.simple_spinner_item,
                        arr
                );
        adapter.setDropDownViewResource
                (android.R.layout.simple_list_item_single_choice);
        snLoaiMon.setAdapter(adapter);
        if (LoaiMon.equals("Sinh tố")){
            snLoaiMon.setSelection(0);
        }
        if (LoaiMon.equals("Nước uống")){
            snLoaiMon.setSelection(1);
        }
        if (LoaiMon.equals("Đồ ăn")){
            snLoaiMon.setSelection(2);
        }


    }
}
