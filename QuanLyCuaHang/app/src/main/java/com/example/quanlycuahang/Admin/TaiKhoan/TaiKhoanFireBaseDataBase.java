package com.example.quanlycuahang.Admin.TaiKhoan;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class TaiKhoanFireBaseDataBase {
    private FirebaseDatabase database;
    private DatabaseReference reference;
    private List<TaiKhoan> taiKhoans = new ArrayList<>();

    public interface TaiKhoanDataStatuts {
        void DataIsLoaded(List<TaiKhoan> taiKhoans, List<String> keys);

        void DataIsInserted();

        void DataIsUpdated();

        void DataIsDeleted();
    }

    public TaiKhoanFireBaseDataBase() {
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("TaiKhoan");
    }

    public void DanhSachTaiKhoan(final TaiKhoanDataStatuts taiKhoanDataStatuts) {
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                taiKhoans.clear();
                List<String> keys = new ArrayList<>();
                for (DataSnapshot keyNode : dataSnapshot.getChildren()) {
                    keys.add(keyNode.getKey());
                    TaiKhoan taiKhoan = keyNode.getValue(TaiKhoan.class);
                    taiKhoans.add(taiKhoan);
                }
                taiKhoanDataStatuts.DataIsLoaded(taiKhoans, keys);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
