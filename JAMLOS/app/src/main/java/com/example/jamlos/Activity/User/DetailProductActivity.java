package com.example.jamlos.Activity.User;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.jamlos.Model.Order;
import com.example.jamlos.Model.lstOrder;
import com.example.jamlos.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class DetailProductActivity extends AppCompatActivity {
    ImageView imgDTProduct, imgDTProduct1, imgDTProduct2, imgDTProduct3;
    ElegantNumberButton elnNumber;
    TextView txtDTName, txtDTPrice, txtBrandName2, txtSlogan3, txtCart, txtDTDiscount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_product);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navView.setItemIconTintList(null);

        Init();
        SetStyle();

        Intent intent = getIntent();
        ArrayList<String> img =  intent.getExtras().getStringArrayList("img");
        final String name = intent.getExtras().getString("name");
        final String price = intent.getExtras().getString("price");
        final String discount = intent.getExtras().getString("discount");

        txtDTName.setText(name);
        txtDTPrice.setText(price + " VND");
        if(discount.equals("0")){
            txtDTDiscount.setVisibility(View.GONE);
        } else {
            txtDTDiscount.setText(discount + "%");
        }


        Picasso.with(this).load(img.get(0)).into(imgDTProduct);
        Picasso.with(this).load(img.get(0)).into(imgDTProduct1);
        Picasso.with(this).load(img.get(1)).into(imgDTProduct2);
        Picasso.with(this).load(img.get(2)).into(imgDTProduct3);

        txtCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = txtDTName.getText().toString();
                String quantity = elnNumber.getNumber().toString();
                String price1 = price;
                String discount1 = discount;

                lstOrder.getLstOrder().add(new Order (name, quantity, price1, discount1));
                Toast.makeText(DetailProductActivity.this, "Added to cart", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void Init() {
        txtBrandName2 = (TextView) findViewById(R.id.txtBranName2);
        txtSlogan3 = (TextView) findViewById(R.id.txtSlogan3);
        txtDTDiscount = (TextView) findViewById(R.id.txtDetailDiscount);
        imgDTProduct = (ImageView) findViewById(R.id.imgDetailProduct);
        imgDTProduct1 = (ImageView) findViewById(R.id.imgDetailProduct1);
        imgDTProduct2 = (ImageView) findViewById(R.id.imgDetailProduct2);
        imgDTProduct3 = (ImageView) findViewById(R.id.imgDetailProduct3);
        txtDTName = (TextView) findViewById(R.id.txtDetailProductName);
        txtDTPrice = (TextView) findViewById(R.id.txtDeTailProductPrice);
        elnNumber = (ElegantNumberButton) findViewById(R.id.elnNumber);
        txtCart = (TextView) findViewById(R.id.txtCart);
    }

    private void SetStyle() {
        Typeface face1 = Typeface.createFromAsset(getAssets(), "fonts/FuturaPTBook.otf");
        Typeface face = Typeface.createFromAsset(getAssets(), "fonts/FuturaPTLight.otf");
        txtBrandName2.setTypeface(face);
        txtSlogan3.setTypeface(face1);
        txtDTName.setTypeface(face1);
        txtDTPrice.setTypeface(face1);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    Intent Home = new Intent(DetailProductActivity.this, HomeActivity.class);
                    startActivity(Home);
                    break;
                case R.id.navigation_sale:
                    Intent Sale = new Intent(DetailProductActivity.this, SaleActivity.class);
                    startActivity(Sale);
                    break;
                case R.id.navigation_products:
                    Intent Product = new Intent(DetailProductActivity.this, ProductActivity.class);
                    startActivity(Product);
                    break;
                case R.id.navigation_shoppingcart:
                    Intent Cart = new Intent(DetailProductActivity.this, CartActivity.class);
                    startActivity(Cart);
                    break;
                case R.id.navigation_account:
                    Intent Account = new Intent(DetailProductActivity.this, AccountActivity.class);
                    startActivity(Account);
                    break;
            }
            return false;
        }
    };
}
