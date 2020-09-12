package com.app.santabanta.Modals.memesModel;

import com.google.gson.annotations.SerializedName;

public class SeoTags {

    @SerializedName("meta_title")
    private String metaTitle;
    @SerializedName("meta_description")
    private String metaDescription;
    @SerializedName("meta_keywords")
    private String metaKeywords;

    @SerializedName("meta_title")
    public String getMetaTitle() {
        return metaTitle;
    }

    public void setMetaTitle(String metaTitle) {
        this.metaTitle = metaTitle;
    }


    public String getMetaDescription() {
        return metaDescription;
    }


    public void setMetaDescription(String metaDescription) {
        this.metaDescription = metaDescription;
    }


    public String getMetaKeywords() {
        return metaKeywords;
    }


    public void setMetaKeywords(String metaKeywords) {
        this.metaKeywords = metaKeywords;
    }


}
