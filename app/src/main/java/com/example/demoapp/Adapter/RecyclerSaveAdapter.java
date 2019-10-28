package com.example.demoapp.Adapter;

import android.content.Context;
import android.net.Uri;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demoapp.Common.Common;
import com.example.demoapp.Model.Room;
import com.example.demoapp.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RecyclerSaveAdapter extends RecyclerView.Adapter<RecyclerSaveAdapter.RoomViewHolderSave>{

    private Context mContext;
    private List<Room> listRoom;
    private RecyclerAdapter.IOnItemClickListener mListener;

    public RecyclerSaveAdapter(Context mContext, List<Room> listRoom) {
        this.mContext = mContext;
        this.listRoom = listRoom;
    }


    public class RoomViewHolderSave extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        ImageView iv_RoomAvatar;
        TextView tv_price,tv_title,tv_address,tv_acreage,tv_time;
        public RoomViewHolderSave(@NonNull View itemView) {
            super(itemView);
            iv_RoomAvatar = itemView.findViewById(R.id.iv_RoomAvatar);
            tv_price = itemView.findViewById(R.id.tv_price);
            tv_title = itemView.findViewById(R.id.tv_title);
            tv_address = itemView.findViewById(R.id.tv_address);
            tv_acreage = itemView.findViewById(R.id.tv_acreage);
            tv_time = itemView.findViewById(R.id.tv_time);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if(mListener!=null){
                int position = getAdapterPosition();
                if(position!=RecyclerView.NO_POSITION){
                    mListener.onItemClick(position);
                }
            }
        }

    }

    // Chức năng này chỉ dùng ở danh sách phòng đã đăng của user
    public interface  IOnItemClickListener{
        void onItemClick(int position);
    }


    public void setOnItemClickListener(RecyclerAdapter.IOnItemClickListener listener) {
        mListener = listener;
    }


    @NonNull
    @Override
    public RoomViewHolderSave onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Tham chiếu View đến list-item (layout)
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item,parent,false);
        // Trả về 1 view holder tham số truyền vào là 1 view(layout)
        return new RoomViewHolderSave(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RoomViewHolderSave holder, int position) {
        Room currentRoom = listRoom.get(position);
        // Format price
        String priceFormat = Common.FormatCurrentcy(currentRoom.getPrice());
        holder.tv_price.setText(priceFormat);
        holder.tv_title.setText(currentRoom.getTitle());
        holder.tv_acreage.setText(Float.toString(currentRoom.getAcreage())+" m2");
        holder.tv_address.setText(currentRoom.getAddress());
        // Set time
        String datePost = Common.getDateFormat(currentRoom.getTimePost());
        holder.tv_time.setText("Đăng ngày: "+datePost);
        Uri uri = Uri.parse(currentRoom.getImageUrl());
        Picasso.with(mContext)
                .load(uri)
                .placeholder(R.drawable.home)
                .fit()
                .centerCrop()
                .into(holder.iv_RoomAvatar);
    }

    @Override
    public int getItemCount() {
        return listRoom.size();
    }
}
