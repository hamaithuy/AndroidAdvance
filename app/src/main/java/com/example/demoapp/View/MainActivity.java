package com.example.demoapp.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.demoapp.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    public static int flag=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new HomeFragment()).commit();
    }

    @Override
    protected void onStart() {

        super.onStart();
    }

//    @Override
//    protected void onResume() {
//        if(flag == 0)
//        {
//            Intent intent = new Intent(MainActivity.this,LoginActivity.class);
//            startActivity(intent);
//        }
//        super.onResume();
//    }

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
