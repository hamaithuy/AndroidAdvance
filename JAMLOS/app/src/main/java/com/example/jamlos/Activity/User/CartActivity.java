package com.example.jamlos.Activity.User;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.example.jamlos.Adapter.CartAdapter;
import com.example.jamlos.Model.Common;
import com.example.jamlos.Model.Order;
import com.example.jamlos.Model.Request;
import com.example.jamlos.Model.lstOrder;
import com.example.jamlos.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CartActivity extends AppCompatActivity {

    SwipeMenuListView grvListCart;
    Button btnPlaceOrder, btnShopping;
    TextView txtTotalPrice;
    FirebaseDatabase database;
    DatabaseReference mRef;
    ArrayList<Order> cart;
    CartAdapter adapter;
    long maxID = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference tblRequests = database.getReference("Requests");

        grvListCart = (SwipeMenuListView) findViewById(R.id.grvListCart);
        btnPlaceOrder = (Button) findViewById(R.id.btnPlaceOrder);
        btnShopping = (Button) findViewById(R.id.btnShopping);
        txtTotalPrice = (TextView) findViewById(R.id.txtTotal);
        loadListFood();
        sumMoney();
        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {
                // create "delete" item
                SwipeMenuItem deleteItem = new SwipeMenuItem(
                        getApplicationContext());
                // set item background
                deleteItem.setBackground(new ColorDrawable(Color.rgb(255,
                        25, 25)));
                // set item width
                deleteItem.setWidth(100);
                // set a icon
                deleteItem.setTitle("DELETE");
                deleteItem.setTitleColor(Color.WHITE);
                // add to menu
                menu.addMenuItem(deleteItem);
            }
        };

        grvListCart.setMenuCreator(creator);

        grvListCart.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(final int position, SwipeMenu menu, int index) {
                switch (index) {
                    case 0:
                        final AlertDialog.Builder builder = new AlertDialog.Builder(CartActivity.this);
                        builder.setTitle("DELETE!");
                        builder.setMessage("Do you want to delete "+cart.get(position).getProductName()+" from your cart?");
                        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                cart.remove(position);
                                sumMoney();
                                adapter.notifyDataSetChanged();
                            }
                        });
                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();
                        break;
                }
                // false : close the menu; true : not close the menu
                return false;
            }
        });

        btnPlaceOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tblRequests.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists())
                            maxID = (dataSnapshot.getChildrenCount());
                        Request request = new Request(Common.currentUser.getUserName(),
                                Common.currentUser.getPhoneNumber(),
                                Common.currentUser.getAddress(),
                                cart,
                                txtTotalPrice.getText().toString());
                        tblRequests.child(String.valueOf(maxID + 1)).setValue(request);
                        Toast.makeText(CartActivity.this, "Thank you!", Toast.LENGTH_SHORT).show();
                        cart.clear();
                        finish();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });
        btnShopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void loadListFood() {
        cart = lstOrder.getLstOrder();
        adapter = new CartAdapter(cart, this);
        grvListCart.setAdapter(adapter);
    }

    private void sumMoney() {
        int totalprice=0;
        txtTotalPrice = (TextView)findViewById(R.id.txtTotal);
        btnPlaceOrder = (Button)findViewById(R.id.btnPlaceOrder);
        String totalprice1;
        for(Order doUong: lstOrder.getLstOrder()){
            String price = doUong.getPrice();
            int price1 = Integer.parseInt(price.replace(".",""));
            String number = doUong.getQuantity();
            int number1 = Integer.parseInt(number);
            totalprice = totalprice + price1*number1;
        }
        totalprice1 = NumberFormat.getInstance(Locale.GERMAN).format(totalprice);
        txtTotalPrice.setText(totalprice1 + " VND");
    }
}
