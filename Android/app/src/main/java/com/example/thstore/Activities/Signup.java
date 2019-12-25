package com.example.thstore.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.thstore.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Signup  extends AppCompatActivity {

    Button btnVerify;
    EditText txtUserEmail_Signup;
    EditText txtPassword_Signup;
    EditText txtUsername_Signup;
    FirebaseAuth mAuthentication;
// ...
// Initialize Firebase Auth


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);


        btnVerify = (Button) findViewById(R.id.btnSignup1);
        txtPassword_Signup = (EditText) findViewById(R.id.txtPassword_Signup);
        txtUserEmail_Signup = (EditText) findViewById(R.id.txtUserEmail_Signup);
        txtUsername_Signup = (EditText) findViewById(R.id.txtUsername_Signup);

        mAuthentication = FirebaseAuth.getInstance();
        btnVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Signup();
            }
        });

    }

    private void Signup(){
        String email = txtUsername_Signup.getText().toString();
        String password = txtPassword_Signup.getText().toString();
        //String name = txtUsername_Signup.getText().toString();

        mAuthentication.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                       if (task.isSuccessful()){
                           Toast.makeText(Signup.this, "Đăng ký thành công!", Toast.LENGTH_SHORT).show();
                           startActivity(new Intent(Signup.this, Login.class));
                       }
                       else {
                           Toast.makeText(Signup.this, "Đăng ký không thành công! Sai định dạng Email hoặc mật khẩu", Toast.LENGTH_SHORT).show();
                       }
                    }
                });
    }


}
