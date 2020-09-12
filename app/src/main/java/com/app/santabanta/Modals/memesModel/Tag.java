package com.app.santabanta.Modals.memesModel;

import com.google.gson.annotations.SerializedName;

public class Tag {

    @SerializedName("tag_id")
    private Integer tagId;
    @SerializedName("name")
    private String name;
    @SerializedName("slug")
    private String slug;
    @SerializedName("pivot")
    private Pivot_ pivot;


    public Integer getTagId() {
        return tagId;
    }


    public void setTagId(Integer tagId) {
        this.tagId = tagId;
    }

    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }


    public String getSlug() {
        return slug;
    }


    public void setSlug(String slug) {
        this.slug = slug;
    }


    public Pivot_ getPivot() {
        return pivot;
    }


    public void setPivot(Pivot_ pivot) {
        this.pivot = pivot;
    }


}
