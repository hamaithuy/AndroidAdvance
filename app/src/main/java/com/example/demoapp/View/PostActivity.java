package com.example.demoapp.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.demoapp.R;
import com.example.demoapp.Common.FireBaseDataBaseHelper;
import com.example.demoapp.Model.Room;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

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

    Uri mImageUri;
    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;
    private StorageTask mUploadTask;

    public static final int CODE_PICK_IMAGE=1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        // Hàm tham chiếu ID element
        init();
        // Tham chiếu đến fire base
        mStorageRef = FirebaseStorage.getInstance().getReference("rooms_uploads");
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("Rooms");
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
                openFileChooser();
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

    private void comeToLoginActivity() {

    }

    /// Thực hiện lấy ảnh trong thư viện
    public void openFileChooser(){
        Intent intent= new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,CODE_PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == CODE_PICK_IMAGE && resultCode == RESULT_OK && data!=null && data.getData()!=null){
            mImageUri = data.getData();
            Picasso.with(this).load(mImageUri).into(img1);
        }
    }

    private String getFileExtension(Uri uri){
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }


    /// Thực hiện post room lên fire base
    private void PostRoom() {
        if(mImageUri!=null){
            // Push ảnh lên storage
            final StorageReference fileReference = mStorageRef.child(System.currentTimeMillis()
            +"."+getFileExtension(mImageUri));

            fileReference.putFile(mImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    // Push phòng lên DB
                    Room room = new Room();


                    // Check loại tin
                    int kindPost = rb_oghep.isChecked()==true ?  2 : 1;
                    room.setKindPost(kindPost);
                    // Check loại phòng
                    int kindRoom = rb_Phong.isChecked() == true ? 1 : rb_Canho.isChecked() == true ? 2 :
                            rb_Canhomini.isChecked() == true ? 3 : 4;
                    room.setKindRoom(kindRoom);
                    // Tiêu đề , Địa chỉ , giá, số điện thoại, diện tích, mô tả...
                    room.setTitle(et_title.getText().toString());
                    room.setAddress(et_address.getText().toString());
                    room.setPrice(Double.parseDouble(et_price.getText().toString()));
                    room.setPhoneNumber(et_phone.getText().toString());
                    room.setAcreage(Float.parseFloat(et_acreage.getText().toString()));
                    room.setDescription(et_description.getText().toString());
                    room.setBoss(et_boss.getText().toString());
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
                    room.setKey(null);


                    final String uploadID = mDatabaseRef.push().getKey();
                    mDatabaseRef.child(uploadID).setValue(room);

                    // Link ảnh
                    Task<Uri> dowloadUrl = fileReference.getDownloadUrl();
                    dowloadUrl.addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                             String imageRef = uri.toString();
                             mDatabaseRef.child(uploadID).child("imageUrl").setValue(imageRef);
                        }
                    });

                    //Get current user id:
                    GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(getApplicationContext());
                    String idUser = acct.getId();

                    DatabaseReference mUserReff = FirebaseDatabase.getInstance().getReference("Users").child(idUser);
                    mUserReff.child("roomPosted").child(uploadID).setValue(uploadID);
                    Toast.makeText(PostActivity.this,"Phòng của bạn đã đăng",Toast.LENGTH_SHORT).show();
                    // Trở về main activity
                    finish();
                }
            });
        }else{
            Toast.makeText(PostActivity.this,"Bạn chưa chọn ảnh",Toast.LENGTH_SHORT).show();
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
