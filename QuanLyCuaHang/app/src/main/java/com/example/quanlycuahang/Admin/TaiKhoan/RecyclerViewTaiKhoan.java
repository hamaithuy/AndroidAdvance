package com.example.quanlycuahang.Admin.TaiKhoan;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quanlycuahang.Admin.Mon.Mon;
import com.example.quanlycuahang.R;

import java.util.List;

public class RecyclerViewTaiKhoan {
    private Context context;
    private TaiKhoanAdapter taiKhoanAdapter;

    public void setConfig(RecyclerView recyclerView, Context mContext, List<TaiKhoan> taiKhoans, List<String> keys) {
        context = mContext;
        taiKhoanAdapter = new TaiKhoanAdapter(taiKhoans, keys);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.setAdapter(taiKhoanAdapter);

    }


    class TaiKhoanItemView extends RecyclerView.ViewHolder {
        private TextView tvTenTaiKhoan;
        private TextView tvMatKhau;
        private TextView tvTen;
        private TextView tvViTri;
        private ImageView imgTaiKhoan;
        private String key;

        public TaiKhoanItemView(ViewGroup parent) {
            super(LayoutInflater.from(context).inflate(R.layout.taikhoan_item_layout, parent, false));
            tvTenTaiKhoan = itemView.findViewById(R.id.tv_ten_tai_khoan);
            tvMatKhau = itemView.findViewById(R.id.tv_mat_khau);
            tvTen = itemView.findViewById(R.id.tv_ten);
            tvViTri = itemView.findViewById(R.id.tv_vi_tri);
            imgTaiKhoan = itemView.findViewById(R.id.img_tai_khoan);


        }

        public void bind(TaiKhoan taiKhoan, String key) {
            tvTenTaiKhoan.setText(taiKhoan.getTenTaiKhoan().toString());
            tvMatKhau.setText(taiKhoan.getMatKhau().toString());
            tvTen.setText(taiKhoan.getTen().toString());
            tvViTri.setText(taiKhoan.getViTri().toString());
            imgTaiKhoan.setImageResource(R.drawable.img_do_an);
            this.key = key;
        }

    }

    class TaiKhoanAdapter extends RecyclerView.Adapter<TaiKhoanItemView> {
        private List<TaiKhoan> taiKhoanList;
        private List<String> mKeys;

        public TaiKhoanAdapter(List<TaiKhoan> taiKhoanList, List<String> mKeys) {
            this.taiKhoanList = taiKhoanList;
            this.mKeys = mKeys;
        }

        @NonNull
        @Override
        public TaiKhoanItemView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new TaiKhoanItemView(parent);
        }

        @Override
        public void onBindViewHolder(@NonNull TaiKhoanItemView holder, int position) {
            holder.bind(taiKhoanList.get(position), mKeys.get(position));
        }

        @Override
        public int getItemCount() {
            return taiKhoanList.size();
        }
    }
}
