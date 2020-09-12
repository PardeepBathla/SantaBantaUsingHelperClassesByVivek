package com.app.santabanta.Modals;

import com.google.gson.annotations.SerializedName;

public class JokesCategoryModel {

    @SerializedName("created_at")
    private Object mCreatedAt;
    @SerializedName("deleted_at")
    private String mDeletedAt;
    @SerializedName("icon")
    private Object mIcon;
    @SerializedName("id")
    private Long mId;
    @SerializedName("meta_description")
    private Object mMetaDescription;
    @SerializedName("meta_title")
    private Object mMetaTitle;
    @SerializedName("name")
    private String mName;
    @SerializedName("old_cat_id")
    private Long mOldCatId;
    @SerializedName("parent_id")
    private Long mParentId;
    @SerializedName("pivot")
    private JokesPivotModel mJokesPivotModel;
    @SerializedName("slug")
    private String mSlug;
    @SerializedName("sort_order")
    private Long mSortOrder;
    @SerializedName("status")
    private Long mStatus;
    @SerializedName("subcat_id")
    private Object mSubcatId;
    @SerializedName("type")
    private String mType;
    @SerializedName("updated_at")
    private String mUpdatedAt;

    public Object getCreatedAt() {
        return mCreatedAt;
    }

    public String getDeletedAt() {
        return mDeletedAt;
    }

    public Object getIcon() {
        return mIcon;
    }

    public Long getId() {
        return mId;
    }

    public Object getMetaDescription() {
        return mMetaDescription;
    }

    public Object getMetaTitle() {
        return mMetaTitle;
    }

    public String getName() {
        return mName;
    }

    public Long getOldCatId() {
        return mOldCatId;
    }

    public Long getParentId() {
        return mParentId;
    }

    public JokesPivotModel getPivot() {
        return mJokesPivotModel;
    }

    public String getSlug() {
        return mSlug;
    }

    public Long getSortOrder() {
        return mSortOrder;
    }

    public Long getStatus() {
        return mStatus;
    }

    public Object getSubcatId() {
        return mSubcatId;
    }

    public String getType() {
        return mType;
    }

    public String getUpdatedAt() {
        return mUpdatedAt;
    }

}
