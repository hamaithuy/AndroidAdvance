package com.example.demoapp1.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.demoapp1.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    public static int flag=0;
    public static MainActivity mainActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainActivity = this;
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new HomeFragment()).commit();
    }

    @Override
    protected void onStart() {

        super.onStart();
    }


    public String getUserUriPhoto(){
        Intent getUserDataIntent = getIntent();
        String photo = getUserDataIntent.getStringExtra("PHOTO");
        return photo;
    }
    public String getUserName(){
        Intent getUserDataIntent = getIntent();
        String name = getUserDataIntent.getStringExtra("NAME");
        return name;
    }
    public String getID(){
        Intent getUserIdIntent = getIntent();
        String id = getUserIdIntent.getStringExtra("ID");
        return id;
    }
    public String getUserEmail(){
        Intent getUserEmailIntent = getIntent();
        String email = getUserEmailIntent.getStringExtra("EMAIL");
        return email;
    }
    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    Fragment selectFragment = null;
                    switch (menuItem.getItemId()){
                        case R.id.nav_home:
                            selectFragment = new HomeFragment();
                            break;
                        case R.id.nav_search:
                            selectFragment = new SearchFragment();
                            break;
                        case R.id.nav_save:
                            if (flag == 1) {
                                selectFragment = new SaveFragment();
                            } else {
                                selectFragment = new LoginFragment();
                            }

                            break;
                        case R.id.nav_account:
                            if (flag == 1) {
                                selectFragment = new AccountFragment();
                            } else {
                                selectFragment = new LoginFragment();
                            }
                            break;

                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,selectFragment).commit();
                    return true;
                };
            };
}
