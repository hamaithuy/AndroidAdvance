package com.example.thstore.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.thstore.R;
import com.squareup.picasso.Picasso;

import static com.example.thstore.Activities.Home.EXTRA_NAME;
import static com.example.thstore.Activities.Home.EXTRA_PRICE;
import static com.example.thstore.Activities.Home.EXTRA_URL;

public class Bill extends Fragment {


    TextView txtProductsName;
    ImageView imgProductImage;
    TextView txtProductPrice;

    public  Bill(){}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_bill, container, false);
        //Intent intent = Intent.getIntentOld();



        return v;
    }




}
