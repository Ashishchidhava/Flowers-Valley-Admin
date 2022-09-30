package com.example.flowervallyadmin.fragment;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.flowervallyadmin.R;
import com.example.flowervallyadmin.model.Banner;
import com.example.flowervallyadmin.model.Flower;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

public class AddFlowerFragment extends Fragment {
    private static final String TAG = "AddFlowerFragment";
    private AppCompatImageButton btnImage;
    private TextInputEditText etFlowerName, etFlowerPrice, etFlowerQuantity, etFlowerDescription;
    private MaterialButton btnAddFlower;
    private String imageURl = null;
    private Uri mImageUri;
    private ProgressBar mProgressBar;

    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;
    private StorageTask mUploadTask;

    public AddFlowerFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mImageUri = Uri.parse(getArguments().getString("image_uri"));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_flower, container, false);

        btnImage = view.findViewById(R.id.btn_image);
        etFlowerName = view.findViewById(R.id.flower_name);
        etFlowerPrice = view.findViewById(R.id.flower_price);
        etFlowerQuantity = view.findViewById(R.id.flower_qty);
        etFlowerDescription = view.findViewById(R.id.flower_description);
        btnAddFlower = view.findViewById(R.id.btn_flower);
        mProgressBar = view.findViewById(R.id.progress_bar);

        mStorageRef = FirebaseStorage.getInstance().getReference("flowers");
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("flowers");


        if (mImageUri != null) {
            btnImage.setImageURI(mImageUri);
        }

        btnImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFileChooser();

            }
        });

        btnAddFlower.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String strFlowerName, strFlowerPrice, strFlowerQuantity, strFlowerDescription;

                if (mImageUri == null) {
                    Snackbar.make(btnAddFlower, "Please Select Image.", Snackbar.LENGTH_SHORT).show();
                } else if (etFlowerName.getText().toString().trim().equalsIgnoreCase("")) {
                    Snackbar.make(btnAddFlower, "Please Enter Flower Name.", Snackbar.LENGTH_SHORT).show();
                    etFlowerName.requestFocus();
                } else if (etFlowerPrice.getText().toString().trim().equalsIgnoreCase("")) {
                    Snackbar.make(btnAddFlower, "Please Enter Price.", Snackbar.LENGTH_SHORT).show();
                    etFlowerPrice.requestFocus();
                } else if (etFlowerQuantity.getText().toString().trim().equalsIgnoreCase("")) {
                    Snackbar.make(btnAddFlower, "Please Enter Quantity.", Snackbar.LENGTH_SHORT).show();
                    etFlowerQuantity.requestFocus();
                } else if (etFlowerDescription.getText().toString().trim().equalsIgnoreCase("")) {
                    Snackbar.make(btnAddFlower, "Please Enter Flower Description.", Snackbar.LENGTH_SHORT).show();
                    etFlowerDescription.requestFocus();
                } else {
                    strFlowerName = etFlowerName.getText().toString().trim();
                    strFlowerPrice = etFlowerPrice.getText().toString().trim();
                    strFlowerQuantity = etFlowerQuantity.getText().toString().trim();
                    strFlowerDescription = etFlowerDescription.getText().toString().trim();

                    uploadFlower(imageURl, strFlowerName, strFlowerPrice, strFlowerQuantity, strFlowerDescription);
                }
            }
        });


        return view;
    }


    private String getFileExtension(Uri uri) {
        ContentResolver cR = getActivity().getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        getActivity().startActivityForResult(intent, 102);
    }

    private void uploadFlower(String imageURl, String flowerName, String flowerPrice, String flowerQuantity, String flowerDescription) {
        if (mImageUri != null) {
            mProgressBar.setVisibility(View.VISIBLE);
            StorageReference fileReference = mStorageRef.child(System.currentTimeMillis() + "." + getFileExtension(mImageUri));

            mUploadTask = fileReference.putFile(mImageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    mProgressBar.setProgress(0);
                                }
                            }, 500);


                            Toast.makeText(getContext(), "Upload successful", Toast.LENGTH_LONG).show();

                            Task<Uri> urlTask = taskSnapshot.getStorage().getDownloadUrl();
                            while (!urlTask.isSuccessful()) ;
                            Uri downloadUrl = urlTask.getResult();

                            Log.i(TAG, "onSuccess: " + downloadUrl);

                            Flower flower = new Flower(mDatabaseRef.push().getKey(), flowerName, flowerPrice, flowerQuantity, flowerDescription, downloadUrl.toString());
                            String uploadId = mDatabaseRef.push().getKey();
                            flower.setFlowerId(uploadId);
                            mDatabaseRef.child(uploadId).setValue(flower);
                            mProgressBar.setVisibility(View.GONE);

                            btnImage.setImageURI(null);
                            etFlowerName.setText(null);
                            etFlowerPrice.setText(null);
                            etFlowerQuantity.setText(null);
                            etFlowerDescription.setText(null);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.e(TAG, "onFailure: ", e);
                            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                            mProgressBar.setVisibility(View.GONE);
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                            mProgressBar.setProgress((int) progress);
                        }
                    });
        } else {
            Toast.makeText(getContext(), "No file selected", Toast.LENGTH_SHORT).show();
        }


    }
}