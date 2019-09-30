package com.example.jamlos.Activity.User;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jamlos.Model.User;
import com.example.jamlos.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignUpActivity extends AppCompatActivity {

    TextView txtSignUp, txtHaveAccount, txtSlogan, txtSlogan1;
    EditText edtName, edtEmail, edtPhoneNumber, edtPassword, edtAddress;
    Button btnSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        Init();
        SetStyle();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference tblUsers = database.getReference("Users");

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ProgressDialog mDialog = new ProgressDialog(SignUpActivity.this);
                mDialog.setMessage("Please waiting...");
                mDialog.show();

                tblUsers.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.child(edtPhoneNumber.getText().toString()).exists()){
                            mDialog.dismiss();
                            Toast.makeText(SignUpActivity.this, "Sign up failed", Toast.LENGTH_SHORT).show();
                        } else{
                            mDialog.dismiss();
                            User user = new User(edtPhoneNumber.getText().toString(), edtName.getText().toString(), edtEmail.getText().toString(), edtPassword.getText().toString(), edtAddress.getText().toString());
                            tblUsers.child(edtPhoneNumber.getText().toString()).setValue(user);
                            Toast.makeText(SignUpActivity.this, "Sign up successfully", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

        txtHaveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUpActivity.this, LogInActivity.class);
                startActivity(intent);
            }
        });
    }

    public void Init(){
        txtSignUp = (TextView) findViewById(R.id.txtSignUp);
        edtName = (EditText) findViewById(R.id.edtSUName);
        edtEmail = (EditText) findViewById(R.id.edtSUEmail);
        edtPhoneNumber = (EditText) findViewById(R.id.edtSUPhoneNumber);
        edtPassword = (EditText) findViewById(R.id.edtSUPassword);
        edtAddress = (EditText) findViewById(R.id.edtSUAddress);
        btnSignUp = (Button) findViewById(R.id.btnSUSignUp);
        txtHaveAccount = (TextView) findViewById(R.id.txtHaveAccount);
        txtSlogan = (TextView) findViewById(R.id.txtSUSlogan);
        txtSlogan1 = (TextView) findViewById(R.id.txtSUSlogan1);
    }

    private void SetStyle(){
        Typeface face1 = Typeface.createFromAsset(getAssets(), "fonts/FuturaPTBold.otf");
        Typeface face = Typeface.createFromAsset(getAssets(), "fonts/FuturaPTBook.otf");
        Typeface face2 = Typeface.createFromAsset(getAssets(), "fonts/FuturaPTLight.otf");
        txtSignUp.setTypeface(face1);
        edtName.setTypeface(face);
        edtEmail.setTypeface(face);
        edtPhoneNumber.setTypeface(face);
        edtPassword.setTypeface(face);
        edtAddress.setTypeface(face);
        btnSignUp.setTypeface(face2);
        txtSlogan.setTypeface(face2);
        txtSlogan1.setTypeface(face2);
        txtHaveAccount.setTypeface(face);
    }
}
