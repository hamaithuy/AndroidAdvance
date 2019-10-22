package com.example.quanlycuahang.Kitchen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.quanlycuahang.Admin.AdminActivity;
import com.example.quanlycuahang.MainActivity;
import com.example.quanlycuahang.R;
import com.google.android.material.navigation.NavigationView;

public class KitchenActivity extends AppCompatActivity {
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kitchen);
        init();
        setUpNavigation(savedInstanceState);
    }

    private void setUpNavigation(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_kitchen,
                    new KitchenFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_kitchen);
        }
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.nav_kitchen:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_kitchen,
                                new KitchenFragment()).commit();
                        break;
                    case R.id.nav_update_kitchen:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_kitchen,
                                new UpdateKitchenFragment()).commit();
                        break;
                    case R.id.nav_logout_kitchen:
                        Intent intent = new Intent(KitchenActivity.this, MainActivity.class);
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

    private void init() {
        toolbar = findViewById(R.id.tb_kitchen);
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.dl_kitchen);
        navigationView = findViewById(R.id.nav_view_kitchen);
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
