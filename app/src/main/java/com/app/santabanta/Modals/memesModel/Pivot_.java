package com.app.santabanta.Modals.memesModel;

import com.google.gson.annotations.SerializedName;

public class Pivot_ {

    @SerializedName("meme_id")
    private Integer memeId;
    @SerializedName("tag_id")
    private Integer tagId;

    @SerializedName("meme_id")
    public Integer getMemeId() {
        return memeId;
    }

    @SerializedName("meme_id")
    public void setMemeId(Integer memeId) {
        this.memeId = memeId;
    }

    @SerializedName("tag_id")
    public Integer getTagId() {
        return tagId;
    }

    @SerializedName("tag_id")
    public void setTagId(Integer tagId) {
        this.tagId = tagId;
    }


}
