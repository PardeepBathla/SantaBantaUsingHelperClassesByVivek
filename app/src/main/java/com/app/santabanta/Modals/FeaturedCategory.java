package com.app.santabanta.Modals;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class FeaturedCategory implements Parcelable {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("hindi_label")
    @Expose
    private String hindiLabel;
    @SerializedName("icon")
    @Expose
    private String icon;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("slug")
    @Expose
    private String slug;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("children")
    @Expose
    private ArrayList<FeaturedSubCategory> children = new ArrayList<>();

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    protected FeaturedCategory(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readInt();
        }
        name = in.readString();
        hindiLabel = in.readString();
        icon = in.readString();
        updatedAt = in.readString();
        slug = in.readString();
        in.readList(children, FeaturedSubCategory.class.getClassLoader());
    }
    public static final Creator<FeaturedCategory> CREATOR = new Creator<FeaturedCategory>() {
        @Override
        public FeaturedCategory createFromParcel(Parcel in) {
            return new FeaturedCategory(in);
        }

        @Override
        public FeaturedCategory[] newArray(int size) {
            return new FeaturedCategory[size];
        }
    };

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHindiLabel() {
        return hindiLabel;
    }

    public void setHindiLabel(String hindiLabel) {
        this.hindiLabel = hindiLabel;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public ArrayList<FeaturedSubCategory> getChildren() {
        return children;
    }

    public void setChildren(ArrayList<FeaturedSubCategory> children) {
        this.children = children;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(id);
        }
        dest.writeString(name);
        dest.writeString(hindiLabel);
        dest.writeString(icon);
        dest.writeString(updatedAt);
        dest.writeString(slug);
        dest.writeList(children);
    }
}