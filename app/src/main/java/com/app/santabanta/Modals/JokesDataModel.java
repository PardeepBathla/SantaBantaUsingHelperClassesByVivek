package com.app.santabanta.Modals;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class JokesDataModel {

    @SerializedName("current_page")
    private Long mCurrentPage;
    @SerializedName("data")
    private List<JokesDetailModel> mData;
    @SerializedName("first_page_url")
    private String mFirstPageUrl;
    @SerializedName("from")
    private Long mFrom;
    @SerializedName("last_page")
    private Long mLastPage;
    @SerializedName("last_page_url")
    private String mLastPageUrl;
    @SerializedName("next_page_url")
    private String mNextPageUrl;
    @SerializedName("path")
    private String mPath;
    @SerializedName("per_page")
    private Long mPerPage;
    @SerializedName("prev_page_url")
    private Object mPrevPageUrl;
    @SerializedName("to")
    private Long mTo;
    @SerializedName("total")
    private Long mTotal;
    @SerializedName("featured_categories")
    @Expose
    private ArrayList<JokesFeaturedCategory> featuredCategories = null;
    public Long getCurrentPage() {
        return mCurrentPage;
    }

    public List<JokesDetailModel> getData() {
        return mData;
    }

    public String getFirstPageUrl() {
        return mFirstPageUrl;
    }

    public Long getFrom() {
        return mFrom;
    }

    public Long getLastPage() {
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

    public ArrayList<JokesFeaturedCategory> getFeaturedCategories() {
        return featuredCategories;
    }

    public void setFeaturedCategories(ArrayList<JokesFeaturedCategory> featuredCategories) {
        this.featuredCategories = featuredCategories;
    }

    public Long getPerPage() {
        return mPerPage;
    }

    public Object getPrevPageUrl() {
        return mPrevPageUrl;
    }

    public Long getTo() {
        return mTo;
    }

    public Long getTotal() {
        return mTotal;
    }

    public static class Builder {

        private Long mCurrentPage;
        private List<JokesDetailModel> mData;
        private String mFirstPageUrl;
        private Long mFrom;
        private Long mLastPage;
        private String mLastPageUrl;
        private String mNextPageUrl;
        private String mPath;
        private Long mPerPage;
        private Object mPrevPageUrl;
        private Long mTo;
        private Long mTotal;

        public JokesDataModel.Builder withCurrentPage(Long currentPage) {
            mCurrentPage = currentPage;
            return this;
        }

        public JokesDataModel.Builder withData(List<JokesDetailModel> data) {
            mData = data;
            return this;
        }

        public JokesDataModel.Builder withFirstPageUrl(String firstPageUrl) {
            mFirstPageUrl = firstPageUrl;
            return this;
        }

        public JokesDataModel.Builder withFrom(Long from) {
            mFrom = from;
            return this;
        }

        public JokesDataModel.Builder withLastPage(Long lastPage) {
            mLastPage = lastPage;
            return this;
        }

        public JokesDataModel.Builder withLastPageUrl(String lastPageUrl) {
            mLastPageUrl = lastPageUrl;
            return this;
        }

        public JokesDataModel.Builder withNextPageUrl(String nextPageUrl) {
            mNextPageUrl = nextPageUrl;
            return this;
        }

        public JokesDataModel.Builder withPath(String path) {
            mPath = path;
            return this;
        }

        public JokesDataModel.Builder withPerPage(Long perPage) {
            mPerPage = perPage;
            return this;
        }

        public JokesDataModel.Builder withPrevPageUrl(Object prevPageUrl) {
            mPrevPageUrl = prevPageUrl;
            return this;
        }

        public JokesDataModel.Builder withTo(Long to) {
            mTo = to;
            return this;
        }

        public JokesDataModel.Builder withTotal(Long total) {
            mTotal = total;
            return this;
        }

        public JokesDataModel build() {
            JokesDataModel jokesDataModel = new JokesDataModel();
            jokesDataModel.mCurrentPage = mCurrentPage;
            jokesDataModel.mData = mData;
            jokesDataModel.mFirstPageUrl = mFirstPageUrl;
            jokesDataModel.mFrom = mFrom;
            jokesDataModel.mLastPage = mLastPage;
            jokesDataModel.mLastPageUrl = mLastPageUrl;
            jokesDataModel.mNextPageUrl = mNextPageUrl;
            jokesDataModel.mPath = mPath;
            jokesDataModel.mPerPage = mPerPage;
            jokesDataModel.mPrevPageUrl = mPrevPageUrl;
            jokesDataModel.mTo = mTo;
            jokesDataModel.mTotal = mTotal;
            return jokesDataModel;
        }

    }

}
