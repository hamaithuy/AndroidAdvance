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
import android.widget.TextView;
import android.widget.Toast;

import com.example.banhang.Model.CurrentUser;
import com.example.banhang.Model.User;
import com.example.banhang.Prevalent.Prevalent;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.example.banhang.Prevalent.Prevalent;

public class LoginActivity extends AppCompatActivity {
    EditText  phonenumber,passowrd;
    Button Login;
    TextView Admin;
    ProgressDialog loadingBar;
    String Databasenameuser = "User";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Login = (Button)findViewById(R.id.Login_button_login);
        phonenumber = (EditText)findViewById(R.id.login_inputphonenumber);
        passowrd = (EditText)findViewById(R.id.login_inputpassword);
        Admin = (TextView)findViewById(R.id.Login_admin);
        loadingBar = new ProgressDialog(this);
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginUser();
            }
        });
        Admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this,LoginAdminActivity.class);
                startActivity(intent);
            }
        });

    }

    private void LoginUser(){
        String phone = phonenumber.getText().toString();
        String pass = passowrd.getText().toString();
        if(TextUtils.isEmpty(phone)){
            Toast.makeText(this,"Hãy nhập số điện thoại",Toast.LENGTH_LONG).show();
        }
        else if(TextUtils.isEmpty(pass)){
            Toast.makeText(this,"Hãy nhập mật khẩu",Toast.LENGTH_LONG).show();
        }
        else {
            loadingBar.setTitle("Đăng nhập");
            loadingBar.setMessage("Chờ trong giây lát");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();
            Dangnhap(phone,pass);
        }

    }
    private void Dangnhap(final String phone, final String pass) {
        final DatabaseReference rootref;
        rootref = FirebaseDatabase.getInstance().getReference();
        rootref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.child(Databasenameuser).child(phone).exists()){
                    User userData = dataSnapshot.child(Databasenameuser).child(phone).getValue(User.class);
                    if(userData.getPhone().equals(phone)&&userData.getPass().equals(pass)){
                        Toast.makeText(LoginActivity.this,"Đăng nhập thành công",Toast.LENGTH_LONG).show();
                        loadingBar.dismiss();
                        Intent intent = new Intent(LoginActivity.this,HomeActivity.class);
                        CurrentUser.CurrentUser = userData;
                        startActivity(intent);

                    }
                }
                else {
                    loadingBar.dismiss();
                    Toast.makeText(LoginActivity.this,"Tài khoản không tồn tại",Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
