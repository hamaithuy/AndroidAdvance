package com.appbtl.appweather;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.appbtl.appweather.model.time;

import java.util.ArrayList;

public class ActivityInfo extends AppCompatActivity {
    private LinearLayout detaillayout;
    private Intent intent;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        ArrayList<time> list = new ArrayList<time>();
        time time1 = new time("rain", "33°C", "1:00", "0.59 m/s");
        time time2 = new time("rain", "31°C", "4:00", "1.59 m/s");
        time time3 = new time("rain", "30°C", "7:00", "1.05 m m/s");
        time time4 = new time("rain", "35°C", "10:00", "2.44 m/s");
        time time5 = new time("rain", "32°C", "13:00", "0.34 m/s");
        time time6 = new time("rain", "29°C", "16:00", "0.12 m/s");
        time time7 = new time("rain", "23°C", "19:00", "2.32 m/s");
        list.add(time1);
        list.add(time2);
        list.add(time3);
        list.add(time4);
        list.add(time5);
        list.add(time6);
        list.add(time7);
        listView = (ListView) findViewById(R.id.lvDaily);
        listView.setAdapter(new TimeAdapter(list, ActivityInfo.this));
        control();
        intent = new Intent(ActivityInfo.this, MainActivity.class);
        detaillayout.setOnTouchListener(new OnSwipeTouchListener(ActivityInfo.this) {
            @Override
            public void onSwipeRight() {
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }
        });
        listView.setOnTouchListener(new OnSwipeTouchListener(ActivityInfo.this) {
            @Override
            public void onSwipeRight() {
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
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

