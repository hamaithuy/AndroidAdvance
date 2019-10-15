package com.example.quanlycuahang.Admin.NguyenLieu;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
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
        private TextView tvLoai;
        private TextView tvGiaMon;
        private ImageView imgItem;
        private String key;
        private ImageButton imgTru;
        private ImageButton imgCong;


        public NguyenLieuItemView(ViewGroup parent) {
            super(LayoutInflater.from(context).inflate(R.layout.nguyen_lieu_item_layout, parent, false));
            tvTenMon = itemView.findViewById(R.id.tv_ten_nguyen_lieu);
            tvLoaiMon = itemView.findViewById(R.id.tv_loai_nguyen_lieu);
            tvLoai = itemView.findViewById(R.id.tv_nguyen_lieu_loai);
            tvSoLuong = itemView.findViewById(R.id.tv_so_luong);
            tvGiaMon = itemView.findViewById(R.id.tv_gia_nguyen_lieu);
            imgTru = itemView.findViewById(R.id.img_tru);
            imgCong = itemView.findViewById(R.id.img_cong);
            imgItem = itemView.findViewById(R.id.img_item_nguyen_lieu);
            imgTru.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Mon mon = new Mon();
                    String sl = tvSoLuong.getText().toString();
                    int soLuong = Integer.parseInt(sl);
                    soLuong--;
                    mon.setTenMon(tvTenMon.getText().toString());
                    mon.setLoai(Integer.parseInt(tvLoai.getText().toString()));
                    mon.setGia(Integer.parseInt(tvGiaMon.getText().toString()));
                    mon.setSoLuong(soLuong);
                    mon.setLoaiMon(tvLoaiMon.getText().toString());
                    String sluong = String.valueOf(soLuong);
                    tvSoLuong.setText(sluong);
                    new NguyenLieuFireBaseDatabaseHelper().SuaMon(key, mon, new NguyenLieuFireBaseDatabaseHelper.NguyenLieuDataStatuts() {
                        @Override
                        public void DataIsLoaded(List<Mon> mons, List<String> keys) {

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
            });
            imgCong.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Mon mon = new Mon();
                    String sl = tvSoLuong.getText().toString();
                    int soLuong = Integer.parseInt(sl);
                    soLuong++;
                    mon.setTenMon(tvTenMon.getText().toString());
                    mon.setLoai(Integer.parseInt(tvLoai.getText().toString()));
                    mon.setGia(Integer.parseInt(tvGiaMon.getText().toString()));
                    mon.setSoLuong(soLuong);
                    mon.setLoaiMon(tvLoaiMon.getText().toString());
                    String sluong = String.valueOf(soLuong);
                    tvSoLuong.setText(sluong);
                    new NguyenLieuFireBaseDatabaseHelper().SuaMon(key, mon, new NguyenLieuFireBaseDatabaseHelper.NguyenLieuDataStatuts() {
                        @Override
                        public void DataIsLoaded(List<Mon> mons, List<String> keys) {

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
            });
        }

        public void bind(Mon mon, String key) {
            tvTenMon.setText(mon.getTenMon());
            tvLoaiMon.setText(mon.getLoaiMon());
            tvSoLuong.setText(mon.getSoLuong().toString());
            tvGiaMon.setText(mon.getGia().toString());
            tvLoai.setText(mon.getLoai().toString());
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
