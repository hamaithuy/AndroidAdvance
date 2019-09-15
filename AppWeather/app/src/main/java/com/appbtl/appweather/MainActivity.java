package com.appbtl.appweather;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity {
    private RelativeLayout mainlayout;
    private Intent intent;
    private Intent intent1;
    private ImageView imgvisibility,imgpressure,imghumidity,imgwind;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        control();
        imgvisibility.setImageResource(R.drawable.visibility);
        imgpressure.setImageResource(R.drawable.airpress);
        imghumidity.setImageResource(R.drawable.humidity);
        imgwind.setImageResource(R.drawable.speed);
        intent = new Intent(MainActivity.this,ActivityDetails.class);
        intent1 = new Intent(MainActivity.this,ActivityInfo.class);
        mainlayout.setOnTouchListener(new OnSwipeTouchListener(MainActivity.this){
            @Override
            public void onSwipeRight() {
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
            }

            @Override
            public void onSwipeLeft() {
                startActivity(intent1);
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
            }
        });
    }
    private void control(){
        mainlayout = (RelativeLayout)findViewById(R.id.mainlayout);
        imgvisibility = (ImageView)findViewById(R.id.imgvisibility);
        imgpressure = (ImageView)findViewById(R.id.imgpressure);
        imghumidity = (ImageView)findViewById(R.id.imghumidity);
        imgwind = (ImageView)findViewById(R.id.imgwind);
    }
}
