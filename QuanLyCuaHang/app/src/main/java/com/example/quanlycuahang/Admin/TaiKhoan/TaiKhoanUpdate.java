package com.example.quanlycuahang.Admin.TaiKhoan;

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

public class TaiKhoanUpdate extends AppCompatActivity {
    private EditText edtTenTaiKhoan, edtMatKhau, edtTen;
    private Spinner spViTri;
    private Button btnCapNhap;
    private String key, TenTaiKhoan, MatKhau, Ten, ViTri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tai_khoan_update);
        init();
        updateTaiKhoan();
    }

    private void updateTaiKhoan() {
        btnCapNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TaiKhoan taiKhoan = new TaiKhoan();
                taiKhoan.setTenTaiKhoan(edtTenTaiKhoan.getText().toString());
                taiKhoan.setMatKhau(edtMatKhau.getText().toString());
                taiKhoan.setTen(edtTen.getText().toString());
                taiKhoan.setViTri(spViTri.getSelectedItemPosition()+1);

                new TaiKhoanFireBaseDataBase().SuaTaiKhoan(key, taiKhoan, new TaiKhoanFireBaseDataBase.TaiKhoanDataStatuts() {
                    @Override
                    public void DataIsLoaded(List<TaiKhoan> taiKhoans, List<String> keys) {

                    }

                    @Override
                    public void DataIsInserted() {

                    }

                    @Override
                    public void DataIsUpdated() {
                        Toast.makeText(TaiKhoanUpdate.this, "Cập nhập thành công", Toast.LENGTH_SHORT).show();
                        finish();

                    }

                    @Override
                    public void DataIsDeleted() {

                    }
                });

            }
        });
    }

    private void init() {
        edtTenTaiKhoan = findViewById(R.id.edt_update_ten_tai_khoan);
        edtMatKhau = findViewById(R.id.edt_update_mat_khau);
        edtTen = findViewById(R.id.edt_update_tentk);
        spViTri = findViewById(R.id.sp_vi_tri);
        btnCapNhap = findViewById(R.id.btn_cap_nhap_tai_khoan);
        key = getIntent().getStringExtra("key");
        TenTaiKhoan = getIntent().getStringExtra("TenTaiKhoan");
        MatKhau = getIntent().getStringExtra("MatKhau");
        Ten = getIntent().getStringExtra("Ten");
        ViTri = getIntent().getStringExtra("ViTri");
        //getData
        edtTenTaiKhoan.setText(TenTaiKhoan);
        edtMatKhau.setText(MatKhau);
        edtTen.setText(Ten);



        //
        String arrUpdateTaiKhoan[] = {
                "Admin",
                "Nhà Bếp",
                "Nhân Viên"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (
                        this,
                        android.R.layout.simple_spinner_item,
                        arrUpdateTaiKhoan
                );
        adapter.setDropDownViewResource
                (android.R.layout.simple_list_item_single_choice);
        spViTri.setAdapter(adapter);
        if (ViTri.equals("Admin")) {
            spViTri.setSelection(0);
        }
        if (ViTri.equals("Nhà Bếp")) {
            spViTri.setSelection(1);
        }
        if (ViTri.equals("Nhân Viên")) {
            spViTri.setSelection(2);
        }
    }
}
