package com.example.flowervallyadmin.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.flowervallyadmin.R;
import com.example.flowervallyadmin.model.Banner;
import com.example.flowervallyadmin.model.Flower;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class FlowerAdapter extends RecyclerView.Adapter<FlowerAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Flower> flowers;
    StorageReference mStorageRef;
    DatabaseReference mDatabaseRef;

    public FlowerAdapter(Context context, ArrayList<Flower> flowers) {
        this.context = context;
        this.flowers = flowers;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.flower_list_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Flower flower = flowers.get(position);

        mStorageRef = FirebaseStorage.getInstance().getReference("flowers");
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("flowers");


        holder.flowerName.setText(flower.getFlowerName());
        holder.flowerPrice.setText(flower.getFlowerPrice());
        holder.flowerQty.setText(flower.getFlowerQuantity());

        Glide.with(context)
                .load(flower.getFlowerImageUrl())
                .into(holder.imgBanner);

        holder.cardBanner.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                showDialog(flower);
                return true;
            }
        });

    }

    private void showDialog(Flower flower) {

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
                        mStorageRef.getStorage().getReferenceFromUrl(flower.getFlowerImageUrl()).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(context, "Banner deleted", Toast.LENGTH_SHORT).show();
                                mDatabaseRef.child(flower.getFlowerId()).removeValue();
                                flowers.remove(flower);

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
        return flowers.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        AppCompatImageView imgBanner;
        CardView cardBanner;
        AppCompatTextView flowerName, flowerPrice, flowerQty;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgBanner = itemView.findViewById(R.id.img_banner);
            cardBanner = itemView.findViewById(R.id.card_banner);
            flowerName = itemView.findViewById(R.id.flower_name);
            flowerPrice = itemView.findViewById(R.id.flower_price);
            flowerQty = itemView.findViewById(R.id.qty);
        }
    }
}
