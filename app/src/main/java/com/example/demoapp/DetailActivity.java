package com.example.demoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {

    TextView tv_price, tv_title,tv_address,tv_acreage,tv_time,tv_phone, tv_description;
    ImageView iv_roomImg, ic_back, ic_bookmark;
    CheckBox cb_wifi,cb_ownWC, cb_keepCar, cb_free, cb_kitchen, cb_airMachine, cb_fridge, cb_washMachine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        tv_price = findViewById(R.id.tv_price);
        tv_title = findViewById(R.id.tv_title);
        tv_address = findViewById(R.id.tv_address);
        tv_acreage = findViewById(R.id.tv_acreage);
        tv_time = findViewById(R.id.tv_time);
        tv_phone = findViewById(R.id.tv_phone);
        tv_description = findViewById(R.id.tv_description);

        ic_back = findViewById(R.id.ic_back);
        ic_bookmark = findViewById(R.id.ic_bookmark);

        cb_wifi = findViewById(R.id.cb_wifi);
        cb_ownWC = findViewById(R.id.cb_ownWC);
        cb_keepCar = findViewById(R.id.cb_free);
        cb_free = findViewById(R.id.cb_free);

        cb_kitchen = findViewById(R.id.cb_kitchen);
        cb_airMachine = findViewById(R.id.cb_airMachine);
        cb_fridge = findViewById(R.id.cb_fridge);
        cb_washMachine = findViewById(R.id.cb_washMachine);


        ic_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
