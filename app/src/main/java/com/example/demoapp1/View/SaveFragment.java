package com.example.demoapp1.View;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demoapp1.Adapter.RecyclerAdapter;
import com.example.demoapp1.Adapter.RecyclerSaveAdapter;
import com.example.demoapp1.Common.Common;
import com.example.demoapp1.R;
import com.example.demoapp1.Model.Room;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class SaveFragment extends Fragment implements RecyclerSaveAdapter.IOnItemClickListener {

    private RecyclerView mRecyclerView;
    private RecyclerSaveAdapter mAdapter;
    private FirebaseStorage mStorage;
    private DatabaseReference mDatabaseRef;
    private DatabaseReference mUserRef;
    private ValueEventListener mDBListener;
    private List<Room> listRooms ;
    private List<String> listIdRoomSaved;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_save,container,false);

        mRecyclerView = view.findViewById(R.id.rv_SaveRoom);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        listRooms = new ArrayList<>();
        listIdRoomSaved = new ArrayList<>();
        mAdapter = new RecyclerSaveAdapter(getActivity(),listRooms);
        mRecyclerView.setAdapter(mAdapter);
        // Connect interface
        mAdapter.setOnItemClickListener(this);

        // Lấy danh sách ID của room
        //Get current user id:
//        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(getApplicationContext());
        MainActivity activity = (MainActivity) getActivity();
        String idUser = activity.getID();

        mUserRef = FirebaseDatabase.getInstance().getReference("Users").child(idUser).child("roomSaved");
        mUserRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot idRoomSnapshot : dataSnapshot.getChildren()){
                    String id = idRoomSnapshot.getKey().toString();
                    listIdRoomSaved.add(id);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        mStorage = FirebaseStorage.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("Rooms");

        mDBListener = mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listRooms.clear();
                for (int i =0 ; i<listIdRoomSaved.size();i++){
                    for(DataSnapshot roomSnapshot : dataSnapshot.getChildren()) {
                        String roomID = roomSnapshot.getKey().toString();
                        String roomSavedID = listIdRoomSaved.get(i).toString();
                        if(roomID.equals(roomSavedID))
                        {
                            Room room = roomSnapshot.getValue(Room.class);
                            room.setKey(roomSnapshot.getKey());
                            listRooms.add(room);
                        }
                    }
                }
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getContext(),databaseError.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }



    @Override
    public void onItemClick(int position) {
        Room roomClicked = listRooms.get(position);
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
        intent.putExtra("SAVE_ROOM",true);
        startActivity(intent);
    }



//    @Override
//    public void onEditItemClick(int position) {
//
//    }
//
//    @Override
//    public void onDeleteItemClick(int position) {
//        Room roomClicked = listRooms.get(position);
//        final String selectedKey = roomClicked.getKey();
//
//        StorageReference imageRef = mStorage.getReferenceFromUrl(roomClicked.getImageUrl());
//        imageRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
//            @Override
//            public void onSuccess(Void aVoid) {
//                mDatabaseRef.child(selectedKey).removeValue();
//                Toast.makeText(getContext(),"Item deleted",Toast.LENGTH_SHORT).show();
//            }
//        });
//    }

    public void onDestroy(){
        super.onDestroy();
        mDatabaseRef.removeEventListener(mDBListener);
    }






}
