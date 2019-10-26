package com.example.quanlycuahang.Order;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quanlycuahang.Admin.Mon.Mon;
import com.example.quanlycuahang.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerViewOder {
    private Context mContext;
    private MonOderAdapter mMonAdapter;

    public void setConfig(RecyclerView recyclerView, Context context, List<Mon> mons, List<String> keys) {
        mContext = context;
        mMonAdapter = new MonOderAdapter(mons, keys);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(mMonAdapter);
    }

    class MonItemOderview extends RecyclerView.ViewHolder {
        private TextView m_txtTenmon;
        private TextView m_txtGia;
        private TextView m_txtLoaimon;
        private TextView m_txtLoai;
        private TextView m_txtSoluongCon;
        private TextView m_txtMonID;
        private EditText m_edtSoluong;
        private Button m_btnpre;
        private Button m_btnNext;
        private Button m_btnAdd;
        private String key;

        public MonItemOderview(ViewGroup parent) {
            super(LayoutInflater.from(mContext).inflate(R.layout.mon_item_oder, parent, false));
            init();
        }

        public void bind(Mon mon, String key) {
            m_txtTenmon.setText(mon.getTenMon());
            m_txtGia.setText(mon.getGia().toString());
            m_txtLoaimon.setText(mon.getLoaiMon());
            m_txtLoai.setText(mon.getLoai().toString());

            if (mon.getSoLuong() < 1) {
                m_txtSoluongCon.setText("Hết hàng");
                m_btnAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(mContext, "Số lượng mua không hợp lệ", Toast.LENGTH_SHORT).show();
                    }
                });
                //  m_txtSoluongCon.setTextColor(12);
            } else
                m_txtSoluongCon.setText(mon.getSoLuong().toString());

            if (mon.getSoLuong() > 0)
                m_edtSoluong.setText("1");
            // m_btnAdd.setcont

            m_txtMonID.setText(key);
            this.key = key;

        }

        private void init() {
            m_txtTenmon = (TextView) itemView.findViewById(R.id.txtTenmon);
            m_txtGia = (TextView) itemView.findViewById(R.id.txtGia);
            m_txtLoaimon = (TextView) itemView.findViewById(R.id.txtLoaimon);
            m_txtSoluongCon = (TextView) itemView.findViewById(R.id.txtSoluongCon);
            m_edtSoluong = (EditText) itemView.findViewById(R.id.edtSoluong);
            m_btnpre = (Button) itemView.findViewById(R.id.btnpre);
            m_btnNext = (Button) itemView.findViewById(R.id.btnNext);
            m_btnAdd = (Button) itemView.findViewById(R.id.btnAdd);
            m_txtMonID = (TextView) itemView.findViewById(R.id.txtMonID);
            m_txtLoai = (TextView) itemView.findViewById(R.id.txtLoai);
            m_btnpre.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Integer soluongMua = Integer.parseInt(m_edtSoluong.getText().toString());
                    if (soluongMua > 1)
                        m_edtSoluong.setText(Integer.toString(soluongMua - 1));
                }
            });
            m_btnNext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Integer soluongCon = Integer.parseInt(m_txtSoluongCon.getText().toString());
                    Integer soluongMua = Integer.parseInt(m_edtSoluong.getText().toString());
                    if (soluongMua < soluongCon)
                        m_edtSoluong.setText(Integer.toString(soluongMua + 1));
                }
            });
            m_btnAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Integer soluongMua = Integer.parseInt(m_edtSoluong.getText().toString());
                    Integer soluongCon = Integer.parseInt(m_txtSoluongCon.getText().toString());
                    if (soluongMua > soluongCon || soluongMua < 1)
                        Toast.makeText(mContext, "Số lượng mua không hợp lệ", Toast.LENGTH_SHORT).show();
                    else {

                        Oder oder = new Oder();
                        oder.setMonID(m_txtMonID.getText().toString());
                        oder.setGia(Integer.parseInt(m_txtGia.getText().toString()));

                        oder.setLoaiMon(m_txtLoaimon.getText().toString());

                        oder.setLoai(Integer.parseInt(m_txtLoai.getText().toString()));

                        oder.setSoluong(Integer.parseInt(m_edtSoluong.getText().toString()));
                        oder.setTenMon(m_txtTenmon.getText().toString());
                        new OderFirebaseHelper().ThemHoaDonTam(oder, new OderFirebaseHelper.OderDataStatuts() {
                            @Override
                            public void DataIsLoaded(List<Oder> oders, List<String> keys) {

                            }

                            @Override
                            public void DataIsInserted() {
                                Toast.makeText(mContext, "Thêm thành công vào giỏ hàng", Toast.LENGTH_SHORT).show();

                            }

                            @Override
                            public void DataIsUpdated() {

                            }

                            @Override
                            public void DataIsDeleted() {

                            }
                        });

                    }
                }
            });

        }
    }

    class MonOderAdapter extends RecyclerView.Adapter<MonItemOderview> {
        private List<Mon> mMonlist;
        private List<String> mKeys;

        @NonNull
        @Override
        public MonItemOderview onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new MonItemOderview(parent);
        }

        @Override
        public void onBindViewHolder(@NonNull MonItemOderview holder, int position) {
            holder.bind(mMonlist.get(position), mKeys.get(position));
        }

        @Override
        public int getItemCount() {
            return mMonlist.size();
        }

        public MonOderAdapter(List<Mon> mMonlist, List<String> mKeys) {
            this.mMonlist = mMonlist;
            this.mKeys = mKeys;
        }
    }
}
