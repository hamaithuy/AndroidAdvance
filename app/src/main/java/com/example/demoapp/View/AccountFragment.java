package com.example.demoapp.View;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.example.demoapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

public class AccountFragment extends Fragment implements View.OnClickListener {
    Button btnLogout;
    Button btnPostedRoom;
    ImageView imgUserAvatar;
    TextView txtUserName;

    private Uri mImageUri;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account,container,false);
        btnLogout = view.findViewById(R.id.btn_logout);
        imgUserAvatar = view.findViewById(R.id.img_user_avatar);
        txtUserName = view.findViewById(R.id.txt_user_name);
        btnPostedRoom = view.findViewById(R.id.btn_phong_da_dang);
//        String photo = getArguments().getString("PHOTO");
//        String name = getArguments().getString("NAME");
        btnPostedRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                comeToPostedActivity();
            }
        });
        MainActivity activity = (MainActivity) getActivity();
        String photoFromMainActivity = activity.getUserUriPhoto();
        String nameFromMainActivity = activity.getUserName();

        mImageUri = Uri.parse(photoFromMainActivity);

        Picasso.with(getContext()).load(mImageUri).into(imgUserAvatar);
        txtUserName.setText(nameFromMainActivity);



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

    private void comeToPostedActivity() {
        Intent intent =new Intent(getContext(),com.example.demoapp.View.PostedActivity.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {

    }
}
