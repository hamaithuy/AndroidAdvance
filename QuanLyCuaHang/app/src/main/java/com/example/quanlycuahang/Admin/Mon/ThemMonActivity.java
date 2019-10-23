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

public class ThemMonActivity extends AppCompatActivity {
    private EditText edtTenMon, edtGia;
    private Button btnThem;
    private Spinner snLoaiMon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_mon);
        init();
        themMon();
    }

    private void init() {
        edtTenMon = findViewById(R.id.edt_ten_mon);
        edtGia = findViewById(R.id.edt_gia_mon);
        btnThem = findViewById(R.id.btn_them);
        snLoaiMon = findViewById(R.id.sn_loai_mon);
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


    }

    private void themMon() {
        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tenMon=edtTenMon.getText().toString();
                String strGia = edtGia.getText().toString();
                String LoaiMon = snLoaiMon.getSelectedItem().toString();
                if (tenMon.equals("")&&strGia.equals("")){
                    Toast.makeText(ThemMonActivity.this, "Xin vui lòng nhập đầy đủ", Toast.LENGTH_SHORT).show();
                }
                else {
                    Mon mon = new Mon();
                    mon.setTenMon(edtTenMon.getText().toString());
                    int Loai = snLoaiMon.getSelectedItemPosition();
                    Loai = Loai+1;
                    int gia = Integer.parseInt(strGia);
                    mon.setGia(gia);
                    mon.setSoLuong(0);
                    mon.setLoaiMon(LoaiMon);
                    mon.setLoai(Loai);
                    new MonFireBaseDatabaseHelper().ThemMon(mon, new MonFireBaseDatabaseHelper.DataStatuts() {
                        @Override
                        public void DataIsLoaded(List<Mon> mons, List<String> keys) {

                        }

                        @Override
                        public void DataIsInserted() {
                            Toast.makeText(ThemMonActivity.this, "Đã thêm món thành công", Toast.LENGTH_SHORT).show();
                            finish();
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
