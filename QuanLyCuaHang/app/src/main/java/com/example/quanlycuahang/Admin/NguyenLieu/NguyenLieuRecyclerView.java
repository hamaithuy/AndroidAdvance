package com.example.quanlycuahang.Admin.NguyenLieu;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quanlycuahang.Admin.Mon.Mon;
import com.example.quanlycuahang.R;

import java.util.List;

public class NguyenLieuRecyclerView {
    private Context context;
    private NguyenLieuAdapter nguyenLieuAdapter;

    public void setConfig(RecyclerView recyclerView, Context mContext, List<Mon> mons, List<String> keys) {
        context = mContext;
        nguyenLieuAdapter = new NguyenLieuAdapter(mons, keys);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.setAdapter(nguyenLieuAdapter);

    }

    class NguyenLieuItemView extends RecyclerView.ViewHolder {
        private TextView tvTenMon;
        private TextView tvSoLuong;
        private TextView tvLoaiMon;
        private ImageView imgItem;
        private String key;

        public NguyenLieuItemView(ViewGroup parent) {
            super(LayoutInflater.from(context).inflate(R.layout.nguyen_lieu_item_layout, parent, false));
            tvTenMon = itemView.findViewById(R.id.tv_ten_mon);
            tvLoaiMon = itemView.findViewById(R.id.tv_loai_mon);
            tvSoLuong = itemView.findViewById(R.id.tv_so_luong);
            imgItem = itemView.findViewById(R.id.img_item);
        }

        public void bind(Mon mon, String key) {
            tvTenMon.setText(mon.getTenMon());
            tvLoaiMon.setText(mon.getLoaiMon());
            tvSoLuong.setText(mon.getSoLuong().toString());
            if (mon.getLoaiMon().equals("Sinh tố")) {
                imgItem.setImageResource(R.drawable.img_sinh_to);
            } else if (mon.getLoaiMon().equals("Nước uống")) {
                imgItem.setImageResource(R.drawable.img_nuoc_uong);
            } else if (mon.getLoaiMon().equals("Đồ ăn")) {
                imgItem.setImageResource(R.drawable.img_do_an);
            }

            this.key = key;
        }
    }

    class NguyenLieuAdapter extends RecyclerView.Adapter<NguyenLieuItemView> {
        private List<Mon> monList;
        private List<String> mKeys;

        public NguyenLieuAdapter(List<Mon> monList, List<String> mKeys) {
            this.monList = monList;
            this.mKeys = mKeys;
        }

        @NonNull
        @Override
        public NguyenLieuItemView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new NguyenLieuItemView(parent);
        }

        @Override
        public void onBindViewHolder(@NonNull NguyenLieuItemView holder, int position) {
            holder.bind(monList.get(position), mKeys.get(position));
        }

        @Override
        public int getItemCount() {
            return monList.size();
        }
    }

}
