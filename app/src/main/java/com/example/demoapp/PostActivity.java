package com.example.demoapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.ClipData;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

public class PostActivity extends AppCompatActivity  {

    RadioButton rb_chothue,rb_oghep;
    TextView tv_Cancel;
    ImageView img1,img2,img3,img4;
    Button btn_upImg;

    final int CODE_MULTIPLE_IMG_GALLERY=4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        // Hàm tham chiếu ID element
        init();

        // Thoát activity
        tv_Cancel = findViewById(R.id.tv_Cancel);
        tv_Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        // Đăng ảnh
        btn_upImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent intent= new Intent();
                    intent.setType("image/*");
                    intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE,true);
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(intent,"Chọn ảnh"),CODE_MULTIPLE_IMG_GALLERY);
                }catch (Exception ex){
                    String e = ex.getMessage();
                }

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == CODE_MULTIPLE_IMG_GALLERY && resultCode == RESULT_OK){
            try {
                ClipData clipData = data.getClipData();
                if(clipData!=null){
                    img1.setImageURI(clipData.getItemAt(0).getUri());
                    img2.setImageURI(clipData.getItemAt(1).getUri());
                    img3.setImageURI(clipData.getItemAt(2).getUri());
                    img4.setImageURI(clipData.getItemAt(3).getUri());
                    for(int i =0; i<clipData.getItemCount();i++){

                        Uri uri = clipData.getItemAt(i).getUri();
                        Log.e("MAS IMGS",uri.toString());
                    }
                }
            }catch (Exception ex){
                String a = ex.getMessage();
            }

        }
    }

    private void init() {
        rb_chothue = findViewById(R.id.rb_chothue);
        rb_oghep = findViewById(R.id.rb_oghep);

        // Phần đăng ảnh
        btn_upImg = (Button) findViewById(R.id.btn_upImg);
        img1 = (ImageView) findViewById(R.id.img1);
        img2 = (ImageView) findViewById(R.id.img2);
        img3 = (ImageView) findViewById(R.id.img3);
        img4 = (ImageView) findViewById(R.id.img4);
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        // Hàm này viết ở fragment chứa list phòng cần cập nhật
    }
}
