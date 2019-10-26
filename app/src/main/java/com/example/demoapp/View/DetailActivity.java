package com.example.demoapp.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.demoapp.Common.Common;
import com.example.demoapp.R;
import com.squareup.picasso.Picasso;


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
        iv_roomImg = findViewById(R.id.iv_roomImg);

        cb_wifi = findViewById(R.id.cb_wifi);
        cb_ownWC = findViewById(R.id.cb_ownWC);
        cb_keepCar = findViewById(R.id.cb_free);
        cb_free = findViewById(R.id.cb_free);

        cb_kitchen = findViewById(R.id.cb_kitchen);
        cb_airMachine = findViewById(R.id.cb_airMachine);
        cb_fridge = findViewById(R.id.cb_fridge);
        cb_washMachine = findViewById(R.id.cb_washMachine);

        // RECEIVE DATA FORM INTENT
        Intent i = this.getIntent();
        tv_price.setText(i.getExtras().getString("PRICE_KEY"));
        tv_title.setText(i.getExtras().getString("TITLE_KEY"));
        tv_address.setText(i.getExtras().getString("ADDRESS_KEY"));
        tv_acreage.setText(i.getExtras().getString("ACREAGE_KEY"));
        tv_time.setText(i.getExtras().getString("TIME_KEY"));
        tv_phone.setText(i.getExtras().getString("PHONE_KEY"));
        tv_description.setText(i.getExtras().getString("DESCRIPTION_KEY"));

        cb_wifi.setChecked(Boolean.parseBoolean(i.getExtras().getString("WIFI_KEY")));
        cb_ownWC.setChecked(Boolean.parseBoolean(i.getExtras().getString("WC_KEY")));
        cb_keepCar.setChecked(Boolean.parseBoolean(i.getExtras().getString("KEEPCAR_KEY")));
        cb_free.setChecked(Boolean.parseBoolean(i.getExtras().getString("FREE_KEY")));
        cb_kitchen.setChecked(Boolean.parseBoolean(i.getExtras().getString("KITCHEN_KEY")));
        cb_airMachine.setChecked(Boolean.parseBoolean(i.getExtras().getString("AIRMACHINE_KEY")));
        cb_fridge.setChecked(Boolean.parseBoolean(i.getExtras().getString("FRIDGE_KEY")));
        cb_washMachine.setChecked(Boolean.parseBoolean(i.getExtras().getString("WASHMACHINE_KEY")));

        Picasso.with(this)
                .load(i.getExtras()
                .getString("IMAGE_KEY"))
                .placeholder(R.drawable.home)
                .fit()
                .centerCrop()
                .into(iv_roomImg);


        ic_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
