package com.app.santabanta.Modals.memesModel;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MemesDetailModel {

    @SerializedName("id")
    private Integer id;
    @SerializedName("content")
    private String content;
    @SerializedName("language")
    private String language;
    @SerializedName("image")
    private String image;
    @SerializedName("meme_type")
    private String memeType;
    @SerializedName("viewing_prefrence")
    private String viewingPrefrence;
    @SerializedName("author_name")
    private String authorName;
    @SerializedName("categories")
    private List<Category> categories = null;
    @SerializedName("favourites")
    private List<MemesFavouriteModel> favourites = null;
    @SerializedName("tags")
    private List<Tag> tags = null;
    @SerializedName("fav_count")
    private int favCount;

    @SerializedName("mBreadcrumbs")
    @Expose
    private List<Breadcrumb> breadcrumbs;

    public List<Breadcrumb> getBreadcrumbs() {
        return breadcrumbs;
    }

    public void setBreadcrumbs(List<Breadcrumb> breadcrumbs) {
        this.breadcrumbs = breadcrumbs;
    }


    public Integer getId() {
        return id;
    }


    public void setId(Integer id) {
        this.id = id;
    }


    public String getContent() {
        return content;
    }


    public void setContent(String content) {
        this.content = content;
    }


    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }


    public String getImage() {
        return image;
    }


    public void setImage(String image) {
        this.image = image;
    }

    public String getMemeType() {
        return memeType;
    }

    public void setMemeType(String memeType) {
        this.memeType = memeType;
    }


    public String getViewingPrefrence() {
        return viewingPrefrence;
    }


    public void setViewingPrefrence(String viewingPrefrence) {
        this.viewingPrefrence = viewingPrefrence;
    }


    public String getAuthorName() {
        return authorName;
    }


    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }


    public List<Category> getCategories() {
        return categories;
    }


    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public List<MemesFavouriteModel> getFavourites() {
        return favourites;
    }


    public void setFavourites(List<MemesFavouriteModel> favourites) {
        this.favourites = favourites;
    }


    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }


    public int getFavCount() {
        return favCount;
    }

    @SerializedName("fav_count")
    public void setFavCount(int favCount) {
        this.favCount = favCount;
    }


}
