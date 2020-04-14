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

        String id;
        String uploader;
        String text;
        int likesCount;
        boolean userLiked;
        String likeId;
        Date created_at;
        Date updated_at;
        String imageUrl;

        public Post() {}

        public Post(String id, String uploader, String text, Date created_at) {
            // defaults
            this.likesCount = 100;
            this.userLiked = false;
            // user's values
            this.id = id;
            this.uploader = uploader;
            this.text = text;
            this.created_at = created_at;
            this.updated_at = created_at;
        }

        public Post(String id, String uploader, String text, Date created_at, Date updated_at, String imageUrl) {
            this.id = id;
            this.uploader = uploader;
            this.text = text;
            this.created_at = created_at;
            this.updated_at = updated_at;
            this.imageUrl = imageUrl;
        }

        public String getLikeId() {
            return likeId;
        }

        public void setLikeId(String likeId) {
            this.likeId = likeId;
        }

        public boolean isUserLiked() {
            return userLiked;
        }

        public void setUserLiked(boolean userLiked) {
            this.userLiked = userLiked;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
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
}