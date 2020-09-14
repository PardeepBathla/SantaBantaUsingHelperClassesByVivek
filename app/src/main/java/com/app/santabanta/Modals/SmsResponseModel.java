package com.app.santabanta.Modals;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class SmsResponseModel {


    @SerializedName("current_page")
    private int mCurrentPage;
    @SerializedName("data")
    private List<SmsDetailModel> mData;
    @SerializedName("first_page_url")
    private String mFirstPageUrl;
    @SerializedName("from")
    private int mFrom;
    @SerializedName("last_page")
    private int mLastPage;
    @SerializedName("last_page_url")
    private String mLastPageUrl;
    @SerializedName("next_page_url")
    private String mNextPageUrl;
    @SerializedName("path")
    private String mPath;
    @SerializedName("per_page")
    private int mPerPage;
    @SerializedName("prev_page_url")
    private Object mPrevPageUrl;
    @SerializedName("to")
    private int mTo;
    @SerializedName("total")
    private int mTotal;

    public ArrayList<SmsFeaturedCategory> getFeaturedCategories() {
        return featuredCategories;
    }

    public void setFeaturedCategories(ArrayList<SmsFeaturedCategory> featuredCategories) {
        this.featuredCategories = featuredCategories;
    }

    @SerializedName("featured_categories")
    @Expose
    private ArrayList<SmsFeaturedCategory> featuredCategories = null;

    public int getCurrentPage() {
        return mCurrentPage;
    }

    public List<SmsDetailModel> getData() {
        return mData;
    }

    public String getFirstPageUrl() {
        return mFirstPageUrl;
    }

    public int getFrom() {
        return mFrom;
    }

    public int getLastPage() {
        return mLastPage;
    }

    public String getLastPageUrl() {
        return mLastPageUrl;
    }

    public String getNextPageUrl() {
        return mNextPageUrl;
    }

    public String getPath() {
        return mPath;
    }

    public int getPerPage() {
        return mPerPage;
    }

    public Object getPrevPageUrl() {
        return mPrevPageUrl;
    }

    public int getTo() {
        return mTo;
    }

    public int getTotal() {
        return mTotal;
    }

    public static class Builder {

        private int mCurrentPage;
        private List<SmsDetailModel> mData;
        private String mFirstPageUrl;
        private int mFrom;
        private int mLastPage;
        private String mLastPageUrl;
        private String mNextPageUrl;
        private String mPath;
        private int mPerPage;
        private Object mPrevPageUrl;
        private int mTo;
        private int mTotal;

        public SmsResponseModel.Builder withCurrentPage(int currentPage) {
            mCurrentPage = currentPage;
            return this;
        }

        public SmsResponseModel.Builder withData(List<SmsDetailModel> data) {
            mData = data;
            return this;
        }

        public SmsResponseModel.Builder withFirstPageUrl(String firstPageUrl) {
            mFirstPageUrl = firstPageUrl;
            return this;
        }

        public SmsResponseModel.Builder withFrom(int from) {
            mFrom = from;
            return this;
        }

        public SmsResponseModel.Builder withLastPage(int lastPage) {
            mLastPage = lastPage;
            return this;
        }

        public SmsResponseModel.Builder withLastPageUrl(String lastPageUrl) {
            mLastPageUrl = lastPageUrl;
            return this;
        }

        public SmsResponseModel.Builder withNextPageUrl(String nextPageUrl) {
            mNextPageUrl = nextPageUrl;
            return this;
        }

        public SmsResponseModel.Builder withPath(String path) {
            mPath = path;
            return this;
        }

        public SmsResponseModel.Builder withPerPage(int perPage) {
            mPerPage = perPage;
            return this;
        }

        public SmsResponseModel.Builder withPrevPageUrl(Object prevPageUrl) {
            mPrevPageUrl = prevPageUrl;
            return this;
        }

        public SmsResponseModel.Builder withTo(int to) {
            mTo = to;
            return this;
        }

        public SmsResponseModel.Builder withTotal(int total) {
            mTotal = total;
            return this;
        }

        public SmsResponseModel build() {
            SmsResponseModel smsResponseModel = new SmsResponseModel();
            smsResponseModel.mCurrentPage = mCurrentPage;
            smsResponseModel.mData = mData;
            smsResponseModel.mFirstPageUrl = mFirstPageUrl;
            smsResponseModel.mFrom = mFrom;
            smsResponseModel.mLastPage = mLastPage;
            smsResponseModel.mLastPageUrl = mLastPageUrl;
            smsResponseModel.mNextPageUrl = mNextPageUrl;
            smsResponseModel.mPath = mPath;
            smsResponseModel.mPerPage = mPerPage;
            smsResponseModel.mPrevPageUrl = mPrevPageUrl;
            smsResponseModel.mTo = mTo;
            smsResponseModel.mTotal = mTotal;
            return smsResponseModel;
        }

    }

}
