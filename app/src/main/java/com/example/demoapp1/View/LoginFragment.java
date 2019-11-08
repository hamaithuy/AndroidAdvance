package com.example.demoapp1.View;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.demoapp1.R;

public class LoginFragment extends Fragment {

    private Button btnLogin;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login,container,false);
        btnLogin = view.findViewById(R.id.btn_login);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                comeToLoginActivity();
            }
        });
        return view;
    }

    private void comeToLoginActivity() {
        Intent intent = new Intent(getActivity(),com.example.demoapp1.View.LoginActivity.class);
        startActivity(intent);
    }
}
