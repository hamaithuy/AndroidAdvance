package com.example.jamlos.Activity.User;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.example.jamlos.Adapter.ViewPagerAdapter;
import com.example.jamlos.Fragment.BestProductFragment;
import com.example.jamlos.Fragment.FeaturedProductFragment;
import com.example.jamlos.Fragment.NewProductFragment;
import com.example.jamlos.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {
    TextView txtBrandName, txtSlogan;
    ViewFlipper viewFlipper;
    TabLayout tabLayout;
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navView.setItemIconTintList(null);
        AnhXa();
        SetStyle();
        ActionViewFlipper();

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.AddFragment(new FeaturedProductFragment(), "Featured Products");
        viewPagerAdapter.AddFragment(new NewProductFragment(), "New             Products");
        viewPagerAdapter.AddFragment(new BestProductFragment(), "Best            Products");
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    return true;
                case R.id.navigation_sale:
                    Intent Sale = new Intent(HomeActivity.this, SaleActivity.class);
                    startActivity(Sale);
                    break;
                case R.id.navigation_products:
                    Intent Product = new Intent(HomeActivity.this, CategoryActivity.class);
                    startActivity(Product);
                    break;
                case R.id.navigation_shoppingcart:
                    Intent Cart = new Intent(HomeActivity.this, CartActivity.class);
                    startActivity(Cart);
                    break;
                case R.id.navigation_account:
                    Intent Account = new Intent(HomeActivity.this, AccountActivity.class);
                    startActivity(Account);
                    break;
            }
            return false;
        }
    };

    private void ActionViewFlipper() {
        ArrayList<String> mangquangcao = new ArrayList<>();
        mangquangcao.add("https://scontent.fhan2-3.fna.fbcdn.net/v/t1.0-9/70506880_594071171122247_287869418510221312_n.jpg?_nc_cat=107&_nc_oc=AQniv9EVBjtHJNDj0eSusgSUk6k9vlRgDLCtnfUakXtH5Rr7X3whMxlwniEX3mVVsjc&_nc_ht=scontent.fhan2-3.fna&oh=09c76a74794af5b53b6909a2f4a9e721&oe=5E06CA37");
        mangquangcao.add("https://scontent.fhan2-3.fna.fbcdn.net/v/t1.0-9/69662167_594071187788912_8039932320003653632_n.jpg?_nc_cat=109&_nc_oc=AQkaGceuFkN8Zs6E3IhZzBPQFiM5qSKwexoSkrdI8ahL7Ffw2vO6rkIzMCQ4dG9DkKU&_nc_ht=scontent.fhan2-3.fna&oh=171d938a32670ba8d41f205a0a751ba5&oe=5E0B1CF5");
        mangquangcao.add("https://scontent.fhan2-4.fna.fbcdn.net/v/t1.0-9/69455474_594071201122244_4835138791582728192_n.jpg?_nc_cat=100&_nc_oc=AQn3qRrs1-jVY8m3cb1C7Y7ZT9UGd7hpaqqKpXYM2_xlWb4vQULHBi2VPZpwdvlvfE4&_nc_ht=scontent.fhan2-4.fna&oh=e0a51d8d276b54d7ad7860059c97e8d6&oe=5DFE0C73");
        for (int i = 0; i < mangquangcao.size(); i++) {
            ImageView imageView = new ImageView(getApplicationContext());
            Picasso.with(getApplicationContext()).load(mangquangcao.get(i)).into(imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            viewFlipper.addView(imageView);
        }
        viewFlipper.setFlipInterval(6000);
        viewFlipper.setAutoStart(true);
        viewFlipper.setInAnimation(this, android.R.anim.slide_in_left);
        viewFlipper.setOutAnimation(this, android.R.anim.slide_out_right);

    }

    private void AnhXa(){
        txtBrandName = (TextView) findViewById(R.id.txtBrandName);
        txtSlogan = (TextView) findViewById(R.id.txtSlogan1);
        viewFlipper = (ViewFlipper) findViewById(R.id.viewFlipper);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        viewPager = (ViewPager) findViewById(R.id.viewPagerBanner);
    }

    private void SetStyle(){
        Typeface face1 = Typeface.createFromAsset(getAssets(), "fonts/FuturaPTBook.otf");
        Typeface face = Typeface.createFromAsset(getAssets(), "fonts/FuturaPTLight.otf");
        txtBrandName.setTypeface(face);
        txtSlogan.setTypeface(face1);
    }
}