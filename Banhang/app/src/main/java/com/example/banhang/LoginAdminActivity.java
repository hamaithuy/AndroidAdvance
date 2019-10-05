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

import com.example.banhang.Model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginAdminActivity extends AppCompatActivity {

    EditText phonenumber,passowrd;
    Button AdminLogin;
    TextView NotAdmin;
    ProgressDialog loadingBar;
    String Databasenameadmin = "Admin";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_admin);
        phonenumber = (EditText)findViewById(R.id.adminlogin_inputphonenumber);
        passowrd = (EditText)findViewById(R.id.adminlogin_inputpassword);
        AdminLogin = (Button)findViewById(R.id.adminLogin_button_login);
        NotAdmin = (TextView)findViewById(R.id.adminLogin_notadmin);
        loadingBar = new ProgressDialog(this);
        AdminLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginAdmin();
            }
        });
        NotAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginAdminActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });

    }

    private void LoginAdmin() {
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

    private void Dangnhap(final String phone,final String pass) {
        final DatabaseReference rootref;
        rootref = FirebaseDatabase.getInstance().getReference();
        rootref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.child(Databasenameadmin).child(phone).exists()){
                    User userData = dataSnapshot.child(Databasenameadmin).child(phone).getValue(User.class);
                    if(userData.getPhone().equals(phone)&&userData.getPass().equals(pass)){
                        Toast.makeText(LoginAdminActivity.this,"Đăng nhập thành công",Toast.LENGTH_LONG).show();
                        loadingBar.dismiss();
                        Intent intent = new Intent(LoginAdminActivity.this,AdminCategoryActivity.class);
                        startActivity(intent);
                    }

                }
                else {
                    loadingBar.dismiss();
                    Toast.makeText(LoginAdminActivity.this,"Tài khoản không tồn tại",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
