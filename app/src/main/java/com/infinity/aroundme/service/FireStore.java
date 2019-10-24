package com.infinity.aroundme.service;

import com.infinity.aroundme.domain.User;

public interface FireStore {

    void registerUser(final User user);

    void upsertUser(User user);
}
