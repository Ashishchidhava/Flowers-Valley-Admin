package com.example.flowervallyadmin.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.flowervallyadmin.R;
import com.example.flowervallyadmin.adapter.BannerAdapter;
import com.example.flowervallyadmin.adapter.FlowerAdapter;
import com.example.flowervallyadmin.model.Banner;
import com.example.flowervallyadmin.model.Flower;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ViewAllFlowerFragment extends Fragment {
    private RecyclerView flowerRecyclerview;
    private DatabaseReference mDatabaseRef;
    private Flower flower;
    ArrayList<Flower> flowers;

    public ViewAllFlowerFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_view_all_flower, container, false);

        flowerRecyclerview = view.findViewById(R.id.flower_recyclerview);


        mDatabaseRef = FirebaseDatabase.getInstance().getReference("flowers");
        flowers = new ArrayList<>();

        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                flowers.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    flower = postSnapshot.getValue(Flower.class);
                    flowers.add(flower);
                }
                flowerRecyclerview.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
                flowerRecyclerview.setAdapter(new FlowerAdapter(getContext(), flowers));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getContext(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


        return view;
    }
}