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

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.GalleryViewHolder>{
    Context context;
    ArrayList<Integer> roomImageList;
    ArrayList<String> roomNameList;

    public GalleryAdapter(Context context, ArrayList<Integer> roomImageList, ArrayList<String> roomNameList){
        this.context = context;
        this.roomImageList = roomImageList;
        this.roomNameList = roomNameList;
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
        holder.ivRoomImage.setImageResource(roomImageList.get(position));
        holder.tvRoomName.setText(roomNameList.get(position));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("roomImage", roomImageList.get(position));
                intent.putExtra("roomName", roomNameList.get(position));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return roomNameList.size();
    }
}