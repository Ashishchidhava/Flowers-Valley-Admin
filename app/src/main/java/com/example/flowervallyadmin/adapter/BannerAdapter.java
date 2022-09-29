package com.example.flowervallyadmin.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.flowervallyadmin.R;
import com.example.flowervallyadmin.model.Banner;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

/**
 * Created by AuthSafe on 29-09-2022.
 * <br>
 * Copyright (c) 2022 SecureLayer7 Technologies. All rights reserved.
 */
public class BannerAdapter extends RecyclerView.Adapter<BannerAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Banner> banners;
    StorageReference mStorageRef;
    DatabaseReference mDatabaseRef;

    public BannerAdapter(Context context, ArrayList<Banner> banners) {
        this.context = context;
        this.banners = banners;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.banners_list_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Banner banner = banners.get(position);

        mStorageRef = FirebaseStorage.getInstance().getReference("banners");
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("banners");

        Glide.with(context)
                .load(banner.getImageUrl())
                .into(holder.imgBanner);

        holder.cardBanner.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                showDialog(banner);
                return true;
            }
        });

    }

    private void showDialog(Banner banner) {

        new MaterialAlertDialogBuilder(context)
                .setTitle("Delete Banner")
                .setMessage("Do you want to delete this banner ?")
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mStorageRef.getStorage().getReferenceFromUrl(banner.getImageUrl()).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(context, "Banner deleted", Toast.LENGTH_SHORT).show();
                                mDatabaseRef.child(banner.getId()).removeValue();
                                banners.remove(banner);

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(context, "File Not deleted " + e, Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }).show();
    }

    @Override
    public int getItemCount() {
        return banners.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        AppCompatImageView imgBanner;
        CardView cardBanner;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgBanner = itemView.findViewById(R.id.img_banner);
            cardBanner = itemView.findViewById(R.id.card_banner);
        }
    }
}
