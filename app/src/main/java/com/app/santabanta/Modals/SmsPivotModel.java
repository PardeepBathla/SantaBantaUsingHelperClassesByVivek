package com.app.santabanta.Modals;

import com.google.gson.annotations.SerializedName;

public class SmsPivotModel {

    @SerializedName("category_id")
    private Long mCategoryId;
    @SerializedName("sms_id")
    private Long mSmsId;

    public Long getCategoryId() {
        return mCategoryId;
    }

    public Long getSmsId() {
        return mSmsId;
    }

    public static class Builder {

        private Long mCategoryId;
        private Long mSmsId;

        public SmsPivotModel.Builder withCategoryId(Long categoryId) {
            mCategoryId = categoryId;
            return this;
        }

        public SmsPivotModel.Builder withSmsId(Long smsId) {
            mSmsId = smsId;
            return this;
        }

        public SmsPivotModel build() {
            SmsPivotModel smsPivotModel = new SmsPivotModel();
            smsPivotModel.mCategoryId = mCategoryId;
            smsPivotModel.mSmsId = mSmsId;
            return smsPivotModel;
        }

    }

}
