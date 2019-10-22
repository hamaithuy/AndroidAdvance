package com.example.quanlycuahang.Admin.TaiKhoan;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
        private ImageButton imgChinhSua;
        private ImageButton imgXoa;

        private String key;

        public TaiKhoanItemView(ViewGroup parent) {
            super(LayoutInflater.from(context).inflate(R.layout.taikhoan_item_layout, parent, false));
            tvTenTaiKhoan = itemView.findViewById(R.id.tv_ten_tai_khoan);
            tvMatKhau = itemView.findViewById(R.id.tv_mat_khau);
            tvTen = itemView.findViewById(R.id.tv_ten);
            tvViTri = itemView.findViewById(R.id.tv_vi_tri);
            imgTaiKhoan = itemView.findViewById(R.id.img_tai_khoan);
            imgChinhSua = itemView.findViewById(R.id.img_chinh_sua_tai_khoan);
            imgXoa = itemView.findViewById(R.id.img_xoa_tai_Khoan);
            imgChinhSua.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, TaiKhoanUpdate.class);
                    intent.putExtra("key", key);
                    intent.putExtra("TenTaiKhoan", tvTenTaiKhoan.getText().toString());
                    intent.putExtra("MatKhau", tvMatKhau.getText().toString());
                    intent.putExtra("Ten", tvTen.getText().toString());
                    intent.putExtra("ViTri", tvViTri.getText().toString());
                    context.startActivity(intent);
                }
            });
            imgXoa.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new TaiKhoanFireBaseDataBase().XoaTaiKhoan(key, new TaiKhoanFireBaseDataBase.TaiKhoanDataStatuts() {
                        @Override
                        public void DataIsLoaded(List<TaiKhoan> taiKhoans, List<String> keys) {

                        }

                        @Override
                        public void DataIsInserted() {

                        }

                        @Override
                        public void DataIsUpdated() {

                        }

                        @Override
                        public void DataIsDeleted() {
                            Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });

        }

        public void bind(TaiKhoan taiKhoan, String key) {
            tvTenTaiKhoan.setText(taiKhoan.getTenTaiKhoan().toString());
            tvMatKhau.setText(taiKhoan.getMatKhau().toString());
            tvTen.setText(taiKhoan.getTen().toString());
            if (taiKhoan.getViTri().toString().equals("1")) {
                tvViTri.setText("Admin");
            }
            if (taiKhoan.getViTri().toString().equals("2")) {
                tvViTri.setText("Nhà Bếp");
            }
            if (taiKhoan.getViTri().toString().equals("3")) {
                tvViTri.setText("Nhân Viên");
            }

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
