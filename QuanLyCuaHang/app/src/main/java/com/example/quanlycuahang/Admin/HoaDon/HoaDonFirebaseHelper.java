package com.example.quanlycuahang.Admin.HoaDon;

import com.example.quanlycuahang.Admin.Mon.Mon;
import com.example.quanlycuahang.Admin.Mon.MonFireBaseDatabaseHelper;
import com.example.quanlycuahang.Order.Oder;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import androidx.annotation.NonNull;

public class HoaDonFirebaseHelper {
    private FirebaseDatabase database;
    private DatabaseReference reference;
    private List<HoaDon> hoaDons = new ArrayList<>();
    private Mon Resmon;

    public interface HoaDonDataStatuts {
        void DataIsLoaded(List<HoaDon> hoaDons, List<String> keys);

        void DataIsInserted();

        void DataIsUpdated();

        void DataIsDeleted();
    }

    public HoaDonFirebaseHelper() {
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("HoaDon");
    }

    public void DanhSachHoa(final HoaDonFirebaseHelper.HoaDonDataStatuts hoaDonDataStatuts) {
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                hoaDons.clear();
                List<String> keys = new ArrayList<>();
                for (DataSnapshot keyNode : dataSnapshot.getChildren()) {
                    keys.add(keyNode.getKey());
                    HoaDon hoaDon = keyNode.getValue(HoaDon.class);
                    hoaDons.add(hoaDon);
                }
                hoaDonDataStatuts.DataIsLoaded(hoaDons, keys);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void SuaHoadon(String key, HoaDon hoaDon, final HoaDonDataStatuts dataStatuts) {
        reference.child(key).setValue(hoaDon).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                dataStatuts.DataIsUpdated();
            }
        });
    }

    public void ThemHoaDon(List<Oder> glstOder, final HoaDonFirebaseHelper.HoaDonDataStatuts hoaDonDataStatuts) {
        String key = reference.push().getKey();
        HoaDon hoaDon = new HoaDon();
        hoaDon.setHoanThanh(false);
        hoaDon.setThoigianGhinhan(new Date());
        Integer tongtien = 0;
        for (final Oder oder : glstOder) {
            tongtien += oder.getGia() * oder.getSoluong();
            new MonFireBaseDatabaseHelper().LayMonTheoMonID(oder.getMonID(), new MonFireBaseDatabaseHelper.DataStatuts() {
                @Override
                public void DataIsLoaded(List<Mon> mons, List<String> keys) {
                    Resmon = mons.get(0);
                    if (Resmon.getSoLuong() >= oder.getSoluong()) {
                        Resmon.setSoLuong(Resmon.getSoLuong() - oder.getSoluong());
                        new MonFireBaseDatabaseHelper().SuaMon(oder.getMonID(), Resmon, new MonFireBaseDatabaseHelper.DataStatuts() {
                            @Override
                            public void DataIsLoaded(List<Mon> mons, List<String> keys) {

                            }

                            @Override
                            public void DataIsInserted() {

                            }

                            @Override
                            public void DataIsUpdated() {
                                return;
                            }

                            @Override
                            public void DataIsDeleted() {

                            }
                        });
                        return;
                    } else {
                        Resmon.setSoLuong(Resmon.getSoLuong() - oder.getSoluong());
                        new MonFireBaseDatabaseHelper().SuaMon(oder.getMonID(), Resmon, new MonFireBaseDatabaseHelper.DataStatuts() {
                            @Override
                            public void DataIsLoaded(List<Mon> mons, List<String> keys) {

                            }

                            @Override
                            public void DataIsInserted() {

                            }

                            @Override
                            public void DataIsUpdated() {
                                return;
                            }

                            @Override
                            public void DataIsDeleted() {

                            }
                        });
                        return;
                    }
                }

                @Override
                public void DataIsInserted() {

                }

                @Override
                public void DataIsUpdated() {

                }

                @Override
                public void DataIsDeleted() {

                }
            });
        }
        hoaDon.setDanhSachOder(glstOder);
        hoaDon.setTongTien(tongtien);
        reference.child(key).setValue(hoaDon).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                reference = database.getReference("HoaDonTam");
                reference.setValue(null);
                hoaDonDataStatuts.DataIsInserted();
            }
        });

    }
}
