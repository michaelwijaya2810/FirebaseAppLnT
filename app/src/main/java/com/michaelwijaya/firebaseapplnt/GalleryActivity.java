package com.michaelwijaya.firebaseapplnt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class GalleryActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        RecyclerView rvGallery = findViewById(R.id.rv_gallery);
        Button btnSettings = findViewById(R.id.btn_settings);

//        ArrayList<Integer> roomImageList = new ArrayList<>();
//        roomImageList.add(R.drawable.room1);
//        roomImageList.add(R.drawable.room2);
//        roomImageList.add(R.drawable.room3);
//        roomImageList.add(R.drawable.room4);
//        roomImageList.add(R.drawable.room5);
//
//        ArrayList<String> roomNameList = new ArrayList<>();
//        roomNameList.add("Nice Room");
//        roomNameList.add("Good Room");
//        roomNameList.add("Cozy Room");
//        roomNameList.add("Luxury Room");
//        roomNameList.add("Legendary Room");

        ArrayList<Room> roomList = new ArrayList<>();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseRef = database.getReference("images");

        databaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Room roomInfo = dataSnapshot.getValue(Room.class);

                    roomList.add(roomInfo);
                }

                GalleryAdapter adapter = new GalleryAdapter(GalleryActivity.this, roomList);
                rvGallery.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(GalleryActivity.this, "Error on loading images", Toast.LENGTH_SHORT).show();
            }
        });

        rvGallery.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));

        btnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GalleryActivity.this, MainActivity.class));
            }
        });
    }
}
















