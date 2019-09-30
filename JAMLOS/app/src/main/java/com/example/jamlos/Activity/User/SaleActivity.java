package com.example.jamlos.Activity.User;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;

import com.example.jamlos.Adapter.ProductAdapter;
import com.example.jamlos.Model.Product;
import com.example.jamlos.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mancj.materialsearchbar.MaterialSearchBar;

import java.util.ArrayList;

public class SaleActivity extends AppCompatActivity {

    EditText SearchBar;
    GridView grvSearchProduct;
    ArrayList<Product> lstProduct;
    ProductAdapter grvAdapter;
    ProductAdapter grvAdapter1;
    FirebaseDatabase mDatabase;
    DatabaseReference tblProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sale);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navView.setItemIconTintList(null);

        lstProduct = new ArrayList<Product>();
        SearchBar = (EditText) findViewById(R.id.searchBar);
        grvSearchProduct = (GridView) findViewById(R.id.grvSearchProduct);

        mDatabase = FirebaseDatabase.getInstance();
        tblProduct = mDatabase.getReference().child("Product");
//        if (SearchBar.getText().isEmpty()) {
//            tblProduct.addValueEventListener(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                    for (DataSnapshot snap : dataSnapshot.getChildren()) {
//                        String Ten = (String) snap.getKey();
//                        String Gia = (String) snap.child("Price").getValue().toString();
//                        String GiamGia = (String) snap.child("Discount").getValue().toString();
//                        String Anh = (String) snap.child("Image/Image1").getValue().toString();
//                        String Anh1 = (String) snap.child("Image/Image2").getValue().toString();
//                        String Anh2 = (String) snap.child("Image/Image3").getValue().toString();
//                        String Kieu = (String) snap.child("Type").getValue().toString();
//                        ArrayList<String> lstPic = new ArrayList<>();
//                        lstPic.add(Anh);
//                        lstPic.add(Anh1);
//                        lstPic.add(Anh2);
//                        lstProduct.add(new Product(Ten, Gia, lstPic, GiamGia, Kieu));
//                    }
//                    grvAdapter = new ProductAdapter(lstProduct, SaleActivity.this);
//                    grvAdapter1 = new ProductAdapter(lstProduct, SaleActivity.this);
//                    grvSearchProduct.setAdapter(grvAdapter);
//                    grvSearchProduct.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                        @Override
//                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                            Intent ProductDetail = new Intent(SaleActivity.this, DetailProductActivity.class);
//                            ProductDetail.putExtra("img", lstProduct.get(i).getLstImage());
//                            ProductDetail.putExtra("name", lstProduct.get(i).getName());
//                            ProductDetail.putExtra("price", lstProduct.get(i).getPrice());
//                            ProductDetail.putExtra("discount", lstProduct.get(i).getDiscount());
//                            startActivity(ProductDetail);
//                        }
//                    });
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                }
//            });
//        } else {
            SearchBar.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    lstProduct.clear();

                    tblProduct.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot snap : dataSnapshot.getChildren()) {
                        String Ten = (String) snap.getKey();
                        if(Ten.toLowerCase().contains(SearchBar.getText().toString().toLowerCase().trim())){
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
                            lstProduct.add(new Product(Ten, Gia, lstPic, GiamGia, Kieu));
                        }

                    }
                    grvAdapter = new ProductAdapter(lstProduct, SaleActivity.this);
                    grvSearchProduct.setAdapter(grvAdapter);
                    grvSearchProduct.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            Intent ProductDetail = new Intent(SaleActivity.this, DetailProductActivity.class);
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

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
        }
//    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    Intent Home = new Intent(SaleActivity.this, HomeActivity.class);
                    startActivity(Home);
                    break;
                case R.id.navigation_sale:
                    return true;
                case R.id.navigation_products:
                    Intent Product = new Intent(SaleActivity.this, CategoryActivity.class);
                    startActivity(Product);
                    break;
                case R.id.navigation_shoppingcart:
                    Intent Cart = new Intent(SaleActivity.this, CartActivity.class);
                    startActivity(Cart);
                    break;
                case R.id.navigation_account:
                    Intent Account = new Intent(SaleActivity.this, AccountActivity.class);
                    startActivity(Account);
                    break;
            }
            return false;
        }
    };
}
