package com.app.santabanta.Modals.memesModel;

import com.google.gson.annotations.SerializedName;

public class MemesFavouriteModel {
    @SerializedName("device_id")
    private String deviceId;

    @SerializedName("item_id")
    private String itemId;

    @SerializedName("id")
    private int id;

    public void setDeviceId(String deviceId){
        this.deviceId = deviceId;
    }

    public String getDeviceId(){
        return deviceId;
    }

    public void setItemId(String itemId){
        this.itemId = itemId;
    }

    public String getItemId(){
        return itemId;
    }

    public void setId(int id){
        this.id = id;
    }

    public int getId(){
        return id;
    }
}
