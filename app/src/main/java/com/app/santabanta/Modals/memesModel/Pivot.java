package com.app.santabanta.Modals.memesModel;

import com.google.gson.annotations.SerializedName;

public class Pivot {

    @SerializedName("meme_id")
    private Integer memeId;
    @SerializedName("category_id")
    private Integer categoryId;

    @SerializedName("meme_id")
    public Integer getMemeId() {
        return memeId;
    }

    @SerializedName("meme_id")
    public void setMemeId(Integer memeId) {
        this.memeId = memeId;
    }

    @SerializedName("category_id")
    public Integer getCategoryId() {
        return categoryId;
    }

    @SerializedName("category_id")
    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }


}
