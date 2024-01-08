package com.mmf.resumeBuilder.service;

import com.mmf.resumeBuilder.model.AppUser;

public interface UserService {
    void saveUser(AppUser user);

    AppUser fetchUser(Integer userId);
    boolean existsByEmail(String email);
    AppUser findByEmail(String email);
}
