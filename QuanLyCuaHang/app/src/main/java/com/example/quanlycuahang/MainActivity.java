package com.example.quanlycuahang;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.quanlycuahang.Admin.AdminActivity;
import com.example.quanlycuahang.Admin.TaiKhoan.RecyclerViewTaiKhoan;
import com.example.quanlycuahang.Admin.TaiKhoan.TaiKhoan;
import com.example.quanlycuahang.Admin.TaiKhoan.TaiKhoanFireBaseDataBase;
import com.example.quanlycuahang.Kitchen.KitchenActivity;
import com.example.quanlycuahang.Order.OrderActivity;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    ImageButton ibLogin;
    EditText edtTaiKhoan, edt_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        dangNhap();
    }

    private void dangNhap() {
        ibLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = edtTaiKhoan.getText().toString();
                String password = edt_password.getText().toString();
                if (username.isEmpty() || password.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Tài khoản và mật khẩu không được để trống.", Toast.LENGTH_SHORT).show();
                    return;
                }
                new TaiKhoanFireBaseDataBase().DanhSachTaiKhoan(new TaiKhoanFireBaseDataBase.TaiKhoanDataStatuts() {
                    @Override
                    public void DataIsLoaded(List<TaiKhoan> taiKhoans, List<String> keys) {
                        String username = edtTaiKhoan.getText().toString();
                        String password = edt_password.getText().toString();
                        String vitri = "";
                        for (TaiKhoan tk : taiKhoans) {
                            if (tk.getTenTaiKhoan().equals(username) && tk.getMatKhau().equals(password)) {
                                vitri = tk.getViTri().toString();
                                break;
                            }
                        }
                        switch (vitri) {
                            case "1": {
                                Intent intent = new Intent(MainActivity.this, AdminActivity.class);
                                startActivity(intent);
                                break;
                            }
                            case "2": {
                                Intent intent = new Intent(MainActivity.this, KitchenActivity.class);
                                startActivity(intent);
                                break;
                            }
                            case "3": {
                                Intent intent = new Intent(MainActivity.this, OrderActivity.class);
                                startActivity(intent);
                                break;
                            }
                            default: {
                                Toast.makeText(MainActivity.this, "Sai tài khoản hoặc mật khẩu.", Toast.LENGTH_SHORT).show();
                            }
                        }
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
        });
    }

    private void init() {
        ibLogin = findViewById(R.id.ib_login);
        edtTaiKhoan = findViewById(R.id.edt_tai_khoan);
        edt_password = findViewById(R.id.edt_password);
    }
}
