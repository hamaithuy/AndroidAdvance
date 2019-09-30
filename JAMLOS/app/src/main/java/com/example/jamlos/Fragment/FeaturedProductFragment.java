package com.example.jamlos.Fragment;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.jamlos.Activity.User.DetailProductActivity;
import com.example.jamlos.Adapter.ProductAdapter;
import com.example.jamlos.Model.Product;
import com.example.jamlos.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class FeaturedProductFragment extends Fragment {

    GridView grvFeaturedProduct;
    ArrayList<Product> lstProduct;
    ProductAdapter listViewAdapter;
    DatabaseReference tblFeaturedProduct;
    FirebaseDatabase mDatabase;

    public FeaturedProductFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_featured_product, container, false);
        grvFeaturedProduct = (GridView) rootView.findViewById(R.id.grvFeaturedProduct);
        return rootView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        lstProduct = new ArrayList<Product>();
        mDatabase = FirebaseDatabase.getInstance();

        tblFeaturedProduct = mDatabase.getReference().child("Advertisment/Featured Product");
        tblFeaturedProduct.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snap : dataSnapshot.getChildren()) {
                    String Ten = (String) snap.getKey();
                    String Gia = (String) snap.child("Price").getValue().toString();
                    String GiamGia = (String) snap.child("Discount").getValue().toString();
                    String Anh = (String) snap.child("Image/Image1").getValue().toString();
                    String Anh1 = (String) snap.child("Image/Image2").getValue().toString();
                    String Anh2 = (String) snap.child("Image/Image3").getValue().toString();
                    String Kieu = (String) snap.child("Type").getValue().toString();
                    ArrayList<String> lstPic = new ArrayList<>();
                    lstPic.add(Anh);
                    lstPic.add(Anh1);
                    lstPic.add(Anh2);
//                    for (DataSnapshot snap1 : snap.child("" + Ten + "picture").getChildren()) {
//                        String Image0 = snap1.getKey();
//                        String Image1 = snap1.child("picture1").getValue().toString();
//                        String Image2 = snap1.child("picture2").getValue().toString();
//                        String Image3 = snap1.child("picture3").getValue().toString();
//                        lstPic.add(new Image(Image1, Image2, Image3));
//                    }
//                    if(Ten.equals(a)){
                    lstProduct.add(new Product(Ten, Gia, lstPic, GiamGia, Kieu));
                }
                listViewAdapter = new ProductAdapter(lstProduct, getContext());
                grvFeaturedProduct.setAdapter(listViewAdapter);
                grvFeaturedProduct.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Intent ProductDetail = new Intent(getContext(), DetailProductActivity.class);
                        ProductDetail.putExtra("img", lstProduct.get(i).getLstImage());
                        ProductDetail.putExtra("name", lstProduct.get(i).getName());
                        ProductDetail.putExtra("price", lstProduct.get(i).getPrice());
                        ProductDetail.putExtra("discount", lstProduct.get(i).getDiscount());
                        startActivity(ProductDetail);
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
