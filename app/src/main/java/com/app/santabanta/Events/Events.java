package com.app.santabanta.Events;

public class Events {

    public static class SMSEvent{
        private String slug;
        private String category;

        public SMSEvent(String slug, String category) {
            this.slug = slug;
            this.category = category;
        }

        public String getSlug() {
            return slug;
        }

        public String getCategory() {
            return category;
        }
    }

    public static class JokesEvent{
        private String slug;

        public JokesEvent(String slug) {
            this.slug = slug;
        }

        public String getSlug() {
            return slug;
        }
    }

    public static class MemesEvent{
        private String slug;

        public MemesEvent(String slug) {
            this.slug = slug;
        }

        public String getSlug() {
            return slug;
        }
    }

}
