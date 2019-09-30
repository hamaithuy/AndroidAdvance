package com.example.jamlos.Activity.User;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.accounts.Account;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.example.jamlos.Adapter.CategoryAdapter;
import com.example.jamlos.Model.Category;
import com.example.jamlos.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CategoryActivity extends AppCompatActivity {

    TextView txtBrandName, txtSlogan;
    GridView grvCategory;
    CategoryAdapter categoryAdapter;
    FirebaseDatabase mDatabase;
    DatabaseReference tblCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        grvCategory = (GridView) findViewById(R.id.grvCategory);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navView.setItemIconTintList(null);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(CategoryActivity.this, "DUONG BINH", Toast.LENGTH_SHORT).show();
            }
        });
        AnhXa();;
        SetStyle();

        mDatabase = FirebaseDatabase.getInstance();
        tblCategory = mDatabase.getReference().child("Category");

        tblCategory.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final ArrayList<Category> lstCategory = new ArrayList<>();
                for (DataSnapshot snap : dataSnapshot.getChildren()){
                    String Kieu = snap.getKey();
                    String Ten = snap.child("Name").getValue().toString();
                    String Anh = snap.child("Image").getValue().toString();
                    lstCategory.add(new Category(Ten, Anh, Kieu));
                }
                categoryAdapter = new CategoryAdapter(lstCategory, CategoryActivity.this);
                grvCategory.setAdapter(categoryAdapter);
                grvCategory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Intent Product = new Intent(CategoryActivity.this, ProductActivity.class);
                        Product.putExtra("type", lstCategory.get(i).getType());
                        startActivity(Product);
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    Intent Home = new Intent(CategoryActivity.this, HomeActivity.class);
                    startActivity(Home);
                    break;
                case R.id.navigation_sale:
                    Intent Sale = new Intent(CategoryActivity.this, SaleActivity.class);
                    startActivity(Sale);
                    break;
                case R.id.navigation_products:
                    Intent Product = new Intent(CategoryActivity.this, ProductActivity.class);
                    startActivity(Product);
                    break;
                case R.id.navigation_shoppingcart:
                    Intent Cart = new Intent(CategoryActivity.this, CartActivity.class);
                    startActivity(Cart);
                    break;
                case R.id.navigation_account:
                    Intent Account = new Intent(CategoryActivity.this, AccountActivity.class);
                    startActivity(Account);
                    break;
            }
            return false;
        }
    };

    private void AnhXa(){
        txtBrandName = (TextView) findViewById(R.id.txtBrandName1);
        txtSlogan = (TextView) findViewById(R.id.txtSlogan2);
    }
    private void SetStyle(){
        Typeface face1 = Typeface.createFromAsset(getAssets(), "fonts/FuturaPTBook.otf");
        Typeface face = Typeface.createFromAsset(getAssets(), "fonts/FuturaPTLight.otf");
        txtBrandName.setTypeface(face);
        txtSlogan.setTypeface(face1);
    }
}
