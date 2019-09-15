package com.appbtl.appweather;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.appbtl.appweather.model.item;

import java.util.ArrayList;
import java.util.List;

public class ActivityDetails extends AppCompatActivity {
    private LinearLayout detaillayout;
    private Intent intent;
    private ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ArrayList<item> list = new ArrayList<item>();
        item day1 = new item("rain", "33°C", "31°C","16/9","Nắng");
        item day2 = new item("shine", "33°C", "31°C","17/9","Nắng");
        item day3 = new item("rain", "33°C", "31°C","18/9","Nắng");
        item day4 = new item("rain", "36°C", "31°C","19/9","Nắng");
        item day5 = new item("shine", "35°C", "31°C","20/9","Nắng");
        item day6 = new item("rain", "34°C", "31°C","21/9","Nắng");
        list.add(day1);
        list.add(day2);
        list.add(day3);
        list.add(day4);
        list.add(day5);
        list.add(day6);
        listView = (ListView) findViewById(R.id.lvDaily);
        listView.setAdapter(new Adapter(list, ActivityDetails.this));
        control();
        intent = new Intent(ActivityDetails.this, MainActivity.class);
        detaillayout.setOnTouchListener(new OnSwipeTouchListener(ActivityDetails.this) {
            @Override
            public void onSwipeLeft() {
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });
        listView.setOnTouchListener(new OnSwipeTouchListener(ActivityDetails.this){
            @Override
            public void onSwipeLeft() {
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });
    }

    @Override
    public void finish() {
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    private void control() {
        detaillayout = (LinearLayout) findViewById(R.id.detaillayout);
    }
}
