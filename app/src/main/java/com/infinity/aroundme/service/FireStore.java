package com.infinity.aroundme.service;

import com.infinity.aroundme.domain.User;

public interface FireStore {

    void registerUser(final User user);

    void upsertUser(User user);

    void listMessage(User sender, String receiver);

    void sendMessage(String sender, String receiver, String message);

    void displayUsers(User currentUser);
}
