package com.example.flowervallyadmin.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.flowervallyadmin.R;
import com.example.flowervallyadmin.Utils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.Executor;

public class OTPVerificationFragment extends Fragment {
    private static final String TAG = "OTPVerificationFragment";
    String token;
    private MaterialButton btnVerify;
    private TextInputEditText etOtp;
    private FirebaseAuth mAuth;

    public OTPVerificationFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            token = getArguments().getString("token");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_o_t_p_verification, container, false);

        btnVerify = view.findViewById(R.id.btn_verify);
        etOtp = view.findViewById(R.id.otp);

        mAuth = FirebaseAuth.getInstance();

        btnVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (etOtp.getText().toString().trim().equalsIgnoreCase("")) {
                    Snackbar.make(btnVerify, "Please Enter OTP.", Snackbar.LENGTH_SHORT).show();
                    etOtp.requestFocus();
                } else if (etOtp.getText().toString().length() != 6) {
                    Snackbar.make(btnVerify, "Please Enter 6 Digit OTP.", Snackbar.LENGTH_SHORT).show();
                    etOtp.requestFocus();
                } else {
                    String otp = etOtp.getText().toString().trim();
                    verifyOtp(otp, token);
                }
            }
        });


        return view;
    }

    private void verifyOtp(String otp, String token) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(token, otp);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");

                            Utils.replaceFragment(new DashBoardFragment(), getActivity());

                            FirebaseUser user = task.getResult().getUser();
                            // Update UI
                        } else {
                            // Sign in failed, display a message and update the UI
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(getContext(), "Invalid OTP.", Toast.LENGTH_SHORT).show();

                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                            }
                        }
                    }
                });
    }
}