package com.example.quanlycuahang.Kitchen;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.quanlycuahang.Admin.HoaDon.HoaDon;
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

        public KitchenPrentItemView(ViewGroup parent) {
            super(LayoutInflater.from(context).inflate(R.layout.kitchen_item_layout, parent, false));
            m_recyclerViewKitchenChild = itemView.findViewById(R.id.rv_kitchen_Child);
            init();
        }

        public void bind(HoaDon hoaDon, String key , Integer Position) {
            new RecyclerViewKitchenChild().setConfig(m_recyclerViewKitchenChild, context, hoaDon.getDanhSachOder());
            txtSttHoadon.setText("Hóa đơn "+ Integer.toString(Position+1)+":");
            this.key = key;
        }
        private void init()
        {
            txtSttHoadon = (TextView) itemView.findViewById(R.id.txtSttHoadon_Nhabep);
            btnHoanthanh = (Button) itemView.findViewById(R.id.btnHoanthanh_Nhabep);
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
            holder.bind(hoaDonList.get(position), mKeys.get(position),position);
        }

        @Override
        public int getItemCount() {
            return hoaDonList.size();
        }
    }
}
