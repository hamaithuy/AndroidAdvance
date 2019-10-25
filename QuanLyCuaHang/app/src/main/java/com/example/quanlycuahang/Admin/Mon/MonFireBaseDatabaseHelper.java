package com.example.quanlycuahang.Admin.Mon;

import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MonFireBaseDatabaseHelper {
    private FirebaseDatabase database;
    private DatabaseReference reference;
    private List<Mon> mons = new ArrayList<>();


    public interface DataStatuts {
        void DataIsLoaded(List<Mon> mons, List<String> keys);

        void DataIsInserted();

        void DataIsUpdated();

        void DataIsDeleted();
    }

    public MonFireBaseDatabaseHelper() {
        // kết nối đến bảng Mon trong database
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("Mon");
    }

    public void DanhSachMon(final DataStatuts dataStatuts) {
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mons.clear();
                List<String> keys = new ArrayList<>();
                for (DataSnapshot keyNode : dataSnapshot.getChildren()) {
                    keys.add(keyNode.getKey());
                    Mon mon = keyNode.getValue(Mon.class);
                    mons.add(mon);
                }
                dataStatuts.DataIsLoaded(mons, keys);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    public void DanhSachMonSinhTo(final DataStatuts dataStatuts) {
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mons.clear();
                List<String> keys = new ArrayList<>();
                for (DataSnapshot keyNode : dataSnapshot.getChildren()) {
                    keys.add(keyNode.getKey());
                    Mon mon = keyNode.getValue(Mon.class);
                    if (mon.getLoai().equals(1)){
                        mons.add(mon);
                    }

                }
                dataStatuts.DataIsLoaded(mons, keys);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    public void DanhSachMonNuocUong(final DataStatuts dataStatuts) {
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mons.clear();
                List<String> keys = new ArrayList<>();
                for (DataSnapshot keyNode : dataSnapshot.getChildren()) {
                    keys.add(keyNode.getKey());
                    Mon mon = keyNode.getValue(Mon.class);
                    if (mon.getLoai().equals(2)){
                        mons.add(mon);
                    }

                }
                dataStatuts.DataIsLoaded(mons, keys);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    public void DanhSachMonThucAn(final DataStatuts dataStatuts) {
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mons.clear();
                List<String> keys = new ArrayList<>();
                for (DataSnapshot keyNode : dataSnapshot.getChildren()) {
                    keys.add(keyNode.getKey());
                    Mon mon = keyNode.getValue(Mon.class);
                    if (mon.getLoai().equals(3)){
                        mons.add(mon);
                    }

                }
                dataStatuts.DataIsLoaded(mons, keys);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void ThemMon(Mon mon, final DataStatuts dataStatuts) {
        String key = reference.push().getKey();
        reference.child(key).setValue(mon).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                dataStatuts.DataIsInserted();
            }
        });

    }

    public void SuaMon(String key, Mon mon, final DataStatuts dataStatuts) {
        reference.child(key).setValue(mon).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                dataStatuts.DataIsUpdated();
            }
        });
    }

    public void XoaMon(String key, final DataStatuts dataStatuts) {
        reference.child(key).setValue(null).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                dataStatuts.DataIsDeleted();
            }
        });
    }



}
