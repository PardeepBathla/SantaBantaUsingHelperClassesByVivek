package com.app.santabanta.Modals;

import com.google.gson.annotations.SerializedName;

public class AddFavouriteRequest {

    @SerializedName("device_id")
    private String deviceId;

    @SerializedName("item_id")
    private int itemId;

    @SerializedName("type")
    private String type;

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public void setType(String type) {
        this.type = type;
    }



    public String getDeviceId(){
        return deviceId;
    }

    public int getItemId(){
        return itemId;
    }

    public String getType(){
        return type;
    }
}
