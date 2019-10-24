package com.infinity.aroundme.domain;

public class User {

    private String displayName;
    private String username;

    public User(String displayName, String username) {
        this.displayName = displayName;
        this.username = username;
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
}
