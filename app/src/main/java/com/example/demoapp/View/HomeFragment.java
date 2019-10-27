package com.example.demoapp.View;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demoapp.Adapter.GridRecyclerAdapter;
import com.example.demoapp.Adapter.RecyclerAdapter;
import com.example.demoapp.Common.Common;
import com.example.demoapp.Model.Room;
import com.example.demoapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements GridRecyclerAdapter.IOnItemClickListener {

    private RecyclerView mRecyclerNewRoom,mRecyclerDRoom;
    private GridRecyclerAdapter mAdapterNewRoom, mAdapterDRoom;
    private FirebaseStorage mStorage;
    private DatabaseReference mDatabaseRef;
    private Query mQuerySelectNewRoom, mQuerySelectDRoom;
    private ValueEventListener mDBListener;
    private List<Room> listNewRooms , listDRoom ;
    private ImageView iv_Post;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home,container,false);
        // Sự kiện đăng phòng
        iv_Post = (ImageView) view.findViewById(R.id.iv_Post);
        iv_Post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(),PostActivity.class);
                startActivity(intent);
            }
        });
        // Get dữ liệu top 4 phòng cho thuê mới  và hiển thị lên RecyclerView phòng mới
        mRecyclerNewRoom = view.findViewById(R.id.rv_NewRoom);
        mRecyclerNewRoom.setHasFixedSize(true);
        mRecyclerNewRoom.setLayoutManager(new GridLayoutManager(getActivity(),2, GridLayoutManager.VERTICAL,false));

        listNewRooms = new ArrayList<>();
        mAdapterNewRoom = new GridRecyclerAdapter(getActivity(),listNewRooms,1);
        mRecyclerNewRoom.setAdapter(mAdapterNewRoom);
        mAdapterNewRoom.setOnItemClickListener(this);

        mStorage = FirebaseStorage.getInstance();
        mQuerySelectNewRoom = FirebaseDatabase.getInstance()
                        .getReference("Rooms")
                        .orderByChild("kindPost")
                        .equalTo(1)
                        .limitToFirst(4);

        mQuerySelectNewRoom.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listNewRooms.clear();
                for(DataSnapshot roomSnapshot : dataSnapshot.getChildren()){
                    Room room = roomSnapshot.getValue(Room.class);
                    room.setKey(roomSnapshot.getKey());
                    listNewRooms.add(room);
                }
                mAdapterNewRoom.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getContext(),databaseError.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });

        // Get top 4 phòng mới ở ghép và hiển thị lên RecyclerView phòng ở ghép

        mRecyclerDRoom = view.findViewById(R.id.rv_DoubleRoom);
        mRecyclerDRoom.setHasFixedSize(true);
        mRecyclerDRoom.setLayoutManager(new GridLayoutManager(getActivity(),2, GridLayoutManager.VERTICAL,false));

        listDRoom = new ArrayList<>();
        mAdapterDRoom = new GridRecyclerAdapter(getActivity(),listDRoom,2);
        mRecyclerDRoom.setAdapter(mAdapterDRoom);
        mAdapterDRoom.setOnItemClickListener(this);

        mQuerySelectDRoom = FirebaseDatabase.getInstance()
                .getReference("Rooms")
                .orderByChild("kindPost")
                .equalTo(2)
                .limitToFirst(4);
        mQuerySelectDRoom.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listDRoom.clear();
                for(DataSnapshot roomSnapshot : dataSnapshot.getChildren()){
                    Room room = roomSnapshot.getValue(Room.class);
                    room.setKey(roomSnapshot.getKey());
                    listDRoom.add(room);
                }
                mAdapterDRoom.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getContext(),databaseError.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    @Override
    public void onItemClick(int position,int kindAdapter) {
        Room roomClicked;
        if(kindAdapter==1){
            roomClicked = listNewRooms.get(position);
        }else{
            roomClicked = listDRoom.get(position);
        }

        // Format datetime
        String dateFormat = Common.getDateFormat(roomClicked.getTimePost());
        // Format price
        String price = Common.FormatCurrentcy(roomClicked.getPrice());
        String [] roomData ={
                price,
                roomClicked.getImageUrl(),
                roomClicked.getTitle(),
                roomClicked.getAddress(),
                dateFormat,
                Float.toString(roomClicked.getAcreage()),
                roomClicked.getPhoneNumber(),
                roomClicked.getWifi().toString(),
                roomClicked.getOwnWc().toString(),
                roomClicked.getKeepCar().toString(),
                roomClicked.getFreedom().toString(),
                roomClicked.getKitchen().toString(),
                roomClicked.getAirMachine().toString(),
                roomClicked.getFridge().toString(),
                roomClicked.getWashMachine().toString(),
                roomClicked.getDescription()
        };
        openDetailActivity(roomData);
    }

    private void openDetailActivity(String[] data){
        Intent intent = new Intent(getContext(), DetailActivity.class);
        intent.putExtra("PRICE_KEY",data[0]);
        intent.putExtra("IMAGE_KEY",data[1]);
        intent.putExtra("TITLE_KEY",data[2]);
        intent.putExtra("ADDRESS_KEY",data[3]);
        intent.putExtra("TIME_KEY",data[4]);
        intent.putExtra("ACREAGE_KEY",data[5]);
        intent.putExtra("PHONE_KEY",data[6]);
        intent.putExtra("WIFI_KEY",data[7]);
        intent.putExtra("WC_KEY",data[8]);
        intent.putExtra("KEEPCAR_KEY",data[9]);
        intent.putExtra("FREE_KEY",data[10]);
        intent.putExtra("KITCHEN_KEY",data[11]);
        intent.putExtra("AIRMACHINE_KEY",data[12]);
        intent.putExtra("FRIDGE_KEY",data[13]);
        intent.putExtra("WASHMACHINE_KEY",data[14]);
        intent.putExtra("DESCRIPTION_KEY",data[15]);
        startActivity(intent);
    }

//    @Override
//    public void onDestroy(){
//        super.onDestroy();
//        mQuery.removeEventListener(mDBListener);
//    }

    @Override
    public void onResume() {
        super.onResume();
        //gridNewRoom.setAdapter();
    }
}
