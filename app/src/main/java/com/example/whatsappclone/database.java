package com.example.whatsappclone;

import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;

import com.bumptech.glide.util.LogTime;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;

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
    private StorageReference storageRef;


    private database() {
        mRootRef = FirebaseDatabase.getInstance().getReference();
        storageRef = FirebaseStorage.getInstance().getReference();
    }

    public void createAccount(String rootNode, String userid, account_details data, final databasecallback databasecallback) {
        mRootRef.child(rootNode).child(userid).setValue(data).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d(TAG, "onSuccess: submitted successfully ");
                databasecallback.onsuccessfull("successfuly create");
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "onFailure:failed " + e.getMessage());
                        databasecallback.onfailed("failed it");
                    }
                });

    }

    public void updateAccount() {

    }

    public void getDateSingle(String rootNode, String userId, final databasefetchSingle listener) {
        mRootRef.child(rootNode)
                .child(userId)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        listener.onGetDataSuccessful(dataSnapshot, "value fetch Successfully");
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        listener.onGetDataFailed(databaseError.getDetails());
                    }
                });
    }

    public void upload(String folder, String filename, String filePath, final fileuploadcallback listener) {

        File file = new File(filePath);
        Uri uri = Uri.fromFile(file);
        storageRef
                .child(folder)
                .child(filename)
                .putFile(uri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        listener.onfileuploadsuccess(taskSnapshot.toString(),"file upload successfully");
                        cloudpath(uri,taskSnapshot);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        listener.onfileuploadfailed(e.getMessage());
                    }
                });

    }

    private void cloudpath(UploadTask.TaskSnapshot taskSnapshot, final fileuploadcallback listener) {
        if (taskSnapshot.getMetadata() != null && taskSnapshot.getMetadata().getReference() != null) {
            taskSnapshot
                    .getMetadata()
                    .getReference()
                    .getDownloadUrl()
                    .addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            listener.onfileuploadsuccess(uri.toString(),"cloud success");
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            listener.onfileuploadfailed(e.getMessage());
                        }
                    });
        }
    }

}


