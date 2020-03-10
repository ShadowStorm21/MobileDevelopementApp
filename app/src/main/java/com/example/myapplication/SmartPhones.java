package com.example.myapplication;

import android.net.Uri;
import android.widget.ImageView;

import java.io.Serializable;

public class SmartPhones implements Serializable {
    private String mProductId;
    private String mName;
    private Double mPrice;
    private Uri mPic;
    private String mDescription;

    public SmartPhones(String mProductId, String mName, Double mPrice, String mDescription, Uri mPic) {
        this.mProductId = mProductId;
        this.mName = mName;
        this.mPrice = mPrice;
        this.mDescription = mDescription;
        this.mPic = mPic;
    }

    public SmartPhones() {
    }

    public String getmDescription() {
        return mDescription;
    }

    public void setmDescription(String mDescription) {
        this.mDescription = mDescription;
    }

    public String getmProductId() {
        return mProductId;
    }

    public void setmProductId(String mProductId) {
        this.mProductId = mProductId;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public Double getmPrice() {
        return mPrice;
    }

    public void setmPrice(Double mPrice) {
        this.mPrice = mPrice;
    }

    public Uri getmPic() {
        return mPic;
    }

    public void setmPic(Uri mPic) {
        this.mPic = mPic;
    }
}
