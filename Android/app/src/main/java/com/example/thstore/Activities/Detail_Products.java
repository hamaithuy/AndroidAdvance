package com.example.thstore.Activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.thstore.Model.Data_Offer;
import com.example.thstore.R;
import com.squareup.picasso.Picasso;


import java.util.ArrayList;

import static com.example.thstore.Activities.Home.EXTRA_ID;
import static com.example.thstore.Activities.Home.EXTRA_NAME;
import static com.example.thstore.Activities.Home.EXTRA_PRICE;
import static com.example.thstore.Activities.Home.EXTRA_URL;

public class Detail_Products  extends AppCompatActivity {


    TextView txtProductsName, txtproductsID ;
    ImageView imgProductImage;
    TextView txtProductPrice, txtAccount;
    RadioButton cbBlack, cbBlue, cbWhite, cbSizeS, cbSizeM, cbSizeL, cbSizeXL;
    RadioGroup rdgColor, rdgSize;
    Button btnAddCart;
    String size = "", color = "", productsName = "", productsPrice = "", productsID = "";
    int newCost = 0;

    public static final String EXTRA_URL_CART = "image_cart";
    public static final String EXTRA_NAME_CART = "productsName_cart";
    public static final String EXTRA_PRICE_CART = "productsPrice_cart";
    public static final String EXTRA_SIZE_CART = "productsSize_cart";
    public static final String EXTRA_COLOR_CART = "productsColor_cart";
    public static final String EXTRA_ACCOUNTS_CART = "productsAccounts_cart";
    public static final String EXTRA_COST_CART = "productsCost_cart";
    public static final String EXTRA_ID_CART = "productsID_cart";
    public static final String BUNDLE = "bundle";


    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_products);

        Intent intent = getIntent();
        final String imageURL = intent.getStringExtra(EXTRA_URL);
        productsName = intent.getStringExtra(EXTRA_NAME);
        productsPrice = intent.getStringExtra(EXTRA_PRICE);
        productsID = intent.getStringExtra(EXTRA_ID);

        txtProductsName = (TextView) findViewById(R.id.txtBillProductsName);
        imgProductImage = (ImageView) findViewById(R.id.imgBilPicture);
        txtProductPrice = (TextView) findViewById(R.id.txtBillPrice);
        txtproductsID = findViewById(R.id.txtBillID);
        txtAccount = (TextView) findViewById(R.id.txtAccount);

        Picasso.get().load(imageURL).fit().into(imgProductImage);
        txtProductsName.setText(productsName);
        txtProductPrice.setText(productsPrice);
        txtproductsID.setText(productsID);
        //Data data = getIntent().getSerializableExtra("");

        Reflection();
        btnAddCart = findViewById(R.id.btnAddCart);
        CheckRadioButtonSize();
        CheckRadioButtonColor();

        btnAddCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (txtAccount.getText().length() < 1 || txtAccount.getText().toString().equals("0") || !cbBlack.isChecked() && !cbBlue.isChecked()
                    && !cbWhite.isChecked() || !cbSizeS.isChecked()&& !cbSizeM.isChecked()&& !cbSizeL.isChecked()
                    && !cbSizeXL.isChecked())
                {
                    Toast.makeText(Detail_Products.this, "Chưa nhập đủ dữ liệu hoặc nhập sai", Toast.LENGTH_SHORT).show();
                }
                else {
                        if (MainActivity.arrayData_Offers.size() > 0)
                        {
                            Data_Offer data_offer = new Data_Offer(productsName, productsPrice, txtAccount.getText().toString(), imageURL, size, productsID);
                            MainActivity.arrayData_Offers.add(data_offer);
                        }
                        else {
                            Data_Offer data_offer = new Data_Offer(productsName, productsPrice, txtAccount.getText().toString(), imageURL, size, productsID);
                            MainActivity.arrayData_Offers.add(data_offer);
                        }

                        Intent intent_cart = new Intent(getApplicationContext(), Cart.class);
                        startActivity(intent_cart);
                }



                }
        });
    }

   void CheckRadioButtonColor(){
        /*if (cbBlack.isChecked()){
            color = cbBlack.getText().toString();
            Toast.makeText(Detail_Products.this,color,Toast.LENGTH_SHORT).show();
        }*/
        rdgColor.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                //checkID là ID của Radiobutton
                switch (checkedId){
                    case R.id.cbBlack:
                        color = cbBlack.getText().toString();
                        break;
                    case R.id.cbBlue:
                        color = cbBlue.getText().toString();
                        break;
                    case R.id.cbWhite:
                        color = cbWhite.getText().toString();
                        break;
                }
            }
        });
    }

    void CheckRadioButtonSize(){
        rdgSize.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.cbSizeS:
                        size = cbSizeS.getText().toString();
                        //Toast.makeText(Detail_Products.this,size,Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.cbSizeM:
                        size = cbSizeM.getText().toString();
                        break;
                    case R.id.cbSizeL:
                        size = cbSizeL.getText().toString();
                        break;
                    case R.id.cbSizeXL:
                        size = cbSizeXL.getText().toString();
                        break;
                }
            }
        });
    }

    void Reflection(){
        cbBlack = findViewById(R.id.cbBlack);
        cbBlue = findViewById(R.id.cbBlue);
        cbWhite = findViewById(R.id.cbWhite);
        cbSizeS = findViewById(R.id.cbSizeS);
        cbSizeM = findViewById(R.id.cbSizeM);
        cbSizeL = findViewById(R.id.cbSizeL);
        cbSizeXL = findViewById(R.id.cbSizeXL);
        btnAddCart = findViewById(R.id.btnAddCart);
        rdgColor = findViewById(R.id.rdgColor);
        rdgSize = findViewById(R.id.rdgSize);
    }


    /*if (MainActivity.arrayData_Offers.size() > 0){

                        int Count = Integer.parseInt(txtAccount.getText().toString());
                        boolean exists = false;
                        for (int i = 0; i < MainActivity.arrayData_Offers.size(); i++)
                        {
                            /*if (MainActivity.arrayData_Offers.get(i).getID().equals(productsID))
                            {
                                int j = Integer.parseInt(MainActivity.arrayData_Offers.get(i).getAccount());
                                MainActivity.arrayData_Offers.get(i).setAcount(String.valueOf(j + Count));

                                /*int n = Integer.parseInt(MainActivity.arrayData_Offers.get(i).getPrice());
                                int newPrice = j * n;
                                MainActivity.arrayData_Offers.get(i).setPrice(String.valueOf(newPrice));

                            }*/
    /*int Acount;
                        Acount = Integer.parseInt(txtAccount.getText().toString());
                        int Price = Integer.parseInt(txtProductPrice.getText().toString());
                        newCost = Acount * Price;

                        /*Bundle bundle = new Bundle();
                        bundle.putString(EXTRA_URL_CART, imageURL);
                        bundle.putString(EXTRA_ACCOUNTS_CART, txtAccount.getText().toString());
                        bundle.putString(EXTRA_NAME_CART, txtProductsName.getText().toString());
                        //intent_cart.putExtra(EXTRA_NAME_CART, (Parcelable) txtProductName);
                        bundle.putString(EXTRA_PRICE_CART, txtProductPrice.getText().toString());
                        bundle.putString(EXTRA_SIZE_CART, size);
                        bundle.putString(EXTRA_COLOR_CART, color);
                        bundle.putString(EXTRA_COST_CART, String.valueOf(newCost));
                        bundle.putString(EXTRA_ID_CART, productsID);
                        intent_cart.putExtra(BUNDLE,bundle);
                        //intent_cart.putExtra(EXTRA_COST_CART, String.valueOf(newCost));
                        //startActivity(intent_cart);*color,
                    }*/
    //MainActivity.arrayData_Offers.add(new Data_Offer(txtProductsName.getText().toString(), txtProductPrice.getText().toString(), size, txtproductsID.getText().toString(), txtAccount.getText().toString(), imageURL));
    /*if (exists == false){
                                /*int Acount;
                                Acount = Integer.parseInt(txtAccount.getText().toString());
                                int Price = Integer.parseInt(txtProductPrice.getText().toString());
                                newCost = Acount * Price;


                            }


                        }
                    }
                   else {
                        MainActivity.arrayData_Offers.add(new Data_Offer(txtProductsName.getText().toString(), txtProductPrice.getText().toString(), size, txtproductsID.getText().toString(), txtAccount.getText().toString(), imageURL));

                    }*/
}
