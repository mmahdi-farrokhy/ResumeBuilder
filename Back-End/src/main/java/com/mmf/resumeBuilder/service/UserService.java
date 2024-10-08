package com.mmf.resumeBuilder.service;

import com.mmf.resumeBuilder.entity.User;

public interface UserService {
    User saveUser(User user);

    boolean existsByEmail(String email);

    User findByEmail(String email);

    User updateUser(User user, String userEmail);
}
