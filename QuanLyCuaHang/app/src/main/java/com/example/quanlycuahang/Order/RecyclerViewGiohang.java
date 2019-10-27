package com.example.quanlycuahang.Order;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quanlycuahang.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerViewGiohang {
    private Context mContext;
    private GioHangAdapter mGiohangAdapter;


    public void setConfig(RecyclerView recyclerView, Context context, List<Oder> oders, List<String> keys) {
        mContext = context;
        mGiohangAdapter = new GioHangAdapter(oders, keys);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(mGiohangAdapter);
    }

    class GiohangItemView extends RecyclerView.ViewHolder {
        private String key;
        private ImageView img_Anhsanpham;
        private TextView txtTenmon, txtGia, txtSoluong,tvLoai,tvLoaiMon,tvId;
        private ImageButton btnPre, btnNext, btnDelete;

        public GiohangItemView(ViewGroup parent) {
            super(LayoutInflater.from(mContext).inflate(R.layout.giohang_item_layout, parent, false));
            init();
        }

        private void init() {
            img_Anhsanpham = (ImageView) itemView.findViewById(R.id.img_Anhsanpham_giohang);
            txtTenmon = (TextView) itemView.findViewById(R.id.txtTenMon_giohang);
            txtGia = (TextView) itemView.findViewById(R.id.txtGia_giohang);
            txtSoluong = (TextView) itemView.findViewById(R.id.txtSoluong_giohang);
            tvLoai = (TextView) itemView.findViewById(R.id.tv_loai_gio_hang);
            tvLoaiMon = (TextView) itemView.findViewById(R.id.tv_loai_mon_gio_hang);
            tvId = (TextView) itemView.findViewById(R.id.tv_id_gio_hang);
            btnPre = (ImageButton) itemView.findViewById(R.id.btnPre_giohang);
            btnNext = (ImageButton) itemView.findViewById(R.id.btnNext_giohang);
            btnDelete = (ImageButton) itemView.findViewById(R.id.btnDelete_giohang);
            btnPre.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Oder oder = new Oder();
                    Integer gia = Integer.parseInt(txtGia.getText().toString());
                    Integer Loai = Integer.parseInt(tvLoai.getText().toString());
                    oder.setGia(gia);
                    oder.setLoai(Loai);
                    oder.setLoaiMon(tvLoaiMon.getText().toString());
                    oder.setMonID(tvId.getText().toString());
                    Integer SoLuong = Integer.parseInt(txtSoluong.getText().toString());
                    SoLuong--;
                    oder.setSoluong(SoLuong);
                    oder.setTenMon(txtTenmon.getText().toString());
                    new OderFirebaseHelper().SuaHoaDonTam(key, oder, new OderFirebaseHelper.OderDataStatuts() {
                        @Override
                        public void DataIsLoaded(List<Oder> oders, List<String> keys) {

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
            btnNext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Oder oder = new Oder();
                    Integer gia = Integer.parseInt(txtGia.getText().toString());
                    Integer Loai = Integer.parseInt(tvLoai.getText().toString());
                    oder.setGia(gia);
                    oder.setLoai(Loai);
                    oder.setLoaiMon(tvLoaiMon.getText().toString());
                    oder.setMonID(tvId.getText().toString());
                    Integer SoLuong = Integer.parseInt(txtSoluong.getText().toString());
                    SoLuong++;
                    oder.setSoluong(SoLuong);
                    oder.setTenMon(txtTenmon.getText().toString());
                    new OderFirebaseHelper().SuaHoaDonTam(key, oder, new OderFirebaseHelper.OderDataStatuts() {
                        @Override
                        public void DataIsLoaded(List<Oder> oders, List<String> keys) {

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
            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new OderFirebaseHelper().XoaHoaDontam(key, new OderFirebaseHelper.OderDataStatuts() {
                        @Override
                        public void DataIsLoaded(List<Oder> oders, List<String> keys) {

                        }

                        @Override
                        public void DataIsInserted() {

                        }

                        @Override
                        public void DataIsUpdated() {

                        }

                        @Override
                        public void DataIsDeleted() {
                            Toast.makeText(mContext, "Xóa thành công", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
        }

        public void bind(Oder oder, String key) {
            if (oder.getLoaiMon().equals("Sinh tố")) {
                img_Anhsanpham.setImageResource(R.drawable.img_sinh_to);
            } else if (oder.getLoaiMon().equals("Nước uống")) {
                img_Anhsanpham.setImageResource(R.drawable.img_nuoc_uong);
            } else if (oder.getLoaiMon().equals("Đồ ăn")) {
                img_Anhsanpham.setImageResource(R.drawable.img_do_an);
            }
            txtTenmon.setText(oder.getTenMon());
            txtGia.setText(Integer.toString( oder.getGia()));
            txtSoluong.setText(Integer.toString(oder.getSoluong()));
            tvLoai.setText(oder.getLoai().toString());
            tvLoaiMon.setText(oder.getLoaiMon().toString());
            tvId.setText(oder.getMonID().toString());


            this.key = key;
        }

    }

    class GioHangAdapter extends RecyclerView.Adapter<GiohangItemView> {
        private List<Oder> mOderlist;
        private List<String> mKeys;

        @NonNull
        @Override
        public GiohangItemView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new GiohangItemView(parent);
        }

        @Override
        public void onBindViewHolder(@NonNull GiohangItemView holder, int position) {
            holder.bind(mOderlist.get(position), mKeys.get(position));
        }

        @Override
        public int getItemCount() {
            return mOderlist.size();
        }

        public GioHangAdapter(List<Oder> mOderlist, List<String> mKeys) {
            this.mOderlist = mOderlist;
            this.mKeys = mKeys;
        }
    }

}
