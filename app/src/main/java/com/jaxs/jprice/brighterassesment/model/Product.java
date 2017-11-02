package com.jaxs.jprice.brighterassesment.model;

import io.realm.RealmObject;

/**
 * Created by E21873 on 10/31/2017.
 */

public class Product extends RealmObject{

    private int mID;
    private String mName;
    private String Desc;
    private String regularPrice;
    private String salePrice;
    private String mImage;
    private String Stores;
    private String colors;



    public Product(){

    }

    public int getmID() {
        return mID;
    }

    public void setmID(int mID) {
        this.mID = mID;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getDesc() {
        return Desc;
    }

    public void setDesc(String desc) {
        Desc = desc;
    }

    public String getRegularPrice() {
        return regularPrice;
    }

    public void setRegularPrice(String regularPrice) {
        this.regularPrice = regularPrice;
    }

    public String getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(String salePrice) {
        this.salePrice = salePrice;
    }

    public String getmImage() {
        return mImage;
    }

    public void setmImage(String mImage) {
        this.mImage = mImage;
    }

    public String getStores() {
        return Stores;
    }

    public void setStores(String stores) {
        Stores = stores;
    }

    public String getColors() {
        return colors;
    }

    public void setColors(String colors) {
        this.colors = colors;
    }


    @Override
    public String toString() {
        return "Product{" +
                "mName='" + mName + '\'' +
                ", Desc='" + Desc + '\'' +
                ", regularPrice='" + regularPrice + '\'' +
                ", salePrice='" + salePrice + '\'' +
                '}';
    }
}
