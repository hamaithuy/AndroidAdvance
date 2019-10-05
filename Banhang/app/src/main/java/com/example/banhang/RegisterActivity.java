package com.example.banhang;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {
    Button Dangky;
    EditText phonenumber,passowrd,confirmpassword;
    ProgressDialog loadingBar;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        Dangky = (Button) findViewById(R.id.register_button_login);
        phonenumber = (EditText) findViewById(R.id.register_inputphonenumber);
        passowrd = (EditText) findViewById(R.id.register_inputpassword);
        confirmpassword = (EditText) findViewById(R.id.register_inputconfirmpassowrd);
        loadingBar = new ProgressDialog(this);

        Dangky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CreateAccount();

            }

        });

    }
    private void CreateAccount() {
        String phone = phonenumber.getText().toString();
        String pass = passowrd.getText().toString();
        String confirmpass = confirmpassword.getText().toString();
        if(TextUtils.isEmpty(phone)){
            Toast.makeText(this,"Hãy nhập số điện thoại",Toast.LENGTH_LONG).show();
        }
        else if(TextUtils.isEmpty(pass)){
            Toast.makeText(this,"Hãy nhập mật khẩu",Toast.LENGTH_LONG).show();
        }
        else if(TextUtils.isEmpty(confirmpass)){
            Toast.makeText(this,"Hãy nhập xác nhận mật khẩu",Toast.LENGTH_LONG).show();
        }
        /*else if(pass.toString().equals(confirmpass.toString())){
            Toast.makeText(this,"Xác nhận mật khẩu chưa đúng",Toast.LENGTH_LONG).show();
        }*/
        else {
            loadingBar.setTitle("Tạo tài khoản");
            loadingBar.setMessage("Chờ trong giây lát");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();
            Validatephone(phone,pass);
        }

    }

    private void Validatephone(final String phone, final String pass) {
        final DatabaseReference rootref;
        rootref = FirebaseDatabase.getInstance().getReference();
        rootref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(!(dataSnapshot.child("User").child(phone).exists())){
                    HashMap<String,Object> userdataMap = new HashMap<>();
                    userdataMap.put("phone",phone);
                    userdataMap.put("pass",pass);
                    rootref.child("User").child(phone).updateChildren(userdataMap)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(RegisterActivity.this,"Thành công",Toast.LENGTH_LONG).show();
                                loadingBar.dismiss();
                                Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                                startActivity(intent);
                            }
                            else {
                                loadingBar.dismiss();
                                Toast.makeText(RegisterActivity.this,"Có lỗi xảy ra",Toast.LENGTH_LONG).show();
                            }
                        }
                    });

                }
                else{
                    Toast.makeText(RegisterActivity.this,"Tài khoản đã tồn tại",Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
