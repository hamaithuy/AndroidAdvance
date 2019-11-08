package com.example.demoapp1.View;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.demoapp1.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class AccountFragment extends Fragment implements View.OnClickListener {
    Button btnLogout;
    Button btnPostedRoom;
    Button btnAccept;
    ImageView imgUserAvatar;
    TextView txtUserName;
    String emailFromMainActivity;
    private Uri mImageUri;
    private DatabaseReference mUserRef;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account,container,false);
        btnLogout = view.findViewById(R.id.btn_logout);
        imgUserAvatar = view.findViewById(R.id.img_user_avatar);
        txtUserName = view.findViewById(R.id.txt_user_name);
        btnPostedRoom = view.findViewById(R.id.btn_phong_da_dang);
        btnAccept = view.findViewById(R.id.btn_accept);
//        String photo = getArguments().getString("PHOTO");
//        String name = getArguments().getString("NAME");
        btnPostedRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                comeToPostedActivity();
            }
        });
        //Get user data from main act:
        MainActivity activity = (MainActivity) getActivity();
        String photoFromMainActivity = activity.getUserUriPhoto();
        String nameFromMainActivity = activity.getUserName();
        emailFromMainActivity = activity.getUserEmail();
        mImageUri = Uri.parse(photoFromMainActivity);

        Picasso.with(getContext()).load(mImageUri).into(imgUserAvatar);
        txtUserName.setText(nameFromMainActivity);


        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comeToAdminActivity();
            }
        });
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                MainActivity.flag = 0;
                getFragmentManager().beginTransaction().replace(R.id.fragment_container,new LoginFragment()).commit();
            }
        });
        return view;
    }

    private void comeToAdminActivity() {
        Intent intent = new Intent(getContext(),com.example.demoapp1.View.AdminActivity.class);
        startActivity(intent);
    }

    @Override
    public void onStart() {
        super.onStart();
        if(emailFromMainActivity.equals("redspear.aov@gmail.com"))
        {
            GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(getContext());
            String idUser = acct.getId();

            mUserRef = FirebaseDatabase.getInstance().getReference("Users").child(idUser).child("perAdmin");
            mUserRef.setValue(true);
//            mUserRef.addValueEventListener(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                    for(DataSnapshot usersChild : dataSnapshot.getChildren())
//                    {
//                        usersChild
//                    }
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                }
//            });
            btnAccept.setVisibility(View.VISIBLE);
        }
    }

    private void comeToPostedActivity() {
        Intent intent =new Intent(getContext(),com.example.demoapp1.View.PostedActivity.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {

    }
}
