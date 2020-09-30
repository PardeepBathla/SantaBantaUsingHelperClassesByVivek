package com.app.santabanta.Modals;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SearchResponse {

    @SerializedName("took")
    @Expose
    private Integer took;
    @SerializedName("timed_out")
    @Expose
    private Boolean timedOut;
    @SerializedName("_shards")
    @Expose
    private Shards shards;
    @SerializedName("hits")
    @Expose
    private Hits hits;

    public Integer getTook() {
        return took;
    }

    public void setTook(Integer took) {
        this.took = took;
    }

    public Boolean getTimedOut() {
        return timedOut;
    }

    public void setTimedOut(Boolean timedOut) {
        this.timedOut = timedOut;
    }

    public Shards getShards() {
        return shards;
    }

    public void setShards(Shards shards) {
        this.shards = shards;
    }

    public Hits getHits() {
        return hits;
    }

    public void setHits(Hits hits) {
        this.hits = hits;
    }


    public class Shards {

        @SerializedName("total")
        @Expose
        private Integer total;
        @SerializedName("successful")
        @Expose
        private Integer successful;
        @SerializedName("skipped")
        @Expose
        private Integer skipped;
        @SerializedName("failed")
        @Expose
        private Integer failed;

        public Integer getTotal() {
            return total;
        }

        public void setTotal(Integer total) {
            this.total = total;
        }

        public Integer getSuccessful() {
            return successful;
        }

        public void setSuccessful(Integer successful) {
            this.successful = successful;
        }

        public Integer getSkipped() {
            return skipped;
        }

        public void setSkipped(Integer skipped) {
            this.skipped = skipped;
        }

        public Integer getFailed() {
            return failed;
        }

        public void setFailed(Integer failed) {
            this.failed = failed;
        }

    }

    public class Hits {

        @SerializedName("total")
        @Expose
        private Total total;
        @SerializedName("max_score")
        @Expose
        private Double maxScore;
        @SerializedName("hits")
        @Expose
        private List<Hit> hits = null;

        public Total getTotal() {
            return total;
        }

        public void setTotal(Total total) {
            this.total = total;
        }

        public Double getMaxScore() {
            return maxScore;
        }

        public void setMaxScore(Double maxScore) {
            this.maxScore = maxScore;
        }

        public List<Hit> getHits() {
            return hits;
        }

        public void setHits(List<Hit> hits) {
            this.hits = hits;
        }

        public class Total {

            @SerializedName("value")
            @Expose
            private Integer value;
            @SerializedName("relation")
            @Expose
            private String relation;

            public Integer getValue() {
                return value;
            }

            public void setValue(Integer value) {
                this.value = value;
            }

            public String getRelation() {
                return relation;
            }

            public void setRelation(String relation) {
                this.relation = relation;
            }

        }

        public class Hit {

            @SerializedName("_index")
            @Expose
            private String index;
            @SerializedName("_type")
            @Expose
            private String type;
            @SerializedName("_id")
            @Expose
            private String id;
            @SerializedName("_score")
            @Expose
            private Double score;
            @SerializedName("_source")
            @Expose
            private Source source;

            public String getIndex() {
                return index;
            }

            public void setIndex(String index) {
                this.index = index;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public Double getScore() {
                return score;
            }

            public void setScore(Double score) {
                this.score = score;
            }

            public Source getSource() {
                return source;
            }

            public void setSource(Source source) {
                this.source = source;
            }

            public class Source {

                @SerializedName("id")
                @Expose
                private Integer id;
                @SerializedName("name")
                @Expose
                private String name;
                @SerializedName("slug")
                @Expose
                private String slug;
                @SerializedName("type")
                @Expose
                private String type;
                @SerializedName("viewing_prefrence")
                @Expose
                private String viewingPrefrence;
                @SerializedName("parent_name")
                @Expose
                private String parentName;

                public String getParentName() {
                    return parentName;
                }

                public void setParentName(String parentName) {
                    this.parentName = parentName;
                }

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

                public String getSlug() {
                    return slug;
                }

                public void setSlug(String slug) {
                    this.slug = slug;
                }

                public String getType() {
                    return type;
                }

                public void setType(String type) {
                    this.type = type;
                }

                public String getViewingPrefrence() {
                    return viewingPrefrence;
                }

                public void setViewingPrefrence(String viewingPrefrence) {
                    this.viewingPrefrence = viewingPrefrence;
                }

            }

        }

    }

}
