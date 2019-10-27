package com.example.quanlycuahang.Order;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;

public class OderFirebaseHelper {
    private FirebaseDatabase database;
    private DatabaseReference reference;
    private List<Oder> oders = new ArrayList<>();

    public interface OderDataStatuts {
        void DataIsLoaded(List<Oder> oders, List<String> keys);

        void DataIsInserted();

        void DataIsUpdated();

        void DataIsDeleted();
    }

    public OderFirebaseHelper() {
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("HoaDonTam");
    }

    public void DanhSachHoaDonTam(final OderFirebaseHelper.OderDataStatuts oderDataStatuts) {
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                oders.clear();
                List<String> keys = new ArrayList<>();
                for (DataSnapshot keyNode : dataSnapshot.getChildren()) {
                    keys.add(keyNode.getKey());
                    Oder oder = keyNode.getValue(Oder.class);
                    oders.add(oder);
                }
                oderDataStatuts.DataIsLoaded(oders, keys);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }




    public void ThemHoaDonTam(Oder oder, final OderFirebaseHelper.OderDataStatuts dataStatuts) {
        String key = reference.push().getKey();
        reference.child(key).setValue(oder).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                dataStatuts.DataIsInserted();
            }
        });
    }

    public void SuaHoaDonTam(String key, Oder oder, final OderFirebaseHelper.OderDataStatuts dataStatuts) {
        reference.child(key).setValue(oder).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                dataStatuts.DataIsUpdated();
            }
        });
    }

    public void XoaHoaDontam(String key, final OderFirebaseHelper.OderDataStatuts dataStatuts) {
        reference.child(key).setValue(null).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                dataStatuts.DataIsDeleted();
            }
        });
    }


}
