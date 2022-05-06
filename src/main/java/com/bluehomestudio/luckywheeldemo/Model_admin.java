package com.bluehomestudio.luckywheeldemo;

public class Model_admin {

        String image, uuid, title, notes;

        public Model_admin() {
        }

        public Model_admin(String image,
                           String uuid, String title, String notes) {
            this.image = image;
            this.uuid = uuid;
            this.title = title;
            this.notes = notes;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getUuid() {
            return uuid;
        }

        public void setUuid(String uuid) {
            this.uuid = uuid;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getNotes() {
            return notes;
        }

        public void setNotes(String notes) {
            this.notes = notes;
        }

}
