package com.infinity.aroundme.domain;

import java.io.Serializable;

public class User implements Serializable {

    private String displayName;
    private String username;
    private String imageUrl;

    public User() {
    }

    public User(String displayName, String username, String imageUrl) {
        this.displayName = displayName;
        this.username = username;
        this.imageUrl = imageUrl;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
