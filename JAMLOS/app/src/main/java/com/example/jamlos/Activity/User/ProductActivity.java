package com.example.jamlos.Activity.User;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.example.jamlos.Adapter.ProductAdapter;
import com.example.jamlos.Model.Image;
import com.example.jamlos.Model.Product;
import com.example.jamlos.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ProductActivity extends AppCompatActivity {

    TextView txtBrandName1, txtSlogan2, txtProduct;
    GridView grvProduct;
    ArrayList<Product> lstProduct;
    ProductAdapter listViewAdapter;
    DatabaseReference tblFeaturedProduct;
    FirebaseDatabase mDatabase;
    String a;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navView.setItemIconTintList(null);
        grvProduct = (GridView) findViewById(R.id.grvProduct);
        txtBrandName1 = (TextView) findViewById(R.id.txtBrandName1);
        txtSlogan2 = (TextView) findViewById(R.id.txtSlogan2);
        txtProduct = (TextView) findViewById(R.id.txtProduct);

        SetStyle();
        Intent b = getIntent();
        a = b.getExtras().getString("type");
        mDatabase = FirebaseDatabase.getInstance();

        tblFeaturedProduct = mDatabase.getReference().child("Product");
        tblFeaturedProduct.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                lstProduct = new ArrayList<Product>();
                for (DataSnapshot snap : dataSnapshot.getChildren()) {
                    String Kieu = (String) snap.child("Type").getValue().toString();
                    if (Kieu.equals(a)) {
                        String Ten = (String) snap.getKey();
                        String Gia = (String) snap.child("Price").getValue().toString();
                        String GiamGia = (String) snap.child("Discount").getValue().toString();
//                        ArrayList<Image> lstPic = new ArrayList<>();
//                        for (DataSnapshot snap1 : snap.getChildren()){
//                            String Anh = (String) snap.child("Image").child("Image1").getValue().toString(); // tổ chức thư mục với image cùng cấp với price
//                            String Anh1 = (String) snap.child("Image2").getValue().toString();
//                            String Anh2 = (String) snap.child("Image3").getValue().toString();
//                            lstPic.add(new Image(Anh, Anh1, Anh2));
//                        }
                        String Anh = (String) snap.child("Image/Image1").getValue().toString();
                        String Anh1 = (String) snap.child("Image/Image2").getValue().toString();
                        String Anh2 = (String) snap.child("Image/Image3").getValue().toString();
                        ArrayList<String> lstPic = new ArrayList<>();
                        lstPic.add(Anh);
                        lstPic.add(Anh1);
                        lstPic.add(Anh2);
                        lstProduct.add(new Product(Ten, Gia, lstPic, GiamGia, Kieu));
                    }
                }
                listViewAdapter = new ProductAdapter(lstProduct, ProductActivity.this);
                grvProduct.setAdapter(listViewAdapter);
                grvProduct.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Intent ProductDetail = new Intent(ProductActivity.this, DetailProductActivity.class);
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

    private void SetStyle() {
        Typeface face1 = Typeface.createFromAsset(getAssets(), "fonts/FuturaPTBook.otf");
        Typeface face = Typeface.createFromAsset(getAssets(), "fonts/FuturaPTLight.otf");
        txtBrandName1.setTypeface(face);
        txtSlogan2.setTypeface(face1);
        txtProduct.setTypeface(face1);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    Intent Home = new Intent(ProductActivity.this, HomeActivity.class);
                    startActivity(Home);
                    break;
                case R.id.navigation_sale:
                    Intent Sale = new Intent(ProductActivity.this, SaleActivity.class);
                    startActivity(Sale);
                    break;
                case R.id.navigation_products:
                    return true;
                case R.id.navigation_shoppingcart:
                    Intent Cart = new Intent(ProductActivity.this, CartActivity.class);
                    startActivity(Cart);
                    break;
                case R.id.navigation_account:
                    Intent Account = new Intent(ProductActivity.this, AccountActivity.class);
                    startActivity(Account);
                    break;
            }
            return false;
        }
    };
}