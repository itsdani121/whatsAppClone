package com.example.whatsappclone;

import com.google.firebase.database.DataSnapshot;

public interface databasefetchSingle {
    void onGetDataSuccessful(DataSnapshot dataSnapshot,String msg);
    void onGetDataFailed(String error);

}
