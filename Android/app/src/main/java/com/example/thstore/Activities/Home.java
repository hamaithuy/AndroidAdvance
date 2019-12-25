package com.example.thstore.Activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thstore.Adapter.Adapter_Products;
import com.example.thstore.Model.Data;
import com.example.thstore.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;

import static com.example.thstore.R.layout.fragment_home;


public class Home extends Fragment implements Adapter_Products.OnItemClickListener {
    @Nullable

    ViewFlipper viewFlipper;
    public static final String EXTRA_URL = "imageURL";
    public static final String EXTRA_NAME = "productsName";
    public static final String EXTRA_PRICE = "productsPrice";
    public static final String EXTRA_ID = "productsID";

    //int SWIPE_MOTIONEVENT = 50;
    //int SWIPE_VELOXITY = 50;

    int position = 0;
    ArrayList<Integer> arrayAds = new ArrayList<>();


    //relate data on Firebase
    StorageReference storageReference;
    DatabaseReference dataFamousProducts;
    RecyclerView recyclerView;
    Adapter_Products adapter_products;
    ArrayList<Data> arrayData;



    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,@Nullable  Bundle savedInstanceState) {

        //int[] arrayAds = {R.drawable.card_seling, R.drawable.kids_wear, R.drawable.sale_50, R.drawable.sale_70};
        //Slide
        View v =  inflater.inflate(fragment_home, container, false);
        viewFlipper = v.findViewById(R.id.VFads);
        setViewFlipper();

        //RECYCLERVIEW
        recyclerView = v.findViewById(R.id.rvProducts);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);


        //FirebaseDatabase code
        arrayData = new ArrayList<>();
        storageReference = FirebaseStorage.getInstance().getReference("Clothes");
        dataFamousProducts = FirebaseDatabase.getInstance().getReference("Clothes");

        dataFamousProducts.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                    Data dtFamous_Pro =  postSnapshot.getValue(Data.class);
                    arrayData.add(dtFamous_Pro);
                }

                adapter_products = new Adapter_Products(getContext(), arrayData);
                recyclerView.setAdapter(adapter_products);
                adapter_products.setOnItemClickListener(Home.this);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        final EditText txtSearchName = v.findViewById(R.id.txtSearchName);
        Button btnSearch = v.findViewById(R.id.btnSearch);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (txtSearchName.getText().toString().length() >= 1)
                {
                    String SearchName = txtSearchName.getText().toString();
                    Query dataSearchResult = FirebaseDatabase.getInstance().getReference("Clothes").child(SearchName);
                    dataSearchResult.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            // This method is called once with the initial value and again
                            // whenever data at this location is updated.
                            String value = dataSnapshot.getValue(String.class);
                            Log.d("", "Value is: " + value);
                        }

                        @Override
                        public void onCancelled(DatabaseError error) {
                            // Failed to read value
                            Log.w("", "Failed to read value.", error.toException());
                        }
                    });
                }
            }
        });

        Button btnCall;
        btnCall = v.findViewById(R.id.btnCall);


        btnCall.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:0966936529"));

                if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                startActivity(callIntent);
            }
        });


        return v;


        /* final GestureDetector gestureDetector = new GestureDetector(getContext(), new MyGesture());

        viewFlipper.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                gestureDetector.onTouchEvent(event);
                return true;
            }
        });*/


    }

    public void onStart() {
        super.onStart();

    }



    void setViewFlipper(){



        arrayAds.add(R.drawable.card_seling);
        arrayAds.add(R.drawable.kids_wear);
        arrayAds.add(R.drawable.sale_50);
        arrayAds.add(R.drawable.sale_70);


        while (position < arrayAds.size())
        {
            ImageView imageView = new ImageView(getContext());
            Picasso.get().load(arrayAds.get(position)).into(imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            viewFlipper.addView(imageView);
            position ++;
        }



       /* for( int i = 0 ; i < arrayAds.size(); i++)
        {

            /*ImageView imageView = new ImageView(getContext());
            imageView.setImageResource(arrayAds[i]);
            viewFlipper.addView(imageView);}*/


        Animation animation_slide_in = AnimationUtils.loadAnimation(getContext(),R.anim.slide_ads_in);
        Animation animation_slide_out = AnimationUtils.loadAnimation(getContext(),R.anim.slide_ads_out);
        viewFlipper.setInAnimation(animation_slide_in);
        viewFlipper.setOutAnimation(animation_slide_out);
        viewFlipper.setFlipInterval(5000);
        viewFlipper.setAutoStart(true);
    }

    @Override
    public void OnItemClick(int position) {
        //Toast.makeText(getContext(), "Hiện ra: " + position, Toast.LENGTH_SHORT).show();
        //startActivity(new Intent(getActivity(), Bill.class));


        //
        Data data = arrayData.get(position);

        Intent intent = new Intent(getActivity(), Detail_Products.class);
        intent.putExtra(EXTRA_NAME, data.getName());
        intent.putExtra(EXTRA_URL, data.getImage());
        intent.putExtra(EXTRA_PRICE, data.getPrice());
        intent.putExtra(EXTRA_ID, data.getID());

        startActivity(intent);
        /**/


       /*
        Bill bill = (Bill) getFragmentManager().findFragmentById(R.id.fragment_bills);
        bill.txtProductsName.setText(data.getName());

        Bundle bundle = new Bundle();
        bundle.putString(EXTRA_NAME, data.getName());
        bundle.putString(EXTRA_URL, data.getImage());
        bundle.putString(EXTRA_PRICE, data.getPrice());
        bill.setArguments(bundle);
*/
    }

    /*class MyGesture extends GestureDetector.SimpleOnGestureListener{

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            arrayAds.add(R.drawable.card_seling);
            arrayAds.add(R.drawable.kids_wear);
            arrayAds.add(R.drawable.sale_50);
            arrayAds.add(R.drawable.sale_70);

            ImageView imageView = new ImageView(getContext());
            //Kéo từ trái sang phải
            if (e2.getX() - e1.getX() > SWIPE_MOTIONEVENT && Math.abs(velocityX) > SWIPE_VELOXITY ){
                position --;
                if (position < 0)
                {
                    position = arrayAds.size()-1;
                }
                imageView.setImageResource(arrayAds.get(position));
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                viewFlipper.addView(imageView);
            }
            //Kéo từ phải sang
            if (e1.getX() - e2.getX() > SWIPE_MOTIONEVENT && Math.abs(velocityX) > SWIPE_VELOXITY ){
                position ++;
                if (position > arrayAds.size()-1)
                {
                    position = 0;
                }
                imageView.setImageResource(arrayAds.get(position));
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                viewFlipper.addView(imageView);
            }
            return super.onFling(e1, e2, velocityX, velocityY);
        }
    }*/




}
