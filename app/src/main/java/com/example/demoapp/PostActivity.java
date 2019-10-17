package com.example.demoapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;
import java.util.List;

public class PostActivity extends AppCompatActivity  {
    RadioGroup rg_kindPost,rg_kindRoom;
    RadioButton rb_chothue,rb_oghep,rb_Phong,rb_Canho,rb_Canhomini,rb_Nguyencan;
    TextView tv_Cancel,tv_Post;
    EditText et_address,et_price,et_acreage,et_title,et_boss,et_phone,et_description;
    CheckBox cb_wifi,cb_ownWC,cb_keepCar,cb_free,cb_kitchen,cb_airMachine,cb_fridge,cb_washMachine;
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
        // Đăng phòng
        tv_Post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PostRoom();
            }
        });

    }

    /// Thực hiện post room lên fire base
    private void PostRoom() {
        Room room = new Room();
        // Check loại tin
        int kindPost = rb_oghep.isChecked()==true ?  2 : 1;
        room.setKindPost(kindPost);
        // Check loại phòng
        int kindRoom = rb_Phong.isChecked() == true ? 1 : rb_Canho.isChecked() == true ? 2 :
                       rb_Canhomini.isChecked() == true ? 3 : 4;
        room.setKindRoom(kindRoom);
        // Tiêu đề , Địa chỉ , giá, số điện thoại, diện tích, mô tả
        room.setTitle(et_title.getText().toString());
        room.setAddress(et_address.getText().toString());
        room.setPrice(Double.parseDouble(et_price.getText().toString()));
        room.setPhoneNumber(et_phone.getText().toString());
        room.setAcreage(Float.parseFloat(et_acreage.getText().toString()));
        room.setDescription(et_description.getText().toString());
        // Set time
        Date date = new Date();
        room.setTimePost(date);
        // Tiện ích phòng
        room.setWifi(cb_wifi.isChecked());
        room.setOwnWc(cb_ownWC.isChecked());
        room.setKeepCar(cb_keepCar.isChecked());
        room.setFreedom(cb_free.isChecked());
        room.setKitchen(cb_kitchen.isChecked());
        room.setAirMachine(cb_airMachine.isChecked());
        room.setFridge(cb_fridge.isChecked());
        room.setWashMachine(cb_washMachine.isChecked());
        // Push room to firebase
        new FireBaseDataBaseHelper<Room>("Rooms").WriteData(room, new FireBaseDataBaseHelper.DataStatus<Room>() {
            @Override
            public void DataIsLoaded(List<Room> objects) {

            }

            @Override
            public void DataIsInserted() {
                Toast.makeText(PostActivity.this,"This room has been inserted successfully",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void DataIsUpdated() {

            }

            @Override
            public void DataIsDeleted() {

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
        // Radiobutton loại phòng và loại tin
        rg_kindPost = findViewById(R.id.rg_kindPost);
        rg_kindRoom = findViewById(R.id.rg_kindRoom);
        rb_chothue = findViewById(R.id.rb_chothue);
        rb_oghep = findViewById(R.id.rb_oghep);
        rb_Phong = findViewById(R.id.rb_Phong);
        rb_Canho = findViewById(R.id.rb_Canho);
        rb_Canhomini = findViewById(R.id.rb_Canhomini);
        rb_Nguyencan = findViewById(R.id.rb_Nguyencan);
        // Text View
        tv_Cancel = findViewById(R.id.tv_Cancel);
        tv_Post = findViewById(R.id.tv_Post);
        // Edit Text
        et_address = findViewById(R.id.et_address);
        et_price = findViewById(R.id.et_price);
        et_acreage = findViewById(R.id.et_acreage);
        et_title = findViewById(R.id.et_title);
        et_boss = findViewById(R.id.et_boss);
        et_phone = findViewById(R.id.et_phone);
        et_description = findViewById(R.id.et_description);
        // Check box
        cb_wifi = findViewById(R.id.cb_wifi);
        cb_ownWC = findViewById(R.id.cb_ownWC);
        cb_keepCar = findViewById(R.id.cb_keepCar);
        cb_free = findViewById(R.id.cb_free);
        cb_kitchen = findViewById(R.id.cb_kitchen);
        cb_airMachine = findViewById(R.id.cb_airMachine);
        cb_fridge = findViewById(R.id.cb_fridge);
        cb_washMachine = findViewById(R.id.cb_washMachine);
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
