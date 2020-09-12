package com.app.santabanta.Modals;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class HomeDetailsModel {

    @SerializedName("data")
    @Expose
    private List<HomeDetailList> data = null;
    @SerializedName("featured_categories")
    @Expose
    private ArrayList<FeaturedCategory> featuredCategories = null;
    @SerializedName("page")
    @Expose
    private Integer next;

    public List<HomeDetailList> getData() {
        return data;
    }

    public void setData(List<HomeDetailList> data) {
        this.data = data;
    }

    public ArrayList<FeaturedCategory> getFeaturedCategories() {
        return featuredCategories;
    }

    public void setFeaturedCategories(ArrayList<FeaturedCategory> featuredCategories) {
        this.featuredCategories = featuredCategories;
    }

    public Integer getNext() {
        return next;
    }

    public void setNext(Integer next) {
        this.next = next;
    }


}
