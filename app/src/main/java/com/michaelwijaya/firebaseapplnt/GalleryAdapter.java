package com.michaelwijaya.firebaseapplnt;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import com.bumptech.glide.Glide;

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.GalleryViewHolder>{
    Context context;
    ArrayList<Room> roomList;

    public GalleryAdapter(Context context, ArrayList<Room> roomList){
        this.context = context;
        this.roomList = roomList;
    }

    public static class GalleryViewHolder extends RecyclerView.ViewHolder{
        ImageView ivRoomImage;
        TextView tvRoomName;
        public GalleryViewHolder(@NonNull View itemView) {
            super(itemView);
            ivRoomImage = itemView.findViewById(R.id.iv_room_image);
            tvRoomName = itemView.findViewById(R.id.tv_room_name);
        }
    }

    @NonNull
    @Override
    public GalleryAdapter.GalleryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View galleryView = inflater.inflate(R.layout.item_gallery, parent, false);
        return new GalleryViewHolder(galleryView);
    }

    @Override
    public void onBindViewHolder(@NonNull GalleryAdapter.GalleryViewHolder holder, int position) {
        Glide.with(context).load(roomList.get(position).getImage()).into(holder.ivRoomImage);
        holder.tvRoomName.setText(roomList.get(position).getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("roomImage", roomList.get(position).getImage());
                intent.putExtra("roomName", roomList.get(position).getName());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return roomList.size();
    }
}