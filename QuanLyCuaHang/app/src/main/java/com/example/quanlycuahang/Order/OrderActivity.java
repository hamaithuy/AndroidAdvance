package com.example.quanlycuahang.Order;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.quanlycuahang.MainActivity;
import com.example.quanlycuahang.R;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

public class OrderActivity extends AppCompatActivity {
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        toolbar = findViewById(R.id.tb_order);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        drawerLayout = findViewById(R.id.dl_order);
        navigationView = findViewById(R.id.nav_view_order);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_oder,
                    new OrderFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_order);
        }

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.nav_order:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_oder,
                                new OrderFragment()).commit();
                        break;
                    case R.id.nav_update_order:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_oder,
                                new UpdateOrderFragment()).commit();
                        break;
                    case R.id.nav_logout_order:
                        Intent intent = new Intent(OrderActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                        break;

                }
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.nav_app_bar_open_drawer_description,
                R.string.navigation_drawer_close);
        toggle.syncState();


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
