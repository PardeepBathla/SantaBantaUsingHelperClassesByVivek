package com.app.santabanta.Modals;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class JokesDetailModel {

    @SerializedName("fav_count")
    int fav_count;
    @SerializedName("categories")
    private List<JokesCategoryModel> mCategories;
    @SerializedName("favourites")
    private List<JokesFavouriteModel> mFavourite;
    @SerializedName("content")
    private String mContent;
    @SerializedName("id")
    private Long mId;
    @SerializedName("image")
    private String mImage;
    @SerializedName("language")
    private String mLanguage;
    @SerializedName("title")
    private String mTitle;
    @SerializedName("viewing_prefrence")
    private Object mViewingPrefrence;
    @SerializedName("mBreadcrumbs")
    @Expose
    private List<Breadcrumbs> breadcrumbs;

    public List<Breadcrumbs> getBreadcrumbs() {
        return breadcrumbs;
    }

    public void setBreadcrumbs(List<Breadcrumbs> breadcrumbs) {
        this.breadcrumbs = breadcrumbs;
    }

    public int getFav_count() {
        return fav_count;
    }

    public void setFav_count(int fav_count) {
        this.fav_count = fav_count;
    }


    public List<JokesCategoryModel> getCategories() {
        return mCategories;
    }

    public String getContent() {
        return mContent;
    }

    public Long getId() {
        return mId;
    }

    public String getImage() {
        return mImage;
    }

    public String getLanguage() {
        return mLanguage;
    }

    public String getTitle() {
        return mTitle;
    }


    public List<JokesFavouriteModel> getmFavourite() {
        return mFavourite;
    }

    public void setmFavourite(List<JokesFavouriteModel> mFavourite) {
        this.mFavourite = mFavourite;
    }


}
