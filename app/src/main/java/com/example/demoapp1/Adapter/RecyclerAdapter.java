package com.example.demoapp1.Adapter;

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

import com.example.demoapp1.Common.Common;
import com.example.demoapp1.Model.Room;
import com.example.demoapp1.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.RoomViewHolder> {
    private Context mContext;
    private List<Room> listRoom;
    private IOnItemClickListener mListener;

    public RecyclerAdapter(Context mContext, List<Room> listRoom) {
        this.mContext = mContext;
        this.listRoom = listRoom;
    }


    public class RoomViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,
            View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener {
        ImageView iv_RoomAvatar;
        TextView tv_price,tv_title,tv_address,tv_acreage,tv_time;
        public RoomViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_RoomAvatar = itemView.findViewById(R.id.iv_RoomAvatar);
            tv_price = itemView.findViewById(R.id.tv_price);
            tv_title = itemView.findViewById(R.id.tv_title);
            tv_address = itemView.findViewById(R.id.tv_address);
            tv_acreage = itemView.findViewById(R.id.tv_acreage);
            tv_time = itemView.findViewById(R.id.tv_time);
            itemView.setOnClickListener(this);
            itemView.setOnCreateContextMenuListener(this);
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

        @Override
        public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
            contextMenu.setHeaderTitle("Select Action");
            MenuItem showItem = contextMenu.add(Menu.NONE,1,1,"Edit");
            MenuItem deleteItem = contextMenu.add(Menu.NONE,2,2,"Delete");
            showItem.setOnMenuItemClickListener(this);
            deleteItem.setOnMenuItemClickListener(this);
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            if(mListener!=null){
                int position = getAdapterPosition();
                if(position!=RecyclerView.NO_POSITION){
                    switch (menuItem.getItemId()){
                        case 1:
                            mListener.onEditItemClick(position);
                            return true;
                        case 2:
                            mListener.onDeleteItemClick(position);
                            return true;
                    }
                }
            }
            return false;
        }
    }

    // Chức năng này chỉ dùng ở danh sách phòng đã đăng của user
    public interface  IOnItemClickListener{
        void onItemClick(int position);
        void onEditItemClick(int position);
        void onDeleteItemClick(int position);
    }


    public void setOnItemClickListener(IOnItemClickListener listener) {
        mListener = listener;
    }

    @NonNull
    @Override
    public RoomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Tham chiếu View đến list-item (layout)
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item,parent,false);
        // Trả về 1 view holder tham số truyền vào là 1 view(layout)
        return new RoomViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RoomViewHolder holder, int position) {
        try {
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
        }catch (Exception ex){

        }

    }

    @Override
    public int getItemCount() {
        return listRoom.size();
    }
}
