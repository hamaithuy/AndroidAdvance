package com.example.quanlycuahang.Admin.NguyenLieu;

import androidx.annotation.NonNull;

import com.example.quanlycuahang.Admin.Mon.Mon;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class NguyenLieuFireBaseDatabaseHelper {
    private FirebaseDatabase database;
    private DatabaseReference reference;
    private List<Mon> mons = new ArrayList<>();

    public interface NguyenLieuDataStatuts {
        void DataIsLoaded(List<Mon> mons, List<String> keys);

        void DataIsInserted();

        void DataIsUpdated();

        void DataIsDeleted();
    }

    public NguyenLieuFireBaseDatabaseHelper() {
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("Mon");
    }

    public void DanhSachNguyenLieu(final NguyenLieuDataStatuts nguyenLieuDataStatuts) {
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mons.clear();
                List<String> keys = new ArrayList<>();
                for (DataSnapshot keynode : dataSnapshot.getChildren()) {
                    keys.add(keynode.getKey());
                    Mon mon = keynode.getValue(Mon.class);
                    mons.add(mon);
                }
                nguyenLieuDataStatuts.DataIsLoaded(mons,keys);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    public void SuaMon(String key, Mon mon, final NguyenLieuDataStatuts nguyenLieuDataStatuts) {
        reference.child(key).setValue(mon).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                nguyenLieuDataStatuts.DataIsUpdated();
            }
        });
    }
}
