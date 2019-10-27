package com.appbtl.appweather;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.appbtl.appweather.model.ListDailys;
import com.appbtl.appweather.model.item;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ActivityDetails extends AppCompatActivity {
    private LinearLayout detaillayout;
    private Intent intent;
    private RecyclerView recyclerView;
    private ListDailys listDailys;
    private String resultDailys;
    private IOFile ioFile;
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        control();
        context = getApplicationContext();
        ioFile = new IOFile();
        intent = new Intent(ActivityDetails.this, MainActivity.class);
        resultDailys = ioFile.readFile("dailys.bat",this);
        listDailys = new Gson().fromJson(resultDailys,ListDailys.class);
        detaillayout.setOnTouchListener(new OnSwipeTouchListener(ActivityDetails.this) {
            @Override
            public void onSwipeLeft() {
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });
        recyclerView.setOnTouchListener(new OnSwipeTouchListener(ActivityDetails.this){
            @Override
            public void onSwipeLeft() {
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });
        updateUI(listDailys);
    }


    @Override
    public void finish() {
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    private void control() {
        detaillayout = (LinearLayout) findViewById(R.id.detaillayout);
        recyclerView =(RecyclerView)findViewById(R.id.recView);

    }
    public void updateUI(ListDailys listDailys){
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager manager =new LinearLayoutManager(ActivityDetails.this,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(manager);
        ListDailysAdapter dailysAdapter =new ListDailysAdapter(listDailys,ActivityDetails.this);
        recyclerView.setAdapter(dailysAdapter);
    }
}
