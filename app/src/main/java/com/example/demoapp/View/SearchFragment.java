package com.example.demoapp.View;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
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

public class SearchFragment extends Fragment implements RecyclerAdapter.IOnItemClickListener {

    private RecyclerView rv_SearchRoom;
    private SearchView tv_searchRoom;
    private RecyclerAdapter mAdapterSearchRoom;
    private Query mQuerySearchRoom;
    private List<Room> listSearchRooms;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search,container,false);

        tv_searchRoom = view.findViewById(R.id.tv_searchRoom);

        rv_SearchRoom = view.findViewById(R.id.rv_SearchRoom);
        rv_SearchRoom.setHasFixedSize(true);
        rv_SearchRoom.setLayoutManager(new LinearLayoutManager(getContext()));

        listSearchRooms = new ArrayList<>();
        mAdapterSearchRoom = new RecyclerAdapter(getActivity(),listSearchRooms);
        rv_SearchRoom.setAdapter(mAdapterSearchRoom);
        // Connect interface
        mAdapterSearchRoom.setOnItemClickListener(this);

        tv_searchRoom.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                String searchText = tv_searchRoom.getQuery().toString().trim();
                firebaseRoomSearch(searchText);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
        return view;
    }



    public void firebaseRoomSearch(String searchText){
        mQuerySearchRoom = FirebaseDatabase.getInstance().getReference("Rooms")
                .orderByChild("address")
                .startAt(searchText).endAt(searchText+"\uf8ff");
        mQuerySearchRoom.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listSearchRooms.clear();
                for(DataSnapshot roomSnapshot : dataSnapshot.getChildren()){
                    Room room = roomSnapshot.getValue(Room.class);
                    room.setKey(roomSnapshot.getKey());
                    listSearchRooms.add(room);
                }
                mAdapterSearchRoom.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onItemClick(int position) {
        Room roomClicked = listSearchRooms.get(position);
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
                roomClicked.getDescription(),
                roomClicked.getKey()
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
        intent.putExtra("CODE_KEY",data[16]);
        startActivity(intent);
    }

    @Override
    public void onEditItemClick(int position) {

    }

    @Override
    public void onDeleteItemClick(int position) {

    }
}
