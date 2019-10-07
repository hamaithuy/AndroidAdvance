package com.example.demoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

public class PostActivity extends AppCompatActivity  {

    RadioButton rb_chothue,rb_oghep;
    TextView tv_Cancel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        rb_chothue = findViewById(R.id.rb_chothue);
        rb_oghep = findViewById(R.id.rb_oghep);

        // Thoat
        tv_Cancel = findViewById(R.id.tv_Cancel);
        tv_Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PostActivity.this,com.example.demoapp.MainActivity.class);
                startActivity(intent);
            }
        });

    }


}
