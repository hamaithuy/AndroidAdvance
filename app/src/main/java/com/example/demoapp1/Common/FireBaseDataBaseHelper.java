package com.example.demoapp1.Common;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;

import java.util.ArrayList;
import java.util.List;

public class FireBaseDataBaseHelper<T>{
    private FirebaseDatabase mDatabase;
    private DatabaseReference mDatabaseReference;
    private StorageReference mStorageRef;
    private StorageTask mUploadTask;

    private List<T>  objects = new ArrayList<>();

    // Create interface to link our main process with onDataChange process
    public interface DataStatus<E>{
        void DataIsLoaded(List<E> objects);
        void DataIsInserted();
        void DataIsUpdated();
        void DataIsDeleted();
    }

    public FireBaseDataBaseHelper(String node){
        mDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mDatabase.getReference(node);
    }

     public void readData(final DataStatus<T> dataStatus){
        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                objects.clear();
                List<String> keys = new ArrayList<>();
                for(DataSnapshot node : dataSnapshot.getChildren()){
                    keys.add(node.getKey());
                    GenericTypeIndicator<T> t = new GenericTypeIndicator<T>() {};
                    T object= node.getValue(t);
                    objects.add(object);
                }
                dataStatus.DataIsLoaded(objects);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void WriteData(T object,final DataStatus<T> dataStatus){
        String key = mDatabaseReference.push().getKey();
        mDatabaseReference.child(key).setValue(object)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        dataStatus.DataIsInserted();
                    }
                });
    }


}
