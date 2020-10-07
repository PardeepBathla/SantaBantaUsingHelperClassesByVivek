package com.app.santabanta.Events;

import com.app.santabanta.Modals.HomeDetailList;

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

    public static class ToggleMemeFavourite{
        private boolean isChecked;
        private String itemId;
        private String favId;
        private String deviceId;

        public ToggleMemeFavourite(boolean isChecked, String itemId, String favId, String deviceId) {
            this.isChecked = isChecked;
            this.itemId = itemId;
            this.favId = favId;
            this.deviceId = deviceId;
        }

        public boolean isChecked() {
            return isChecked;
        }

        public String getItemId() {
            return itemId;
        }

        public String getFavId() {
            return favId;
        }

        public String getDeviceId() {
            return deviceId;
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
