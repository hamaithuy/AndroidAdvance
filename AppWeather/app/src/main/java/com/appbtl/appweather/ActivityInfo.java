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

