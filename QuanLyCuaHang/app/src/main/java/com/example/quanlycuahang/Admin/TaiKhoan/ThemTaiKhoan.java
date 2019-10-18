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

public class ThemTaiKhoan extends AppCompatActivity {
    private EditText edtTenTaiKhoan, edtMatKhau, edtTen;
    private Spinner spViTri;
    private Button btnTao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_tai_khoan);
        init();
        TaoTaiKhoan();
    }

    private void TaoTaiKhoan() {
        btnTao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TaiKhoan taiKhoan = new TaiKhoan();
                taiKhoan.setTenTaiKhoan(edtTenTaiKhoan.getText().toString());
                taiKhoan.setMatKhau(edtMatKhau.getText().toString());
                taiKhoan.setTen(edtTen.getText().toString());
                int viTri = spViTri.getSelectedItemPosition();
                viTri++;
                taiKhoan.setViTri(viTri);
                new TaiKhoanFireBaseDataBase().ThemTaiKhoan(taiKhoan, new TaiKhoanFireBaseDataBase.TaiKhoanDataStatuts() {
                    @Override
                    public void DataIsLoaded(List<TaiKhoan> taiKhoans, List<String> keys) {

                    }

                    @Override
                    public void DataIsInserted() {
                        Toast.makeText(ThemTaiKhoan.this, "Thêm tài khoản Thành công", Toast.LENGTH_SHORT).show();
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
        edtTenTaiKhoan = findViewById(R.id.edt_ten_tai_khoan);
        edtMatKhau = findViewById(R.id.edt_mat_khau);
        edtTen = findViewById(R.id.edt_ten);
        spViTri = findViewById(R.id.sp_vi_tri);
        String arrViTri[] = {
                "Admin",
                "Nhà Bếp",
                "Nhân Viên"};
        ArrayAdapter<String> adapterTK = new ArrayAdapter<String>
                (
                        this,
                        android.R.layout.simple_spinner_item,
                        arrViTri
                );
        adapterTK.setDropDownViewResource
                (android.R.layout.simple_list_item_single_choice);
        spViTri.setAdapter(adapterTK);
        btnTao = findViewById(R.id.btn_tao_tai_khoan);

    }
}
