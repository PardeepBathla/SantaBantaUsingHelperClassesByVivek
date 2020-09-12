package com.app.santabanta.Modals;

import com.google.gson.annotations.SerializedName;

public class JokesPivotModel {

    @SerializedName("category_id")
    private Long mCategoryId;
    @SerializedName("joke_id")
    private Long mJokeId;

    public Long getCategoryId() {
        return mCategoryId;
    }

    public Long getJokeId() {
        return mJokeId;
    }

    public static class Builder {

        private Long mCategoryId;
        private Long mJokeId;

        public JokesPivotModel.Builder withCategoryId(Long categoryId) {
            mCategoryId = categoryId;
            return this;
        }

        public JokesPivotModel.Builder withJokeId(Long jokeId) {
            mJokeId = jokeId;
            return this;
        }

        public JokesPivotModel build() {
            JokesPivotModel jokesPivotModel = new JokesPivotModel();
            jokesPivotModel.mCategoryId = mCategoryId;
            jokesPivotModel.mJokeId = mJokeId;
            return jokesPivotModel;
        }

    }
}
