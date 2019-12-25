package com.example.thstore.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thstore.Adapter.Adapter_Cart;
import com.example.thstore.Model.Data_Bills;
import com.example.thstore.Model.Data_Offer;
import com.example.thstore.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import static com.example.thstore.Activities.MainActivity.*;

public class Cart extends AppCompatActivity {

    RecyclerView recyclerView;
    Adapter_Cart adapter_cart;
    public static TextView txtTotallyMoney;
    Data_Offer data_offer;
    Button btnGoingOrder, btnOrder, btnValue;
    public static int total = 0;
    TextView txtName_Cart_Products, txtPrice_Cart, txtSize_Cart, txtID_Cart_Products;
    ImageView img_Cart_Products;
    DatabaseReference databaseBill;
    Button btnDelete;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        databaseBill = FirebaseDatabase.getInstance().getReference("Bills");


        recyclerView = findViewById(R.id.rvCart_Products);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(Cart.this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        //ArrayList<Data_Offer> data_offers = new ArrayList<>();
        if (arrayData_Offers.size() < 10){

        adapter_cart = new Adapter_Cart(getApplicationContext(), MainActivity.arrayData_Offers);

        recyclerView.setAdapter(adapter_cart);
        CheckData();}
        else {
            Toast.makeText(this, "Hàng không quá 10 món", Toast.LENGTH_SHORT).show();
        }
        txtTotallyMoney = findViewById(R.id.txtTotallyMoney);

        //Đây là arrayList
        totallyMoney();
        btnGoingOrder = findViewById(R.id.btnGoingOrder);
        btnGoingOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                total = 0;
            }
        });


        btnOrder = findViewById(R.id.btnOrder);
        btnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                saveBill();
                arrayData_Offers = null;
                total = 0;
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                /**/
            }
        });

    }


    private void saveBill(){
        btnValue =  findViewById(R.id.btnValue);
        txtName_Cart_Products = findViewById(R.id.txtName_Cart_Products);
        txtID_Cart_Products = findViewById(R.id.txtID_Cart_Products);
        txtPrice_Cart = findViewById(R.id.txtPrice_Cart);
        txtSize_Cart = findViewById(R.id.txtSize_Cart);
        img_Cart_Products = findViewById(R.id.img_Cart_Products);


        String Account = btnValue.getText().toString();
        String Name = txtName_Cart_Products.getText().toString();
        String Price = txtPrice_Cart.getText().toString();
        String Size = txtSize_Cart.getText().toString();
        String ID = txtID_Cart_Products.getText().toString();
        String Image = img_Cart_Products.getDrawable().toString();
        String Total = txtTotallyMoney.getText().toString();

        if(!TextUtils.isEmpty(Name))
        {

            ArrayList<Data_Bills> arrayList_Bill = new ArrayList<>();
            Data_Bills data_bills = new Data_Bills(Name, Price, Account, Image, Size, ID, Total);
            arrayList_Bill.add(data_bills);
            String  ID_Bills = databaseBill.push().getKey();

            /*for (int i = 0; i < arrayData_Offers.size(); i++ ) {

                databaseBill.child(ID_Bills).push().setValue(arrayList_Bill);
            }*/
            databaseBill.child(ID_Bills).push().setValue(arrayList_Bill);

            Toast.makeText(getApplicationContext(), "Đã lưu hóa đơn", Toast.LENGTH_SHORT).show();
        }

    }



    public static void totallyMoney(){
        for (int i = 0; i < arrayData_Offers.size(); i++) {
            int pr = Integer.parseInt(arrayData_Offers.get(i).getPrice());
            int ac = Integer.parseInt(arrayData_Offers.get(i).getAccount());
            total += (pr * ac);
        }
        txtTotallyMoney.setText(String.valueOf(total));
    }

    private void CheckData() {
        if (arrayData_Offers.size() <= 0){
            adapter_cart.notifyItemInserted(getChangingConfigurations());
            recyclerView.setVisibility(View.INVISIBLE);
        }
        else {
            recyclerView.setVisibility(View.VISIBLE);
            adapter_cart.notifyDataSetChanged();
        }
    }





        /*Bundle bundle = intent_cart.getBundleExtra(BUNDLE);
        String txtName_Cart_Products = bundle.getString(EXTRA_NAME_CART);
        String txtPrice_Cart = bundle.getString(EXTRA_PRICE_CART);
        String txtSize_Cart = bundle.getString(EXTRA_SIZE_CART);
        String imgColor_Cart = bundle.getString(EXTRA_COLOR_CART);
        String btnValue = bundle.getString(EXTRA_ACCOUNTS_CART);
        String newCost = bundle.getString(EXTRA_COST_CART);
        String ID_Cart_Products = bundle.getString(EXTRA_ID_CART);
        final String imgProducts = bundle.getString(EXTRA_URL_CART);
        String newCost = intent_cart.getStringExtra(EXTRA_COST_CART);


        data_offer = new Data_Offer(txtName_Cart_Products,txtPrice_Cart, txtSize_Cart, btnValue, imgColor_Cart, imgProducts, ID_Cart_Products);
        totallyMoney = findViewById(R.id.txtTotallyMoney);
        totallyMoney.setText(newCost);*/

        /*name_test = findViewById(R.id.txtNametest);
        size_test = findViewById(R.id.txtSizetest);
        acount_test = findViewById(R.id.txtAccountest);

        //name_test.setText(txtName_Cart_Products);
        size_test.setText(imgColor_Cart);
        //acount_test.setImageDrawable(Drawable.createFromPath((String) imgProducts));
        Picasso.get().load(imgProducts).fit().into(acount_test);
        //acount_test.setImageResource(imgProducts);
        name_test.setText(txtName_Cart_Products);*/

        /*AlertDialog.Builder alertDialog = new AlertDialog.Builder(getApplicationContext(), android.R.style.Theme_DeviceDefault_Light_Dialog);
                alertDialog.setTitle("Bạn chắc là order?");
                alertDialog.setIcon(android.R.drawable.alert_light_frame);
                alertDialog.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        onBackPressed();

                    }
                });
                /*alertDialog.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                alertDialog.show( );*/
}
