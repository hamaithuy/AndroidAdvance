package com.appbtl.appweather;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.appbtl.appweather.model.Hours;
import com.appbtl.appweather.model.ListDailys;
import com.appbtl.appweather.model.time;
import com.google.gson.Gson;

import java.util.ArrayList;

public class ActivityInfo extends AppCompatActivity {
    private LinearLayout detaillayout;
    private Intent intent;
    private RecyclerView recyclerView;
    private Hours listHours;
    private String resultHours;
    private IOFile ioFile;
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        control();
        context = getApplicationContext();
        ioFile = new IOFile();
        intent = new Intent(ActivityInfo.this, MainActivity.class);
        resultHours = ioFile.readFile("hours.bat",this);
        listHours = new Gson().fromJson(resultHours,Hours.class);
        detaillayout.setOnTouchListener(new OnSwipeTouchListener(ActivityInfo.this) {
            @Override
            public void onSwipeRight() {
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }
        });
        recyclerView.setOnTouchListener(new OnSwipeTouchListener(ActivityInfo.this){
            @Override
            public void onSwipeRight() {
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }
        });
        updateUI(listHours);
    }


    @Override
    public void finish() {
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    private void control() {
        detaillayout = (LinearLayout) findViewById(R.id.detaillayout);
        recyclerView =(RecyclerView)findViewById(R.id.recView);

    }
    public void updateUI(Hours listHours){
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager manager =new LinearLayoutManager(ActivityInfo.this,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(manager);
        ListInfoAdapter hoursAdapter =new ListInfoAdapter(listHours,ActivityInfo.this);
        recyclerView.setAdapter(hoursAdapter);
    }
}

