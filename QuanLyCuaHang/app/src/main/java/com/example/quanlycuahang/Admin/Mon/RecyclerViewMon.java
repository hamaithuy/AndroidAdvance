package com.example.quanlycuahang.Admin.Mon;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quanlycuahang.R;

import java.util.List;


public class RecyclerViewMon {
    private Context context;
    private MonAdapter monAdapter;

    public void setConfig(RecyclerView recyclerView, Context mContext, List<Mon> mons, List<String> keys) {
        context = mContext;
        monAdapter = new MonAdapter(mons, keys);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.setAdapter(monAdapter);

    }


    class MonItemView extends RecyclerView.ViewHolder {
        private TextView tvTenMon;
        private TextView tvGia;
        private String key;

        public MonItemView(ViewGroup parent) {
            super(LayoutInflater.from(context).inflate(R.layout.mon_item_layout, parent, false));
            tvTenMon = itemView.findViewById(R.id.tv_ten_mon);
            tvGia = itemView.findViewById(R.id.tv_gia_mon);

        }

        public void bind(Mon mon, String key) {
          tvGia.setText(Integer.toString(mon.getGia()));
           tvTenMon.setText(mon.getTenMon());

            this.key = key;
        }
    }

    class MonAdapter extends RecyclerView.Adapter<MonItemView> {
        private List<Mon> monList;
        private List<String> mKeys;

        public MonAdapter(List<Mon> monList, List<String> mKeys) {
            this.monList = monList;
            this.mKeys = mKeys;
        }

        @NonNull
        @Override
        public MonItemView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new MonItemView(parent);
        }

        @Override
        public void onBindViewHolder(@NonNull MonItemView holder, int position) {
            holder.bind(monList.get(position), mKeys.get(position));
        }

        @Override
        public int getItemCount() {
            return monList.size();
        }
    }
}
