package com.example.whatsappclone;

public class account_details {

    private String userId;

    public account_details() {
        this.userId = userId;
        this.email = email;
        this.mName = mName;
        this.mCity = mCity;
        this.mAddress = mAddress;
        this.imageLocalPath = imageLocalPath;
        this.imageCloudPath = imageCloudPath;
    }

    private String email;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    private String mName;
    private String mCity;
    private String mAddress;
    private String imageLocalPath;
    private String imageCloudPath;

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getmCity() {
        return mCity;
    }

    public void setmCity(String mCity) {
        this.mCity = mCity;
    }

    public String getmAddress() {
        return mAddress;
    }

    public void setmAddress(String mAddress) {
        this.mAddress = mAddress;
    }

    public String getImageLocalPath() {
        return imageLocalPath;
    }

    public void setImageLocalPath(String imageLocalPath) {
        this.imageLocalPath = imageLocalPath;
    }

    public String getImageCloudPath() {
        return imageCloudPath;
    }

    public void setImageCloudPath(String imageCloudPath) {
        this.imageCloudPath = imageCloudPath;
    }



}
