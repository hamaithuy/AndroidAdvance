package com.example.banhang;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class AdminCategoryActivity extends AppCompatActivity {

    ImageView aonu,aonam,mu,giay,dongho,dienthoai;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_category);
        Toast.makeText(this, "Chào mừng Admin", Toast.LENGTH_SHORT).show();
        aonu = (ImageView) findViewById(R.id.aonu);
        aonam = (ImageView) findViewById(R.id.aonam);
        mu = (ImageView) findViewById(R.id.mu);
        giay = (ImageView) findViewById(R.id.giay);
        dongho = (ImageView) findViewById(R.id.dongho);
        dienthoai = (ImageView) findViewById(R.id.dienthoai);
        aonu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminCategoryActivity.this, AdminaddnewProductActivity.class);
                startActivity(intent);
            }
        });
        aonam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminCategoryActivity.this, AdminaddnewProductActivity.class);
                startActivity(intent);
            }
        });
        mu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminCategoryActivity.this, AdminaddnewProductActivity.class);
                startActivity(intent);
            }
        });
        giay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminCategoryActivity.this, AdminaddnewProductActivity.class);
                startActivity(intent);
            }
        });
       dongho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminCategoryActivity.this, AdminaddnewProductActivity.class);
                startActivity(intent);
            }
        });
        dienthoai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminCategoryActivity.this, AdminaddnewProductActivity.class);
                startActivity(intent);
            }
        });
    }

}
