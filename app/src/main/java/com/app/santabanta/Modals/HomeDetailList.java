package com.app.santabanta.Modals;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class HomeDetailList {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("content")
    @Expose
    private String content;
    @SerializedName("language")
    @Expose
    private String language;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("viewing_prefrence")
    @Expose
    private Object viewingPrefrence;
    @SerializedName("author_name")
    @Expose
    private String authorName;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("categories")
    @Expose
    private List<Category> categories = null;
    @SerializedName("favourites")
    @Expose
    private List<Favourite> favourites = null;
    @SerializedName("tags")
    @Expose
    private List<Object> tags = null;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("mBreadcrumbs")
    @Expose
    private List<Breadcrumb> breadcrumbs = null;
    @SerializedName("fav_count")
    @Expose
    private Integer favCount;
    @SerializedName("meme_type")
    @Expose
    private String memeType;

    public String getMemeType() {
        return memeType;
    }

    public void setMemeType(String memeType) {
        this.memeType = memeType;
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

    public Object getViewingPrefrence() {
        return viewingPrefrence;
    }

    public void setViewingPrefrence(Object viewingPrefrence) {
        this.viewingPrefrence = viewingPrefrence;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public List<Favourite> getFavourites() {
        return favourites;
    }

    public void setFavourites(List<Favourite> favourites) {
        this.favourites = favourites;
    }

    public List<Object> getTags() {
        return tags;
    }

    public void setTags(List<Object> tags) {
        this.tags = tags;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<Breadcrumb> getBreadcrumbs() {
        return breadcrumbs;
    }

    public void setBreadcrumbs(List<Breadcrumb> breadcrumbs) {
        this.breadcrumbs = breadcrumbs;
    }

    public Integer getFavCount() {
        return favCount;
    }

    public void setFavCount(Integer favCount) {
        this.favCount = favCount;
    }
}
