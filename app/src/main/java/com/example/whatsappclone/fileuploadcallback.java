package com.example.whatsappclone;

public interface fileuploadcallback {
    void onfileuploadsuccess(String cloudPath,String msg);
    void onfileuploadfailed(String error);
}
