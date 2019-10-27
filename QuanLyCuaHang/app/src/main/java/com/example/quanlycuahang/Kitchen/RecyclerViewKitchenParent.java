package com.example.quanlycuahang.Kitchen;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quanlycuahang.Admin.HoaDon.HoaDon;
import com.example.quanlycuahang.Admin.HoaDon.HoaDonFirebaseHelper;
import com.example.quanlycuahang.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

public class RecyclerViewKitchenParent {
    private Context context;
    private KitchenPrentAdapter kitchenPrentAdapter;

    public void setConfig(RecyclerView recyclerView, Context mContext, List<HoaDon> hoaDons, List<String> keys) {
        context = mContext;
        kitchenPrentAdapter = new KitchenPrentAdapter(hoaDons, keys);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.setAdapter(kitchenPrentAdapter);
    }

    class KitchenPrentItemView extends ViewHolder {
        private String key;
        private RecyclerView m_recyclerViewKitchenChild;
        private TextView txtSttHoadon;
        private Button btnHoanthanh;
        private LinearLayout LinearlayoutItem;

        public KitchenPrentItemView(ViewGroup parent) {
            super(LayoutInflater.from(context).inflate(R.layout.kitchen_item_layout, parent, false));
            m_recyclerViewKitchenChild = itemView.findViewById(R.id.rv_kitchen_Child);
            init();
        }

        public void bind(final HoaDon hoaDon, final String key , Integer Position) {
            LinearlayoutItem.setVisibility(View.VISIBLE);
            m_recyclerViewKitchenChild.setVisibility(View.VISIBLE);
            txtSttHoadon.setVisibility(View.VISIBLE);
            btnHoanthanh.setVisibility(View.VISIBLE);
            new RecyclerViewKitchenChild().setConfig(m_recyclerViewKitchenChild, context, hoaDon.getDanhSachOder());
            txtSttHoadon.setText("Hóa đơn KH"+ Integer.toString(Position+1)+":");
            btnHoanthanh.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    HoaDon hd = hoaDon;
                    hd.setHoanThanh(true);
                    new HoaDonFirebaseHelper().SuaHoadon(key, hd, new HoaDonFirebaseHelper.HoaDonDataStatuts() {
                        @Override
                        public void DataIsLoaded(List<HoaDon> hoaDons, List<String> keys) {

                        }

                        @Override
                        public void DataIsInserted() {

                        }

                        @Override
                        public void DataIsUpdated() {
                            Toast.makeText(context,"Đã hoàn thành hóa đơn",Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void DataIsDeleted() {

                        }
                    });
                }
            });
            this.key = key;
        }
        private void init()
        {
            txtSttHoadon = (TextView) itemView.findViewById(R.id.txtSttHoadon_Nhabep);
            btnHoanthanh = (Button) itemView.findViewById(R.id.btnHoanthanh_Nhabep);
            LinearlayoutItem = (LinearLayout) itemView.findViewById(R.id.LinearlayoutItem_Nhabep);
        }
    }

    class KitchenPrentAdapter extends RecyclerView.Adapter<KitchenPrentItemView> {
        private List<HoaDon> hoaDonList;
        private List<String> mKeys;

        public KitchenPrentAdapter(List<HoaDon> hoaDonList, List<String> mKeys) {
            this.hoaDonList = hoaDonList;
            this.mKeys = mKeys;
        }

        @NonNull
        @Override
        public KitchenPrentItemView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new KitchenPrentItemView(parent);
        }

        @Override
        public void onBindViewHolder(@NonNull KitchenPrentItemView holder, int position) {
            if(!hoaDonList.get(position).isHoanThanh())
            holder.bind(hoaDonList.get(position), mKeys.get(position),position);
        }

        @Override
        public int getItemCount() {
            return hoaDonList.size();
        }
    }
}
