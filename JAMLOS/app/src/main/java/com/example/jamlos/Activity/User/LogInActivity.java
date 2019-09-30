package com.example.jamlos.Activity.User;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;

import com.example.jamlos.Model.Common;
import com.example.jamlos.Model.User;
import com.example.jamlos.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;


public class LogInActivity extends AppCompatActivity {

    ImageView imgLogo;
    TextView txtSlogan, txtForgotPassWord, txtLogIn;
    EditText edtPhoneNumber, edtPassWord;
    Button btnLogIn,  btnSignUp, btnOthers;
    RadioGroup rdGroup;
    RadioButton rdAdmin, rdShipper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Init();
        SetStyle();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference tblUsers = database.getReference("Users");

        btnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tblUsers.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        //Check if User doesn't exist in database
                        if(dataSnapshot.child(edtPhoneNumber.getText().toString()).exists()){
                            //Get User information
                            User user = dataSnapshot.child(edtPhoneNumber.getText().toString()).getValue(User.class);
                            if(user.getPassword().equals(edtPassWord.getText().toString())){
                                Intent HomeIntent = new Intent(LogInActivity.this, HomeActivity.class);
                                Common.currentUser = user;
                                startActivity(HomeIntent);
                                finish();
                            } else{
                                Toast.makeText(LogInActivity.this, "Wrong password!", Toast.LENGTH_SHORT).show();
                            }
                        } else{
                            Toast.makeText(LogInActivity.this, "User doesn't exist!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LogInActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });

        txtForgotPassWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        btnOthers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rdAdmin.setVisibility(View.VISIBLE);
                rdShipper.setVisibility(View.VISIBLE);
            }
        });
    }

    private void Init(){
        imgLogo = (ImageView) findViewById(R.id.imgLogo);
        txtSlogan = (TextView) findViewById(R.id.txtSlogan);
        txtLogIn = (TextView) findViewById(R.id.txtLogIn);
        edtPhoneNumber = (EditText) findViewById(R.id.edtPhoneNumber);
        edtPassWord = (EditText) findViewById(R.id.edtPassWord);
        btnLogIn = (Button) findViewById(R.id.btnLogIn);
        btnSignUp = (Button) findViewById(R.id.btnSignUp);
        txtForgotPassWord = (TextView) findViewById(R.id.txtForgotPassWord);
        btnOthers = (Button) findViewById(R.id.btnOthers);
        rdGroup = (RadioGroup) findViewById(R.id.rdGroup);
        rdAdmin = (RadioButton) findViewById(R.id.rdAdmin);
        rdShipper = (RadioButton) findViewById(R.id.rdShipper);
    }

    private void SetStyle(){
        Typeface face = Typeface.createFromAsset(getAssets(), "fonts/FuturaPTBook.otf");
        txtSlogan.setTypeface(face);
        txtLogIn.setTypeface(face);
        edtPhoneNumber.setTypeface(face);
        edtPassWord.setTypeface(face);
        btnLogIn.setTypeface(face);
        btnSignUp.setTypeface(face);
        txtForgotPassWord.setTypeface(face);
        btnOthers.setTypeface(face);
        rdAdmin.setTypeface(face);
        rdShipper.setTypeface(face);
    }
}
