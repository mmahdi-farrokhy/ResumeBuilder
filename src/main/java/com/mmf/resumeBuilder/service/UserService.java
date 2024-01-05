package com.mmf.resumeBuilder.service;

import com.mmf.resumeBuilder.model.User;

public interface UserService {
    void saveUser(User user);

    User fetchUser(Integer userId);
    boolean existsByEmail(String email);
    User findByEmail(String email);
}
