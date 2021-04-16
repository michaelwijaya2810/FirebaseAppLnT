package com.michaelwijaya.firebaseapplnt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ivRoomImage = findViewById(R.id.iv_room_image);
        TextView tvRoomImage = findViewById(R.id.tv_room_name);

        Intent intent = getIntent();

        Glide.with(this).load(intent.getExtras().getString("roomImage")).into(ivRoomImage);
        tvRoomImage.setText(intent.getExtras().getString("roomName"));
    }
}