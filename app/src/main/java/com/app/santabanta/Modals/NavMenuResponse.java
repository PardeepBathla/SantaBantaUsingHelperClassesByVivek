package com.app.santabanta.Modals;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class NavMenuResponse {



    @SerializedName("data")
    @Expose
    private ArrayList<NavMenuDetail> data = null;

    public ArrayList<NavMenuDetail> getData() {
        return data;
    }

    public void setData(ArrayList<NavMenuDetail> data) {
        this.data = data;
    }

    public class NavMenuDetail {
        public  Boolean isExpanded;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("color")
        @Expose
        private String color;
        @SerializedName("icon")
        @Expose
        private String icon;

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        @SerializedName("info")
        @Expose
        private ArrayList<NavMenuDetailChildInfo> info = null;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }

        public ArrayList<NavMenuDetailChildInfo> getInfo() {
            return info;
        }

        public Boolean getExpanded() {
            return isExpanded;
        }

        public void setExpanded(Boolean expanded) {
            isExpanded = expanded;
        }

        public void setInfo(ArrayList<NavMenuDetailChildInfo> info) {
            this.info = info;
        }

        public class NavMenuDetailChildInfo {
            private Boolean isExpanded;
            @SerializedName("slug")
            @Expose
            private String slug;
            @SerializedName("id")
            @Expose
            private String id;
            @SerializedName("name")
            @Expose
            private String name;

            @SerializedName("color")
            @Expose
            private String color;
            @SerializedName("icon")
            @Expose
            private String icon;
            @SerializedName("info")
            @Expose
            private ArrayList<NavMenuDetailChildSubInfo> info = null;

            public String getSlug() {
                return slug;
            }

            public void setSlug(String slug) {
                this.slug = slug;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getIcon() {
                return icon;
            }

            public void setIcon(String icon) {
                this.icon = icon;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getColor() {
                return color;
            }

            public void setColor(String color) {
                this.color = color;
            }

            public ArrayList<NavMenuDetailChildSubInfo> getInfo() {
                return info;
            }

            public void setInfo(ArrayList<NavMenuDetailChildSubInfo> info) {
                this.info = info;
            }

            public Boolean getExpanded() {
                return isExpanded;
            }

            public void setExpanded(Boolean expanded) {
                isExpanded = expanded;
            }

            public class NavMenuDetailChildSubInfo {
                private  Boolean isExpanded;
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

                public String getType() {
                    return type;
                }

                public void setType(String type) {
                    this.type = type;
                }

                @SerializedName("type")
                @Expose
                private String type;
                @SerializedName("children")
                @Expose
                private ArrayList<NavMenuChildInfoChild> children = null;

                public Boolean getExpanded() {
                    return isExpanded;
                }

                public void setExpanded(Boolean expanded) {
                    isExpanded = expanded;
                }

                @SerializedName("parent_id")
                @Expose
                private Integer parentId;
                @SerializedName("subcat_id")
                @Expose
                private Object subcatId;
                @SerializedName("old_cat_id")
                @Expose
                private Integer oldCatId;

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

                public ArrayList<NavMenuChildInfoChild> getChildren() {
                    return children;
                }

                public void setChildren(ArrayList<NavMenuChildInfoChild> children) {
                    this.children = children;
                }

                public Integer getParentId() {
                    return parentId;
                }

                public void setParentId(Integer parentId) {
                    this.parentId = parentId;
                }

                public Object getSubcatId() {
                    return subcatId;
                }

                public void setSubcatId(Object subcatId) {
                    this.subcatId = subcatId;
                }

                public Integer getOldCatId() {
                    return oldCatId;
                }

                public void setOldCatId(Integer oldCatId) {
                    this.oldCatId = oldCatId;
                }

                public class NavMenuChildInfoChild {

                    @SerializedName("id")
                    @Expose
                    private Integer id;
                    @SerializedName("parent_id")
                    @Expose
                    private Integer parentId;
                    @SerializedName("subcat_id")
                    @Expose
                    private Integer subcatId;
                    @SerializedName("old_cat_id")
                    @Expose
                    private Integer oldCatId;
                    @SerializedName("name")
                    @Expose
                    private String name;
                    @SerializedName("slug")
                    @Expose
                    private String slug;
                    @SerializedName("updated_at")
                    @Expose
                    private String updatedAt;

                    public Integer getId() {
                        return id;
                    }

                    public void setId(Integer id) {
                        this.id = id;
                    }

                    public Integer getParentId() {
                        return parentId;
                    }

                    public void setParentId(Integer parentId) {
                        this.parentId = parentId;
                    }

                    public Integer getSubcatId() {
                        return subcatId;
                    }

                    public void setSubcatId(Integer subcatId) {
                        this.subcatId = subcatId;
                    }

                    public Integer getOldCatId() {
                        return oldCatId;
                    }

                    public void setOldCatId(Integer oldCatId) {
                        this.oldCatId = oldCatId;
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

                    public String getUpdatedAt() {
                        return updatedAt;
                    }

                    public void setUpdatedAt(String updatedAt) {
                        this.updatedAt = updatedAt;
                    }

                }


            }

        }

    }

}
