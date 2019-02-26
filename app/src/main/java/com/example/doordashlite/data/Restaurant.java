package com.example.doordashlite.data;

import com.google.gson.annotations.SerializedName;

public class Restaurant {
    public String id;
    public String name;
    public String description;
    @SerializedName("cover_img_url")
    public String coverImgUrl;
    public String status;
    @SerializedName("status_type")
    public String statusType;
    @SerializedName("delivery_fee")
    public int deliveryFree;
}
