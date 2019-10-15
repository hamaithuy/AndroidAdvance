package com.example.quanlycuahang.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.quanlycuahang.Admin.Mon.ThemMonFragment;
import com.example.quanlycuahang.Admin.NguyenLieu.ThemNguyenLieuFragment;
import com.example.quanlycuahang.Admin.TaiKhoan.QuanLyTaiKhoanFragment;
import com.example.quanlycuahang.MainActivity;
import com.example.quanlycuahang.R;
import com.google.android.material.navigation.NavigationView;

public class AdminActivity extends AppCompatActivity {
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        init();
        setUpNavigation(savedInstanceState);


    }

    private void setUpNavigation(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_admin,
                    new ThemNguyenLieuFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_admin_nhap_nguyen_lieu);
        }
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.nav_admin_nhap_nguyen_lieu:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_admin,
                                new ThemNguyenLieuFragment()).commit();
                        break;
                    case R.id.nav_them_mon:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_admin,
                                new ThemMonFragment()).commit();
                        break;
                    case R.id.nav_quan_ly_tai_khoan:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_admin,
                                new QuanLyTaiKhoanFragment()).commit();
                        break;
                    case R.id.nav_thong_ke:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_admin,
                                new ThongKeFragment()).commit();
                        break;
                    case R.id.nav_dang_xuat_admin:
                        Intent intent = new Intent(AdminActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();

                }
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.nav_app_bar_open_drawer_description,
                R.string.navigation_drawer_close);
        toggle.setDrawerIndicatorEnabled(false);
        toggle.setHomeAsUpIndicator(R.drawable.ic_menu_icon);

        toggle.setToolbarNavigationClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
        toggle.syncState();


    }

    private void init() {
        toolbar = findViewById(R.id.tb_admin);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        drawerLayout = findViewById(R.id.dl_admin);
        navigationView = findViewById(R.id.nav_view_admin);
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }


    }
}
