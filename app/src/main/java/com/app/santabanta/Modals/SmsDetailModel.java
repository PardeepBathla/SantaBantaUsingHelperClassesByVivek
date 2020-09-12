package com.app.santabanta.Modals;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SmsDetailModel {


    @SerializedName("author_name")
    private Object mAuthorName;
    @SerializedName("categories")
    private List<SmsCategoryModel> mCategories;
    @SerializedName("favourites")
    private List<SmsFavouriteModel> mFavourite;
    @SerializedName("content")
    private String mContent;
    @SerializedName("id")
    private Long mId;
    @SerializedName("image")
    private String mImage;
    @SerializedName("language")
    private String mLanguage;
    @SerializedName("fav_count")
    private int fav_count;
    @SerializedName("mBreadcrumbs")
    @Expose
    private List<Breadcrumbs> breadcrumbs;

    public List<Breadcrumbs> getBreadcrumbs() {
        return breadcrumbs;
    }

    public void setBreadcrumbs(List<Breadcrumbs> breadcrumbs) {
        this.breadcrumbs = breadcrumbs;
    }
    @SerializedName("viewing_prefrence")
    private Object mViewingPrefrence;

    private int busy = 8;

        public int getBusy() {
        return this.busy;
    }

    public void setBusy(int busy) {
        this.busy = busy;
//        notifyPropertyChanged(BR.busy);
    }


    public int getFav_count() {
        return fav_count;
    }

    public void setFav_count(int fav_count) {
        this.fav_count = fav_count;
    }


    public List<SmsFavouriteModel> getmFavourite() {
        return mFavourite;
    }

    public void setmFavourite(List<SmsFavouriteModel> mFavourite) {
        this.mFavourite = mFavourite;
    }

    public Object getAuthorName() {
        return mAuthorName;
    }

    public List<SmsCategoryModel> getCategories() {
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

    public Object getViewingPrefrence() {
        return mViewingPrefrence;
    }

    public static class Builder {

        private Object mAuthorName;
        private List<SmsCategoryModel> mCategories;
        private String mContent;
        private Long mId;
        private String mImage;
        private String mLanguage;
        private Object mViewingPrefrence;

        public SmsDetailModel.Builder withAuthorName(Object authorName) {
            mAuthorName = authorName;
            return this;
        }

        public SmsDetailModel.Builder withCategories(List<SmsCategoryModel> categories) {
            mCategories = categories;
            return this;
        }

        public SmsDetailModel.Builder withContent(String content) {
            mContent = content;
            return this;
        }

        public SmsDetailModel.Builder withId(Long id) {
            mId = id;
            return this;
        }

        public SmsDetailModel.Builder withImage(String image) {
            mImage = image;
            return this;
        }

        public SmsDetailModel.Builder withLanguage(String language) {
            mLanguage = language;
            return this;
        }

        public SmsDetailModel.Builder withViewingPrefrence(Object viewingPrefrence) {
            mViewingPrefrence = viewingPrefrence;
            return this;
        }

        public SmsDetailModel build() {
            SmsDetailModel smsDetailModel = new SmsDetailModel();
            smsDetailModel.mAuthorName = mAuthorName;
            smsDetailModel.mCategories = mCategories;
            smsDetailModel.mContent = mContent;
            smsDetailModel.mId = mId;
            smsDetailModel.mImage = mImage;
            smsDetailModel.mLanguage = mLanguage;
            smsDetailModel.mViewingPrefrence = mViewingPrefrence;
            return smsDetailModel;
        }

    }


}
