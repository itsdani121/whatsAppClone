package com.example.whatsappclone;

import android.util.Log;

import androidx.annotation.NonNull;

import com.bumptech.glide.util.LogTime;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class database {
    private static final String TAG = "database";
    private static database instance;


    public static database getInstance() {
        if (instance == null) {

            instance = new database();
        }


        return instance;
    }

    private DatabaseReference mRootRef;


    private database() {
        mRootRef = FirebaseDatabase.getInstance().getReference();
    }

    public void createAccount(String userid, account_details data, final databasecallback databasecallback) {
        mRootRef.child("profile").child(userid).setValue(data).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d(TAG, "onSuccess: submitted successfully ");
                databasecallback.onsuccessfull("successfuly create");
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "onFailure:failed "+e.getMessage());
                    databasecallback.onfailed("failed it");
                    }
                });

    }

    public void updateAccount() {

    }
}
