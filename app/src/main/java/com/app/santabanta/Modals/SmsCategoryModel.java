package com.app.santabanta.Modals;

import com.google.gson.annotations.SerializedName;

public class SmsCategoryModel {

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
    private SmsPivotModel mSmsPivotModel;
    @SerializedName("slug")
    private String mSlug;
    @SerializedName("sort_order")
    private Long mSortOrder;
    @SerializedName("status")
    private Long mStatus;
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

    public SmsPivotModel getPivot() {
        return mSmsPivotModel;
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

    public String getType() {
        return mType;
    }

    public String getUpdatedAt() {
        return mUpdatedAt;
    }

    public static class Builder {

        private Object mCreatedAt;
        private String mDeletedAt;
        private Object mIcon;
        private Long mId;
        private Object mMetaDescription;
        private Object mMetaTitle;
        private String mName;
        private Long mOldCatId;
        private Long mParentId;
        private SmsPivotModel mSmsPivotModel;
        private String mSlug;
        private Long mSortOrder;
        private Long mStatus;
        private String mType;
        private String mUpdatedAt;

        public SmsCategoryModel.Builder withCreatedAt(Object createdAt) {
            mCreatedAt = createdAt;
            return this;
        }

        public SmsCategoryModel.Builder withDeletedAt(String deletedAt) {
            mDeletedAt = deletedAt;
            return this;
        }

        public SmsCategoryModel.Builder withIcon(Object icon) {
            mIcon = icon;
            return this;
        }

        public SmsCategoryModel.Builder withId(Long id) {
            mId = id;
            return this;
        }

        public SmsCategoryModel.Builder withMetaDescription(Object metaDescription) {
            mMetaDescription = metaDescription;
            return this;
        }

        public SmsCategoryModel.Builder withMetaTitle(Object metaTitle) {
            mMetaTitle = metaTitle;
            return this;
        }

        public SmsCategoryModel.Builder withName(String name) {
            mName = name;
            return this;
        }

        public SmsCategoryModel.Builder withOldCatId(Long oldCatId) {
            mOldCatId = oldCatId;
            return this;
        }

        public SmsCategoryModel.Builder withParentId(Long parentId) {
            mParentId = parentId;
            return this;
        }

        public SmsCategoryModel.Builder withPivot(SmsPivotModel smsPivotModel) {
            mSmsPivotModel = smsPivotModel;
            return this;
        }

        public SmsCategoryModel.Builder withSlug(String slug) {
            mSlug = slug;
            return this;
        }

        public SmsCategoryModel.Builder withSortOrder(Long sortOrder) {
            mSortOrder = sortOrder;
            return this;
        }

        public SmsCategoryModel.Builder withStatus(Long status) {
            mStatus = status;
            return this;
        }

        public SmsCategoryModel.Builder withType(String type) {
            mType = type;
            return this;
        }

        public SmsCategoryModel.Builder withUpdatedAt(String updatedAt) {
            mUpdatedAt = updatedAt;
            return this;
        }

        public SmsCategoryModel build() {
            SmsCategoryModel smsCategoryModel = new SmsCategoryModel();
            smsCategoryModel.mCreatedAt = mCreatedAt;
            smsCategoryModel.mDeletedAt = mDeletedAt;
            smsCategoryModel.mIcon = mIcon;
            smsCategoryModel.mId = mId;
            smsCategoryModel.mMetaDescription = mMetaDescription;
            smsCategoryModel.mMetaTitle = mMetaTitle;
            smsCategoryModel.mName = mName;
            smsCategoryModel.mOldCatId = mOldCatId;
            smsCategoryModel.mParentId = mParentId;
            smsCategoryModel.mSmsPivotModel = mSmsPivotModel;
            smsCategoryModel.mSlug = mSlug;
            smsCategoryModel.mSortOrder = mSortOrder;
            smsCategoryModel.mStatus = mStatus;
            smsCategoryModel.mType = mType;
            smsCategoryModel.mUpdatedAt = mUpdatedAt;
            return smsCategoryModel;
        }

    }

}
