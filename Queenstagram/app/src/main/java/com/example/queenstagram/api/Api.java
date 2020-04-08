package com.example.queenstagram.api;

import java.util.Date;

public class Api {

    /**
     *
     * {
     "id": 48,
     "uploader": "g82",
     "text": "하이하이",
     "likes": {
     "count": 0,
     "userliked": false
     },
     "image": {
     "url": "https://bucket-anstagram.s3.ap-northeast-2.amazonaws.com/uploads/post/image/48/20160514_071342.png"
     },
     "created_at": "2016-05-14T10:13:46.997Z",
     "updated_at": "2016-05-14T10:13:46.997Z"
     }
     */

    public static class Post {

        int id = 1;
        String uploader;
        String text;
        // Likes likes;
        int likesCount;
        Date created_at;
        Date updated_at;
        String imageUrl;

        public Post() {}

        public Post(String uploader, String text, String imageUrl, Date created_at) {
            // defaults
            this.id = id++;
            this.likesCount = 100;
            /*
            this.likes.count = 0;
            this.likes.userliked = false;
            */
            // user's values
            this.uploader = uploader;
            this.text = text;
            this.created_at = created_at;
            this.updated_at = created_at;
            this.imageUrl = imageUrl;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getUploader() {
            return uploader;
        }

        public void setUploader(String uploader) {
            this.uploader = uploader;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public int getLikesCount() {
            return likesCount;
        }

        public void setLikesCount(int likesCount) {
            this.likesCount = likesCount;
        }

        public Date getCreated_at() {
            return created_at;
        }

        public void setCreated_at(Date created_at) {
            this.created_at = created_at;
        }

        public Date getUpdated_at() {
            return updated_at;
        }

        public void setUpdated_at(Date updated_at) {
            this.updated_at = updated_at;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }
    }

    public static class Likes {
        int count;
        boolean userliked;

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public boolean isUserliked() {
            return userliked;
        }

        public void setUserliked(boolean userliked) {
            this.userliked = userliked;
        }
    }

}